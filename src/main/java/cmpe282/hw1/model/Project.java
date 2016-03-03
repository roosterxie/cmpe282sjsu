package cmpe282.hw1.model;

import org.springframework.data.annotation.Id;

public class Project {
    @Id
    private String ID;

    private int id;

    private String name;
    private float budget;

    public Project(){}

    public Project(int id, String name, float budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name){
        this.name=name;
    }

    public float getbudget(){
        return budget;
    }


    public void setbudget(float budget){
        this.budget=budget;
    }
}
