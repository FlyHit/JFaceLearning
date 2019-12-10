package notepad.actions;

import notepad.FileManager;
import notepad.MainWindow;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;

public class NewAction extends Action {
    public NewAction() {
        super();
        setText("新建(&N)");
        this.setAccelerator(SWT.ALT + SWT.SHIFT + 'N');
        setToolTipText("新建");
        setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\new.gif"));
    }

    public void run() {
        MainWindow.getMainWindow().getContent().setText("");
        MainWindow.getMainWindow().setFileManager(new FileManager());
    }
}
