package uk.ac.soton.SEG16;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.xml.sax.SAXException;

@XmlRootElement (name="obstacle")
@XmlAccessorType (XmlAccessType.FIELD)

/** Obstacle class, holds a trapezoid shape of the obstacle and location relative to runway. */
public class Obstacle {

    // Attributes
    @XmlElement private String name = "";
    @XmlElement private Float distanceX = 0f;
    @XmlElement private Float distanceY = 0f;
    @XmlElement private Float length = 0f;
    @XmlElement private Float width = 0f;
    @XmlElement private Float frontHeight = 0f;
    @XmlElement private Float backHeight = 0f;

    /**
     * Create Obstacle.
     * @param name Obstacle name
     * @param distanceX Distance along runway.
     * @param distanceY Distance across runway.
     * @param length Length of Obstacle along runway.
     * @param width Width of obstacle across runway.
     * @param frontHeight Height of obstacle from start of runway.
     * @param backHeight Height of Rear of obstacle.
     */
    public Obstacle (String name, Float distanceX, Float distanceY, Float length, Float width, Float frontHeight, Float backHeight) {
        this.name = name;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.length = length;
        this.width = width;
        this.frontHeight = frontHeight;
        this.backHeight = backHeight;
    }

    /** Create an empty obstacle. */
    public Obstacle () {}

    // Getters
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getDistanceX () {
        return this.distanceX;
    }
    public void setDistanceX (float distanceX) {
        this.distanceX = distanceX;
    }

    public Float getDistanceY () {
        return this.distanceY;
    }
    public void setDistanceY (float distanceY) {
        this.distanceY = distanceY;
    }

    public Float getLength () {
        return this.length;
    }
    public void setLength (float length) {
        this.length = length;
    }

    public Float getWidth () {
        return this.width;
    }
    public void setWidth (float width) {
        this.width = width;
    }

    public Float getFrontHeight () {
        return this.frontHeight;
    }
    public void setFrontHeight (float frontHeight) {
        this.frontHeight = frontHeight;
    }

    public Float getBackHeight () {
        return this.backHeight;
    }
    public void setBackHeight (float backHeight) {
        this.backHeight = backHeight;
    }

    // Functions
    public static String ToXML (Obstacle obstacle) throws JAXBException {
        String xmlContent = "";
        
        JAXBContext jaxbContext = JAXBContext.newInstance(Obstacle.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(obstacle, sw);
        
        xmlContent = sw.toString();

        return xmlContent;
    }

    public static Obstacle FromXML (File file) throws JAXBException, FileNotFoundException, SAXException {
        Obstacle obstacle = new Obstacle();
        
        obstacle = XMLLoader.loadWithSchema(file, Obstacle.class, "obstacle.xsd");

        return obstacle;
    }

    public String toString () {
        return "[" + this.name + ", " +
        this.distanceX + ", " + this.distanceY + ", " + 
        this.length + ", " + this.width + ", " + 
        this.frontHeight + ", " + this.backHeight + 
        "]";
    }
}
