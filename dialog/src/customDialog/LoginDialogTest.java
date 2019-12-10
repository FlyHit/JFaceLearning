package customDialog;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class LoginDialogTest extends ApplicationWindow {
    /**
     * 创建应用窗口
     */
    public LoginDialogTest() {
        super(null);
        createActions();
        addToolBar(SWT.FLAT | SWT.WRAP);
        addMenuBar();
        addStatusLine();
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
        button.setText("打开自定义对话框示例");
        button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                LoginDialog dialog = new LoginDialog(Display.getCurrent().getActiveShell());
                dialog.open();
            }
        });

        return container;
    }

    /**
     * 创建窗体动作
     */
    private void createActions() {
        // Create the actions
    }

    /**
     * 创建菜单管理器
     *
     * @return 菜单管理器
     */
    @Override
    protected MenuManager createMenuManager() {
        MenuManager menuManager = new MenuManager("menu");
        return menuManager;
    }

    /**
     * 创建工具栏管理器
     *
     * @return 工具栏管理器
     */
    @Override
    protected ToolBarManager createToolBarManager(int style) {
        ToolBarManager toolBarManager = new ToolBarManager(style);
        return toolBarManager;
    }

    /**
     * 创建状态栏管理器
     *
     * @return 状态栏管理器
     */
    @Override
    protected StatusLineManager createStatusLineManager() {
        StatusLineManager statusLineManager = new StatusLineManager();
        return statusLineManager;
    }

    /**
     * 加载应用
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            LoginDialogTest window = new LoginDialogTest();
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
        newShell.setText("自定义按钮示例");
    }

    /**
     * @return 窗体的初始尺寸
     */
    @Override
    protected Point getInitialSize() {
        return new Point(450, 300);
    }
}