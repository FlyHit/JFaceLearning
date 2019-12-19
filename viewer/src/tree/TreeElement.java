package tree;

import java.util.List;

public interface TreeElement {
    String getName();

    boolean hasChildren();

    List getChildren();
}
