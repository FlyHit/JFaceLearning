package tree;

import java.util.ArrayList;
import java.util.List;

public class CategoryEO implements TreeElement {
    private String name;
    private List lists;

    public CategoryEO(String name) {
        this.name = name;
        lists = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public boolean hasChildren() {
        return lists.size() > 0;
    }

    public List getChildren() {
        return lists;
    }

    public void add(TreeElement element) {
        lists.add(element);
    }

}
