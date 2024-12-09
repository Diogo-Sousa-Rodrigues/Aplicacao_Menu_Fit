import org.junit.jupiter.api.Test;
import pt.isec.model.meals.ExtraMeal;
import pt.isec.persistence.BDManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddExtraMealTest {

    @Test
    void testExtraMealName() {
        ExtraMeal extraMeal = new ExtraMeal("Extra Meal", 100);
        BDManager bdManager = new BDManager();
        bdManager.addExtraMeal(999, extraMeal);
        ExtraMeal extraMealFromDB = bdManager.getExtraMeal(999);
        assertEquals("Extra Meal", extraMealFromDB.getName(), "Extra Meal name should be 'Extra Meal'.");
    }

}