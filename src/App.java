
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
// import threads.FileReaderThread;

public class App{
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> fileQueue = new ArrayBlockingQueue<>(10); // Queue for files
        BlockingQueue<String> commandQueue = new ArrayBlockingQueue<>(10); // Queue for commands

        // Specify the directory you want to monitor
        String directoryToMonitor = "./data";

        // Create and start the producer and consumer threads
        FileMonitoringThread producer = new FileMonitoringThread(directoryToMonitor, fileQueue);
        Thread producerThread = new Thread(producer, "FileMonitoringThread");
        

        FilterManager filterManager = new FilterManager();

        FileReaderThread consumer = new FileReaderThread(fileQueue, commandQueue, filterManager);
        Thread consumerThread = new Thread(consumer, "FileReaderThread");
        

        UserFile user = new UserFile(commandQueue);
        Thread userThread = new Thread(user, "UserInputThread");
        

        producerThread.start();
        consumerThread.start();
        userThread.start();

        consumerThread.join();
        
        

        // Add a shutdown hook to stop the producer gracefully on application exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            producer.stop(); // Gracefully stop the producer
            producerThread.interrupt();
            consumerThread.interrupt();
        }));
    }
}