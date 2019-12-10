package notepad.actions;

import notepad.MainWindow;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Text;

public class FormatAction extends Action {
    public static final String TYPE_FORECOLOR = "FORECOLOR";  // 前景色
    public static final String TYPE_BGCOLOR = "BGCOLOR";  // 背景色
    public static final String TYPE_FONT = "FONT";  // 字体
    private String formatType;  // 通过不同的类型来构造不同的处理事件

    public FormatAction(String type) {
        super();
        this.formatType = type;
        initAction();
    }

    /**
     * 根据不同的类型来初始动作
     */
    private void initAction() {
        switch (formatType) {
            case TYPE_FONT:
                this.setText("设置字体");
                this.setToolTipText("设置字体");
                setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\font.gif"));
                break;
            case TYPE_FORECOLOR:
                this.setText("设置前景色");
                this.setToolTipText("设置前景色");
                setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\foreColor.gif"));
                break;
            case TYPE_BGCOLOR:
                this.setText("设置背景色");
                this.setToolTipText("设置背景色");
                setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\bgColor.gif"));
                break;
            default:
                break;
        }
    }

    public void run() {
        Text content = MainWindow.getMainWindow().getContent();
        switch (formatType) {
            case TYPE_FONT:
                FontDialog fontDialog = new FontDialog(MainWindow.getMainWindow().getShell());  // 系统字体选择窗口
                fontDialog.setFontList(content.getFont().getFontData());  // 字体窗口默认选择的字体
                FontData fontData = fontDialog.open();  // 打开字体窗口，返回选择的FontData
                if (fontData != null) {
                    Font font = new Font(MainWindow.getMainWindow().getShell().getDisplay(), fontData);
                    content.setFont(font);
                }
                break;
            case TYPE_FORECOLOR: {
                ColorDialog colorDialog = new ColorDialog(MainWindow.getMainWindow().getShell());
                colorDialog.setRGB(content.getForeground().getRGB());
                RGB rgb = colorDialog.open();
                if (rgb != null) {
                    Color foregroundColor = new Color(MainWindow.getMainWindow().getShell().getDisplay(), rgb);
                    content.setForeground(foregroundColor);
                    foregroundColor.dispose();
                }
            }
            break;
            case TYPE_BGCOLOR: {
                ColorDialog colorDialog = new ColorDialog(MainWindow.getMainWindow().getShell());
                colorDialog.setRGB(content.getBackground().getRGB());
                RGB rgb = colorDialog.open();
                if (rgb != null) {
                    Color bgColor = new Color(MainWindow.getMainWindow().getShell().getDisplay(), rgb);
                    content.setBackground(bgColor);
                    bgColor.dispose();
                }
            }
            break;
            default:
                break;
        }
    }
}
