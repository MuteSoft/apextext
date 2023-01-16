/*
 * Task.java
 * Created on 23 June, 2007, 1:43 AM
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
package org.apex.base.common;

import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 * An interface for unit of work.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface Task {

    /**
     * Performs an initial part of unit task. Required parameters are retrieved from {@code in}, executes
     * the task and puts output parameters to {@code out}.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    void preProcess(InputParams in, OutputParams out);

    /**
     * Performs the final part of unit task. Required parameters are retrieved from {@code in}, executes
     * the task and puts output parameters to {@code out}.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    void postProcess(InputParams in, OutputParams out);
}
