package net.intellij.window;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Separator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bas Leijdekkers
 */
public class WindowActionGroup extends ActionGroup {

	private List<AnAction> children = new ArrayList();

	public void addProjectContainer(@NotNull String name, @NotNull Container projectContainer) {
		if (children.isEmpty()) {
			children.add(Separator.getInstance());
		}
		children.add(new WindowAction(name, projectContainer));
	}

	public AnAction[] getChildren(@Nullable AnActionEvent e) {
		return children.toArray(new AnAction[children.size()]);
	}

	public void removeProjectContainer(@NotNull String name, @NotNull Container projectContainer) {
		for (Iterator<AnAction> iterator = children.iterator(); iterator.hasNext();) {
			final AnAction child = iterator.next();
			if (child instanceof WindowAction) {
				final WindowAction windowAction = (WindowAction)child;
				if (name.equals(windowAction.getTemplatePresentation().getText()) &&
				    projectContainer.equals(windowAction.getProjectContainer())) {
					iterator.remove();
				}
			}		
		}
	}
}
