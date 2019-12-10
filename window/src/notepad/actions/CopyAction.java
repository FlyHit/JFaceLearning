package notepad.actions;

import notepad.MainWindow;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;

/**
 * 复制动作
 */
public class CopyAction extends Action {
    public CopyAction() {
        super();  // Action():构造没有文字和图片的Action对象
        setText("复制");
        setToolTipText("复制");
        setAccelerator(SWT.CTRL + 'C');
//        设置动作的图片
        setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\copy.gif"));
    }

    //    Action的子类通过实现run()来执行动作
    public void run() {
        MainWindow.getMainWindow().getContent().copy();  // copy()复制选中的文本，content为Text
    }
}
