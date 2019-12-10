package notepad;

import notepad.actions.*;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

/**
 * 使用JFace创建简单的记事本，包括MenuManager、ToolManager、
 * Action、ModalContext的使用
 */
public class MainWindow extends ApplicationWindow {
    //    Action对象可被Menu、Tool、单击按钮复用
    private NewAction newAction;  // 新建菜单项
    private OpenAction openAction;  // 打开菜单项
    private SaveAction saveAction;  // 保存菜单项
    private SaveAsAction saveAsAction;  // 另存为菜单项
    private ExitAction exitAction;  // 退出菜单项
    private CopyAction copyAction;  // 复制菜单项
    private CutAction cutAction;  // 剪切菜单项
    private PasteAction pasteAction;  // 粘贴菜单项
    private HelpAction helpAction;  // 帮助菜单项
    private FileManager fileManager;  // 文件管理器
    private Text content;  // 文本框
    private static MainWindow mainWindow;  // 主程序窗口

    /**
     * 创建应用程序窗口
     */
    public MainWindow() {
//        创建应用窗口实例，null-窗体的shell为顶层shell
        super(null);
        mainWindow = this;
        fileManager = new FileManager();
        createActions();
        addToolBar(SWT.FLAT | SWT.WRAP);
        addMenuBar();
        addStatusLine();
    }

    /**
     * @return 返回主窗口
     */
    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * 创建窗体的内容——Text,由Window类的create()调用,create()由open()调用
     *
     * @param parent the parent composite for the controls in this window. The type
     *               of layout used is determined by getLayout()
     * @return the control that will be returned by subsequent calls to
     * getContents()
     */
    @Override
    protected Control createContents(Composite parent) {
        content = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
        content.addModifyListener(e -> fileManager.setModified(true));  // 修改标志为true

        return parent;
    }

    /**
     * 创建窗体的动作
     */
    private void createActions() {
        // 初始化按钮动作
        newAction = new NewAction();
        openAction = new OpenAction();
        saveAction = new SaveAction();
        saveAsAction = new SaveAsAction();
        exitAction = new ExitAction();
        copyAction = new CopyAction();
        cutAction = new CutAction();
        pasteAction = new PasteAction();
        helpAction = new HelpAction();
    }

    /**
     * 创建菜单管理器
     *
     * @return 菜单管理器
     */
    @Override
    protected MenuManager createMenuManager() {
//        MenuManager封装了MenuBar,MenuItem,context menu对象，使用不同的构造器可以创建不同的对象
        MenuManager menuBar = new MenuManager();  // 创建菜单栏对象

        MenuManager fileMenu = new MenuManager("文件(&F)");  // 文件菜单项
        MenuManager editMenu = new MenuManager("编辑(&E)");  // 编辑菜单项
        MenuManager formatMenu = new MenuManager("格式(&M)");  // 格式菜单项
        MenuManager helpMenu = new MenuManager("帮助(&H)");  // 帮助菜单项

//        将菜单项添加到主菜单中
        menuBar.add(fileMenu);  // add添加item或action
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

//        文件菜单项
        fileMenu.add(newAction);
        fileMenu.add(openAction);
        fileMenu.add(new Separator());
        fileMenu.add(saveAction);
        fileMenu.add(saveAsAction);
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);

//        编辑菜单项
        editMenu.add(copyAction);
        editMenu.add(cutAction);
        editMenu.add(pasteAction);

//        格式菜单项
        formatMenu.add(new FormatAction(FormatAction.TYPE_FONT));
        formatMenu.add(new FormatAction(FormatAction.TYPE_BGCOLOR));
        formatMenu.add(new FormatAction(FormatAction.TYPE_FORECOLOR));

        helpMenu.add(helpAction);  // 帮助菜单项

        return menuBar;
    }

    /**
     * 创建工具管理器
     *
     * @return the toolbar manager
     */
    @Override
    protected ToolBarManager createToolBarManager(int style) {
//        ToolBarManager封装了ToolBar和ToolItem
        ToolBarManager toolBar = new ToolBarManager(style);
        toolBar.add(newAction);
        toolBar.add(openAction);
        toolBar.add(saveAction);
        toolBar.add(saveAsAction);
        toolBar.add(new Separator());
        toolBar.add(copyAction);
        toolBar.add(cutAction);
        toolBar.add(pasteAction);
        toolBar.add(new Separator());
        toolBar.add(new FormatAction(FormatAction.TYPE_FONT));
        toolBar.add(new FormatAction(FormatAction.TYPE_BGCOLOR));
        toolBar.add(new FormatAction(FormatAction.TYPE_FORECOLOR));
        return toolBar;
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

    public StatusLineManager getStatusLineManager() {
        return super.getStatusLineManager();
    }

    /**
     * 加载应用
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            MainWindow window = new MainWindow();
            window.setBlockOnOpen(true);
            window.open();
            Display.getCurrent().dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 返回 content。
     */
    public Text getContent() {
        return content;
    }

    /**
     * @return 返回 manager。
     */
    public FileManager getFileManager() {
        return fileManager;
    }

    /**
     * @param fileManager 要设置的 manager。
     */
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * 配置shell
     *
     * @param newShell
     */
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setSize(new Point(450, 300));
        newShell.setText("Notepad");
    }

    /**
     * 返回shell初始尺寸
     */
    @Override
    protected Point getInitialSize() {
        return new Point(450, 300);
    }
}