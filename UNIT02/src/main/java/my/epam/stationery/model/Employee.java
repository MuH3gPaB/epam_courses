package my.epam.stationery.model;

import my.epam.stationery.entity.HasId;
import my.epam.stationery.entity.AbstractEntity;

public class Employee implements HasId{
    private final Long id;
    private final String firstName;
    private final String secondName;
    private final String department;

    public Employee(String firstName, String secondName, String department) {
        this.id = null;
        this.firstName = firstName;
        this.secondName = secondName;
        this.department = department;
    }

    private Employee(Long id, String firstName, String secondName, String department) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.department = department;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (secondName != null ? !secondName.equals(employee.secondName) : employee.secondName != null) return false;
        return department != null ? department.equals(employee.department) : employee.department == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }

    static class Entity implements AbstractEntity<Employee> {
        private Long id;
        private String firstName;
        private String secondName;
        private String department;


        public Entity() {
        }

        @Override
        public Employee build() {
            return new Employee(id, firstName, secondName, department);
        }
    }
}
