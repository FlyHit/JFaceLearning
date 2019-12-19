package fileExplorer;

import org.eclipse.jface.viewers.ITreeContentProvider;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileTreeContentProvider implements ITreeContentProvider {
    @Override
    public Object[] getElements(Object inputElement) {
        File[] roots = File.listRoots();
        List directory = new ArrayList();
        for (File file : roots) {
//            文件树是不显示文件的,只显示文件夹
            if (file.isDirectory()) {
                directory.add(file);
            }
        }
        return directory.toArray();
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        return ((File) parentElement).listFiles(new OnlyAllowFoldersFilter());
    }

    @Override
    public Object getParent(Object element) {
        return ((File) element).getParentFile();
    }

    @Override
    public boolean hasChildren(Object element) {
        Object[] obj = getChildren(element);
//        listFiles()返回的数组可能为空,因此还需加数组长度大于0的条件,否则没有children仍会显示有
        return obj != null && obj.length > 0;
    }
}

/**
 * 筛选出文件夹
 * FileFilter:文件路径名筛选器
 */
class OnlyAllowFoldersFilter implements FileFilter {
    /**
     * @param pathname 文件的路径名
     * @return 若为文件夹的路径名则为true, 否则false
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory() && !pathname.isHidden();
    }
}
