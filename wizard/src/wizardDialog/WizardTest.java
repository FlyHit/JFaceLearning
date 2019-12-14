package wizardDialog;

import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.io.IOException;

/**
 * wizard负责管理多个wizardPage,使用DialogSettings保存设置
 */
public class WizardTest extends ApplicationWindow {
    public static DialogSettings dialogSettings;

    /**
     * 创建应用窗口
     */
    public WizardTest() {
        super(null);
        dialogSettings = new DialogSettings("bookSurvey");
        dialogSettings.put("Q1", 2);
        dialogSettings.put("Q2", 1);
        dialogSettings.put("contact", "No");

        try {
            dialogSettings.save("dialog.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建应用窗体的内容
     *
     * @param parent
     */
    @Override
    protected Control createContents(Composite parent) {
        parent.setLayout(new RowLayout(SWT.VERTICAL));
        Button button = new Button(parent, SWT.NONE);
        button.setText("打开简单向导对话框");
        button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                //为给定的wizard创建对话框
                WizardDialog dlg = new WizardDialog(Display.getCurrent().getActiveShell(), new BookSurveyWizard());
                dlg.addPageChangedListener(event -> {
//                    保存设置
                    try {
                        dialogSettings.save("dialog.xml");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                dlg.open();
            }
        });
        return parent;
    }

    /**
     * 加载应用
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            WizardTest window = new WizardTest();
            window.setBlockOnOpen(true);
            window.open();
            Display.getCurrent().dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置shell
     *
     * @param newShell
     */
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("向导页示例");
    }

    /**
     * @return 窗体的初始尺寸
     */
    @Override
    protected Point getInitialSize() {
        return new Point(450, 300);
    }
}