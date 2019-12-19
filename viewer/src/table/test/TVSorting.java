package table.test;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

/**
 * 使用ViewerComparator进行表的排序
 */
public class TVSorting {

    public class Person {
        public String giveName;
        public String surname;
        public String email;

        public Person(String givenName, String surname, String email) {
            this.giveName = givenName;
            this.surname = surname;
            this.email = email;
        }
    }

    /**
     * setEditingSupport()提供编辑支持,需要EditingSupport类实例
     */
    protected abstract class AbstractEditingSupport extends EditingSupport {
        private TextCellEditor editor;

        public AbstractEditingSupport(TableViewer viewer) {
            super(viewer);
            this.editor = new TextCellEditor(viewer.getTable());
        }

        @Override
        protected boolean canEdit(Object element) {
            return true;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return editor;
        }

        @Override
        protected void setValue(Object element, Object value) {
            doSetValue(element, value);
            getViewer().update(element, null);
        }

        protected abstract void doSetValue(Object element, Object value);
    }

    public TVSorting(Shell shell) {
        TableViewer viewer = new TableViewer(shell, SWT.BORDER
                | SWT.FULL_SELECTION);
        viewer.setContentProvider(ArrayContentProvider.getInstance());

//        TableViewerColumn : 特定的列支持label provider和编辑
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

        });  // 支持编辑

        ColumnViewerComparator cSorter = new ColumnViewerComparator(viewer, column) {

            @Override
            protected int doCompare(Viewer viewer, Object e1, Object e2) {
                Person p1 = (Person) e1;
                Person p2 = (Person) e2;
                return p1.giveName.compareToIgnoreCase(p2.giveName);
            }

        };

        column = createColumnFor(viewer, "Surname");
        column.setLabelProvider(new ColumnLabelProvider() {

            @Override
            public String getText(Object element) {
                return ((Person) element).surname;
            }

        });

        column.setEditingSupport(new AbstractEditingSupport(viewer) {

            @Override
            protected Object getValue(Object element) {
                return ((Person) element).surname;
            }

            @Override
            protected void doSetValue(Object element, Object value) {
                ((Person) element).surname = value.toString();
            }

        });

        new ColumnViewerComparator(viewer, column) {

            @Override
            protected int doCompare(Viewer viewer, Object e1, Object e2) {
                Person p1 = (Person) e1;
                Person p2 = (Person) e2;
                return p1.surname.compareToIgnoreCase(p2.surname);
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

        new ColumnViewerComparator(viewer, column) {

            @Override
            protected int doCompare(Viewer viewer, Object e1, Object e2) {
                Person p1 = (Person) e1;
                Person p2 = (Person) e2;
                return p1.email.compareToIgnoreCase(p2.email);
            }

        };

        viewer.setInput(createModel());
        viewer.getTable().setLinesVisible(true);
        viewer.getTable().setHeaderVisible(true);
        cSorter.setSorter(cSorter, ColumnViewerComparator.ASC);
    }

    /**
     * 创建可移动带标签的列
     *
     * @param viewer 列所属的table viewer
     * @param label  列的标签
     * @return 支持标签和编辑的列
     */
    private TableViewerColumn createColumnFor(TableViewer viewer, String label) {
        TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);  // 新建table viewer的列
        column.getColumn().setWidth(200);
        column.getColumn().setText(label);
        column.getColumn().setMoveable(true);
        return column;
    }

    private Person[] createModel() {
        return new Person[]{
                new Person("Tom", "Schindl", "tom.schindl@bestsolution.at"),
                new Person("Boris", "Bokowski", "Boris_Bokowski@ca.ibm.com"),
                new Person("Tod", "Creasey", "Tod_Creasey@ca.ibm.com"),
                new Person("Wayne", "Beaton", "wayne@eclipse.org"),
                new Person("Jeanderson", "Candido", "jeandersonbc@gmail.com"),
                new Person("Lars", "Vogel", "Lars.Vogel@gmail.com"),
                new Person("Hendrik", "Still", "hendrik.still@gammas.de")};
    }

    private static abstract class ColumnViewerComparator extends ViewerComparator {

        public static final int ASC = 1;
        public static final int NONE = 0;
        public static final int DESC = -1;

        private int direction = 0;
        private TableViewerColumn column;
        private ColumnViewer viewer;

        /**
         * 构造column viewer比较器(为待排序的列添加单击鼠标事件)
         *
         * @param viewer 比较器适用的ColumnViewer
         * @param column 排序的列
         */
        public ColumnViewerComparator(ColumnViewer viewer, TableViewerColumn column) {
            this.column = column;
            this.viewer = viewer;
            SelectionAdapter selectionAdapter = createSelectionAdapter();
            this.column.getColumn().addSelectionListener(selectionAdapter);
        }

        private SelectionAdapter createSelectionAdapter() {
            return new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
//                    内部匿名类访问实例域的方式:ColumnViewerComparator.this.viewer
                    if (ColumnViewerComparator.this.viewer.getComparator() != null) {
                        if (ColumnViewerComparator.this.viewer.getComparator() == ColumnViewerComparator.this) {
                            int tdirection = ColumnViewerComparator.this.direction;
                            if (tdirection == ASC) {
                                setSorter(ColumnViewerComparator.this, DESC);
                            } else if (tdirection == DESC) {
                                setSorter(ColumnViewerComparator.this, NONE);
                            }
                        } else {
                            setSorter(ColumnViewerComparator.this, ASC);
                        }
                    } else {
                        setSorter(ColumnViewerComparator.this, ASC);
                    }
                }
            };
        }

        /**
         * @param sorter
         * @param direction
         */
        public void setSorter(ColumnViewerComparator sorter, int direction) {
            Table columnParent = column.getColumn().getParent();
            if (direction == NONE) {
                columnParent.setSortColumn(null);
                columnParent.setSortDirection(SWT.NONE);
                viewer.setComparator(null);
            } else {
                columnParent.setSortColumn(column.getColumn());
                sorter.direction = direction;
                columnParent.setSortDirection(direction == ASC ? SWT.DOWN : SWT.UP);

                if (viewer.getComparator() == sorter) {
                    viewer.refresh();
                } else {
                    viewer.setComparator(sorter);
                }
            }
        }

        /**
         * @param viewer 比较元素所在viewer
         * @param e1     比较的元素1
         * @param e2     比较的元素2
         * @return
         */
        @Override
        public int compare(Viewer viewer, Object e1, Object e2) {
            return direction * doCompare(viewer, e1, e2);
        }

        protected abstract int doCompare(Viewer viewer, Object e1, Object e2);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Display display = new Display();

        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        new TVSorting(shell);
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}
