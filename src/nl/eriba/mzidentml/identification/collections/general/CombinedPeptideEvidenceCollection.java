/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdCombinedPeptideEvidence;

/**
 * Defines a collection of gimped PeptideEvidence data.
 * Contains less data then the original collection.
 *
 * @author vnijenhuis
 */
public class CombinedPeptideEvidenceCollection {
    /**
     * 
     */
    private final ArrayList<MzIdCombinedPeptideEvidence> smallEvidenceList;

    public CombinedPeptideEvidenceCollection() {
        this.smallEvidenceList = new ArrayList<>();
    }

    /**
     * Returns the list of MzIdPeptideIdEvidence objects.
     *
     * @return ArrayList with MzIdPeptideIdEvidence objects.
     */
    public ArrayList<MzIdCombinedPeptideEvidence> getSmallPeptideEvidenceList() {
        return this.smallEvidenceList;
    }
    
    /**
     * Adds a MzIdPeptideIdEvidence object to the ArrayList.
     *
     * @param smallEvidence MzIdPeptideIdEvidence object.
     */
    public final void addCombinedPeptidEvidence(final MzIdCombinedPeptideEvidence smallEvidence) {
        this.smallEvidenceList.add(smallEvidence);
    }

    /**
     * Gets the MzIdCombinedPeptidEvidence data based on the peptide sequence.
     * 
     * @param sequence peptide amino acid sequence.
     * @return Integer value based on the peptide sequence.
     */
    public final MzIdCombinedPeptideEvidence getCombinedPeptidEvidence(final String sequence) {
        for (MzIdCombinedPeptideEvidence evidence : this.getSmallPeptideEvidenceList()) {
            if (evidence.getPeptideSequence().equals(sequence)) {
                return evidence;
            }
        }
        return null;
    }

    /**
     * Removes a MzIdPeptideIdEvidence object from the ArrayList.
     *
     * @param smallEvidence MzIdPeptideIdEvidence object.
     */
    public final void removeSmallPeptideEvidence(final MzIdCombinedPeptideEvidence smallEvidence) {
        this.smallEvidenceList.remove(smallEvidence);
    }
    
    /**
     * Compares the peptide sequences of PeptideEvidences.
     * 
     * @return Integer based on the peptide sequences.
     */
    static Comparator<MzIdCombinedPeptideEvidence> getPeptideSequenceComparator() {
        return new Comparator<MzIdCombinedPeptideEvidence>() {
            @Override
            public int compare(MzIdCombinedPeptideEvidence o1, MzIdCombinedPeptideEvidence o2) {
                return o1.getPeptideSequence().compareTo(o2.getPeptideSequence());
            }
        };
    }

    /**
     * Sorts the collection based on the peptide sequence.
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.smallEvidenceList, getPeptideSequenceComparator());
    }

    /**
     * Compares the boolean decoy sequences with each other.
     * 
     * @return Integer value based on the decoy sequence value.
     */
    static Comparator<MzIdCombinedPeptideEvidence> getDecoyComparator() {
        return new Comparator<MzIdCombinedPeptideEvidence>() {
            @Override
            public int compare(MzIdCombinedPeptideEvidence o1, MzIdCombinedPeptideEvidence o2) {
                return o1.isDecoySequence().compareTo(o2.isDecoySequence());
            }
        };
    }

    /**
     * Sorts on the decoy boolean value.
     */
    public final void sortOnDecoy() {
        Collections.sort(this.smallEvidenceList, getDecoyComparator());
    }
}
