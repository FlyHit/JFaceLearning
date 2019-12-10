package errorDialog;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * 错误消息对话框（ErrorDialog):可根据错误的级别显示错误消息，可显示一个或多个错误，
 * 也可使用mask进行过滤
 */
public class ErrorDialogTest extends ApplicationWindow {
    final String dummyPlugin = "plugin id";

    /**
     * 创建应用窗口
     */
    public ErrorDialogTest() {
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
        container.setLayout(new RowLayout(SWT.VERTICAL));
        //region 打开一个错误对话框
        Button bt1 = new Button(container, SWT.NONE);
        bt1.setText("打开一个错误对话框");
        bt1.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                //创建一个Status对象(表示操作的结果）
                Status status = new Status(IStatus.ERROR, dummyPlugin, 1, "未找到该类", new ClassNotFoundException());
                /*
                ErrorDialog：显示一个或多个包含在IStatus对象中的错误的对话框，通常使用的是ErrorDialog.openError()方法显示错误消息
                (无需实例化，直接使用静态方法打开对话框），只有在要显示的错误对象包含子项并且需要指定掩码（mask）来过滤需要显示的
                子项时，才需要用到该构造器。错误对话框只有在至少一个子项状态匹配掩码时才会显示
                 */
                ErrorDialog dlg = new ErrorDialog(Display.getCurrent().getActiveShell(),
                        "提示错误", //对话框的标题
                        "装载类时出现错误！",//对话框的描述信息
                        status, //Status对象（显示的错误）
                        IStatus.ERROR);//只显示级别为IStatus.ERROR的错误(用于过滤的mask）
                dlg.open();
            }
        });
        //endregion
        //region 打开一个可显示多错误对话框
        Button bt2 = new Button(container, SWT.NONE);
        bt2.setText("打开一个可显示多错误对话框");
        bt2.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                IStatus[] statuses = new IStatus[4];
                statuses[0] = new Status(IStatus.INFO, dummyPlugin, IStatus.OK, "未找到类错误", new ClassNotFoundException());
                statuses[1] = new Status(IStatus.ERROR, dummyPlugin, IStatus.OK, "未找到文件错误", new FileNotFoundException());
                statuses[2] = new Status(IStatus.WARNING, dummyPlugin, IStatus.OK, "运行错误", new RuntimeException());
                statuses[3] = new Status(IStatus.WARNING, dummyPlugin, IStatus.OK, "数据库查询错误", new SQLException());
                MultiStatus multiStatus = new MultiStatus(dummyPlugin, IStatus.OK, statuses, "运行期间错误 ", new Exception());
//                可显示多个错误，也可添加多个过滤条件
                ErrorDialog dlg = new ErrorDialog(Display.getCurrent().getActiveShell(),
                        "提示错误",
                        "运行JFace期间发生的错误",
                        multiStatus,
                        IStatus.WARNING | IStatus.ERROR);//显示级别为IStatus.WARNING或IStatus.ERROR的错误
                dlg.open();
            }
        });
        //endregion
        return container;
    }

    /**
     * 加载应用
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            ErrorDialogTest window = new ErrorDialogTest();
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
        newShell.setSize(500, 90);
        newShell.setText("错误消息对话框示例");
    }
}