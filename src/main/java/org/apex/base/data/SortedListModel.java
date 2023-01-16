/*
 * SortedListModel.java
 *
 * Copyright 2006 Sun Microsystems, Inc. ALL RIGHTS RESERVED Use of 
 * this software is authorized pursuant to the terms of the license 
 * found at http://developers.sun.com/berkeley_license.html . 
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
 *
 */
package org.apex.base.data;

import org.apex.base.component.ApexLabel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.text.Collator;

/**
 * SortedListModel decorates an unsorted ListModel to provide
 * a sorted model. You can create a SortedListModel from models you
 * already have. Place the SortedListModel into a JList, for example, to provide
 * a sorted view of your underlying model.
 * @author John O'Conner
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SortedListModel extends AbstractListModel {

    /**
     * Creates a new instance of {@code SortedListModel}.
     */
    private SortedListModel() {
    }

    /**
     * Create a SortedListModel from an existing model
     * using a default text comparator for the default Locale. Sort
     * in ascending order.
     * 
     * @param model The underlying unsorted model.
     */
    public SortedListModel(ListModel model) {
        this(model, SortOrder.ASCENDING, null);
    }

    /**
     * Create a SortedListModel from an existing model
     * using a specific comparator and sort order. Use
     * a default text comparator.
     * @param model The unsorted list model.
     * @param sortOrder The sort order should be used.
     */
    public SortedListModel(ListModel model, SortOrder sortOrder) {
        this(model, sortOrder, null);
    }

    /**
     * Create a SortedListModel from an existing model. Sort the model
     * in the specified sort order using the given comparator.
     * @param model The unsorted list model.
     * @param sortOrder The sort order should be used.
     * @param comp The comparator to be used.
     */
    public SortedListModel(ListModel model, SortOrder sortOrder,
            Comparator comp) {
        unsortedModel = model;
        unsortedModel.addListDataListener(new ListDataListener() {

            public void intervalAdded(ListDataEvent e) {
                unsortedIntervalAdded(e);
            }

            public void intervalRemoved(ListDataEvent e) {
                unsortedIntervalRemoved(e);
            }

            public void contentsChanged(ListDataEvent e) {
                unsortedContentsChanged(e);
            }
        });
        this.sortOrder = sortOrder;
        if (comp != null) {
            comparator = comp;
        } else {
            comparator = new MultilingualComparator();
        }

        // get base model info
        int size = model.getSize();
        sortedModel = new ArrayList<SortedListEntry>(size);
        for (int x = 0; x < size; ++x) {
            SortedListEntry entry = new SortedListEntry(x);
            int insertionPoint = findInsertionPoint(entry);
            sortedModel.add(insertionPoint, entry);
        }
    }

    /**
     * Returns the length of the list.
     * @return the length of the list
     */
    public int size() {
        return this.unsortedModel.getSize();
    }

    /**
     * Retrieve the sorted entry from the original model.
     * @param index index of an entry in the sorted model.
     * @return element In the original model to which our entry points.
     * @throws java.lang.IndexOutOfBoundsException 
     */
    public Object getElementAt(int index) throws IndexOutOfBoundsException {
        int modelIndex = toUnsortedModelIndex(index);
        ApexLabel element = (ApexLabel) unsortedModel.getElementAt(modelIndex);
        return element;
    }

    /**
     * Retrieve the size of the underlying model.
     * @return size of the model.
     */
    public int getSize() {
        int size = sortedModel.size();
        return size;
    }

    /**
     * Convert sorted model index to an unsorted model index.
     * @return modelIndex an index in the unsorted model
     * @param index an index in the sorted model
     * @throws java.lang.IndexOutOfBoundsException 
     */
    public int toUnsortedModelIndex(int index) throws IndexOutOfBoundsException {
        int modelIndex = -1;
        SortedListEntry entry = sortedModel.get(index);
        modelIndex = entry.getIndex();
        return modelIndex;

    }

    /**
     * Convert an array of sorted model indices to their unsorted model indices. Sort
     * the resulting set of indices.
     *@param sortedSelectedIndices Indices of selected elements in the sorted model
     *              or sorted view.
     *@return unsortedSelectedIndices Selected indices in the unsorted model.
     */
    public int[] toUnsortedModelIndices(int[] sortedSelectedIndices) {
        int[] unsortedSelectedIndices = new int[sortedSelectedIndices.length];
        int x = 0;
        for (int sortedIndex : sortedSelectedIndices) {
            unsortedSelectedIndices[x++] = toUnsortedModelIndex(sortedIndex);
        }
        // sort the array of indices before returning
        Arrays.sort(unsortedSelectedIndices);
        return unsortedSelectedIndices;

    }

    /**
     * Convert an unsorted model index to a sorted model index.
     * @param unsortedIndex An element index in the unsorted model.
     * @return sortedIndex An element index in the sorted model.
     */
    public int toSortedModelIndex(int unsortedIndex) {
        int sortedIndex = -1;
        int x = -1;
        for (SortedListEntry entry : sortedModel) {
            ++x;
            if (entry.getIndex() == unsortedIndex) {
                sortedIndex = x;
                break;
            }
        }
        return sortedIndex;
    }

    /**
     * Convert an array of unsorted model selection indices to 
     * indices in the sorted model. Sort the model indices from 
     * low to high to duplicate JList's getSelectedIndices method.
     * @param unsortedModelIndices Indices in the unsorted model.
     * @return An array of selected indices in the sorted model.
     */
    public int[] toSortedModelIndices(int[] unsortedModelIndices) {
        int[] sortedModelIndices = new int[unsortedModelIndices.length];
        int x = 0;
        for (int unsortedIndex : unsortedModelIndices) {
            sortedModelIndices[x++] = toSortedModelIndex(unsortedIndex);
        }
        Arrays.sort(sortedModelIndices);
        return sortedModelIndices;
    }

    /**
     * Resets data of sorted model.
     */
    private void resetModelData() {
        int index = 0;
        for (SortedListEntry entry : sortedModel) {
            entry.setIndex(index++);
        }
    }

    /**
     * Sets the comparator.
     * @param comp The comparator.
     */
    @SuppressWarnings("unchecked")
    public void setComparator(Comparator comp) {
        if (comp == null) {
            sortOrder = SortOrder.UNORDERED;
            comparator = new MultilingualComparator();
            resetModelData();
        } else {
            comparator = comp;
            Collections.sort(sortedModel);
        }
        fireContentsChanged(ListDataEvent.CONTENTS_CHANGED, 0, sortedModel.size()
                - 1);
    }

    /**
     * Change the sort order of the model at runtime.
     * @param sortOrder The sort order to be used.
     */
    @SuppressWarnings("unchecked")
    public void setSortOrder(SortOrder sortOrder) {
        if (this.sortOrder != sortOrder) {
            this.sortOrder = sortOrder;
            if (sortOrder == SortOrder.UNORDERED) {
                resetModelData();
            } else {
                Collections.sort(sortedModel);
            }
            fireContentsChanged(ListDataEvent.CONTENTS_CHANGED, 0, sortedModel.
                    size()
                    - 1);
        }
    }

    /**
     * 
     * Update the sorted model whenever new items
     * are added to the original/decorated model.
     * @param e The list data event.
     */
    private void unsortedIntervalAdded(ListDataEvent e) {
        int begin = e.getIndex0();
        int end = e.getIndex1();
        int nElementsAdded = end - begin + 1;

        /* Items in the decorated model have shifted in flight.
         * Increment our model pointers into the decorated model.
         * We must increment indices that intersect with the insertion
         * point in the decorated model.
         */
        for (SortedListEntry entry : sortedModel) {
            int index = entry.getIndex();
            // if our model points to a model index >= to where
            // new model entries are added, we must bump up their index
            if (index >= begin) {
                entry.setIndex(index + nElementsAdded);
            }
        }

        // now add the new items from the decorated model
        for (int x = begin; x <= end; ++x) {
            SortedListEntry newEntry = new SortedListEntry(x);
            int insertionPoint = findInsertionPoint(newEntry);
            sortedModel.add(insertionPoint, newEntry);
            fireIntervalAdded(ListDataEvent.INTERVAL_ADDED, insertionPoint,
                    insertionPoint);
        }
    }

    /**
     * Update this model when items are removed from the original/decorated
     * model. Also, let our listeners know that we've removed items.
     * @param e The list data event.
     */
    private void unsortedIntervalRemoved(ListDataEvent e) {
        int begin = e.getIndex0();
        int end = e.getIndex1();
        int nElementsRemoved = end - begin + 1;

        /*
         * Move from end to beginning of our sorted model, updating
         * element indices into the decorated model or removing
         * elements as necessary
         */
        int sortedSize = sortedModel.size();
        boolean[] bElementRemoved = new boolean[sortedSize];
        for (int x = sortedSize - 1; x >= 0; --x) {
            SortedListEntry entry = sortedModel.get(x);
            int index = entry.getIndex();
            if (index > end) {
                entry.setIndex(index - nElementsRemoved);
            } else if (index >= begin) {
                sortedModel.remove(x);
                bElementRemoved[x] = true;
            }
        }
        /*
         * Let listeners know that we've removed items.
         */
        for (int x = bElementRemoved.length - 1; x >= 0; --x) {
            if (bElementRemoved[x]) {
                fireIntervalRemoved(ListDataEvent.INTERVAL_REMOVED, x, x);
            }
        }

    }

    /**
     * Resort the sorted model if there are changes in the original 
     * unsorted model. Let any listeners know about changes. Since I don't
     * track specific changes, sort everywhere and redisplay all items.
     * @param e The list data event.
     */
    @SuppressWarnings("unchecked")
    private void unsortedContentsChanged(ListDataEvent e) {
        Collections.sort(sortedModel);
        fireContentsChanged(ListDataEvent.CONTENTS_CHANGED, 0, sortedModel.size()
                - 1);
    }

    /**
     * Internal helper method to find the insertion point for a new 
     * entry in the sorted model.
     * @param entry The sorted list entry.
     * @return The insertion point.
     */
    @SuppressWarnings("unchecked")
    private int findInsertionPoint(SortedListEntry entry) {
        int insertionPoint = sortedModel.size();
        if (sortOrder != SortOrder.UNORDERED) {
            insertionPoint = Collections.binarySearch((List) sortedModel, entry);
            if (insertionPoint < 0) {
                insertionPoint = -(insertionPoint + 1);
            }
        }
        return insertionPoint;
    }
    /**
     * The sorted list model.
     */
    private List<SortedListEntry> sortedModel;
    /**
     * The unsorted list model.
     */
    private ListModel unsortedModel;
    /**
     * The comparator to be used.
     */
    private Comparator comparator;
    /**
     * The sort order to be used.
     */
    private SortOrder sortOrder;

    /**
     * Sort orders.
     */
    public enum SortOrder {

        /**
         * Unordered.
         */
        UNORDERED,
        /**
         * Ascending order.
         */
        ASCENDING,
        /**
         * Descending order.
         */
        DESCENDING;
    }

    /**
     * A class to compare multilingual strings. In release 1.3, it works well
     * only for english strings. And can be enhanced later to work with
     * all UTF-8 based strings.
     */
    class MultilingualComparator implements Comparator<String> {
        /**
         * Use the collator in future releases.
         */
        private Collator collator = null;

        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * A sorted list entry.
     */
    class SortedListEntry implements Comparable {

        /**
         * Creates a sorted list entry.
         */
        private SortedListEntry() {
        }

        /**
         * Creates a sorted list entry and assign given index.
         * @param index The index.
         */
        public SortedListEntry(int index) {
            this.index = index;
        }

        /**
         * Returns the index of this entry.
         * @return The index.
         * @see #setIndex(int) 
         */
        public int getIndex() {
            return index;
        }

        /**
         * Sets the index of this entry.
         * @param index The index.
         * @see #getIndex() 
         */
        public void setIndex(int index) {
            this.index = index;
        }

        /**
         * Compares this entry with a given entry.
         * @param o The object with which to compare.
         * @return a negative integer, zero, or a positive integer as this object
         *		is less than, equal to, or greater than the specified object.
         */
        public int compareTo(Object o) {
            // retrieve the element that this entry points to
            // in the original model
            Object thisElement = unsortedModel.getElementAt(index);
            SortedListEntry thatEntry = (SortedListEntry) o;
            // retrieve the element that thatEntry points to in the original
            // model
            Object thatElement =
                    unsortedModel.getElementAt(thatEntry.getIndex());
            if (!(o instanceof String)) {
                thisElement = thisElement.toString();
                thatElement = thatElement.toString();
            }
            // compare the base model's elements using the provided comparator
            @SuppressWarnings("unchecked")
            int comparison = comparator.compare(thisElement, thatElement);
            // convert to descending order as necessary
            if (sortOrder == SortOrder.DESCENDING) {
                comparison = -comparison;
            }
            return comparison;
        }
        /**
         * The index of an entry.
         */
        private int index;
    }
}
