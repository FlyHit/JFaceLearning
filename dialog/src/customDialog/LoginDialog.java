package customDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * 创建自定义对话框：
 * 继承Dialog：创建对话框区域和按钮条
 * 1 createDialogArea
 * 2 createButtonsForButtonBar
 * 3 buttonPressed：处理按键单击事件
 */
public class LoginDialog extends Dialog {
    //定义按钮的常量
    public static final int LOGIN_ID = 0;
    public static final int LOGOUT_ID = 1;
    public static final String LOGIN_LABEL = "登录";
    public static final String LOGOUT_LABEL = "退出";
    //用户名和密码文本框
    private Text userName;
    private Text password;

    protected LoginDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        Group group = new Group(container, SWT.NONE);
        group.setText("登录系统");
        GridLayout layout = new GridLayout();
        layout.marginHeight = 20;
        layout.marginWidth = 20;
        layout.numColumns = 2;
        group.setLayout(layout);
        new Label(group, SWT.NONE).setText("用户名: ");
        userName = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.NONE).setText("密码: ");
        password = new Text(group, SWT.BORDER | SWT.SINGLE);
        password.setEchoChar('*');
        return container;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        // 应使用父类中创建按钮的方法创建登陆和退出按钮，createButton()：创建有id的按钮
        // defaultButton参数：默认选中的按钮
        createButton(parent, LoginDialog.LOGIN_ID, LoginDialog.LOGIN_LABEL, false);
        createButton(parent, LoginDialog.LOGOUT_ID, LoginDialog.LOGOUT_LABEL, true);
    }

    @Override
    protected void buttonPressed(int buttonId) {
        //如果此时单击了登陆按钮
        if (LoginDialog.LOGIN_ID == buttonId)
            System.out.println("登录！用户名为" + userName.getText() + ",密码为" + password.getText());
            //如果此时单击了取消按钮，调用父类的
        else if (LoginDialog.LOGOUT_ID == buttonId)
            close();
    }
}
