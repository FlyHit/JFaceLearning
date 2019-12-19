package table.tableOperation;

public class Person {
    public String giveName;
    public String gender;
    public String email;

    public Person(String givenName, String gender, String email) {
        this.giveName = givenName;
        this.gender = gender;
        this.email = email;
    }

    public String getGiveName() {
        return giveName;
    }

    public void setGiveName(String giveName) {
        this.giveName = giveName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
