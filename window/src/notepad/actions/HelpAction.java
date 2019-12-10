package notepad.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class HelpAction extends Action {
    public HelpAction() {
        super();
        setText("帮助");
        setToolTipText("帮助");
        setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\help.gif"));
    }

    public void run() {
    }
}
