package net.intellij.window;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * @author Bas Leijdekkers
 */
public class WindowActionGroup extends ActionGroup {

	private List<WindowAction> children = new ArrayList();

	public void addProjectContainer(@NotNull String name, @NotNull Container projectContainer) {
		children.add(new WindowAction(name, projectContainer));
	}

	public AnAction[] getChildren(@Nullable AnActionEvent e) {
		return children.toArray(new AnAction[children.size()]);
	}

	public void removeProjectContainer(@NotNull String name, @NotNull Container projectContainer) {
		for (Iterator<WindowAction> iterator = children.iterator(); iterator.hasNext();) {
			final WindowAction child = iterator.next();
			if (name.equals(child.getTemplatePresentation().getText()) &&
					projectContainer.equals(child.getProjectContainer())) {
				iterator.remove();
			}
		}
	}
}
