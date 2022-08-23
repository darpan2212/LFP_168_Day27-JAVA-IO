package com.bl.javaio.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class FileIoOperations {

	public static final String DIRECTORY_PATH = "myfiles/";
	public static final String FILE_PATH = DIRECTORY_PATH + "output.txt";

	public static void main(String[] args) {

		Path directoryPath = Paths.get(DIRECTORY_PATH);

		System.out.println(DIRECTORY_PATH + " exists? : " + Files.exists(directoryPath));

		if (Files.notExists(directoryPath)) {
			try {
				Files.createDirectory(directoryPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			IntStream.range(1, 10).forEach((n) -> {
				String fileName = "temp" + n + ".txt";
				Path filePath = Paths.get(DIRECTORY_PATH + fileName);
				if (Files.notExists(filePath)) {
					try {
						Files.createFile(filePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			System.out.println("-----printing list of files-----");

			try {
				if (Files.isDirectory(directoryPath)) {
					Files.list(directoryPath).filter(fP -> Files.isRegularFile(fP))
							.forEach((fileName) -> {
								if (fileName.getFileName().toString()
										.equals("temp5.txt")) {
									try {
										Files.delete(fileName);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
								System.out.println(fileName.getFileName());
							});
				}
				System.out.println("----print with directory stream----");
				Files.newDirectoryStream(directoryPath).forEach(System.out::println);
				System.out.println("----print with directory stream and filter----");
				Files.newDirectoryStream(directoryPath, fP -> {
					return fP.toFile().isFile() && fP.getFileName().toString().startsWith("temp");
				}).forEach(System.out::println);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("-----printing list of files-----");
		}

		System.out.println(DIRECTORY_PATH + " exists? : " + Files.exists(directoryPath));
	}

}