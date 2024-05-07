package com.example.demo.cont.dto;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class EmployeeDTO {
    private String empName;
    private String department;
    private int amount;
    private String currency;

    	
	@JsonFormat(pattern="MMM-dd-yyyy")
	private Date joiningDate;
	
	@JsonFormat(pattern="MMM-dd-yyyy")
	private Date exitDate;

}
