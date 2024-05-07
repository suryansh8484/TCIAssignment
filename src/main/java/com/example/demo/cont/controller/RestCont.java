package com.example.demo.cont.controller;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.cont.dto.EmployeeDTO;
import com.example.demo.cont.dto.EpmlBonusResp;
import com.example.demo.cont.service.EpmloyeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.text.ParseException;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
;

@RestController
public class RestCont {

    @Autowired
    private EpmloyeeService epmloyeeService;

    @PostMapping("/tci/employee-bonus")
    public ResponseEntity<String> storeEmployeeDetails(@RequestBody Map<String,List<EmployeeDTO>> employeeDTO ) {
       
       try{
        List<EmployeeDTO> empl = employeeDTO.get("employees");
       for(EmployeeDTO emp : empl){
           epmloyeeService.addEmployee(emp);  
       }
       System.out.println(employeeDTO.toString());
       return ResponseEntity.ok(employeeDTO.toString());
    }
     catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
        
    }
    
    @GetMapping("/tci/employee-bonus")
    public ResponseEntity<EpmlBonusResp> getEligibleEmployees(@RequestParam String date) throws ParseException {
        try{
            List<Map<String,Object>> response = epmloyeeService.getEligibleEmployeesForBonus(date);
        EpmlBonusResp empBonusResp = new EpmlBonusResp();
        empBonusResp.setData(response);
        empBonusResp.setErrorMessage("");
        return ResponseEntity.ok(empBonusResp);
        }
        catch (ParseException e) {
            EpmlBonusResp errorResponse = new EpmlBonusResp();
            errorResponse.setErrorMessage("Invalid date format: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            EpmlBonusResp errorResponse = new EpmlBonusResp();
            errorResponse.setErrorMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        }

    
}
