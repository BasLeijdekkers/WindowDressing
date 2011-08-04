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

public class WindowDressing extends AbstractProjectComponent {

    public WindowDressing(@NotNull Project project) {
        super(project);
    }

    public void projectOpened() {
        final WindowActionGroup windowActionGroup = getWindowActionGroup();
        windowActionGroup.addProject(myProject.getName());
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
}