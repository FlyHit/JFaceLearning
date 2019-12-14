package wizardDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.io.IOException;

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
        b1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                WizardTest.dialogSettings.put("Q1", "1");
            }
        });
        new Label(composite, SWT.LEFT).setText("B.");
        Button b2 = new Button(composite, SWT.RADIO);
        b2.setText("还可以");
        b2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                WizardTest.dialogSettings.put("Q1", "2");
            }
        });
        new Label(composite, SWT.LEFT).setText("C.");
        Button b3 = new Button(composite, SWT.RADIO);
        b3.setText("太难");
        b3.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                WizardTest.dialogSettings.put("Q1", "3");
            }
        });

        try {
            WizardTest.dialogSettings.load("dialog.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (WizardTest.dialogSettings.get("Q1")) {
            case "1":
                b1.setSelection(true);
                break;
            case "2":
                b2.setSelection(true);
                break;
            case "3":
                b3.setSelection(true);
                break;
        }
        setControl(composite);  // Important！！！
    }

    public void performHelp() {
        MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "帮助", "请联系ABC@yahoo.com.cn!");
    }
}
