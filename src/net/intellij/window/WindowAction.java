package net.intellij.window;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.awt.*;

/**
 * This class is programmatically instantiated and registered when opening and closing projects
 * and thus not registered in plugin.xml
 * @author Bas Leijdekkers
 */
@SuppressWarnings({"ComponentNotRegistered"})
public class WindowAction extends AnAction {

	private Container projectContainer;

	public WindowAction(String name, Container projectContainer) {
		super(name);
		this.projectContainer = projectContainer;
	}

	public void actionPerformed(AnActionEvent e) {
		projectContainer.setVisible(true);
		if (projectContainer instanceof Window) {
			final Window window = (Window) projectContainer;
			window.toFront();
		}
	}

	public Container getProjectContainer() {
		return projectContainer;
	}
}
