package com.tushar.completablefuture.controller;

import com.tushar.completablefuture.model.Employee;
import com.tushar.completablefuture.model.EmployeeProjectDetails;
import com.tushar.completablefuture.model.Response;
import com.tushar.completablefuture.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller class for employee related API
 * @author tushar.narang
 */

@RestController
@RequestMapping("/async")
public class EmployeeController {
    @Autowired
    private EmployeeService empService;

    @GetMapping("/id")
    public Response<Employee> getEmployeeById(@PathVariable int empId){
        Response<Employee> response = new Response<>();
        Employee employee = empService.getEmployeeById(empId);
        if(employee != null){
            response.setData(employee);
            response.setSuccessMessage("Fetched employee data successfully!");
        }else{
            response.setErrorMessage("No record found for the given employee Id- " + empId);
        }
        return response;
    }

    @GetMapping("/all")
    public Response<List<Employee>> getAllEmployees(){
        Response<List<Employee>> response = new Response<>();
        List<Employee> employeeList = empService.getAllEmployee();
        if(employeeList != null && employeeList.size() >0){
            response.setData(employeeList);
            response.setSuccessMessage("All active records fetched successfully!");
        }else{
            response.setErrorMessage("No data found for any active records");
        }
        return response;
    }

    @GetMapping("/emp/projects/empId")
    public Response<EmployeeProjectDetails> getAllProjectsOfEmp(@PathVariable int empId){
        Response<EmployeeProjectDetails> response = new Response<>();
        EmployeeProjectDetails employeeProjectDetails = empService.getAllEmployeeProjectsById(empId);
        if(employeeProjectDetails != null){
            response.setData(employeeProjectDetails);
            response.setSuccessMessage("Fetched all projects of " + employeeProjectDetails.getEmpName());
        }else{
            response.setErrorMessage("No data found for any projects for employee id - " + empId);
        }
        return response;
    }
}
