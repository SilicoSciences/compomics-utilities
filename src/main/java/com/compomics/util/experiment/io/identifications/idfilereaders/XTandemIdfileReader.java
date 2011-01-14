package com.compomics.util.experiment.io.identifications.idfilereaders;

import com.compomics.util.experiment.biology.PTM;
import com.compomics.util.experiment.biology.PTMFactory;
import com.compomics.util.experiment.biology.Protein;
import com.compomics.util.experiment.biology.ions.PeptideFragmentIon;
import com.compomics.util.experiment.identification.Advocate;
import com.compomics.util.experiment.io.identifications.IdfileReader;
import com.compomics.util.experiment.identification.PeptideAssumption;
import com.compomics.util.experiment.identification.matches.IonMatch;
import com.compomics.util.experiment.identification.matches.ModificationMatch;
import com.compomics.util.experiment.identification.matches.SpectrumMatch;
import com.compomics.util.experiment.massspectrometry.Charge;
import com.compomics.util.experiment.massspectrometry.MSnSpectrum;
import com.compomics.util.experiment.massspectrometry.Peak;
import com.compomics.util.experiment.massspectrometry.Precursor;
import com.compomics.util.experiment.personalization.ExperimentObject;
import de.proteinms.xtandemparser.interfaces.Ion;
import de.proteinms.xtandemparser.interfaces.Modification;
import de.proteinms.xtandemparser.xtandem.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This reader will import identifications from an X!Tandem xml result file.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Marc
 * Date: Jun 23, 2010
 * Time: 9:45:54 AM
 */
public class XTandemIdfileReader extends ExperimentObject implements IdfileReader {

    /**
     * the instance of the X!Tandem parser
     */
    private XTandemFile xTandemFile = null;
    /**
     * the modification map
     */
    private ModificationMap modificationMap;
    /**
     * the protein map
     */
    private ProteinMap proteinMap;
    /**
     * the peptide map
     */
    private PeptideMap peptideMap;
    /**
     * the PTM factory
     */
    private PTMFactory ptmFactory = PTMFactory.getInstance();

    /**
     * Constructor for the reader
     */
    public XTandemIdfileReader() {
    }

    /**
     * constructor for the reader
     *
     * @param aFile the inspected file
     */
    public XTandemIdfileReader(File aFile) {
        if (!aFile.getName().endsWith("mods.xml") || !aFile.getName().endsWith("usermods.xml")) {
            try {
                xTandemFile = new XTandemFile(aFile.getPath());
                modificationMap = xTandemFile.getModificationMap();
                proteinMap = xTandemFile.getProteinMap();
                peptideMap = xTandemFile.getPeptideMap();
            } catch (SAXException e) {
                System.out.println("xml exception");
            }
        }
    }

    /**
     * getter for the file name
     *
     * @return the file name
     */
    public String getFileName() {
        File tempFile = new File(xTandemFile.getFileName());
        return tempFile.getName();
    }

    /**
     * method which returns all spectrum matches found in the file
     *
     * @return a set containing all spectrum matches
     */
    public HashSet<SpectrumMatch> getAllSpectrumMatches() {
        HashSet<SpectrumMatch> foundPeptides = new HashSet<SpectrumMatch>();
        try {
        Iterator<Spectrum> spectraIt = xTandemFile.getSpectraIterator();
        while (spectraIt.hasNext()) {
            // informations to provide
            File tempFile = new File(xTandemFile.getInputParameters().getSpectrumPath());
            String filename = tempFile.getName();
            ArrayList<Protein> proteins = new ArrayList<Protein>();
            double measuredMass, eValue, deltaMass;
            Boolean reverseHit;
            MSnSpectrum spectrum;
            com.compomics.util.experiment.biology.Peptide peptide;
            Precursor precursor;

            Spectrum currentSpectrum = spectraIt.next();

            Charge charge = new Charge(Charge.PLUS, currentSpectrum.getPrecursorCharge());

            int nSpectrum = currentSpectrum.getSpectrumNumber();
            SupportData supportData = xTandemFile.getSupportData(nSpectrum);
            String spectrumName = supportData.getFragIonSpectrumDescription();
            // Spectra are not imported at this stage to save memory
            HashSet<Peak> peakList = peakList = new HashSet<Peak>();
            ArrayList<Peptide> spectrumPeptides = peptideMap.getAllPeptides(currentSpectrum.getSpectrumNumber());
            if (spectrumPeptides.size() > 0) {
                Peptide bestPeptide = spectrumPeptides.get(0);
                Boolean conflict = false;
                for (int i = 1; i < spectrumPeptides.size(); i++) {
                    if (spectrumPeptides.get(i).getDomainExpect() < bestPeptide.getDomainExpect()) {
                        bestPeptide = spectrumPeptides.get(i);
                        conflict = false;
                    } else if (spectrumPeptides.get(i).getDomainExpect() == bestPeptide.getDomainExpect() &&
                            spectrumPeptides.get(i).getDomainSequence().compareTo(bestPeptide.getDomainSequence()) != 0) {
                        conflict = true;
                    }
                }
                if (!conflict) {
                    reverseHit = true;
                    for (int i = 0; i < spectrumPeptides.size(); i++) {
                        if (spectrumPeptides.get(i).getDomainSequence().compareTo(bestPeptide.getDomainSequence()) == 0) {
                            String description = proteinMap.getProteinWithPeptideID(spectrumPeptides.get(i).getDomainID()).getLabel();
                            String accession = "";
                            try {
                                int start = description.indexOf("|");
                                int end = description.indexOf("|", ++start);
                                accession = description.substring(start, end);
                            } catch (Exception e) {
                                int end = description.indexOf(" ");
                                accession = description.substring(0, end);
                            }
                            if (!accession.startsWith("REV_") && !accession.endsWith("_REV") && !accession.endsWith("_REVERSED")) {
                                reverseHit = false;
                            }
                            proteins.add(new Protein(accession, description, reverseHit));
                        }
                    }
                    eValue = bestPeptide.getDomainExpect();
                    measuredMass = bestPeptide.getDomainMh() + bestPeptide.getDomainDeltaMh();
                    precursor = new Precursor(-1, measuredMass, charge); // The retention time is not known at this stage
                    spectrum = new MSnSpectrum(2, precursor, spectrumName, peakList, filename);
                    ArrayList<Modification> foundFixedModifications = modificationMap.getFixedModifications(bestPeptide.getDomainID());
                    PTM currentPTM;
                    ArrayList<ModificationMatch> foundModifications = new ArrayList<ModificationMatch>();
                    for (Modification currentModification : foundFixedModifications) {
                        String[] parsedName = currentModification.getName().split("@");
                        double mass = new Double(parsedName[0]);
                        String aa = parsedName[1];
                        currentPTM = ptmFactory.getPTM(mass, aa, bestPeptide.getDomainSequence());
                        // location not implemented yet
                        foundModifications.add(new ModificationMatch(currentPTM, false, -1));
                    }
                    ArrayList<de.proteinms.xtandemparser.interfaces.Modification> foundVariableModifications = modificationMap.getVariableModifications(bestPeptide.getDomainID());
                    for (Modification currentModification : foundVariableModifications) {
                        String[] parsedName = currentModification.getName().split("@");
                        double mass = new Double(parsedName[0]);
                        String aa = parsedName[1];
                        currentPTM = ptmFactory.getPTM(mass, aa, bestPeptide.getDomainSequence());
                        // location not implemented yet
                        foundModifications.add(new ModificationMatch(currentPTM, true, -1));
                    }
                    peptide = new com.compomics.util.experiment.biology.Peptide(bestPeptide.getDomainSequence(), bestPeptide.getDomainMh(), proteins, foundModifications);
                    deltaMass = Math.abs(1000000 * (measuredMass - bestPeptide.getDomainMh()) / bestPeptide.getDomainMh());
                    PeptideAssumption currentAssumption = new PeptideAssumption(peptide, 1, Advocate.XTANDEM, deltaMass, eValue, getFileName());
                    // secondary hits are not implemented yet
                    SpectrumMatch currentMatch = new SpectrumMatch(spectrum, currentAssumption);
                    foundPeptides.add(currentMatch);
                }
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundPeptides;
    }
}