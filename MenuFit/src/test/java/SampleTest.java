import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {
    @Test
    void testAddition() {
        int result = 2 + 3;
        assertEquals(5, result, "2 + 3 should be 5.");
    }

    @Test
    void testSubtraction() {
        int result = 3 - 2;
        assertEquals(1, result, "3 - 2 should be 1.");
    }
}