package by.alexander.security.entity;

import java.util.Objects;

public class Student {

    private final Long id;
    private final String firstName;

    public Student(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(firstName, student.firstName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
