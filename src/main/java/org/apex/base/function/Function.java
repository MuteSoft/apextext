/*
 * Function.java
 * Created on January 1, 2007, 5:01 PM
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
package org.apex.base.function;

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 * An abstract class represents a function. Generally the task is executed in a separate thread.
 * <p>
 * After initializing required data it executes the original task and then performs post
 * processing stuff.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class Function {

    /**
     * Creates a new instance of Function.
     */
    public Function() {
    }

    /**
     * Processes the function in a new thread.
     * <p>
     * It calls following methods sequentially:
     * <ul>
     *  <li> {@link #init(InputParams, OutputParams)} to initialize objects required
     *          for function execution.
     * <li> {@link #doExecute(InputParams, OutputParams)} to execute the function.
     * <li> {@link #postExecute(InputParams, OutputParams) } to release resources.
     * </ul>
     * @param in Input parameters. 
     * @param out Output parameters.
     */
    public final void process(final InputParams in, final OutputParams out) {
        new Thread() {

            @Override
            public void run() {
                init(in, out);
                doExecute(in, out);
                postExecute(in, out);                
            }
        }.start();
    }

    /**
     * Executes the function.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected abstract void doExecute(InputParams in, OutputParams out);

    /**
     * Releases resources used to process this function.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected abstract void postExecute(InputParams in, OutputParams out);

    /**
     * Returns the name of this function.
     * @return The name of this function.
     */
    protected abstract String getName();

    /**
     * Initializes objects required for executing the function.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected abstract void init(InputParams in, OutputParams out);

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Returns whether or not command processing is successful.
     * @return {@code true} if command processed successfully; otherwise returns {@code false}.
     */
    public boolean isSuccessful() {
        return true;
    }
}
