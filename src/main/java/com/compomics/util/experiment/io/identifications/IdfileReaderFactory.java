package com.compomics.util.experiment.io.identifications;

import com.compomics.util.experiment.identification.advocates.SearchEngine;
import com.compomics.util.experiment.io.identifications.idfilereaders.MascotIdfileReader;
import com.compomics.util.experiment.io.identifications.idfilereaders.OMSSAIdfileReader;
import com.compomics.util.experiment.io.identifications.idfilereaders.XTandemIdfileReader;
import com.compomics.util.experiment.io.identifications.IdfileReader;
import com.compomics.util.experiment.massspectrometry.SpectrumCollection;

import java.io.File;
import org.xml.sax.SAXException;

/**
 * This factory will provide the appropriate identification file reader for each type of file.
 * Null when the format is not supported.
 *
 * Created by IntelliJ IDEA.
 * User: Marc
 * Date: Jun 23, 2010
 * Time: 5:33:24 PM
 */
public class IdfileReaderFactory {

    /**
     * The factory instance
     */
    private static IdfileReaderFactory singleton = null;

    /**
     * the factory constructor
     */
    private IdfileReaderFactory() {

    }

    /**
     * A static method to retrieve the instance of the factory
     *
     * @return the factory instance
     */
    public static IdfileReaderFactory getInstance() {
        if (singleton == null) {
            singleton = new IdfileReaderFactory();
        }
        return singleton;
    }

    /**
     * This method returns the proper identification file reader depending on the format of the provided file
     *
     * @param aFile the file to parse
     * @return an adapted file reader
     */
    public IdfileReader getFileReader(File aFile) throws SAXException {
        String name = aFile.getName().toLowerCase();
        if (name.endsWith("dat")) {
            return new MascotIdfileReader(aFile);
        } else if (name.endsWith("omx")) {
            return new OMSSAIdfileReader(aFile);
        } else if (name.endsWith("xml")) {
            return new XTandemIdfileReader(aFile);
        }
        return null;
    }

    /**
     * This method returns the proper identification file reader depending on the format of the provided file. If a spectrum collection is given, basic information about the spectra will also be stored.
     *
     * @param aFile the file to parse
     * @param spectrumCollection the spectrum collection where to store the spectrum information
     * @return an adapted file reader
     */
    public IdfileReader getFileReader(File aFile, SpectrumCollection spectrumCollection) throws SAXException {
        String name = aFile.getName().toLowerCase();
        if (name.endsWith("dat")) {
            return new MascotIdfileReader(aFile, spectrumCollection);
        } else if (name.endsWith("omx")) {
            return new OMSSAIdfileReader(aFile, spectrumCollection);
        } else if (name.endsWith("xml")) {
            return new XTandemIdfileReader(aFile, spectrumCollection);
        }
        return null;
    }

    /**
     * This method return the search engine corresponding to the given file.
     * @param aFile     an identification file
     * @return the index of the search engine
     */
    public int getSearchEngine(File aFile) {
        if (aFile.getName().endsWith("dat")) {
            return SearchEngine.MASCOT;
        } else if (aFile.getName().endsWith("omx")) {
            return SearchEngine.OMSSA;
        } else if (aFile.getName().endsWith("xml")) {
            if (!aFile.getName().equals("mods.xml") && !aFile.getName().equals("usermods.xml")) {
                return SearchEngine.XTANDEM;
            }
        }
        return -1;
    }
}
