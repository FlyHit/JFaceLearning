package msgDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * 消息对话框的使用，包括错误消息、警告、确认消息、警告、询问对话框
 */
public class MsgDialog extends ApplicationWindow {
    /**
     * 创建应用窗口
     */
    public MsgDialog() {
        super(null);
    }

    /**
     * 创建应用窗体的内容
     *
     * @param parent
     */
    @Override
    protected Control createContents(Composite parent) {
        // 窗口中的区域由一个文本框做为消息输出台和几个按钮分别打开不同的对话框
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout(6, false);
        composite.setLayout(gridLayout);
        final Text console = new Text(composite, SWT.NONE | SWT.READ_ONLY | SWT.V_SCROLL);  // Text只读
        GridData data = new GridData(GridData.FILL_BOTH);
        data.horizontalSpan = 6;
        console.setLayoutData(data);
        //region 错误消息对话框按钮
        Button openError = new Button(composite, SWT.NONE);
        openError.setText("错误消息对话框");
        openError.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误消息对话框", "读取文件发生错误！");
                console.append("\n openError对话框，返回void");
            }
        });
        //endregion
        Button openConfirm = new Button(composite, SWT.NONE);
        openConfirm.setText("确认消息对话框");
        openConfirm.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                boolean b = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),
                        "确认消息对话框",
                        "确实要保存文件吗？");
                console.append("\n openConfirm对话框，返回" + b);
            }
        });
        Button openInformation = new Button(composite, SWT.NONE);
        openInformation.setText("消息对话框");
        openInformation.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "消息对话框", "确实要保存文件吗？");
                console.append("\n openInformation对话框，返回void");
            }
        });
        Button openQuestion = new Button(composite, SWT.NONE);
        openQuestion.setText("询问对话框");
        openQuestion.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                boolean b = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "询问对话框", "确实要保存文件吗！");
                console.append("\n openQuestion对话框，返回" + b);
            }
        });
        Button openWarning = new Button(composite, SWT.NONE);
        openWarning.setText("警告对话框");
        openWarning.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "警告对话框", "确实要保存文件吗！");
                console.append("\n openWarning对话框，返回void");
            }
        });
        Button customMessageDig = new Button(composite, SWT.NONE);
        customMessageDig.setText("自定义对话框");
        customMessageDig.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),//shell窗口
                        "这是对话框的标题",//标题
                        null,//对话框的图标，为null则不显示图标
                        "这是一个自定义的对话框，可以改变按钮的设置",//对话框中的提示信息
                        MessageDialog.INFORMATION,//提示信息的图标
                        new String[]{"查看", "保存", "确认"},//显示三个按钮
                        1);//默认选择的按钮的索引值，这里为1，表示默认选中第二个按钮，也就是保存按钮
                int i = dialog.open();//返回值为按钮
                console.append("\n 自定义对话框，返回按钮的索引值" + i);
            }
        });
        return composite;
    }

    /**
     * 加载应用
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            MsgDialog window = new MsgDialog();
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
        newShell.setSize(700, 300);
        newShell.setText("消息对话框示例");
    }
}