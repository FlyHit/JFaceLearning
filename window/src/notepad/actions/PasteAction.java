package notepad.actions;

import notepad.MainWindow;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;

public class PasteAction extends Action {
    public PasteAction() {
        super();
        setText("粘贴");
        setToolTipText("粘贴");
        setAccelerator(SWT.CTRL + 'V');
        setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\paste.gif"));
    }

    public void run() {
        MainWindow.getMainWindow().getContent().paste();
    }
}
