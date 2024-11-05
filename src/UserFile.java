


import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class UserFile implements Runnable{
    
    private final BlockingQueue<String> commandQueue;

    public UserFile(BlockingQueue<String> commandQueue) {
        this.commandQueue = commandQueue;
    }


    @Override
    public void run() {
        
        try (Scanner input = new Scanner(System.in)) {
            int command;
            do {
                System.out.println("Enter the command number: "+
                    "1 - Color");
                command = Integer.parseInt(input.nextLine());
    
                // Check for the type of command entered
                switch (command) {
                    case 1:
                        System.out.print("Enter color (e.g., red, blue): ");
                        String color = input.nextLine().trim().toLowerCase();
                        commandQueue.put("color=" + color);  // Put full command in queue
                        break;
    
                    // case "size":
                    //     System.out.print("Enter size (e.g., S, M, 10): ");
                    //     String size = input.nextLine().trim().toLowerCase();
                    //     commandQueue.put("size=" + size);
                    //     break;
    
                    // case "brand":
                    //     System.out.print("Enter brand (e.g., nike, puma): ");
                    //     String brand = input.nextLine().trim().toLowerCase();
                    //     commandQueue.put("brand=" + brand);
                    //     break;
    
                    // case "showall":
                    //     commandQueue.put("showall");
                    //     break;
    
                    // case "exit":
                    //     commandQueue.put("exit");
                    //     break;
    
                    default:
                        System.out.println("Unknown command, try again.");
                }
            } while (command != (2));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("User input interrupted.");
        }
    }
    

}