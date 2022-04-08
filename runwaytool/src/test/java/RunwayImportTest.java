import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.UnmarshalException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.ac.soton.SEG16.runway.Runway;
import uk.ac.soton.SEG16.runway.RunwaySide;
import uk.ac.soton.SEG16.runway.TOL;

public class RunwayImportTest {
    @Test
    @DisplayName("Importing blank runway leads to correct error")
    void blankRunwayTest() {
        assertThrows(JAXBException.class, () -> Runway.FromXML(new File("src/test/resources/Runway_Blank.xml")));
    }
    @Test
    @DisplayName("Malformed runway file returns an exception (and screen message will also be shown)")
    /**
     * Will throw UnmarshalException as the it does not match the schema
     */
    void malformedRunwayTest() {
        assertThrows(UnmarshalException.class, () -> Runway.FromXML(new File("src/test/resources/Runway_Malformed.xml")));
    }

    @Test
    @DisplayName("Empty file")
    /**
     * Will throw IllegalArguementException as we pass in an invalid arguement into the File constructor
     */
    void emptyFileTest() {
        assertThrows(IllegalArgumentException.class, () -> Runway.FromXML(new File("")));
    }

    @Test
    @DisplayName("Successfully import all attributes")
    void successful() {
        assertDoesNotThrow(() -> {
            Runway rw = Runway.FromXML(new File("src/test/resources/Runway.xml"));
            assertEquals("09", rw.getMainDesignatorString());
            assertEquals(RunwaySide.L,rw.getSide());
            assertEquals(TOL.TakeOff,rw.getTOL());
            assertEquals(700,rw.getTORA());
            assertEquals(600,rw.getTODA());
            assertEquals(500,rw.getASDA());
            assertEquals(400,rw.getThreshold());
            assertEquals(300,rw.getLDA());
            assertEquals(200,rw.getRESA());
            assertEquals(100,rw.getBlastAllowance());
        });
    }
}
