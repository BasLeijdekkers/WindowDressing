/*
 * Copyright 2006-2011 Bas Leijdekkers, Maarten Hazewinkel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.intellij.window;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@State(name="WindowDressing", roamingType = RoamingType.DISABLED,
        storages = {@Storage(id="other", file = "$WORKSPACE_FILE$")})
public class WindowDressing extends AbstractProjectComponent implements
        PersistentStateComponent<WindowDressing.WindowState> {

    public static class WindowState {
        public int x = -1;
        public int y = -1;
        public int width = -1;
        public int height = -1;
        public int extendedState = -1;
    }

    private WindowState windowState;

    public WindowDressing(@NotNull Project project) {
        super(project);
    }

    public void projectOpened() {
        final WindowActionGroup windowActionGroup = getWindowActionGroup();
        windowActionGroup.addProject(myProject.getName());
        if (windowState.x == -1 || windowState.y == -1 ||
                windowState.width == -1 || windowState.height == -1) {
            return;
        }
        final Frame projectFrame = WindowAction.findProjectFrame(myProject.getName());
        if (projectFrame != null) {
            final Rectangle bounds = new Rectangle(windowState.x, windowState.y,
                    windowState.width, windowState.height);
            projectFrame.setBounds(bounds);
            if (windowState.extendedState != -1) {
                projectFrame.setExtendedState(windowState.extendedState);
            }
        }
    }

    public void projectClosed() {
        final WindowActionGroup windowActionGroup = getWindowActionGroup();
        windowActionGroup.removeProject(myProject.getName());
    }

    public static WindowActionGroup getWindowActionGroup() {
        final ActionManager actionManager = ActionManager.getInstance();
        return (WindowActionGroup)
                actionManager.getAction("net.intellij.window.WindowActionGroup");
    }

    public WindowState getState() {
        final Frame projectFrame = WindowAction.findProjectFrame(myProject.getName());
        if (projectFrame != null) {
            final Rectangle bounds = projectFrame.getBounds();
            if (windowState == null) {
                windowState = new WindowState();
            }
            windowState.x = bounds.x;
            windowState.y = bounds.y;
            windowState.width = bounds.width;
            windowState.height = bounds.height;
            windowState.extendedState = projectFrame.getExtendedState();
        }
        return windowState;
    }

    public void loadState(final WindowState windowState) {
        this.windowState = windowState;
    }
}