package inputDialog;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * 输入对话框，含输入内容验证
 */
public class InputDialogTest extends ApplicationWindow {
    /**
     * 创建应用窗口
     */
    public InputDialogTest() {
        super(null);
    }

    /**
     * 创建应用窗体的内容
     *
     * @param parent
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());
        Button button = new Button(container, SWT.NONE);
        button.setText("打开输入对话框");
        button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
//                输入对话框会一直保持打开状态直到用户关闭
                InputDialog inputDialog = new InputDialog(Display.getCurrent().getActiveShell(),
                        "输入电子邮件",//对话框的标题
                        "请输入电子邮件地址：",//对话框的提示信息
                        "ABC@hotmail.com",//输入框中默认值
                        new EmailValidator()//验证输入字符的有效性
                );
                int r = inputDialog.open();//打开窗口
                if (r == Window.OK)//如果输入有效，则输出输入的值
                    System.out.println(inputDialog.getValue());
                else
                    System.out.println("取消");
            }
        });
        return container;
    }

    /**
     * 加载应用
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            InputDialogTest window = new InputDialogTest();
//            true:open()在窗口关闭前不应该返回，也就是说窗口会一直保持打开状态直到关闭
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
        newShell.setText("输入对话框示例");
    }

    /**
     * @return 窗体的初始尺寸
     */
    @Override
    protected Point getInitialSize() {
        return new Point(450, 300);
    }
}