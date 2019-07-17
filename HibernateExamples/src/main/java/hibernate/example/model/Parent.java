package hibernate.example.model;

import java.util.HashSet;
import java.util.Set;

public class Parent {

    private int id;

    private Set<Child> children;

    public Parent() {
        this.children = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {

        this.children = children;
    }

    public void addChild(Child c) {
        c.setParent(this);
        children.add(c);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", has children=" + children.size() +
                '}';
    }
}
