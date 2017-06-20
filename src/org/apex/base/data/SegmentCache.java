/*
 * SegmentCache.java 
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

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.Segment;

/**
 * SegmentCache caches <code>Segment</code>s to avoid continually creating
 * and destroying of <code>Segment</code>s. A common use of this class would
 * be:
 * <pre>
 *   Segment segment = segmentCache.getSegment();
 *   // do something with segment
 *   ...
 *   segmentCache.releaseSegment(segment);
 * </pre>
 *
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SegmentCache {

    /**
     * A global cache.
     */
    private static SegmentCache sharedCache = new SegmentCache();
    /**
     * A list of the currently unused Segments.
     */
    private List segments;

    /**
     * Returns the shared SegmentCache.
     * @return The shared SegmentCache instance.
     */
    public static SegmentCache getSharedInstance() {
        return sharedCache;
    }

    /**
     * A convenience method to get a Segment from the shared
     * <code>SegmentCache</code>.
     * @return A segment.
     */
    public static Segment getSharedSegment() {
        return getSharedInstance().getSegment();
    }

    /**
     * A convenience method to release a Segment to the shared
     * <code>SegmentCache</code>.
     * @param segment A segment.
     */
    public static void releaseSharedSegment(Segment segment) {
        getSharedInstance().releaseSegment(segment);
    }

    /**
     * Creates and returns a SegmentCache.
     */
    public SegmentCache() {
        segments = new ArrayList(11);
    }

    /**
     * Returns a <code>Segment</code>. When done, the <code>Segment</code>
     * should be recycled by invoking <code>releaseSegment</code>.
     * @return A segment.
     */
    public Segment getSegment() {
        synchronized (this) {
            int size = segments.size();

            if (size > 0) {
                return (Segment) segments.remove(size - 1);
            }
        }
        return new CachedSegment();
    }

    /**
     * Releases a Segment. You should not use a Segment after you release it,
     * and you should NEVER release the same Segment more than once, eg:
     * <pre>
     *   segmentCache.releaseSegment(segment);
     *   segmentCache.releaseSegment(segment);
     * </pre>
     * Will likely result in very bad things happening!
     * @param segment A segment.
     */
    @SuppressWarnings("unchecked")
    public void releaseSegment(Segment segment) {
        if (segment instanceof CachedSegment) {
            synchronized (this) {
                segment.array = null;
                segment.count = 0;
                segments.add(segment);
            }
        }
    }

    /**
     * CachedSegment is used as a tagging interface to determine if
     * a Segment can successfully be shared.
     */
    private static class CachedSegment extends Segment {
    }
}
