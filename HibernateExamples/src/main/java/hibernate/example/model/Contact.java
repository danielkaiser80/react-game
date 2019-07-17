package hibernate.example.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * The persistence class for the contact database table.
 */
@Entity
@Table(name = "Contact")
public class Contact {

    private int id;
    private String name;
    private Integer age;
    private String address;
    private Float salary;

    /**
     * Constructor for Hibernate
     */
    public Contact() { /* EMPTY */
    }

    public Contact(String name, int age, String address, Float salary) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Fetch(FetchMode.JOIN)
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getSalary() {
        return this.salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Contact [id=%s, name=%s, age=%s, address=%s]", id,
                name, age, address);
    }
}