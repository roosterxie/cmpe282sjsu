package cmpe282.hw1.model;

import org.springframework.data.annotation.Id;

public class Employee {

    @Id
    private String ID;

    private int id;
    private String firstName;
    private String lastName;

    public Employee(){}

    public Employee(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName){
        this.firstName=firstName;
    }

    public String getlastName(){
        return lastName;
    }


    public void setlastName(String lastName){
        this.lastName=lastName;
    }
}
