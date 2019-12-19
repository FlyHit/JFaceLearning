package tree;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class TreeWindow extends ApplicationWindow {
    private TreeViewer tree;
    private Menu popUpMenu;
    private List data;

    public TreeWindow() {
        super(null);
        initData();
    }

    private void initData() {
        data = new ArrayList();
        CategoryEO cate1 = new CategoryEO("ͼ��");
        cate1.add(new CategoryEO("С˵"));
        cate1.add(new ProductEO("Eclispe"));
        cate1.add(new ProductEO("SWT"));
        data.add(cate1);
        CategoryEO cate2 = new CategoryEO("����");
        data.add(cate2);
        ProductEO product1 = new ProductEO("��װ");
        data.add(product1);
    }

    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setSize(200, 300);
        shell.setText("TreeViewer����ʾ��");
    }

    protected Control createContents(Composite parent) {
        initTree(parent);
        createContextMenu(parent);
        return parent;
    }

    private void initTree(Composite parent) {
        tree = new TreeViewer(parent);
        tree.setContentProvider(new TreeContentProvider());
        tree.setLabelProvider(new TreeLabelProvider());
        tree.setInput(data);
    }

    private void createContextMenu(Composite parent) {
        MenuManager top = new MenuManager();
        top.add(new NewProductAction());
        top.add(new NewCategoryAction());
        top.add(new Separator());
        top.add(new DeleteAction());
        popUpMenu = top.createContextMenu(parent);
        tree.getTree().setMenu(popUpMenu);

    }

    private TreeElement getSelectElement() {
        StructuredSelection select = (StructuredSelection) tree.getSelection();
        TreeElement element = (TreeElement) select.getFirstElement();
        TreeViewer treeViewer = (TreeViewer) select.getFirstElement();
        return element;
    }

    public static void main(String[] args) {
        TreeWindow test = new TreeWindow();
        test.setBlockOnOpen(true);
        test.open();
        Display.getCurrent().dispose();
    }

    public class NewProductAction extends Action {
        NewProductAction() {
            super("�½���Ʒ");
        }

        public void run() {
            TreeElement element = getSelectElement();
            InputDialog input = new InputDialog(getShell(), " �������Ʒ", "��Ʒ��", "", null);
            int rt = input.open();
            if (rt == OK)
                tree.add(element, new ProductEO(input.getValue()));
        }
    }

    public class NewCategoryAction extends Action {
        NewCategoryAction() {
            super("�½����");
        }

        public void run() {
            TreeElement element = getSelectElement();
            if (element instanceof ProductEO)
                return;
            InputDialog input = new InputDialog(getShell(), " ���������", "�����", "", null);
            int rt = input.open();
            if (rt == OK)
                tree.add(element, new CategoryEO(input.getValue()));
        }
    }

    public class DeleteAction extends Action {
        DeleteAction() {
            super("ɾ��");
        }

        public void run() {
            TreeElement element = getSelectElement();
            tree.remove(element);
        }
    }
}
