package com.bl.javaio;

import java.util.Scanner;

public class EmployeePayrollService {

	public enum IOStream {
		CONSOLE_IO, FILE_IO, DB_IO
	}

	public static void main(String[] args) {
		IOStream iOStream = IOStream.CONSOLE_IO;

		EmployeePayrollService service = new EmployeePayrollService();

		service.getEmployeeData(iOStream);
	}

	private void getEmployeeData(IOStream iOStream) {

		if (iOStream == IOStream.CONSOLE_IO) {
			Scanner sc = new Scanner(System.in);

			int randomId = (int) (Math.random() * 899 + 100);

			Employee employee = new Employee();
			employee.setId(randomId);
			System.out.println("Enter the employee name.");
			employee.setName(sc.nextLine());
			System.out.println("Enter the employee salary");
			employee.setSalary(sc.nextDouble());
	
			writeEmployeeData(employee);
			sc.close();
		}

	}

	private void writeEmployeeData(Employee employee) {
		System.out.println("----------Console output------------");
		System.out.println(employee);
	}
}