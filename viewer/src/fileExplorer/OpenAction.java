package fileExplorer;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.program.Program;

import java.io.File;

public class OpenAction implements ISelectionChangedListener, IDoubleClickListener {
    private TableViewer tableViewer;
    private TreeViewer treeViewer;

    public OpenAction(TableViewer tableViewer, TreeViewer treeViewer) {
        this.tableViewer = tableViewer;
        this.treeViewer = treeViewer;
    }

    /**
     * 单击树形目录,文件夹的内容显示在表格上
     *
     * @param event
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
        tableViewer.setInput(selection.getFirstElement());
    }

    /**
     * 双击树形目录,展开目录;双击表格中的文件,打开文件
     *
     * @param event
     */
    @Override
    public void doubleClick(DoubleClickEvent event) {
        if (event.getSource() instanceof TreeViewer) {
            final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
            if (selection == null || selection.isEmpty())
                return;

            final Object sel = selection.getFirstElement();

            final ITreeContentProvider provider = (ITreeContentProvider) treeViewer.getContentProvider();

            if (!provider.hasChildren(sel))
                return;

            if (treeViewer.getExpandedState(sel))
                treeViewer.collapseToLevel(sel, AbstractTreeViewer.ALL_LEVELS);
            else
                treeViewer.expandToLevel(sel, 1);
        } else if (event.getSource() instanceof TableViewer) {
            Object selection = getTableSelection();
            if (selection == null)
                return;
            File file = (File) selection;
            if (file.isFile()) {
                Program.launch(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                tableViewer.setInput(selection);
            }
        }
    }

    public Object getTableSelection() {
        IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
        if (selection.size() != 1)
            return null;
        return selection.getFirstElement();
    }
}
