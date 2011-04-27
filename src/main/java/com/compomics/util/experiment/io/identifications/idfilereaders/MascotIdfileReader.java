package com.compomics.util.experiment.io.identifications.idfilereaders;

import com.compomics.mascotdatfile.util.interfaces.MascotDatfileInf;
import com.compomics.mascotdatfile.util.interfaces.Modification;
import com.compomics.mascotdatfile.util.interfaces.QueryToPeptideMapInf;
import com.compomics.mascotdatfile.util.mascot.PeptideHit;
import com.compomics.mascotdatfile.util.mascot.ProteinHit;
import com.compomics.mascotdatfile.util.mascot.enumeration.MascotDatfileType;
import com.compomics.mascotdatfile.util.mascot.factory.MascotDatfileFactory;
import com.compomics.util.experiment.biology.PTM;
import com.compomics.util.experiment.biology.PTMFactory;
import com.compomics.util.experiment.biology.Peptide;
import com.compomics.util.experiment.biology.Protein;
import com.compomics.util.experiment.identification.Advocate;
import com.compomics.util.experiment.identification.PeptideAssumption;
import com.compomics.util.experiment.identification.matches.ModificationMatch;
import com.compomics.util.experiment.identification.matches.SpectrumMatch;
import com.compomics.util.experiment.io.identifications.IdfileReader;
import com.compomics.util.experiment.massspectrometry.Charge;
import com.compomics.util.experiment.massspectrometry.MSnSpectrum;
import com.compomics.util.experiment.massspectrometry.Precursor;
import com.compomics.util.experiment.massspectrometry.SpectrumCollection;
import com.compomics.util.experiment.personalization.ExperimentObject;
import com.compomics.util.experiment.refinementparameters.MascotScore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

/**
 * This reader will import identifications from a Mascot dat file.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Marc
 * Date: Jun 23, 2010
 * Time: 9:45:35 AM
 */
public class MascotIdfileReader extends ExperimentObject implements IdfileReader {

    /**
     * The inspected file
     */
    private File inspectedFile;
    /**
     * Instance of the mascotdatfile parser
     */
    private MascotDatfileInf iMascotDatfile;
    /**
     * the PTM factory
     */
    private PTMFactory ptmFactory = PTMFactory.getInstance();
    /**
     * The spectrum collection to complete
     */
    private SpectrumCollection spectrumCollection = null;

    /**
     * constructor for the mascotIdileReader
     */
    public MascotIdfileReader() {
    }

    /**
     * Constructor for the MascotIdilereader
     *
     * @param aFile a file to read
     */
    public MascotIdfileReader(File aFile) {
        inspectedFile = aFile;
        try {
            iMascotDatfile = MascotDatfileFactory.create(inspectedFile.getCanonicalPath(), MascotDatfileType.MEMORY); //getPath might have to be changed into getcanonicalPath
        } catch (IOException e) {
            System.exit(1);
        }
    }

    /**
     * Constructor for the MascotIdilereader with a spectrum collection to put spectrum information in
     *
     * @param aFile              a file to read
     * @param spectrumCollection the spectrum collection used
     */
    public MascotIdfileReader(File aFile, SpectrumCollection spectrumCollection) {
        this.spectrumCollection = spectrumCollection;
        inspectedFile = aFile;
        try {
            iMascotDatfile = MascotDatfileFactory.create(inspectedFile.getCanonicalPath(), MascotDatfileType.MEMORY); //getPath might have to be changed into getcanonicalPath
        } catch (IOException e) {
            System.exit(1);
        }
    }

    /**
     * get the spectrum file name
     *
     * @return the spectrum file name
     */
    public String getMgfFileName() {
        File temp = new File(iMascotDatfile.getParametersSection().getFile());
        return temp.getName();
    }

    /**
     * getter for the file name
     *
     * @return the file name
     */
    public String getFileName() {
        return iMascotDatfile.getFileName();
    }

    /**
     * a method to get all the spectrum matches
     *
     * @return a set containing all spectrum matches
     */
    public HashSet<SpectrumMatch> getAllSpectrumMatches() {

        HashSet<SpectrumMatch> assignedPeptideHits = new HashSet<SpectrumMatch>();
        try {
            QueryToPeptideMapInf lQueryToPeptideMap = iMascotDatfile.getQueryToPeptideMap();
            QueryToPeptideMapInf lDecoyQueryToPeptideMap = iMascotDatfile.getDecoyQueryToPeptideMap();
            for (int i = 0; i < iMascotDatfile.getNumberOfQueries(); i++) {
                Vector<PeptideHit> mascotDecoyPeptideHits = null;
                if (lDecoyQueryToPeptideMap != null) {
                    mascotDecoyPeptideHits = lDecoyQueryToPeptideMap.getAllPeptideHits(i + 1);
                }
                Vector<PeptideHit> mascotPeptideHits = lQueryToPeptideMap.getAllPeptideHits(i + 1);
                Boolean nonDecoyHitFound = false;
                if (mascotPeptideHits != null) {                                        // There might not be an identification for every query
                    if (mascotDecoyPeptideHits != null) {
                        if (mascotDecoyPeptideHits.get(0).getIonsScore() < mascotPeptideHits.get(0).getIonsScore()) {
                            nonDecoyHitFound = true;
                        }
                    } else {
                        nonDecoyHitFound = true;
                    }
                }
                if (nonDecoyHitFound) {
                    boolean singleBestHit = true;
                    if (mascotPeptideHits.size() > 1) {
                        if ((mascotPeptideHits.get(0).getExpectancy() == mascotPeptideHits.get(1).getExpectancy()) && (mascotPeptideHits.get(0).getSequence().compareTo(mascotPeptideHits.get(1).getSequence()) != 0)) {
                            singleBestHit = false;
                        }
                    }
                    if (singleBestHit) {
                        PeptideHit thisPeptideHit = mascotPeptideHits.get(0);
                        SpectrumMatch currentMatch = getSpectrumMatch(thisPeptideHit, i + 1, false);
                        assignedPeptideHits.add(currentMatch);
                    }
                } else if (mascotDecoyPeptideHits != null) {
                    boolean singleBestHit = true;
                    if (mascotDecoyPeptideHits.size() > 1) {
                        if ((mascotDecoyPeptideHits.get(0).getExpectancy() == mascotDecoyPeptideHits.get(1).getExpectancy()) && (mascotDecoyPeptideHits.get(0).getSequence().compareTo(mascotDecoyPeptideHits.get(1).getSequence()) != 0)) {
                            singleBestHit = false;
                        }
                    }
                    if (singleBestHit) {
                        PeptideHit thisPeptideHit = mascotDecoyPeptideHits.get(0);
                        SpectrumMatch currentMatch = getSpectrumMatch(thisPeptideHit, i + 1, true);
                        assignedPeptideHits.add(currentMatch);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignedPeptideHits;
    }

    /**
     * parses a spectrum match out of a peptideHit
     *
     * @param aPeptideHit  the peptide hit to parse
     * @param query        the corresponding query
     * @param decoySection is it in the decoy section?
     * @return a spectrum match
     */
    private SpectrumMatch getSpectrumMatch(PeptideHit aPeptideHit, int query, boolean decoySection) {
        boolean c13 = false;
        Double measuredMass = aPeptideHit.getPeptideMr() + aPeptideHit.getDeltaMass();
        String measuredCharge = iMascotDatfile.getQuery(query).getChargeString();
        String sign = String.valueOf(measuredCharge.charAt(1));
        Charge charge;
        if (sign.compareTo("+") == 0) {
            charge = new Charge(Charge.PLUS, Integer.valueOf(measuredCharge.substring(0, 1)));
        } else {
            charge = new Charge(Charge.MINUS, Integer.valueOf(measuredCharge.substring(0, 1)));
        }
        ArrayList<ModificationMatch> foundModifications = new ArrayList();
        Modification handledModification;
        PTM correspondingPTM;
        int modificationSite;
        for (int l = 0; l < aPeptideHit.getModifications().length; l++) {
            if (l > 0) {
                modificationSite = l - 1;
            } else {
                modificationSite = l;
            }
            handledModification = aPeptideHit.getModifications()[l];
            if (handledModification != null) {
                correspondingPTM = ptmFactory.getPTMFromMascotName(handledModification.getShortType());
                if (correspondingPTM != null) {
                    foundModifications.add(new ModificationMatch(correspondingPTM, !handledModification.isFixed(), modificationSite));
                } else {
                    foundModifications.add(new ModificationMatch(correspondingPTM, !handledModification.isFixed(), modificationSite));
                }
            }
        }
        Double mascotEValue = aPeptideHit.getExpectancy();

        String spectrumId = iMascotDatfile.getQuery(query).getTitle();
        //com.compomics.mascotdatfile.util.mascot.Peak[] kennysPeakList = iMascotDatfile.getQuery(query).getPeakList();
        /**
         * Peak lists are not imported anymore to save memory
        for (com.compomics.mascotdatfile.util.mascot.Peak peak : kennysPeakList) {
        peakList.add(new Peak(peak.getMZ(), peak.getIntensity()));
        }
         **/
        Precursor precursor = new Precursor(-1, measuredMass, charge); // The RT is not known at this stage
        MSnSpectrum spectrum = new MSnSpectrum(2, precursor, spectrumId, getMgfFileName());
        String spectrumKey = spectrum.getSpectrumKey();
        if (spectrumCollection != null) {
            spectrumCollection.addSpectrum(spectrum);
        }
        ArrayList<Protein> proteins = new ArrayList();
        for (int j = 0; j < aPeptideHit.getProteinHits().size(); j++) {
            String accession = ((ProteinHit) aPeptideHit.getProteinHits().get(j)).getAccession();
            if (accession.lastIndexOf('|') != -1) {
                int start = accession.indexOf("|");
                int end = accession.indexOf("|", ++start);
                accession = accession.substring(start, end);
            }
            proteins.add(new Protein(accession, decoySection || accession.contains(DECOY_FLAG)));
        }

        Peptide thePeptide = new Peptide(aPeptideHit.getSequence(), aPeptideHit.getPeptideMr(), proteins, foundModifications);
        PeptideAssumption currentAssumption = new PeptideAssumption(thePeptide, 1, Advocate.MASCOT, measuredMass, mascotEValue, getFileName());
        MascotScore scoreParam = new MascotScore(aPeptideHit.getIonsScore());
        currentAssumption.addUrParam(scoreParam);
        // Secondary hits are not implemented yet
        return new SpectrumMatch(spectrumKey, currentAssumption);
    }
}
