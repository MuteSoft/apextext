/*
 * DocumentQueue.java
 * Created on 1 July, 2007, 8:03 AM
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
package org.apex.base.data;

import java.util.Vector;

/**
 * A list of documents opened in editor; sorted by documents last access time.
 * <p>
 * When a document is opened in editor or created newly the entry goes to top of the queue. And when a document is
 * randomly chosen to view or edit the entry is deleted from current position,inserted at top and other document names
 * are adjusted in queue accordingly.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentQueue {

    /**
     * Items in the document queue.
     */
    private Vector<String> entries;
    /**
     * List of additional information of documents in the queue.
     * <p>
     * Documents absolute path is considered as additional information.
     */
    private Vector<String> additionalInfoList;
    /**
     * Maximum queue size.
     */
    private int maxQueueSize = 0;

    /**
     * Creates a new instance of {@code DocumentQueue} with a specified
     * initial size.
     * @param initialSize Initial size of document queue.
     */
    public DocumentQueue(int initialSize) {
        this(initialSize, Byte.MAX_VALUE);
    }

    /**
     * Creates a new instance of {@code DocumentQueue} with a specified
     * initial size.
     * @param initialSize Initial size of document queue.
     * @param maxQueueSize The maximum queue size.
     */
    public DocumentQueue(int initialSize, int maxQueueSize) {
        this.entries = new Vector<String>(initialSize);
        additionalInfoList = new Vector<String>(initialSize);
        this.maxQueueSize = maxQueueSize;
    }

    /**
     * Adds an entry to the queue at specified position.
     * @param index The position where to insert the element.
     * @param element The element to be inserted.
     * @param additionalInfo Additional information of element.
     */
    private synchronized void add(int index, String element,
            String additionalInfo) {
        if (this.entries.size() + 1 > this.maxQueueSize) {
            remove(this.additionalInfoList.get(this.entries.size() - 1));
        }
        this.entries.add(index, element);
        this.additionalInfoList.add(index, additionalInfo);
    }

    /**
     * Removes an entry from queue having additional information equals to given
     * string.
     * @param additionalInfo The additional information of element.
     */
    public synchronized void remove(String additionalInfo) {
        removeEntry(additionalInfo);
    }

    /**
     * Clears the document queue.
     */
    public synchronized void removeAll() {
        this.additionalInfoList.clear();
        this.entries.clear();
    }

    /**
     * Adds an entry to queue.
     * <p>
     * It verifies whether given element already exists in queue. If exists, then it moves the element to top
     * of the queue and if does not exist, then inserts the element at the top of the queue.
     * @param element The element name.
     * @param additionalInfo Additional information of element.
     */
    public synchronized void add(String element, String additionalInfo) {
        if (exists(additionalInfo)) {
            moveToTop(element, additionalInfo);
        } else {
            insertAtTop(element, additionalInfo);
        }
    }

    /**
     * Replaces an element in queue.  It removes the old element from queue and inserts
     * the new element at top of queue.
     * @param oldadditionalInfo Old additional information of element.
     * @param element New element.
     * @param additionalInfo New additional information of element.
     */
    public synchronized void replace(String oldadditionalInfo, String element,
            String additionalInfo) {
        remove(oldadditionalInfo);
        add(element, additionalInfo);
    }

    /**
     * Determines whether or not an element exists in document queue.
     * @param additionalInfo Absolue path of document.
     * @return {@code true} if element having given additional information exists; otherwise
     *                returns {@code false}.
     */
    private synchronized boolean exists(String additionalInfo) {
        boolean exists = true;
        if (!additionalInfoList.contains(additionalInfo)) {
            return false;
        }
        return exists;
    }

    /**
     * Returns additional information at a specified index.
     * @param index The index in queue.
     * @return Additional information.
     */
    public synchronized String getAdditionalInfoAt(int index) {
        return this.additionalInfoList.get(index);
    }

    /**
     * Returns the list of elements this queue holds.
     * @return A list of entries.
     */
    public synchronized Vector<String> getEntries() {
        return this.entries;
    }

    /**
     * Moves an element to top of queue.
     * <p>
     * First it removes the given element from its current position and then 
     * inserts at top postion of queue..
     * @param element The element.
     * @param additionalInfo The additional information of element.
     */
    private void moveToTop(String element, String additionalInfo) {
        removeEntry(additionalInfo);
        insertAtTop(element, additionalInfo);
    }

    /**
     * Removes an element from queue having additional information equals
     * to given string.
     * @param additionalInfo The additional information of element.
     */
    private synchronized void removeEntry(String additionalInfo) {
        int indexOfElement = additionalInfoList.indexOf(additionalInfo);
        if (indexOfElement >= 0) {
            additionalInfoList.removeElementAt(indexOfElement);
            this.entries.removeElementAt(indexOfElement);
        }
    }

    /**
     * Inserts an element at top of the queue.
     * @param element The element to be added.
     * @param additionalInfo Additional information of element.
     */
    private void insertAtTop(String element, String additionalInfo) {
        this.add(0, element, additionalInfo);
    }
}
