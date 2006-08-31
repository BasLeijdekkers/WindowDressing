package net.intellij.window;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;

import java.awt.Container;
import java.awt.Window;
import java.awt.Frame;

/**
 * This class is programmatically instantiated and registered when opening and closing projects
 * and thus not registered in plugin.xml
 */
@SuppressWarnings({"ComponentNotRegistered"})
public class WindowAction extends ToggleAction {

	private Container projectContainer;

	public WindowAction(String name, Container projectContainer) {
		super(name);
		this.projectContainer = projectContainer;
	}

	public Container getProjectContainer() {
		return projectContainer;
	}

	public boolean isSelected(AnActionEvent e) {
		if (projectContainer.isVisible()) {
			if (projectContainer instanceof Window) {
				final Window window = (Window) projectContainer;
				return window.isActive();
			}
		}
		return false;
	}

	public void setSelected(AnActionEvent e, boolean state) {
		if (!state) {
			return;
		}
		projectContainer.setVisible(true);
		if (projectContainer instanceof Frame) {
			final Frame frame = (Frame) projectContainer;
			final int frameState = frame.getExtendedState();
			if (frameState == Frame.ICONIFIED) {
				frame.setExtendedState(frameState ^ Frame.ICONIFIED);
			}
			frame.toFront();
		}
	}
}
