package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.entity.Employee;
import in.nareshit.raghu.exception.EmployeeNotFoundException;
import in.nareshit.raghu.repo.EmployeeRepository;
import in.nareshit.raghu.service.IEmployeeService;

@Service
public class EmployeeServiceImpl 
	implements IEmployeeService
{

	@Autowired
	private EmployeeRepository repo; //HAS-A

	@Override
	public Long createEmployee(Employee employee) {
		employee = repo.save(employee);
		return employee.getEmpId();
	}

	@Override
	public List<Employee> findAllEmployees() {
		List<Employee> list = repo.findAll();
		return list;
	}
	
	@Override
	public Employee findOneEmployee(Long id) {
		Optional<Employee> opt = repo.findById(id);
		if(opt.isPresent()) 
			return opt.get();
		else
			throw new EmployeeNotFoundException("Employee '"+id+"' Not exist");
		/*return repo.findById(id).orElseThrow(
				()->new EmployeeNotFoundException("Employee '"+id+"' Not exist"));*/
	}
	
	@Override
	public void deleteOneEmployee(Long id) {
		//repo.deleteById(id);
		repo.delete(findOneEmployee(id));
	}
	
	@Override
	public void updateEmployee(Employee emp) {
		Long id = emp.getEmpId();
		if(id!=null && repo.existsById(id)) {
			//id exist in DB then
			repo.save(emp);
		} else {
			throw new EmployeeNotFoundException("Employee '"+id+"' Not exist");
		}
	}

	@Override
	@Transactional
	public int updateEmployeeName(String ename, Long id) {
		if(id!=null && repo.existsById(id)) {
			//id exist in DB then
			return repo.updateEmployeeName(ename, id);
		} else {
			throw new EmployeeNotFoundException("Employee '"+id+"' Not exist");
		}
	}
	
}
