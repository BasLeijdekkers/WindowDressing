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

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class WindowPositionMemoizer implements ApplicationComponent {

    private MyProjectManagerListener myProjectManagerListener;

    public WindowPositionMemoizer() {
    }

    public void initComponent() {
        myProjectManagerListener = new MyProjectManagerListener();
        ProjectManager.getInstance().addProjectManagerListener(myProjectManagerListener);
    }

    public void disposeComponent() {
        ProjectManager.getInstance().removeProjectManagerListener(myProjectManagerListener);
        myProjectManagerListener = null;
    }

    @NotNull
    public String getComponentName() {
        return "WindowPositionMemoizer";
    }

    private static class MyProjectManagerListener extends ProjectManagerAdapter {
        @Override
        public void projectOpened(Project project) {
            final PropertiesComponent props = PropertiesComponent.getInstance(project);
            final Rectangle bounds = readWindowPosition(props);
            final Frame projectFrame = WindowAction.getProjectFrame(project.getName());
            if (bounds != null && projectFrame != null) {
                projectFrame.setBounds(bounds);
            }
        }

        @Override
        public void projectClosing(Project project) {
            final PropertiesComponent props = PropertiesComponent.getInstance(project);
            final Frame projectFrame = WindowAction.getProjectFrame(project.getName());
            if (projectFrame != null) {
                final Rectangle bounds = projectFrame.getBounds();
                writeWindowPosition(props, bounds);
                project.save();
            }
        }
    }

    private static final String PROPERTY_KEY_X = "WindowDressing.ProjectWindow.x";
    private static final String PROPERTY_KEY_Y = "WindowDressing.ProjectWindow.y";
    private static final String PROPERTY_KEY_WIDTH = "WindowDressing.ProjectWindow.width";
    private static final String PROPERTY_KEY_HEIGHT = "WindowDressing.ProjectWindow.height";

    private static void writeWindowPosition(PropertiesComponent props, Rectangle bounds) {
        props.setValue(PROPERTY_KEY_X, Integer.toString(bounds.x));
        props.setValue(PROPERTY_KEY_Y, Integer.toString(bounds.y));
        props.setValue(PROPERTY_KEY_WIDTH, Integer.toString(bounds.width));
        props.setValue(PROPERTY_KEY_HEIGHT, Integer.toString(bounds.height));
    }

    private static Rectangle readWindowPosition(PropertiesComponent props) {
        try {
            return new Rectangle(
                    Integer.parseInt(props.getValue(PROPERTY_KEY_X, "UNKNOWN")),
                    Integer.parseInt(props.getValue(PROPERTY_KEY_Y, "UNKNOWN")),
                    Integer.parseInt(props.getValue(PROPERTY_KEY_WIDTH, "UNKNOWN")),
                    Integer.parseInt(props.getValue(PROPERTY_KEY_HEIGHT, "UNKNOWN")));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
