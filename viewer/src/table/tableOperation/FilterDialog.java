package table.tableOperation;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class FilterDialog extends Dialog {
    Text nameText;
    Text genderText;
    Text emailText;

    protected FilterDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        container.setLayout(gridLayout);
        new Label(container, SWT.NONE).setText("GiveName : ");
        nameText = new Text(container, SWT.NONE);
        nameText.setText(TWindow.name);
        new Label(container, SWT.NONE).setText("gender : ");
        genderText = new Text(container, SWT.NONE);
        genderText.setText(TWindow.gender);
        new Label(container, SWT.NONE).setText("email : ");
        emailText = new Text(container, SWT.NONE);
        emailText.setText(TWindow.email);
        return null;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, 0, "OK", false);
        createButton(parent, 1, "cancel", true);
    }

    @Override
    protected void buttonPressed(int buttonId) {
        switch (buttonId) {
            case 0:
                TWindow.name = nameText.getText();
                TWindow.gender = genderText.getText();
                TWindow.email = emailText.getText();
                close();
                break;
            case 1:
                close();
                break;
            default:
                break;
        }
    }
}
