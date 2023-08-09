package in.nareshit.raghu.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nareshit.raghu.entity.Employee;
import in.nareshit.raghu.exception.EmployeeNotFoundException;
import in.nareshit.raghu.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "SAMPLE DATA")
@RestController
@RequestMapping("/api/employee")
@CrossOrigin("http://localhost:4200/")
public class EmployeeRestController {

	@Autowired
	private IEmployeeService service; //HAS-A
	
	@ApiOperation("CREATING ONE EMPLOYEE")
	@PostMapping("/create")
	public ResponseEntity<String> createEmployee(
			@RequestBody Employee employee) 
	{
		Long id = service.createEmployee(employee);
		String message = "Employee '"+id+"' created!";
		return new ResponseEntity<String>(message,HttpStatus.OK);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Employee>> findAllEmployees() {
		List<Employee> list = service.findAllEmployees();
		return new ResponseEntity<List<Employee>>(list,HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findOneEmployee(
			@PathVariable Long id
			) 
	{
		ResponseEntity<?> resp = null;
		try {
			Employee employee =  service.findOneEmployee(id);
			resp = new ResponseEntity<Employee>(employee,HttpStatus.OK); //200
		} catch (EmployeeNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		return resp;
	}
	
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> deleteEmployee(
			@PathVariable Long id
			)
	{
		ResponseEntity<String> resp = null;
		try {
			service.deleteOneEmployee(id);
			resp = new ResponseEntity<String>("Employee Deleted",HttpStatus.OK);
		} catch (EmployeeNotFoundException e) {
			e.printStackTrace();
			throw e;//calls Global Handler
		}
		
		return resp;
	}
 	
	@PutMapping("/modify")
	public ResponseEntity<String> updateEmployee(
			@RequestBody Employee employee
			) 
	{
		ResponseEntity<String> response = null;
		try {
			service.updateEmployee(employee);
			response = new ResponseEntity<String>("Employee Updated!",HttpStatus.OK);
		} catch (EmployeeNotFoundException e) {
			e.printStackTrace();
			throw e;//calls Global Handler
		}
		return response;
	}
	
	@PatchMapping("/modify/name/{id}/{name}")
	public ResponseEntity<String> updateEmployeeName(
			@PathVariable Long id,
			@PathVariable String name
			) 
	{
		ResponseEntity<String> response = null;
		try {
			service.updateEmployeeName(name, id);
			response = new ResponseEntity<String>("Employee Name Updated!",HttpStatus.OK);
		} catch (EmployeeNotFoundException e) {
			e.printStackTrace();
			throw e;//calls Global Handler
		}
		return response;
	}
	
	
}
