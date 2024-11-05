


import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

// Producer that monitors a directory for new files and adds them to a shared queue
public class FileMonitoringThread implements Runnable {
    private final String directory; // The directory to monitor for new files
    private final BlockingQueue<String> queue; 
    private final Set<String> processedFiles; // A set to store files that have already been processed
    private volatile boolean running = true;

    public FileMonitoringThread(String directory, BlockingQueue<String> queue) {
        this.directory = directory;
        this.queue = queue;
        this.processedFiles = new HashSet<>();
    }

    @Override
    public void run() {

        System.out.println("FileMonitoringThread is running");
        

        while (running) {
            File directoryPath = new File(directory);
            File[] files = directoryPath.listFiles((dir, name) -> name.endsWith(".csv"));

            if (files != null) {
                for (File file : files) {
                    if (!processedFiles.contains(file.getAbsolutePath())) {  // Check if the file has already been read
                        try {
                            // Add the file to the queue
                            queue.put(file.getAbsolutePath());
                            processedFiles.add(file.getAbsolutePath());
                            System.out.println("Added " + file.getAbsolutePath() + " to queue.");
                        } catch (InterruptedException e) {
                            System.out.println("FileMonitoringThread interrupted while adding file.");
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }

            try {
                // Check for new files every 5 seconds
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("FileMonitoringThread interrupted during sleep.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // Method to safely stop the monitoring thread
    public void stop() {
        running = false;
    }
}
