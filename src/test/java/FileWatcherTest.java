import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nagarro.training.corejavatraining.models.CompositeKey;
import com.nagarro.training.corejavatraining.models.Product;
import com.nagarro.training.corejavatraining.watcher.FileWatcher;

public class FileWatcherTest {

    private FileWatcher fileWatcher;
    private ConcurrentMap<CompositeKey, Product> productMap;

    @BeforeEach
    void setUp() {
        productMap = new ConcurrentHashMap<>();
        Path directoryPath = Paths.get("src/test/resources/testdata/");
        String filePattern = "^nike.csv$";
        fileWatcher = new FileWatcher(directoryPath, filePattern, productMap);
    }

    @Test
    public void testProcessInitialFiles() {
        fileWatcher.processInitialFiles();
        // Assuming there’s at least one product in the test data
        assertTrue(productMap.size() > 0, "Product map should contain at least one entry after processing.");
    }


}
