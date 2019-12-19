package fileExplorer;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import java.io.File;
import java.io.FileFilter;

public class FileTableContentProvider implements IStructuredContentProvider {
    @Override
    public Object[] getElements(Object inputElement) {
        return ((File) inputElement).listFiles(new OnlyVisibleFileFilter());
    }

    class OnlyVisibleFileFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            return !pathname.isHidden();
        }
    }
}
