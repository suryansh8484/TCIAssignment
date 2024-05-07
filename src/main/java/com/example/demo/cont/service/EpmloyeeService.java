package com.example.demo.cont.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.cont.dto.EmployeeDTO;
import com.example.demo.cont.entity.Department;
import com.example.demo.cont.entity.Employee;
import com.example.demo.cont.repo.DepartmentRepo;
import com.example.demo.cont.repo.EmployeeRepo;

@Service
public class EpmloyeeService {

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private DepartmentRepo departmentRepository;

    public void addEmployee(EmployeeDTO employeeDTO){

        Employee employee = new Employee();
        employee.setEmpName(employeeDTO.getEmpName());
        employee.setAmount(employeeDTO.getAmount());
        employee.setCurrency(employeeDTO.getCurrency());
        employee.setJoiningDate(employeeDTO.getJoiningDate());
        employee.setExitDate(employeeDTO.getExitDate());

        Department department = departmentRepository.findByName(employeeDTO.getDepartment());
        if (department == null) {
            department = new Department();
            department.setName(employeeDTO.getDepartment());
            departmentRepository.save(department);
        }
        employee.setDepartment(department);

        employeeRepository.save(employee);
    }

    public List<Map<String,Object>> getEligibleEmployeesForBonus(String dateStr) throws ParseException {

        try{
            dateStr = dateStr.replaceAll("\"", "");
		Date date = null;
	    SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy");
	    date = formatter.parse(dateStr);

        List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> eligibleEmployees = filterEligibleEmployees(allEmployees, date);
        return groupByCurrency(eligibleEmployees);
        }
        catch (Exception e) {
        throw new ParseException(dateStr, 0);
        }
    }

    private List<Map<String, Object>> groupByCurrency(List<Employee> employees) throws Exception {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getCurrency))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> currencyMap = new HashMap<>();
                    currencyMap.put("currency", entry.getKey());
                    List<Map<String, Object>> employeesList = entry.getValue().stream()
                            .map(employee -> {
                                Map<String, Object> employeeMap = new HashMap<>();
                                employeeMap.put("empName", employee.getEmpName());
                                employeeMap.put("amount", employee.getAmount());
                                return employeeMap;
                            })
                            .collect(Collectors.toList());
                    currencyMap.put("employees", employeesList);
                    return currencyMap;
                })
                .collect(Collectors.toList());
    }
    

    private List<Employee> filterEligibleEmployees(List<Employee> employees, Date date)throws Exception {
        return employees.stream()
                .filter(employee -> employee.getJoiningDate().before(date) && (employee.getExitDate() == null || employee.getExitDate().after(date)))
                .collect(Collectors.toList());
    }

    

}
    

