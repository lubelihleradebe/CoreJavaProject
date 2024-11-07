package com.nagarro.training.corejavatraining.watcher;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * Responsible for looking at a specific directory and watching csv with the specific name
 */
public class FileWatch implements Runnable {

    private final Path directory;
    private final String name;

    public FileWatch(Path directory, String name) {
        this.directory = directory;
        this.name = name;
    }

    @Override
    public void run() {
        // Check if the directory exists and exit if it doesn't return
        if (!Files.isDirectory(directory)) {
            return;
        }

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();

            directory.register(watcher,
                                    ENTRY_CREATE,
                                    ENTRY_MODIFY);

            while (true) {
                WatchKey key = watcher.take();

                for (WatchEvent<?> event : key.pollEvents()) 
                {
                    // Look for files with a particular name
                    Path changedFile = (Path) event.context();
                    String fileName = changedFile.getFileName().toString();

                    System.out.println(fileName.matches(name));
                }

                // To receive further events, reset the key
                key.reset();
            }
        } catch (Exception exception) {
        }

    }
}
