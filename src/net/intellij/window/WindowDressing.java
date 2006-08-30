package net.intellij.window;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

import java.awt.Container;

public class WindowDressing implements ProjectComponent {

	private Project project;
	private Container projectContainer = null;

	public WindowDressing(Project project) {
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

	public void projectOpened() {
		final ActionManager actionManager = ActionManager.getInstance();
		final WindowActionGroup windowActionGroup = (WindowActionGroup)
				actionManager.getAction("net.intellij.window.WindowActionGroup");
		final WindowManager windowManager = WindowManager.getInstance();
		projectContainer = windowManager.suggestParentWindow(project);
		if (projectContainer == null) {
			return;
		}
		Container parent = projectContainer.getParent();
		while (parent != null) {
			projectContainer = parent;
			parent = projectContainer.getParent();
		}
		windowActionGroup.addProjectContainer(project.getName(), projectContainer);
	}

	public void projectClosed() {
		final ActionManager actionManager = ActionManager.getInstance();
		final WindowActionGroup windowActionGroup = (WindowActionGroup)
				actionManager.getAction("net.intellij.window.WindowActionGroup");
		windowActionGroup.removeProjectContainer(project.getName(), projectContainer);
		project = null;
		projectContainer = null;
	}
}
