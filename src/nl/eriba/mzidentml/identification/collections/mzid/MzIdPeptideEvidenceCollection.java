/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module *
 */
package nl.eriba.mzidentml.identification.collections.mzid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdPeptideEvidence;

/**
 * Defines a collection of MzIdPeptideEvidence objects.
 *
 * @author vnijenhuis
 */
public class MzIdPeptideEvidenceCollection {

    /**
     * Creates an ArrayList for MzIdPeptideEvidence objects.
     */
    private final ArrayList<MzIdPeptideEvidence> peptideEvidenceList;

    /**
     * ArrayList of MzIdPeptideEvidence objects.
     */
    public MzIdPeptideEvidenceCollection() {
        peptideEvidenceList = new ArrayList<>();
    }

    /**
     * Adds a MzIdPeptideEvidence object to the ArrayList.
     *
     * @param peptideEvidence MzIdPeptideEvidence object.
     */
    public final void addPeptideEvidence(final MzIdPeptideEvidence peptideEvidence) {
        peptideEvidenceList.add(peptideEvidence);
    }

    /**
     * Removes a MzIdPeptideEvidence object from the ArrayList.
     *
     * @param peptideEvidence MzIdPeptideEvidence object.
     */
    public final void removePeptideEvidence(final MzIdPeptideEvidence peptideEvidence) {
        peptideEvidenceList.remove(peptideEvidence);
    }

    /**
     * Returns an ArrayList of MzIdPeptideEvidence objects.
     *
     * @return ArrayList of MzIdPeptideEvidence objects.
     */
    public final ArrayList<MzIdPeptideEvidence> getPeptideEvidenceItems() {
        return peptideEvidenceList;
    }

    /**
     * Compares the peptide sequence of MzIdPeptideEvidence objects.
     * 
     * @return Integer based on the peptide sequence.
     */
    static Comparator<MzIdPeptideEvidence> getPeptideSequenceComparator() {
        return new Comparator<MzIdPeptideEvidence>() {
            @Override
            public int compare(MzIdPeptideEvidence o1, MzIdPeptideEvidence o2) {
                return o1.getPeptideSequence().compareTo(o2.getPeptideSequence());
            }
        };
    }

    /**
     * Sorts the collection based on the peptide sequence.
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.peptideEvidenceList, getPeptideSequenceComparator());
    }
}
