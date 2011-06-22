/*
 * Copyright 2011 Maarten Hazewinkel, Bas Leijdekkers
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

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;

public class NextWindow  extends AnAction implements DumbAware {

    public void actionPerformed(AnActionEvent e) {
        WindowDressing.getWindowActionGroup().activateNextWindow(e);
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(WindowDressing.getWindowActionGroup().isEnabled());
    }
}
