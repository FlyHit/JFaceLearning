package fileExplorer;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import toolkit.MyImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTableLabelProvider implements ITableLabelProvider {
    private Display display;

    public FileTableLabelProvider(Display display) {
        this.display = display;
    }

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        File f = (File) element;
        if (f != null && f.exists() && columnIndex == 0) {
            return MyImage.getSystemIconFromSwing(display, f);
        }
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        File file = (File) element;

        switch (columnIndex) {
            case 0:
                return file.getName();
            case 1:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(file.lastModified());
                return sdf.format(date);
            case 2:
                return "";
            case 3:
                if (file.isDirectory())
                    return "";
                else
                    return file.length() / 1024 + " KB";
            default:
                break;
        }

        return null;
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
