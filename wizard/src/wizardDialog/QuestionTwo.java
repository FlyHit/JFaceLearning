package wizardDialog;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.io.IOException;

public class QuestionTwo extends WizardPage {
    public QuestionTwo() {
        super(BookSurveyWizard.Q2, "问题2:", ImageDescriptor.createFromFile(QuestionOne.class, "q.gif"));
        this.setMessage("您会考虑在今后的项目中使用SWT开发桌面程序吗？");
    }

    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        new Label(composite, SWT.LEFT).setText("A.");
        Button b1 = new Button(composite, SWT.RADIO);
        b1.setText("会");
        b1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                WizardTest.dialogSettings.put("Q2", "1");
            }
        });
        new Label(composite, SWT.LEFT).setText("B.");
        Button b2 = new Button(composite, SWT.RADIO);
        b2.setText("可能会");
        b2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                WizardTest.dialogSettings.put("Q2", "2");
            }
        });
        new Label(composite, SWT.LEFT).setText("C.");
        Button b3 = new Button(composite, SWT.RADIO);
        b3.setText("不会");
        b3.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                WizardTest.dialogSettings.put("Q2", "3");
            }
        });

        try {
            WizardTest.dialogSettings.load("dialog.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (WizardTest.dialogSettings.get("Q2")) {
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

        setControl(composite);
    }
}
