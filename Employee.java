import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
abstract class Employee {

    public Employee (){};

    abstract public void setSalary(int salary);

    abstract public int getSalary();

    abstract public void setGrade(String grade);

    abstract public String getGrade();

    void label(){
        System.out.println("Employee's data:");
    }

}

class Engineer extends Employee {

    private int salary;
    private String grade;

    public Engineer(){
        super();
    }

    public void setSalary(int salary){
        this.salary = salary;
    }

    public int getSalary(){
        return this.salary;
    }

    public void setGrade(String grade){
        this.grade = grade;
    }

    public String getGrade(){
        return this.grade;
    }


}

class Manager extends Employee {

    private int salary;
    private String grade;

    public void setSalary(int salary){
        this.salary = salary;
    }

    public int getSalary(){
        return this.salary;
    }

    public void setGrade(String grade){
        this.grade = grade;
    }

    public String getGrade(){
        return this.grade;
    }


}
