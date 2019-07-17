package hibernate.example.model;

public class Child {

    private int id;
    private Parent parent;

    public Child() {
        /* empty constructor for hibernate */
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", parent=" + parent +
                '}';
    }
}
