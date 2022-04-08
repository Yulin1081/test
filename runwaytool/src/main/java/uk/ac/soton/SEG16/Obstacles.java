package uk.ac.soton.SEG16;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "obstacles")
@XmlAccessorType (XmlAccessType.FIELD)

/**
 * Group of Obstacle objects for the XML Parser.
 */
public class Obstacles {
    
    @XmlElement (name="obstacle")
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    /**
     * Returns an ArrayList of Obstacle objects.
     */
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
    
    /**
     * Import form XML and return ArrayList of Obstacles.
     * @throws JAXBException
     */
    public static ArrayList<Obstacle> FromXML(String fileName) throws FileNotFoundException, JAXBException {
        Obstacles obs = XMLLoader.load(fileName, Obstacles.class);
        return obs.getObstacles();
    };

    public static ArrayList<Obstacle> FromXML(java.net.URL fileName) throws FileNotFoundException, UnsupportedEncodingException, JAXBException {
        String stringFileName = URLDecoder.decode(fileName.getPath(), "UTF-8");
        Obstacles obs = XMLLoader.load(stringFileName, Obstacles.class);
        return obs.getObstacles();
    };
}
