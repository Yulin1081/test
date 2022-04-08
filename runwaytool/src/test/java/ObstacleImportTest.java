import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.UnmarshalException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.ac.soton.SEG16.Obstacle;

public class ObstacleImportTest {
    @Test
    @DisplayName("Importing blank obstacle leads to zeroed fields")
    void blankObstacleTest() {
        assertThrows(JAXBException.class, () -> Obstacle.FromXML(new File("src/test/resources/Obstacle_Blank.xml")));
    }

    @Test
    @DisplayName("Malformed obstacle file returns empty obstacle (and screen message will also be shown)")
    /**
     * Will throw UnmarshalException as the it does not match the schema
     */
    void malformedObstacleTest() {
        assertThrows(UnmarshalException.class, () -> Obstacle.FromXML(new File("src/test/resources/Obstacle_Malformed.xml")));
    }

    @Test
    @DisplayName("Empty file")
    /**
     * Will throw IllegalArguementException as we pass in an invalid arguement into the File constructor
     */
    void emptyFileTest() {
        assertThrows(IllegalArgumentException.class, () -> Obstacle.FromXML(new File("")));
    }

    @Test
    @DisplayName("Successfully import all attributes")
    void successful() {
        assertDoesNotThrow( () -> {
            Obstacle ob = Obstacle.FromXML(new File("src/test/resources/Obstacle.xml"));
            assertEquals("Nom",ob.getName());
            assertEquals(375.0f,ob.getDistanceX());
            assertEquals(620.0f,ob.getDistanceY());
            assertEquals(250.0f,ob.getLength());
            assertEquals(200.0f,ob.getWidth());
            assertEquals(180.0f,ob.getFrontHeight());
            assertEquals(120.0f,ob.getBackHeight());
        });
    }
}
