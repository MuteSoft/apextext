/*
 * TaskProgressIndicator.java 
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
package org.apex.base.component;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import org.apex.base.core.EditorBase;
import org.apex.base.logging.Logger;
import org.apex.base.ui.StatusBar;

/**
 * A progress indicator. This component is invoked to show the progress
 * and also to prevent user input when a long running task is executed. With
 * multiple bars it forms a cycle and animates the same.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class TaskProgressIndicator {

    private static final TaskProgressIndicator taskProgressIndicator = new TaskProgressIndicator();
    private boolean inProgress;
    private boolean paused;

    private static JProgressBar progressBar;

    /**
     * Creates a new instance of {@code TaskProgressIndicator} using given progress
     * message.
     * @param text The progress message.
     */
    private TaskProgressIndicator() {
    }

    public static TaskProgressIndicator getSharedInstance() {
        if (progressBar == null) {
            StatusBar statusBar = EditorBase.getContext().getEditorComponents().
                    getStatusBar();
            if (statusBar == null) {
                return null;
            }
            progressBar = statusBar.getProgressBar();
        }

        return taskProgressIndicator;
    }

    public void reset() {
        setProgress(0);
        this.inProgress = false;

    }

    public void start() {
        setProgress(5);
        this.inProgress = true;
    }

    public void finish() {
        setProgress(100);
        this.inProgress = false;
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.logWarning("Unable to finish the task indicator", ex);
        }
        reset();
    }

    public void pause() {
        this.paused = true;
        // Find a reason to pause first.
    }

    public void resume() {
        this.paused = false;
        // Find a reason to pause first.
    }

    public void setProgress(int progress) {
        updateProgress(progress);
    }

    public boolean isInProgress() {
        return this.inProgress;
    }

    public void updateProgress(final int progress) {
        if (SwingUtilities.isEventDispatchThread()) {
            progressBar.setValue(progress);
        } else {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    progressBar.setValue(progress);
                });
            } catch (InterruptedException | InvocationTargetException ex) {
                Logger.logWarning("Unable to update progress", ex);
            }
        }
    }
}
