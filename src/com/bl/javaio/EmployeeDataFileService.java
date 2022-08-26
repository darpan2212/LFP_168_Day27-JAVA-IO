package com.bl.javaio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EmployeeDataFileService {

	public final String EMP_DATA = "emp_data.txt";
	Path empDataPath;

	public boolean writeToFile(List<Employee> employeeData) {
		empDataPath = Paths.get(EMP_DATA);

		try {
			if (Files.notExists(empDataPath)) {
				Files.createFile(empDataPath);
			}
			StringBuffer empBuffer = new StringBuffer();
			for (Employee emp : employeeData) {
				empBuffer.append(emp.getId() + "\t" + emp.getName() + "\t"
						+ emp.getSalary() + "\n");
			}

			Files.write(empDataPath, empBuffer.toString().getBytes());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int countEntries() {
		int count = 0;
		if (empDataPath != null) {
			try {
				count = (int) Files.lines(empDataPath).count();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public void readFileData() {
		try {
			Files.lines(new File(EMP_DATA).toPath()).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}