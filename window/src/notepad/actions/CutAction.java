package notepad.actions;

import notepad.MainWindow;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;

/**
 * 剪切动作
 */
public class CutAction extends Action {
    public CutAction() {
        super();
        setText("剪切");
        setToolTipText("剪切");
        setAccelerator(SWT.CTRL + 'X');
        setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\cut.gif"));
    }

    public void run() {
        MainWindow.getMainWindow().getContent().cut();
    }
}
