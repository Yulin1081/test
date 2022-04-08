package uk.ac.soton.SEG16.runway;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import uk.ac.soton.SEG16.XMLLoader;

@XmlRootElement(name = "runways")
@XmlAccessorType (XmlAccessType.FIELD)

public class Runways {

    @XmlElement(name = "runway")
    private ArrayList<Runway> runways = new ArrayList<>();

    public ArrayList<Runway> getRunways() {
        return runways;
    }

    public static ArrayList<Runway> GetPredefinedRunways () throws JAXBException, FileNotFoundException {
        Runways runways = new Runways();
        runways = XMLLoader.load("PredefinedRunways.xml", Runways.class);
        return runways.getRunways();
    }
}
