package com.tushar.completablefuture.repository;

import com.tushar.completablefuture.exception.CustomException;
import com.tushar.completablefuture.model.EmployeeProjectMapping;
import com.tushar.completablefuture.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class EmployeeRepo {
    @Autowired
    private NamedParameterJdbcTemplate dbRepository;

    public static final String GET_EMPLOYEE_DETAIL_BY_ID = "SELECT * FROM Employee WHERE empId=:empId";
    public static final String GET_ALL_EMPLOYEE = "SELECT * FROM Employee";
    public static final String GET_ALL_PROJECTS_BY_EMPID = "SELECT * FROM EmpProjectMapping Where empId=:empId";

    @Async
    public CompletableFuture<Employee> getEmployeeById(Integer empId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("empId", empId);
        Employee employee;
        try {
            employee = dbRepository.queryForObject(GET_EMPLOYEE_DETAIL_BY_ID,
                    parameterSource, BeanPropertyRowMapper.newInstance(Employee.class));
        } catch (EmptyResultDataAccessException e) {
            // if employee id doesn't exist
            throw new CustomException("No record exist for employee id - " + empId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(employee);
    }

    @Async
    public CompletableFuture<List<Employee>> getAllEmployee(){
        List<Employee> employeeList = null;
        try{
            employeeList = dbRepository.query(GET_ALL_EMPLOYEE,
                    new MapSqlParameterSource(),BeanPropertyRowMapper.newInstance(Employee.class));
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(employeeList);
    }

    public List<EmployeeProjectMapping> getAllProjectOfEmp(Integer empId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("empId", empId);
        List<EmployeeProjectMapping> empProjects = null;
        try{
            empProjects = dbRepository.query(GET_ALL_PROJECTS_BY_EMPID,
                    parameterSource,BeanPropertyRowMapper.newInstance(EmployeeProjectMapping.class));
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return empProjects;
    }
}
