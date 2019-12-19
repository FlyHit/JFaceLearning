package fileExplorer;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import toolkit.MyImage;

import java.io.File;

public class FileTreeLabelProvider implements ILabelProvider {
    private Display display;

    public FileTreeLabelProvider(Display display) {
        this.display = display;
    }

    @Override
    public Image getImage(Object element) {
        File f = (File) element;
        if (f != null && f.exists()) {
            return MyImage.getSystemIconFromSwing(display, f);
        }
        return null;
    }

    @Override
    public String getText(Object element) {
//        getName()：返回文件名，如D://1.jpg,返回1.jpg
        String text = ((File) element).getName();
        if (text.length() == 0) {
            text = ((File) element).getPath();
        }
        return text;
    }

    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
    }
}

