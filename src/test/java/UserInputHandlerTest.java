import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.nagarro.training.corejavatraining.UserInputHandler;

import java.util.Scanner;

class UserInputHandlerTest {

    private UserInputHandler userInputHandler;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner = Mockito.mock(Scanner.class);
        userInputHandler = new UserInputHandler(scanner);
    }

    @Test
    void testGetUserChoice_validInput() {
        // Mock user input for choice "1"
        Mockito.when(scanner.nextLine()).thenReturn("1");

        int choice = userInputHandler.getUserChoice();

        assertEquals(1, choice);
    }

    @Test
    void testGetUserChoice_invalidInput() {
        // Mock user input for an invalid choice
        Mockito.when(scanner.nextLine()).thenReturn("abc").thenReturn("1");

        int choice = userInputHandler.getUserChoice();

        assertEquals(1, choice); // After invalid input, it should retry and return valid input
    }
}
