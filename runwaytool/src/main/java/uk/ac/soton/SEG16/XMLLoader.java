package uk.ac.soton.SEG16;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

public class XMLLoader {
    
    /**
     * Loads a given XML file.
     * @param fileName Name of file to load.
     * @param rootClass Class invoking load call.
     * @return Loaded Object (Can be cast)
     * @throws FileNotFoundException If file cannot be found.
     * @throws JAXBException If theres an error with XML parsing
     */
    public static <T> T load(String fileName, Class<T> jaxbClass) throws FileNotFoundException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(jaxbClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        
        InputStream stream = jaxbClass.getResourceAsStream(fileName);
        if (stream == null) throw new FileNotFoundException("Cannot find file " + fileName);
        
        return jaxbClass.cast(unmarshaller.unmarshal(stream));
    }

    /**
     * Loads a given XML file with a given schema.
     * @param fileName Name of file to load.
     * @param rootClass Class invoking load call.
     * @param schemaName Name of schema file
     * @return Loaded Object (can be cast)
     * @throws FileNotFoundException If files cannot be found.
     * @throws JAXBException If theres an error with XML parsing.
     * @throws SAXException If theres an error with Schema parsing.
     */
    public static <T> T loadWithSchema(File fileName, Class<T> jaxbClass, String schemaName) throws FileNotFoundException, JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(jaxbClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        StreamSource schemaStream = new StreamSource(XMLLoader.class.getResourceAsStream(schemaName));

        Schema s = sf.newSchema(schemaStream);

        unmarshaller.setSchema(s);

        return jaxbClass.cast(unmarshaller.unmarshal(fileName));
    }
}