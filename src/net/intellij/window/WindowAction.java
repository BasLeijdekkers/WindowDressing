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

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import org.jetbrains.annotations.NotNull;

import java.awt.Container;
import java.awt.Frame;

/**
 * This class is programmatically instantiated and registered when opening and closing projects
 * and thus not registered in plugin.xml
 */
@SuppressWarnings({"ComponentNotRegistered"})
public class WindowAction extends ToggleAction {

	@NotNull private Frame projectFrame;

	public WindowAction(@NotNull String name, @NotNull Frame projectFrame) {
		super(name);
		this.projectFrame = projectFrame;
	}

	public Container getProjectFrame() {
		return projectFrame;
	}

	public boolean isSelected(AnActionEvent e) {
		// show check mark for active and visible project frame
		return projectFrame.isVisible() && projectFrame.isActive();
	}

	public void setSelected(AnActionEvent e, boolean selected) {
		if (!selected) {
			return;
		}
		final int frameState = projectFrame.getExtendedState();
		if ((frameState & Frame.ICONIFIED) == Frame.ICONIFIED) {
			// restore the frame if it is minimized
			projectFrame.setExtendedState(frameState ^ Frame.ICONIFIED);
		}
		// bring the frame forward
		projectFrame.toFront();
	}
}