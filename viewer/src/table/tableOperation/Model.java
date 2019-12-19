package table.tableOperation;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Person> TVModel;
    public static int num = 1;

    public Model() {
        this.TVModel = new ArrayList<Person>();
        TVModel.add(new Person("A", "male", "1"));
        TVModel.add(new Person("B", "male", "3"));
        TVModel.add(new Person("C", "female", "4"));
        TVModel.add(new Person("F", "male", "5"));
        TVModel.add(new Person("E", "female", "2"));
        TVModel.add(new Person("G", "male", "6"));
        TVModel.add(new Person("H", "male", "8"));
    }

    public List<Person> getTVModel() {
        return TVModel;
    }

    public void setTVModel(List<Person> TVModel) {
        this.TVModel = TVModel;
    }

    public void addPerson() {
        this.TVModel.add(new Person("name" + num, "male", "None"));
        num++;
    }

    public void deletePerson(int index) {
        this.TVModel.remove(index);
    }
}
