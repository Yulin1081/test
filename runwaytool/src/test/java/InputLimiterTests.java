import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.ac.soton.SEG16.Calculator;
import uk.ac.soton.SEG16.InputLimiter;
import uk.ac.soton.SEG16.Obstacle;
import uk.ac.soton.SEG16.runway.Runway;

public class InputLimiterTests {
    
    /**
     * isInteger Tests
     */
    @Test
    @DisplayName("isInteger: Valid Integer")
    void isInteger_validInteger () {
        assertEquals(true, InputLimiter.isInteger("36"));
    }
    @Test
    @DisplayName("isInteger: Invalid Float")
    void isInteger_invalidFloat () {
        assertEquals(false, InputLimiter.isInteger("34.25f"));
    }
    @Test
    @DisplayName("isInteger: Invalid Character Int")
    void isInteger_invalidCharacterInt () {
        assertEquals(false, InputLimiter.isInteger("34.25a"));
    }

    /**
     * isFloat Tests
     */
    @Test
    @DisplayName("isFloat: Valid Float")
    void isFloat_validFloat () {
        assertEquals(true, InputLimiter.isFloat("34.25"));
    }
    @Test
    @DisplayName("isFloat: Invalid Character Float")
    void isFloat_invalidIntegerFloat () {
        assertEquals(false, InputLimiter.isFloat("36a"));
    }

    /**
     * isPositive Tests
     */
    @Test
    @DisplayName("isPositive: Valid Positive Int")
    void isPositive_validPositiveInt () {
        assertEquals(true, InputLimiter.isPositive(563));
    }
    @Test
    @DisplayName("isPositive: Invalid Negative Int")
    void isPositive_invalidNegativeInt () {
        assertEquals(false, InputLimiter.isPositive(-46));
    }
    @Test
    @DisplayName("isPositive: Invalid 0 Int")
    void isPositive_invalid0Int () {
        assertEquals(false, InputLimiter.isPositive(0));
    }
    @Test
    @DisplayName("isPositive: Valid Positive Float")
    void isPositive_validPositiveFloat () {
        assertEquals(true, InputLimiter.isPositive(234.7f));
    }
    @Test
    @DisplayName("isPositive: Invalid Negative Float")
    void isPositive_invalidNegativeFloat () {
        assertEquals(false, InputLimiter.isPositive(-4563.34f));
    }
    @Test
    @DisplayName("isPositive: Invalid 0.0f Float")
    void isPositive__invalid0Float () {
        assertEquals(false, InputLimiter.isPositive(0.0f));
    }

    /**
      * isNotZero Tests
      */
    @Test
    @DisplayName("isNotZero: Valid 0 Int")
    void isNotZero_valid0Int () {
        assertEquals(true, InputLimiter.isZero(0));
    }
    @Test
    @DisplayName("isNotZero: Invalid Positive Int")
    void isNotZero_invalidPositiveInt () {
        assertEquals(false, InputLimiter.isZero(867));
    }
    @Test
    @DisplayName("isNotZero: Invalid Negative Int")
    void isNotZero_invalidNegativeInt () {
        assertEquals(false, InputLimiter.isZero(-23));
    }
    @Test
    @DisplayName("isNotZero: Valid 0 Float")
    void isNotZero_valid0Float () {
        assertEquals(true, InputLimiter.isZero(0f));
    }
    @Test
    @DisplayName("isNotZero: Invalid Positive Float")
    void isNotZero_invalidPositiveFloat () {
        assertEquals(false, InputLimiter.isZero(235.2f));
    }
    @Test
    @DisplayName("isNotZero: Invalid Negative Float")
    void isNotZero_invalidNegativeFloat () {
        assertEquals(false, InputLimiter.isZero(-656.37f));
    }
}
