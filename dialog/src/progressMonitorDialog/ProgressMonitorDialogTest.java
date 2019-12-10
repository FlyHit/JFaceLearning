package progressMonitorDialog;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

/**
 * 使用ProgressMonitorDialog（实现了IRunnableContext接口）显示任务的进度，
 * 一般配合IRunnableWithProgress（创建可供监视的耗时线程）使用
 */
public class ProgressMonitorDialogTest extends ApplicationWindow {
    /**
     * 创建应用窗口
     */
    public ProgressMonitorDialogTest() {
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
        Button bt1 = new Button(container, SWT.NONE);
        bt1.setText("打开进度条对话框");
        bt1.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                showDialog();
            }
        });
        return container;
    }

    private void showDialog() {
        try {
//            ProgressMonitorDialog:一个显示耗时操作进度的模态对话框
            ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
            /*
            实例会执行耗时操作的任何类都应该实现IRunnableWithProgress接口。耗时的操作通常通过模态对话框显示在
            UI上（该对话框上含进度条和取消按钮）。实现该接口的类必须定义一个含进度监视器的run方法。run方法通常
            不会直接被调用，而是通过将一个IRunnableWithProgress对象传递给IRunnableContext的run方法来调用，而
            IRunnableContext对象提供了进度监视器和取消按钮（ProgressMonitorDialog实现了该接口）
             */
            IRunnableWithProgress runnable = monitor -> {
                // beginTask：通知主任务已经开始
                monitor.beginTask("开始执行任务...", 100);
                // 该线程在用户没有取消操作的情况下循环10次
                // 并且每次循环后设置进度增加10,表示一个任务已完成
                for (int i = 0; i < 10 && !monitor.isCanceled(); i++) {
                    Thread.sleep(500);  // 为了模拟耗时的操作，每次循环后让该线程休息半秒钟
                    monitor.worked(10);  // 进度增加10
                    monitor.subTask("已完成第" + i + "个任务");  // 显示任务状态
                }
                // 循环完成后设置此任务已完成
                monitor.done();
                // 如果此时为用户取消的操作
                if (monitor.isCanceled())
                    throw new InterruptedException("用户已取消了操作");
            };
//            fork：是否在单独的线程运行
            progressDialog.run(true, true, runnable);  // 启动线程
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    /**
     * 加载应用
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            ProgressMonitorDialogTest window = new ProgressMonitorDialogTest();
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
        newShell.setText("进度条对话框示例");
        newShell.setSize(500, 200);
    }
}