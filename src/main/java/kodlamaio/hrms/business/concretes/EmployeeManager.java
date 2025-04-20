package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployeeService;
import kodlamaio.hrms.business.abstracts.verifications.EmailVerificationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployeeDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.entities.concretes.Employee;

@Service
public class EmployeeManager implements EmployeeService{
	
	private EmployeeDao employeeDao;
	private UserDao userDao;
	private EmailVerificationService emailVerificationService;

	@Autowired
	public EmployeeManager(EmployeeDao employeeDao, UserDao userDao, EmailVerificationService emailVerificationService) {
		super();
		this.employeeDao = employeeDao;
		this.userDao = userDao;
		this.emailVerificationService = emailVerificationService;
	}

	@Override
	public DataResult<List<Employee>> getAll() {

		return new SuccessDataResult<List<Employee>>(this.employeeDao.findAll(), "Data listed");
	}

	@Override
	public Result add(Employee employee) {

		if (userDao.existsByUsername(employee.getUsername())) {
			return new ErrorResult("Username is already registered.");
		}
		
		if (userDao.existsByEmail(employee.getEmail())) {
			return new ErrorResult("Email is already registered.");
		}		
		
		this.employeeDao.save(employee);
		emailVerificationService.generateVerificationEmailForEmployee(employee);
		return new SuccessResult("HRMS employee added to system");
	}

	@Override
	public Result delete(int employeeId) {
		Employee emp = employeeDao.getReferenceById(employeeId);
		this.employeeDao.delete(emp);
		
		return new SuccessResult("HRMS employee deleted from system");
	}

}
