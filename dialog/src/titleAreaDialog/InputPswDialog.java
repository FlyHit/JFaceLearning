package titleAreaDialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * TitleAreaDialog:含用来显示标题和图片的标题区域的对话框（该区域也可用来显示提示信息）
 * 通常调用这两个方法super.createDialogArea(parent),super.createContents(parent)，
 * 以在超类的基础上绘制对话框
 */
public class InputPswDialog extends TitleAreaDialog {
    private Text userName;
    private Text password;
    private Text confirmPassword;
    public final String DEFAULT_INFO = "请输入要注册的用户名的和密码";

    /**
     * 实例化TitleAreaDialog
     *
     * @param parentShell the parent SWT shell
     */
    public InputPswDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    protected Control createContents(Composite parent) {
//        使用超类的createContents()，然后在其基础上进行自定义
        super.createContents(parent);
//        TitleAreaDialog继承自Window类，也有setText等方法
        this.getShell().setText("用户注册对话框");//设置对话框标题栏
        this.setTitle("用户注册");//设置标题信息
        this.setMessage("请输入要注册的用户名和密码", IMessageProvider.INFORMATION);//设置初始化对话框的提示信息
        return parent;
    }

    /*
     * 创建对话框显示的主体区域
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        super.createDialogArea(parent);
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        new Label(composite, SWT.NONE).setText("用户名：");
        userName = new Text(composite, SWT.BORDER);
        userName.addFocusListener(new FocusAdapter() {
            //当用户名文本框失去焦点时，判断是否有效
            public void focusLost(FocusEvent e) {
                checkValid();
            }
        });
        new Label(composite, SWT.NONE).setText("密码：");
        password = new Text(composite, SWT.BORDER);
        password.setEchoChar('*');
        new Label(composite, SWT.NONE).setText("确认密码：");
        confirmPassword = new Text(composite, SWT.BORDER);
        confirmPassword.setEchoChar('*');
        confirmPassword.addFocusListener(new FocusAdapter() {
            //当确认密码文本框失去焦点时，判断是否有效
            public void focusLost(FocusEvent e) {
                checkValid();
            }
        });
        return parent;
    }

    //判断是是否输入有效，并提示用户
    protected void checkValid() {
        if (!password.getText().equals(confirmPassword.getText()))
            setMessage("确认密码不一致，请重新输入!", IMessageProvider.WARNING);
        else if (userName.getText().equals(""))
            setMessage("用户名不能为空!", IMessageProvider.ERROR);
        else
            setMessage(DEFAULT_INFO);
    }
}