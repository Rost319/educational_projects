package com.zaurtregulov.spring.rest;

import com.zaurtregulov.spring.rest.configuration.MyConfig;
import com.zaurtregulov.spring.rest.entity.Employee;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);

//        List<Employee> allEmployees = communication.getAllEmployees();
//        allEmployees.stream().forEach(s -> System.out.println(s));

//        System.out.println(communication.getEmployee(140));

//        Employee employee = new Employee("Sveta", "Sokolova", "IT", 1200);
//        employee.setId(18);
//        communication.saveEmployee(employee);

        communication.deleteEmployee(18);
    }
}
