package fileExplorer;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import toolkit.TVComparator;

import java.io.File;

public class MyFileExplorer extends ApplicationWindow {
    private SashForm sashForm;
    private Composite treeComposite;
    private Composite tableComposite;
    private TreeViewer treeViewer;
    private TableViewer tableViewer;
    private OpenAction openAction;

    /**
     * 创建应用窗口
     */
    public MyFileExplorer() {
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
        container.setLayout(new FillLayout());
        sashForm = new SashForm(container, SWT.BORDER);
        sashForm.setLayout(new FillLayout());
        treeComposite = new Composite(sashForm, SWT.NONE);
        treeComposite.setLayout(new FillLayout());
        addTree();
        tableComposite = new Composite(sashForm, SWT.NONE);
        tableComposite.setLayout(new FillLayout());
        addTable();
        treeViewer.addSelectionChangedListener(new OpenAction(tableViewer, treeViewer));
        treeViewer.addDoubleClickListener(new OpenAction(tableViewer, treeViewer));
        tableViewer.addDoubleClickListener(new OpenAction(tableViewer, treeViewer));
        sashForm.setWeights(new int[]{40, 60});

        return container;
    }

    private void addTree() {
        treeViewer = new TreeViewer(treeComposite, SWT.SINGLE);
        treeViewer.setContentProvider(new FileTreeContentProvider());
        treeViewer.setLabelProvider(new FileTreeLabelProvider(MyFileExplorer.this.getShell().getDisplay()));
        treeViewer.setInput("root");  // ？
        treeViewer.setSelection(null);
//        // 设置item高度的方法
//        treeViewer.getTree().addListener(SWT.MeasureItem,event -> {
//            event.height = 50;
//        });
    }


    private void addTable() {
        tableViewer = new TableViewer(tableComposite, SWT.BORDER
                | SWT.FULL_SELECTION | SWT.MULTI);
        tableViewer.setContentProvider(new FileTableContentProvider());
        TableViewerColumn column = createColumnFor(tableViewer, "名称");
        new TVComparator(tableViewer, column) {
            @Override
            public int doCompare(Viewer viewer, Object e1, Object e2) {
                File f1 = (File) e1;
                File f2 = (File) e2;
                if (f1.isDirectory() && f2.isFile()) {
                    return -1;
                } else if (f1.isFile() && f2.isDirectory()) {
                    return 1;
                } else {
                    return f1.getName().compareToIgnoreCase(f2.getName());
                }
            }
        };
        column = createColumnFor(tableViewer, "修改日期");
        new TVComparator(tableViewer, column) {
            @Override
            public int doCompare(Viewer viewer, Object e1, Object e2) {
                File f1 = (File) e1;
                File f2 = (File) e2;
                if (f1.isDirectory() && f2.isFile()) {
                    return -1;
                } else if (f1.isFile() && f2.isDirectory()) {
                    return 1;
                } else {
                    if (f1.lastModified() > f2.lastModified()) {
                        return 1;
                    } else if (f1.lastModified() < f2.lastModified()) {
                        return -1;
                    } else return 0;
                }
            }
        };
        column = createColumnFor(tableViewer, "类型");
        column = createColumnFor(tableViewer, "大小");
        new TVComparator(tableViewer, column) {
            @Override
            public int doCompare(Viewer viewer, Object e1, Object e2) {
                File f1 = (File) e1;
                File f2 = (File) e2;
                if (f1.isDirectory() && f2.isFile()) {
                    return -1;
                } else if (f1.isFile() && f2.isDirectory()) {
                    return 1;
                } else {
                    if (f1.length() > f2.length()) {
                        return 1;
                    } else if (f1.length() < f2.length()) {
                        return -1;
                    } else return 0;
                }
            }
        };
        tableViewer.setLabelProvider(new FileTableLabelProvider(MyFileExplorer.this.getShell().getDisplay()));
        tableViewer.getTable().setHeaderVisible(true);
    }

    private TableViewerColumn createColumnFor(TableViewer viewer, String label) {
        TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);  // 新建table viewer的列
        column.getColumn().setWidth(200);
        column.getColumn().setText(label);
        column.getColumn().setMoveable(true);
        return column;
    }

    /**
     * 加载应用
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            MyFileExplorer window = new MyFileExplorer();
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
        newShell.setSize(1200, 800);
        newShell.setText("文件管理器");
    }

    /**
     * @return 窗体的初始尺寸
     */
    @Override
    protected Point getInitialSize() {
        return new Point(450, 300);
    }
}