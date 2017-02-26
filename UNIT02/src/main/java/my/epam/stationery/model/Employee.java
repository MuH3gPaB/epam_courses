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
