package com.bl.javaio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {

	List<Employee> employeeList;

	public EmployeePayrollService() {
		employeeList = new ArrayList<>();
	}

	public enum IOStream {
		CONSOLE_IO, FILE_IO, DB_IO
	}

	public static void main(String[] args) {
		IOStream iOStream = IOStream.CONSOLE_IO;

		EmployeePayrollService service = new EmployeePayrollService();

		int option = 1;

		Scanner mainScanner = null;
		while (option == 1) {
			mainScanner = new Scanner(System.in);
			Employee emp = service.readEmployeeData(iOStream, mainScanner);
			service.employeeList.add(emp);

			System.out.println("______________________________");
			System.out.println("1. Add Employee Data\n2. Exit");
			System.out.println("Enter the option from above.");
			option = mainScanner.nextInt();
		}
		mainScanner.close();
		System.out.println("______________________________");
		service.writeEmployeeData(IOStream.FILE_IO);
	}

	private Employee readEmployeeData(IOStream iOStream, Scanner sc) {

		Employee employee = new Employee();

		if (iOStream == IOStream.CONSOLE_IO) {

			int randomId = (int) (Math.random() * 899 + 100);
			employee.setId(randomId);
			System.out.println("Enter the employee name.");
			employee.setName(sc.nextLine());
			System.out.println("Enter the employee salary");
			employee.setSalary(sc.nextDouble());

		}

		return employee;
	}

	private void writeEmployeeData(IOStream ioStream) {
		if (ioStream == IOStream.CONSOLE_IO) {
			for (Employee employee : employeeList) {
				System.out.println(employee);
			}
		} else if (ioStream == IOStream.FILE_IO) {

			EmployeeDataFileService fileService = new EmployeeDataFileService();
			if (fileService.writeToFile(employeeList)) {
				System.out.println(
						fileService.countEntries() + " Employee data has been saved....");
				fileService.readFileData();
			} else {
				System.out.println("Error occured while saving employee data");
			}
		}
	}
}