package notepad.actions;

import notepad.MainWindow;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * 退出动作
 */
public class ExitAction extends Action {
    public ExitAction() {
        super();
        setText("退出(&E)");
        setToolTipText("退出系统");
        setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\exit.gif"));
    }

    public void run() {
        MainWindow.getMainWindow().close();
    }
}
