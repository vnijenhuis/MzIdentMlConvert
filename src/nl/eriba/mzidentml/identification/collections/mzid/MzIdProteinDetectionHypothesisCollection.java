/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module *
 */
package nl.eriba.mzidentml.identification.collections.mzid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdProteinDetectionHypothesis;

/**
 * Defines a collection of MzIdProteinDetectionHypothesis objects.
 *
 * @author vnijenhuis
 */
public class MzIdProteinDetectionHypothesisCollection {

    /**
     * Creates an ArrayList for MzIdProteinDetectionHypothesis objects.
     */
    private final ArrayList<MzIdProteinDetectionHypothesis> proteinHypothesisList;

    /**
     * ArrayList of MzIdProteinDetectionHypothesis objects.
     */
    public MzIdProteinDetectionHypothesisCollection() {
        proteinHypothesisList = new ArrayList<>();
    }

    /**
     * Adds a MzIdProteinDetectionHypothesis object to the ArrayList.
     *
     * @param peptideHypothesis MzIdProteinDetectionHypothesis object.
     */
    public final void addProteinDetectionHypothesis(final MzIdProteinDetectionHypothesis peptideHypothesis) {
        proteinHypothesisList.add(peptideHypothesis);
    }

    /**
     * Removes a MzIdProteinDetectionHypothesis object from the ArrayList.
     *
     * @param databaseSequence MzIdProteinDetectionHypothesis object.
     */
    public final void removeProteinDetectionHypothesis(final MzIdProteinDetectionHypothesis databaseSequence) {
        proteinHypothesisList.remove(databaseSequence);
    }
    
    /**
     * Removes a MzIdProteinDetectionHypothesis object from the ArrayList.
     *
     * @param index index as Integer.
     */
    public final void removeProteinDetectionHypothesis(final Integer index) {
        proteinHypothesisList.remove(index);
    }

    /**
     * Returns an ArrayList of MzIdProteinDetectionHypothesis objects.
     *
     * @return ArrayList of MzIdProteinDetectionHypothesis objects.
     */
    public final ArrayList<MzIdProteinDetectionHypothesis> getProteinDetectionHypothesisList() {
        return proteinHypothesisList;
    }

    /**
     * Compares the protein accession id of MzIdProteinDetectionHypothesis objects.
     * 
     * @return Integer based on the protein accession.
     */
    static Comparator<MzIdProteinDetectionHypothesis> getProteinAccessionComparator() {
        return new Comparator<MzIdProteinDetectionHypothesis>() {
            @Override
            public int compare(MzIdProteinDetectionHypothesis o1, MzIdProteinDetectionHypothesis o2) {
                return o1.getProteinAccession().compareTo(o2.getProteinAccession());
            }
        };
    }

    /**
     * Sorts the collection based on the protein accession id.
     */
    public final void sortOnProteinAccession() {
        Collections.sort(this.proteinHypothesisList, getProteinAccessionComparator());
    }

    /**
     * Compares the PeptideEvidence id of MzIdProteinDetectionHypothesis objects.
     * 
     * @return Integer based on the PeptideEvidence id.
     */
    static Comparator<MzIdProteinDetectionHypothesis> getPeptideEvidenceIdComparator() {
        return new Comparator<MzIdProteinDetectionHypothesis>() {
            @Override
            public int compare(MzIdProteinDetectionHypothesis o1, MzIdProteinDetectionHypothesis o2) {
                return o1.getPeptideEvidenceId().compareTo(o2.getPeptideEvidenceId());
            }
        };
    }

    /**
     * Sorts the collcetion based on the PeptideEvidence id.
     */
    public final void sortOnPeptideEvidence() {
        Collections.sort(this.proteinHypothesisList, getPeptideEvidenceIdComparator());
    }
}
