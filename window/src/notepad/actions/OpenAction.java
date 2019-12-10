package notepad.actions;

import notepad.FileManager;
import notepad.MainWindow;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import java.lang.reflect.InvocationTargetException;

public class OpenAction extends Action {
    public OpenAction() {
        super();
        setText("打开(&O)");
        setToolTipText("打开文件");
        setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\open.gif"));
    }

    public void run() {
        // 打开一个文件对话框，选择一个文件
        FileDialog dialog = new FileDialog(MainWindow.getMainWindow().getShell(), SWT.OPEN);
        dialog.setFilterExtensions(new String[]{"*.java", "*.*"});  // 文件格式筛选
        final String name = dialog.open();
        if ((name == null) || (name.length() == 0))
            return;

        final FileManager fileManager = MainWindow.getMainWindow().getFileManager();
        try {
            // 创建一个线程打开文件
            /*ModalContext：支持模态操作的实用类
            fork参数：决定传递给run()的Runnable对象是否在单独的线程中运行，如果是在单独的线程中运行，那么当前线程将等待
            新线程结束（if the current thread is the UI thread,it polls the SWT event queue and dispatches each event）
            monitor参数：显示进程的监视器，可接收取消操作的请求
            display参数：用于读取和分发事件的display对象
             */
            ModalContext.run(progressMonitor -> {
                progressMonitor.beginTask("打开文件", IProgressMonitor.UNKNOWN);
                fileManager.load(name);
                //为了模拟一个较大的文件，让该线程休息1秒。
                //取消该注释运行可以看到进度情况
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progressMonitor.done();
            }, true, MainWindow.getMainWindow().getStatusLineManager().getProgressMonitor(), MainWindow.getMainWindow().getShell().getDisplay());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //装载后将字符显示到文本框中
        MainWindow.getMainWindow().getContent().setText(fileManager.getContent());
        //设置状态栏显示的为打开的文件目录
        MainWindow.getMainWindow().getStatusLineManager().setMessage("当前打开的文件是：" + name);
    }

}
