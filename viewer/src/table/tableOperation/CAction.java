package table.tableOperation;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;

public class CAction extends Action {
    private String name;
    private TableViewer viewer;
    private Model model;
    private Shell shell;

    public CAction(String name, TableViewer viewer, Model model) {
        super(name);
        this.name = name;
        this.viewer = viewer;
        this.model = model;
    }

    public CAction(String name, TableViewer viewer, Shell shell) {
        super(name);
        this.name = name;
        this.viewer = viewer;
        this.shell = shell;
    }

    @Override
    public void run() {
        switch (name) {
            case "add":
                model.addPerson();
                viewer.refresh();
                break;
            case "delete":
                int index = viewer.getTable().getSelectionIndex();
                if (index >= 0) {
                    model.deletePerson(index);
                    viewer.refresh();
                }
                break;
            case "filter":
                new FilterDialog(shell).open();
                viewer.addFilter(new ViewerFilter() {
                    @Override
                    public boolean select(Viewer viewer, Object parentElement, Object element) {
                        boolean nameFilter = ((Person) element).giveName.equals(TWindow.name) || TWindow.name.isEmpty();
                        boolean genderFilter = ((Person) element).gender.equals(TWindow.gender) || TWindow.gender.isEmpty();
                        boolean emailFilter = ((Person) element).email.equals(TWindow.email) || TWindow.email.isEmpty();
                        return nameFilter && genderFilter && emailFilter;
                    }
                });
                break;
        }
    }
}
