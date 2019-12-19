package table.test;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * TableViewer的简单使用
 */
public class TableViewerTest {
    /**
     * IContentProvider : A content provider mediates between the viewer's model and the viewer itself.
     * (即当viewer的输入发生改变时根据model进行更新)
     * IStructuredContentProvider : An interface to content providers for structured viewers.
     * ArrayContentProvider : This implementation of <code>IStructuredContentProvider</code> handles
     * the case where the viewer input is an unchanging array or collection of elements.
     */
    private class MyContentProvider implements IStructuredContentProvider {
        /**
         * These elements can be presented as rows in a table, items in a list, etc.
         *
         * @param inputElement 输入的元素
         * @return the elements to display in the viewer when its input is set to the given element.
         */
        @Override
        public Object[] getElements(Object inputElement) {
            return (MyModel[]) inputElement;
        }
    }

    public class MyModel {
        public int counter;

        public MyModel(int counter) {
            this.counter = counter;
        }

        @Override
        public String toString() {
            return "Item " + this.counter;
        }
    }

    public TableViewerTest(Shell shell) {
        final TableViewer v = new TableViewer(shell);
//        v.setLabelProvider(new LabelProvider());
//        出于演示的目的,这里使用了自定义的content provider,可以选择使用ArrayContentProvider.getInstance()
        v.setContentProvider(new MyContentProvider());
        createColumn(v.getTable(), "Values");
        MyModel[] model = createModel();
//        ContentViewer对该Viewer方法的实现调用了inputChanged及其挂钩的方法
        v.setInput(model);
        v.getTable().setLinesVisible(true);
    }

    /**
     * 新创建的TableViewer中的表不含columns,需自己创建
     *
     * @param tb   列所属的表
     * @param text 列名
     */
    public void createColumn(Table tb, String text) {
        TableColumn column = new TableColumn(tb, SWT.NONE);
        column.setWidth(100);
        column.setText(text);
        tb.setHeaderVisible(true);
    }

    private MyModel[] createModel() {
        MyModel[] elements = new MyModel[10];

        for (int i = 0; i < 10; i++) {
            elements[i] = new MyModel(i);
        }

        return elements;
    }

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        new TableViewerTest(shell);
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }

        display.dispose();

    }
}