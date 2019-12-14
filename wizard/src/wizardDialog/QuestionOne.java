package wizardDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class QuestionOne extends WizardPage {
    public QuestionOne() {
        super(BookSurveyWizard.Q1, "问题1:", ImageDescriptor.createFromFile(QuestionOne.class, "q.gif"));
        // 设置标题消息
        this.setMessage("您认为本书的难度是：");
    }

    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        new Label(composite, SWT.LEFT).setText("A.");
        Button b1 = new Button(composite, SWT.RADIO);
        b1.setText("太简单");
        b1.setSelection(true);
        new Label(composite, SWT.LEFT).setText("B.");
        Button b2 = new Button(composite, SWT.RADIO);
        b2.setText("还可以");
        new Label(composite, SWT.LEFT).setText("C.");
        Button b3 = new Button(composite, SWT.RADIO);
        b3.setText("太难");

        setControl(composite);  // Important！！！
    }

    public void performHelp() {
        MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "帮助", "请联系ABC@yahoo.com.cn!");
    }
}
