package com.bl.javaio.watch_service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

public class JavaIOWatchService {

	private final WatchService watcher;
	private final Map<WatchKey, Path> dirMap;

	public JavaIOWatchService(Path dir) throws IOException {
		watcher = FileSystems.getDefault().newWatchService();
		dirMap = new HashMap<>();
		scanAndRegister(dir);
	}

	private void scanAndRegister(Path dir) throws IOException {

		Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs)
					throws IOException {
				registerDirForWatch(path);
				System.out.println(super.preVisitDirectory(dir, attrs));
				return super.preVisitDirectory(dir, attrs);
			}
		});

	}

	public void registerDirForWatch(Path dirPath) throws IOException {
		WatchKey key = dirPath.register(watcher, ENTRY_CREATE, ENTRY_MODIFY,
				ENTRY_DELETE);
		dirMap.put(key, dirPath);
	}

	public void startWatch() {

		while (true) {

			WatchKey key = null;

			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Path dirPath = dirMap.get(key);

			try {
				System.out.println(dirPath.toString());
			} catch (NullPointerException e) {
				System.out.println("dirPath is null");
			}
			if (dirPath == null)
				continue;

			for (WatchEvent<?> event : key.pollEvents()) {

				Kind<?> eventKind = event.kind();

				@SuppressWarnings("unchecked")
				Path name = ((WatchEvent<Path>) event).context();

				Path child = dirPath.resolve(name);

				System.out.println(eventKind.name() + "\t" + child);

				if (eventKind == ENTRY_CREATE) {
					if (Files.isDirectory(child))
						try {
							scanAndRegister(child);
						} catch (IOException e) {
							e.printStackTrace();
						}
				}

				if (eventKind == ENTRY_DELETE) {
					if (Files.isDirectory(dirPath))
						dirMap.remove(key);
				}

				boolean validate = key.reset();

				if (!validate) {
					dirMap.remove(key);
					if (dirMap.isEmpty())
						break;
				}

			}

		}
	}
}