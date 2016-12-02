/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.mzid;

import java.util.ArrayList;

/**
 * Defines a MzIdPeptideIdEvidence object.
 * @author vnijenhuis
 */
public class MzIdCombinedPeptideEvidence {
    /**
     * Peptide Reference. Refers to the Peptide Object id.
     */
    private final String peptideSequence;

    /**
     * Boolean to check if the sequence is a Decoy sequence.
     */
    private final Boolean isDecoy;

    /**
     * List of peptide evidence ids.
     */
    private final ArrayList<Integer> peptideIdList;

    /**
     * Defines the values of MzIdSmallPeptideEvidence object.
     * 
     * @param peptideSequence peptide amino acid sequence as String.
     * @param isDecoy boolean to check if the sequence is a decoy.
     * @param peptideIdList list of peptide evidence ids.
     */
    public MzIdCombinedPeptideEvidence(final String peptideSequence, final Boolean isDecoy, final ArrayList<Integer> peptideIdList) {
        this.peptideSequence = peptideSequence;
        this.isDecoy = isDecoy;
        this.peptideIdList = peptideIdList;
    }

    /**
     * Amino acid peptide sequence.
     * 
     * @return peptide sequence as String.
     */
    public String getPeptideSequence() {
        return this.peptideSequence;
    }

    /**
     * Returns a boolean wether the sequence is a decoy sequence or not.
     * 
     * @return true/false Boolean to determine if a sequence is a decoy sequence.
     */
    public Boolean isDecoySequence() {
        return this.isDecoy;
    }

    /**
     * List of peptide evidence ids.
     * 
     * @return peptide evidence id as Integer. 
     */
    public ArrayList<Integer> getPeptideEvidenceIdList() {
        return this.peptideIdList;
    }
}
