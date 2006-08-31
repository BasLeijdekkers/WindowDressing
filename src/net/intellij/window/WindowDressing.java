/*
 * Copyright 2006 Bas Leijdekkers
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
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

import java.awt.Container;
import java.awt.Frame;

public class WindowDressing implements ProjectComponent {

	private Project project;
	private Frame projectFrame = null;

	public WindowDressing(@NotNull Project project) {
		this.project = project;
	}

	public void initComponent() {
	}

	public void disposeComponent() {
	}

	@NotNull
	public String getComponentName() {
		return "WindowDressing";
	}

	public Frame getProjectFrame(@NotNull Project project) {
		final WindowManager windowManager = WindowManager.getInstance();
		Container projectContainer = windowManager.suggestParentWindow(project);
		if (projectContainer == null) {
			return null;
		}
		Container parent = projectContainer.getParent();
		while (parent != null) {
			projectContainer = parent;
			parent = projectContainer.getParent();
		}
		if (!(projectContainer instanceof Frame)) {
			return null;
		}
		return (Frame)projectContainer;
	}

	public void projectOpened() {
		final ActionManager actionManager = ActionManager.getInstance();
		final WindowActionGroup windowActionGroup = (WindowActionGroup)
				actionManager.getAction("net.intellij.window.WindowActionGroup");
		projectFrame = getProjectFrame(project);
		windowActionGroup.addProjectFrame(project.getName(), projectFrame);
	}

	public void projectClosed() {
		final ActionManager actionManager = ActionManager.getInstance();
		final WindowActionGroup windowActionGroup = (WindowActionGroup)
				actionManager.getAction("net.intellij.window.WindowActionGroup");
		windowActionGroup.removeProjectFrame(project.getName(), projectFrame);
		project = null;
		projectFrame = null;
	}
}
