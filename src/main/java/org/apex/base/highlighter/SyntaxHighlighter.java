/*
 * SyntaxHighlighter.java
 * 
 * This file is part of the programmer editor demo
 * Copyright (C) 2001-2005 Stephen Ostermiller
 * http://ostermiller.org/contact.pl?regarding=Syntax+Highlighting
 *
 * Copyright (C) 2008 Mrityunjoy Saha
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.apex.base.highlighter;

import org.apex.base.data.HighlightedDocument;
import org.apex.base.highlighter.lexer.Lexer;
import org.apex.base.highlighter.lexer.Token;
import org.apex.base.highlighter.style.DocumentStyle;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;

/**
 * Run the Syntax Highlighting as a separate thread. Things that need to be
 * colored are messaged to the thread and put in a list.
 * @author Stephen Ostermiller
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SyntaxHighlighter extends Thread {

    /**
     * A simple wrapper representing something that needs to be colored. Placed
     * into an object so that it can be stored in a Vector.
     */
    private static class RecolorEvent {

        /**
         * Current position in the document.
         */
        public int position;
        /**
         * The offset.
         */
        public int adjustment;

        /**
         * Constructs a new instance of {@code RecolorEvent}.
         * @param position Current position in the document.
         * @param adjustment The offset.
         */
        public RecolorEvent(int position, int adjustment) {
            this.position = position;
            this.adjustment = adjustment;
        }
    }
    /**
     * Stores the document we are coloring. We use a WeakReference
     * so that the document is eligible for garbage collection when
     * it is no longer being used. At that point, this thread will
     * shut down itself.
     */
    private WeakReference document;
    /**
     * Keep a list of places in the file that it is safe to restart the
     * highlighting. This happens whenever the lexer reports that it has
     * returned to its initial state. Since this list needs to be sorted and
     * we need to be able to retrieve ranges from it, it is stored in a
     * balanced tree.
     */
    @SuppressWarnings("unchecked")
    private TreeSet iniPositions = new TreeSet(DocPositionComparator.instance);
    /**
     * As we go through and remove invalid positions we will also be finding
     * new valid positions. Since the position list cannot be deleted from
     * and written to at the same time, we will keep a list of the new
     * positions and simply add it to the list of positions once all the old
     * positions have been removed.
     */
    private HashSet newPositions = new HashSet();
    /**
     * Vector that stores the communication between the two threads.
     */
    private volatile LinkedList events = new LinkedList();
    /**
     * When accessing the linked list, we need to create a critical section.
     * we will synchronize on this object to ensure that we don't get unsafe
     * thread behavior.
     */
    private Object eventsLock = new Object();
    /**
     * The amount of change that has occurred before the place in the
     * document that we are currently highlighting (lastPosition).
     */
    private volatile int change = 0;
    /**
     * The last position colored.
     */
    private volatile int lastPosition = -1;

    /**
     * Creates the coloring thread for the given document.
     *
     * @param document The document to be colored.
     */
    @SuppressWarnings("unchecked")
    public SyntaxHighlighter(HighlightedDocument document) {
        this.document = new WeakReference(document);
    }

    /**
     * Tell the Syntax Highlighting thread to take another look at this
     * section of the document. It will process this as a FIFO. This method
     * should be done inside a docLock.
     * @param position Current position in the document.
     * @param adjustment The offset.
     */
    @SuppressWarnings("unchecked")
    public void color(int position, int adjustment) {
        // figure out if this adjustment effects the current run.
        // if it does, then adjust the place in the document
        // that gets highlighted.
        if (position < lastPosition) {
            if (lastPosition < position - adjustment) {
                change -= lastPosition - position;
            } else {
                change += adjustment;
            }
        }
        synchronized (eventsLock) {
            if (!events.isEmpty()) {
                // check whether to coalesce with current last element
                RecolorEvent curLast = (RecolorEvent) events.getLast();
                if (adjustment < 0 && curLast.adjustment < 0) {
                    // both are removals
                    if (position == curLast.position) {
                        curLast.adjustment += adjustment;
                        return;
                    }
                } else if (adjustment >= 0 && curLast.adjustment >= 0) {
                    // both are insertions
                    if (position == curLast.position + curLast.adjustment) {
                        curLast.adjustment += adjustment;
                        return;
                    } else if (curLast.position == position + adjustment) {
                        curLast.position = position;
                        curLast.adjustment += adjustment;
                        return;
                    }
                }
            }
            events.add(new RecolorEvent(position, adjustment));
            eventsLock.notifyAll();
        }
    }

    /**
     * The colorer runs forever and may sleep for long periods of time. It
     * should be interrupted every time there is something for it to do.
     */
    @Override
    public void run() {
        while (document.get() != null) {
            try {
                RecolorEvent re;
                synchronized (eventsLock) {
                    // get the next event to process - stalling until the
                    // event becomes available
                    while (events.isEmpty() && document.get() != null) {
                        // stop waiting after a second in case document
                        // has been cleared.
                        eventsLock.wait(1000);
                    }
                    re = (RecolorEvent) events.removeFirst();
                }
                processEvent(re.position, re.adjustment);
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Processes the color event.
     * @param position Current position in the document.
     * @param adjustment The offset.
     */
    @SuppressWarnings("unchecked")
    private void processEvent(int position, int adjustment) {
        final HighlightedDocument doc =
                (HighlightedDocument) document.get();
        if (doc == null) {
            return;
        }
        Lexer syntaxLexer = doc.getSyntaxLexer();
        if (syntaxLexer == null) {
            return;
        }

        // Slurp everything up into local variables in case another
        // thread changes them during coloring process
        AttributeSet globalStyle = doc.getGlobalStyle();
        DocumentReader documentReader = doc.getDocumentReader();
        Object docLock = doc.getDocumentLock();
        final DocumentStyle syntaxStyle = doc.getSyntaxStyle();

        if (globalStyle != null) {
            int start = Math.min(position, position + adjustment);
            int stop = Math.max(position, position + adjustment);
            synchronized (docLock) {
                doc.setCharacterAttributes(start, stop - start,
                        globalStyle, true);
            }
            return;
        }

        SortedSet workingSet;
        Iterator workingIt;
        DocPosition startRequest = new DocPosition(position);
        DocPosition endRequest =
                new DocPosition(position + Math.abs(adjustment));
        DocPosition dp;
        DocPosition dpStart = null;
        DocPosition dpEnd = null;

        // find the starting position. We must start at least one
        // token before the current position
        try {
            // all the good positions before
            workingSet = iniPositions.headSet(startRequest);
            // the last of the stuff before
            dpStart = (DocPosition) workingSet.last();
        } catch (NoSuchElementException x) {
            // if there were no good positions before the requested
            // start,
            // we can always start at the very beginning.
            dpStart = new DocPosition(0);
        }

        // if stuff was removed, take any removed positions off the
        // list.
        if (adjustment < 0) {
            workingSet = iniPositions.subSet(startRequest,
                    endRequest);
            workingIt = workingSet.iterator();
            while (workingIt.hasNext()) {
                workingIt.next();
                workingIt.remove();
            }
        }

        // adjust the positions of everything after the
        // insertion/removal.
        workingSet = iniPositions.tailSet(startRequest);
        workingIt = workingSet.iterator();
        while (workingIt.hasNext()) {
            ((DocPosition) workingIt.next()).adjustPosition(adjustment);
        }

        // now go through and highlight as much as needed
        workingSet = iniPositions.tailSet(dpStart);
        workingIt = workingSet.iterator();
        dp = null;
        if (workingIt.hasNext()) {
            dp = (DocPosition) workingIt.next();
        }
        try {
            Token t;
            boolean done = false;
            dpEnd = dpStart;
            synchronized (docLock) {
                // we are playing some games with the lexer for
                // efficiency.
                // we could just create a new lexer each time here,
                // but instead,
                // we will just reset it so that it thinks it is
                // starting at the
                // beginning of the document but reporting a funny
                // start position.
                // Reseting the lexer causes the close() method on
                // the reader
                // to be called but because the close() method has
                // no effect on the
                // DocumentReader, we can do this.
                syntaxLexer.reset(documentReader, 0, dpStart.getPosition(), 0);
                // After the lexer has been set up, scroll the
                // reader so that it
                // is in the correct spot as well.
                documentReader.seek(dpStart.getPosition());
                // we will highlight tokens until we reach a good
                // stopping place.
                // the first obvious stopping place is the end of
                // the document.
                // the lexer will return null at the end of the
                // document and wee
                // need to stop there.
                t = syntaxLexer.getNextToken();
            }
            newPositions.add(dpStart);
            while (!done && t != null) {
                // this is the actual command that colors the stuff.
                // Color stuff with the description of the styles
                // stored in tokenStyles.
                if (t.getCharEnd() <= doc.getLength()) {
                    final Token t1 = t;
                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            doc.setCharacterAttributes(t1.getCharBegin() +
                                    change,
                                    t1.getCharEnd() - t1.getCharBegin(),
                                    syntaxStyle.getStyle(t1.getDescription()),
                                    true);
                        // record the position of the last bit of
                        // text that we colored                            
                        }
                    });
                    dpEnd = new DocPosition(t.getCharEnd());
                }
                lastPosition = (t.getCharEnd() + change);
                // The other more complicated reason for doing no
                // more highlighting
                // is that all the colors are the same from here on
                // out anyway.
                // We can detect this by seeing if the place that
                // the lexer returned
                // to the initial state last time we highlighted is
                // the same as the
                // place that returned to the initial state this
                // time.
                // As long as that place is after the last changed
                // text, everything
                // from there on is fine already.
                if (t.getState() == Token.INITIAL_STATE) {
                    // look at all the positions from last time that
                    // are less than or
                    // equal to the current position
                    while (dp != null && dp.getPosition() <= t.getCharEnd()) {
                        if (dp.getPosition() == t.getCharEnd() &&
                                dp.getPosition() >= endRequest.getPosition()) {
                            // we have found a state that is the
                            // same
                            done = true;
                            dp = null;
                        } else if (workingIt.hasNext()) {
                            // didn't find it, try again.
                            dp = (DocPosition) workingIt.next();
                        } else {
                            // didn't find it, and there is no more
                            // info from last
                            // time. This means that we will just
                            // continue
                            // until the end of the document.
                            dp = null;
                        }
                    }
                    // so that we can do this check next time,
                    // record all the
                    // initial states from this time.
                    newPositions.add(dpEnd);
                }
                synchronized (docLock) {
                    t = syntaxLexer.getNextToken();
                }
            }

            // remove all the old initial positions from the place
            // where
            // we started doing the highlighting right up through
            // the last
            // bit of text we touched.
            workingIt = iniPositions.subSet(dpStart, dpEnd).iterator();
            while (workingIt.hasNext()) {
                workingIt.next();
                workingIt.remove();
            }

            // Remove all the positions that are after the end of
            // the file.:
            workingIt = iniPositions.tailSet(
                    new DocPosition(doc.getLength())).iterator();
            while (workingIt.hasNext()) {
                workingIt.next();
                workingIt.remove();
            }

            // and put the new initial positions that we have found
            // on the list.
            iniPositions.addAll(newPositions);
            newPositions.clear();
        } catch (IOException x) {
        }
        synchronized (docLock) {
            lastPosition = -1;
            change = 0;
        }
    }
}
