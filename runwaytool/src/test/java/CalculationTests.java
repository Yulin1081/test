import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.ac.soton.SEG16.Calculator;
import uk.ac.soton.SEG16.Obstacle;
import uk.ac.soton.SEG16.runway.Runway;

public class CalculationTests {
    
    @Test
    @DisplayName("Scenario 1 Take Off Away")
    void scenario1TakeoffAway () {
        Obstacle obs = new Obstacle("name", -50.0f, 0.0f, 100.0f, 20.0f, 0.0f, 12.0f);
        Runway runway = new Runway(3902, 3902, 3902, 306, 3595, 240, 300, obs);
        Calculator calc = new Calculator(runway);
        calc.runCalculations("right", false);
        assertEquals(3902 - 300 + 50 - 306, calc.getTORA());
        assertEquals(3902 - 300 + 50 - 306, calc.getASDA());
        assertEquals(3902 - 300 + 50 - 306, calc.getTODA());
    }

    @Test
    @DisplayName("Scenario 1 Land Over")
    void scenario1LandOver () {
        Obstacle obs = new Obstacle("name", -50.0f, 0.0f, 100.0f, 20.0f, 0.0f, 12.0f);
        Runway runway = new Runway(3902, 3902, 3902, 306, 3595, 240, 300, obs);
        Calculator calc = new Calculator(runway);
        calc.runCalculations("right", true);
        assertEquals(2985, calc.getLDA());
    }

    @Test
    @DisplayName("Scenario 1 Take off towards")
    void scenario1TakeOffTowards () {
        Obstacle obs = new Obstacle("name", 3646.0f, 0.0f, 100.0f, 20.0f, 0.0f, 12.0f);
        Runway runway = new Runway(3902, 3902, 3902, 0, 3595, 240, 300, obs);
        Calculator calc = new Calculator(runway);
        calc.runCalculations("right", false);
        assertEquals(2986, calc.getTORA());
        assertEquals(2986, calc.getASDA());
        assertEquals(2986, calc.getTODA());
    }

    @Test
    @DisplayName("Scenario 1 landing towards")
    void scenario1LandingTowards () {
        Obstacle obs = new Obstacle("name", 3646.0f, 0.0f, 100.0f, 20.0f, 0.0f, 12.0f);
        Runway runway = new Runway(3902, 3902, 3902, 0, 3595, 240, 300, obs);
        Calculator calc = new Calculator(runway);
        calc.runCalculations("right", true);
        assertEquals(3346, calc.getLDA());
    }

    @Test
    @DisplayName("Scenario 2 Take off towards")
    void scenario2TakeOffTowards () {
        Obstacle obs = new Obstacle("name", 2853.0f, -20.0f, 100.0f, 20.0f, 0.0f, 25.0f);
        Runway runway = new Runway(3660, 3660, 3660, 307, 3353, 240, 300, obs);
        Calculator calc = new Calculator(runway);
        calc.runCalculations("right", false);
        assertEquals(1850, calc.getTORA());
        assertEquals(1850, calc.getASDA());
        assertEquals(1850, calc.getTODA());
    }    

    @Test
    @DisplayName("Scenario 2 landing towards")
    void scenario2LandingTowards () {
        Obstacle obs = new Obstacle("name", 2853.0f, -20.0f, 100.0f, 20.0f, 0.0f, 25.0f);
        Runway runway = new Runway(3660, 3660, 3660, 307, 3353, 240, 300, obs);
        Calculator calc = new Calculator(runway);
        calc.runCalculations("right", true);
        assertEquals(2553, calc.getLDA());
    } 

    @Test
    @DisplayName("Scenario 2 Take Off Away")
    void scenario2TakeOffAway () {
        Obstacle obs = new Obstacle("name", 500.0f, -20.0f, 100.0f, 20.0f, 0.0f, 25.0f);
        Runway runway = new Runway(3660, 3660, 3660, 0, 3884, 240, 300, obs);
        Calculator calc = new Calculator(runway);
        calc.runCalculations("right", false);
        assertEquals(2860, calc.getTORA());
        assertEquals(2860, calc.getASDA());
        assertEquals(2860, calc.getTODA());
    }   

    @Test
    @DisplayName("Scenario 2 Landing Over")
    void scenario2LandingOver () {
        Obstacle obs = new Obstacle("name", 500.0f, -20.0f, 100.0f, 20.0f, 0.0f, 25.0f);
        Runway runway = new Runway(3660, 3660, 3660, 0, 3660, 240, 300, obs);
        Calculator calc = new Calculator(runway);
        calc.runCalculations("right", true);
        assertEquals(1850, calc.getLDA());
    }   
}
