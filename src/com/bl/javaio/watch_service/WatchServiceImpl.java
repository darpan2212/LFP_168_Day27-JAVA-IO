package com.bl.javaio.watch_service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WatchServiceImpl {

	public static final String DIR_FOR_WATCH = "D:/JavaIOTest";

	public static void main(String[] args) {

		Path dirPath = Paths.get(DIR_FOR_WATCH);

		if (Files.isDirectory(dirPath)) {
			try {
				System.out.println("Watch service started");
				JavaIOWatchService watchService = new JavaIOWatchService(dirPath);
				watchService.startWatch();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}