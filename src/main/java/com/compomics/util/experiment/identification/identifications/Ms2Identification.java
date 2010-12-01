package com.compomics.util.experiment.identification.identifications;

import com.compomics.util.experiment.identification.IdentificationMethod;
import com.compomics.util.experiment.identification.Identification;

/**
 * This class models an Ms2 Identification.
 * User: Marc
 * Date: Nov 11, 2010
 * Time: 4:40:47 PM
 */
public class Ms2Identification extends Identification {

    /**
     * constructor for MS2 identification
     */
    public Ms2Identification() {
        this.methodUsed = IdentificationMethod.MS2_IDENTIFICATION;
    }
}