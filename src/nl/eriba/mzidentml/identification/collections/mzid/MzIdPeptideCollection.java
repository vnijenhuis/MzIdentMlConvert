/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module *
 */
package nl.eriba.mzidentml.identification.collections.mzid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdPeptide;

/**
 * Defines a collection of MzIdPeptide objects.
 *
 * @author vnijenhuis
 */
public class MzIdPeptideCollection {

    /**
     * Creates an ArrayList for MzIdPeptide objects.
     */
    private final ArrayList<MzIdPeptide> peptides;

    /**
     * ArrayList of MzIdPeptide objects.
     */
    public MzIdPeptideCollection() {
        peptides = new ArrayList<>();
    }

    /**
     * Adds a MzIdPeptide object to the ArrayList.
     *
     * @param peptide MzIdPeptide object.
     */
    public final void addPeptide(final MzIdPeptide peptide) {
        peptides.add(peptide);
    }

    /**
     * Removes a MzIdPeptide object from the ArrayList.
     *
     * @param peptide MzIdPeptide object.
     */
    public final void removePeptide(final MzIdPeptide peptide) {
        peptides.remove(peptide);
    }

    /**
     * Returns an ArrayList of MzIdPeptide objects.
     *
     * @return ArrayList of MzIdPeptide objects.
     */
    public final ArrayList<MzIdPeptide> getPeptides() {
        return peptides;
    }

    /**
     * Compares the peptide sequene of MzIdPeptide objects.
     * 
     * @return Integer based on the peptide sequence.
     */
    static Comparator<MzIdPeptide> getPeptideSequenceComparator() {
        return new Comparator<MzIdPeptide>() {
            @Override
            public int compare(MzIdPeptide o1, MzIdPeptide o2) {
                return o1.getPeptideSequence().compareTo(o2.getPeptideSequence());
            }
        };
    }

    /**
     * Sorts the collection based on the peptide sequence.
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.peptides, getPeptideSequenceComparator());
    }
}
