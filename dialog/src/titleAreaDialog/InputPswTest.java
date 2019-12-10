package titleAreaDialog;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

/**
 * 含提示信息的密码输入框
 */
public class InputPswTest extends ApplicationWindow {
    /**
     * 创建应用窗口
     */
    public InputPswTest() {
        super(null);
    }

    /**
     * 创建应用窗体的内容
     *
     * @param parent
     */
    @Override
    protected Control createContents(Composite parent) {
        Button button = new Button(parent, SWT.NONE);
        button.setText("打开输入对话框");
        button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                //调用该对话框
                InputPswDialog dialog = new InputPswDialog(Display.getCurrent().getActiveShell());
                dialog.open();
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
            InputPswTest window = new InputPswTest();
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
        newShell.setSize(400, 100);
        newShell.setText("含提示信息的密码输入框");
    }
}
