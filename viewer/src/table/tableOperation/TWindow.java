package table.tableOperation;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbenchActionConstants;
import toolkit.AbstractEditingSupport;
import toolkit.TVComparator;

public class TWindow extends ApplicationWindow {
    private TableViewer viewer;
    private Model model;
    public static String name = "";
    public static String gender = "";
    public static String email = "";

    /**
     * 创建应用窗口
     */
    public TWindow() {
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
        viewer = new TableViewer(container, SWT.BORDER
                | SWT.FULL_SELECTION);
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        TableViewerColumn column = createColumnFor(viewer, "GivenName");
        column.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return ((Person) element).giveName;
            }
        });
        column.setEditingSupport(new AbstractEditingSupport(viewer) {

            @Override
            protected Object getValue(Object element) {
                return ((Person) element).giveName;
            }

            @Override
            protected void doSetValue(Object element, Object value) {
                ((Person) element).giveName = value.toString();
            }

        });
        TVComparator cSorter = new TVComparator(viewer, column) {

            @Override
            public int doCompare(Viewer viewer, Object e1, Object e2) {
                Person p1 = (Person) e1;
                Person p2 = (Person) e2;
                return p1.giveName.compareToIgnoreCase(p2.giveName);
            }
        };

        column = createColumnFor(viewer, "gender");
        column.setLabelProvider(new ColumnLabelProvider() {

            @Override
            public String getText(Object element) {
                return ((Person) element).gender;
            }

        });

        column.setEditingSupport(new AbstractEditingSupport(viewer) {

            @Override
            protected Object getValue(Object element) {
                return ((Person) element).gender;
            }

            @Override
            protected void doSetValue(Object element, Object value) {
                ((Person) element).gender = value.toString();
            }

        });
        new TVComparator(viewer, column) {

            @Override
            public int doCompare(Viewer viewer, Object e1, Object e2) {
                Person p1 = (Person) e1;
                Person p2 = (Person) e2;
                return p1.gender.compareToIgnoreCase(p2.gender);
            }
        };

        column = createColumnFor(viewer, "E-Mail");
        column.setLabelProvider(new ColumnLabelProvider() {

            @Override
            public String getText(Object element) {
                return ((Person) element).email;
            }

        });

        column.setEditingSupport(new AbstractEditingSupport(viewer) {

            @Override
            protected Object getValue(Object element) {
                return ((Person) element).email;
            }

            @Override
            protected void doSetValue(Object element, Object value) {
                ((Person) element).email = value.toString();
            }

        });
        new TVComparator(viewer, column) {

            @Override
            public int doCompare(Viewer viewer, Object e1, Object e2) {
                Person p1 = (Person) e1;
                Person p2 = (Person) e2;
                return p1.email.compareToIgnoreCase(p2.email);
            }

        };

        model = new Model();
        viewer.setInput(model.getTVModel());
        viewer.getTable().setLinesVisible(true);
        viewer.getTable().setHeaderVisible(true);
        cSorter.setSorter(cSorter, TVComparator.ASC);
        createContextMenu(viewer);

        return container;
    }

    private TableViewerColumn createColumnFor(TableViewer viewer, String label) {
        TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);  // 新建table viewer的列
        column.getColumn().setWidth(200);
        column.getColumn().setText(label);
        column.getColumn().setMoveable(true);
        return column;
    }

    /**
     * 创建右键菜单
     *
     * @param viewer 右键菜单所属的viewer
     */
    protected void createContextMenu(Viewer viewer) {
        MenuManager contextMenu = new MenuManager("#ViewerMenu");
        contextMenu.setRemoveAllWhenShown(true);
        contextMenu.addMenuListener(mgr -> fillContextMenu(mgr));

        Menu menu = contextMenu.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
    }

    /**
     * 填充动态的右键菜单
     *
     * @param contextMenu
     */
    protected void fillContextMenu(IMenuManager contextMenu) {
        contextMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

        contextMenu.add(new CAction("add", viewer, model));

        contextMenu.add(new CAction("delete", viewer, model));

        contextMenu.add(new CAction("filter", viewer, TWindow.this.getShell()));
    }

    /**
     * 加载应用
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            TWindow window = new TWindow();
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
        newShell.setSize(800, 400);
        newShell.setText("MVC表");
    }
}