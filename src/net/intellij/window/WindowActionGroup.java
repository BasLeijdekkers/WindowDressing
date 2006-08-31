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

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Separator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Container;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bas Leijdekkers
 */
public class WindowActionGroup extends ActionGroup {

	private List<AnAction> children = new ArrayList();

	public void addProjectFrame(@NotNull String name, @NotNull Frame projectFrame) {
		if (children.isEmpty()) {
			children.add(Separator.getInstance());
		}
		children.add(new WindowAction(name, projectFrame));
	}

	public AnAction[] getChildren(@Nullable AnActionEvent e) {
		return children.toArray(new AnAction[children.size()]);
	}

	public void removeProjectFrame(@NotNull String name, @NotNull Frame projectFrame) {
		for (Iterator<AnAction> iterator = children.iterator(); iterator.hasNext();) {
			final AnAction child = iterator.next();
			if (child instanceof WindowAction) {
				final WindowAction windowAction = (WindowAction)child;
				if (name.equals(windowAction.getTemplatePresentation().getText()) &&
				    projectFrame.equals(windowAction.getProjectFrame())) {
					iterator.remove();
				}
			}		
		}
	}
}
