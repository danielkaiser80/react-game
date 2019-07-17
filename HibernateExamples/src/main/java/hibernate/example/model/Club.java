package hibernate.example.model;

import java.util.Set;

/**
 * An example of a club
 */
class Club {

    private Set<Person> members;

    Set<Person> getMembers() {
        return members;
    }
}