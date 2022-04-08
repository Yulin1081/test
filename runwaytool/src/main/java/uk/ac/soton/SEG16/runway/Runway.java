package uk.ac.soton.SEG16.runway;

import uk.ac.soton.SEG16.Obstacle;
import uk.ac.soton.SEG16.XMLLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.*;

import org.xml.sax.SAXException;

/**
 * Runway Object which holds all runway parameters including name, takeoff/landing designation and Obstacle.
 */

@XmlRootElement (name="runway")
@XmlAccessorType (XmlAccessType.FIELD)

public class Runway implements Serializable {

    // HELLO - these were changed from attribute to element. Not sure whether by mistake or by choice.
    // But because it's now 11pm and I can't figure out whether it's me or the existing pull which has the
    // issue, I've changed back to attribute. Feel free to change! BW Michael
    @XmlElement private int mainDesignator;
    @XmlElement private RunwaySide side;
    @XmlElement private TOL takeOffLanding;
    @XmlElement private int TORA;
    @XmlElement private int TODA;
    @XmlElement private int ASDA;
    @XmlElement private int threshold;
    @XmlElement private int LDA;
    @XmlElement private int RESA;
    @XmlElement private int blastAllowance;
    @XmlAttribute private String name = "Unnamed Runway";
    @XmlAttribute private String icao = "N/A";
    private transient Obstacle obstacle;

    /**
     * Create a runway with the required values.
     * @param designator Runway Name/Number.
     * @param side Runway Letter.
     * @param tol Designate if the Runway should display takeoff or landing.
     * @param tora TORA.
     * @param toda TODA.
     * @param asda ASDA.
     * @param threshold Threshold.
     * @param lda LDA.
     * @param resa RESA.
     * @param blastAllowance Blast allowance.
     * @param obstacle Obstacle on the runway (if it exists).
     */
    public Runway (int designator, RunwaySide side, TOL tol, int tora, int toda, int asda, int threshold, int lda, int resa, int blastAllowance, Obstacle obstacle) {
        this.mainDesignator = designator;
        this.side = side;
        this.takeOffLanding = tol;
        this.TORA = tora;
        this.TODA = toda;
        this.ASDA = asda;
        this.threshold = threshold;
        this.LDA = lda;
        this.RESA = resa;
        this.blastAllowance = blastAllowance; 
        this.obstacle = obstacle;
    }

    /**
     * Create a pure runway with just data.
     * @param tora TORA.
     * @param toda TODA.
     * @param asda ASDA.
     * @param threshold Threshold.
     * @param lda LDA.
     * @param resa RESA.
     * @param blastAllowance Blast allowance.
     * @param obst Obstacle on the runway (if it exists).
     */
    public Runway(int tora, int toda, int asda, int threshold, int lda, int resa, int blastAllowance, Obstacle obst) {
        this(0, RunwaySide.NA, TOL.TakeOff, tora, toda, asda, threshold, lda, resa, blastAllowance, obst);
    }

    public Runway() {
        this(0, RunwaySide.NA, TOL.TakeOff, 0, 0, 0, 0, 0, 0, 0, new Obstacle());
    }

    public String ToString () {
        return "[" + this.TORA + ", " + this.TODA + ", " + this.ASDA + ", " + this.threshold + ", " + this.LDA + ", " + this.RESA + ", " + this.blastAllowance + "]";
    }

    //Getter and Setter functions

    public int getMainDesignator() {
        return mainDesignator;
    }

    public String getMainDesignatorString() {
        int designator = getMainDesignator();
        if (designator < 10) return "0" + Integer.toString(designator);
        return Integer.toString(designator);
    }

    public void setMainDesignator(int designator) {
        this.mainDesignator = designator;
    }

    public int getSecondaryDesignator() {
        if (mainDesignator < 18) return mainDesignator + 18;
        return mainDesignator - 18;
    }

    public String getSecondaryDesignatorString() {
        int designator = getSecondaryDesignator();
        if (designator < 10) return "0" + Integer.toString(designator);
        return Integer.toString(designator);
    }

    public String getMainSide() {
        if (this.side == RunwaySide.NA) return "";
        return this.side.toString();
    }

    public void setMainRunwaySide(RunwaySide side) {
        this.side = side;
    }

    public String getSecondarySide() {
        switch (this.side) {
            case R:
                return "L";
            case C:
                return "C";
            case L:
                return "R";
            default:
                return "";
        }
    }

    public TOL getTOL() {
        return takeOffLanding;
    }
    public void setTOL(TOL newtol) {
        this.takeOffLanding = newtol;
    }

    public int getTORA () {
        return TORA;
    }
    public void setTORA (int TORA) {
        this.TORA = TORA;
    }
    
    public int getTODA () {
        return TODA;
    }
    public void setTODA (int TODA) {
        this.TODA = TODA;
    }

    public int getASDA() {
        return ASDA;
    }
    public void setASDA (int ASDA) {
        this.ASDA = ASDA;
    }

    public int getThreshold () {
        return threshold;
    }
    public void setThreshold (int threshold) {
        this.threshold = threshold;
    }

    public int getLDA () {
        return LDA;
    }
    public void setLDA (int LDA) {
        this.LDA = LDA;
    }

    public int getRESA () {
        return RESA;
    }
    public void setRESA (int RESA) {
        this.RESA = RESA; 
    }

    public int getBlastAllowance () {
        return blastAllowance;
    }
    public void setBlastAllowance (int blastAllowance) {
        this.blastAllowance = blastAllowance;
    }

    public Obstacle getObstacle () {
        return obstacle;
    }
    public void setObstacle (Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public RunwaySide getSide () {
        return this.side;
    }

    public String toString() {
        return getTODA() + ", " + getTORA() + ", " + getTOL();
    }

    public String PredefinedInfo () {
        return this.name + " [" + this.icao + "]";
    }

    /**
     * Saves the given Runway to a given File
     * @param file
     * @param runway
     * @throws IOException
     */
    public static String ToXML (File file, Runway runway) throws JAXBException, SAXException, NullPointerException, IOException {
        String xmlContent = "";

        JAXBContext jaxbContext = JAXBContext.newInstance(Runway.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema s;

        s = sf.newSchema(new File(runway.getClass().getResource("runway.xsd").getFile().replace("%20", " ")));
        jaxbMarshaller.setSchema(s);

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(runway, sw);
            
        xmlContent = sw.toString();

        FileWriter fw = new FileWriter(file);
        fw.write(xmlContent);
        fw.close();

        return xmlContent;
    }

    /**
     * Loads a Runway object from a given File
     * @param file
     * @throws JAXBException
     */
    public static Runway FromXML (File file) throws FileNotFoundException, JAXBException, SAXException {
        Runway runway = new Runway(0, 0, 0, 0, 0, 0, 0, new Obstacle());

        runway = XMLLoader.loadWithSchema(file, Runway.class, "runway.xsd");
        runway.setObstacle(new Obstacle());

        return runway;
    }

}
