package toolkit;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.internal.dialogs.ViewComparator;

public abstract class TVComparator extends ViewComparator {
    public static final int ASC = 1;
    public static final int NONE = 0;
    public static final int DESC = -1;

    private int direction = 0;
    private TableViewerColumn column;
    private ColumnViewer viewer;

    public TVComparator(ColumnViewer viewer, TableViewerColumn column) {
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
                if (TVComparator.this.viewer.getComparator() != null) {
                    if (TVComparator.this.viewer.getComparator() == TVComparator.this) {
                        int tdirection = TVComparator.this.direction;
                        if (tdirection == ASC) {
                            setSorter(TVComparator.this, DESC);
                        } else if (tdirection == DESC) {
                            setSorter(TVComparator.this, NONE);
                        }
                    } else {
                        setSorter(TVComparator.this, ASC);
                    }
                } else {
                    setSorter(TVComparator.this, ASC);
                }
            }
        };
    }

    public void setSorter(TVComparator sorter, int direction) {
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

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        return direction * doCompare(viewer, e1, e2);
    }

    public abstract int doCompare(Viewer viewer, Object e1, Object e2);
}


