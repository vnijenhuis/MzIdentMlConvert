/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.general.CombinedPeptideEntry;

/**
 * Defines a collection of CombinedPeptideEntry objects.
 *
 * @author vnijenhuis
 */
public class CombinedPeptideEntryCollection {
    
    /**
     * Creates an ArrayList for CombinedPeptideEntry objects.
     */
    private final ArrayList<CombinedPeptideEntry> combinedPeptideCollection;

    /**
     * ArrayList of CombinedPeptideEntry objects.
     */
    public CombinedPeptideEntryCollection() {
        combinedPeptideCollection = new ArrayList<>();
    }

    /**
     * Adds a CombinedPeptideEntry object to the ArrayList.
     *
     * @param combinedPeptideEntry CombinedPeptideEntry object.
     */
    public final void addCombinedPeptideEntry(final CombinedPeptideEntry combinedPeptideEntry) {
        combinedPeptideCollection.add(combinedPeptideEntry);
    }

    /**
     * Removes a CombinedPeptideEntry object from the ArrayList.
     *
     * @param combinedPeptideEntry CombinedPeptideEntry object.
     */
    public final void removeCombinedPeptideEntry(final CombinedPeptideEntry combinedPeptideEntry) {
        combinedPeptideCollection.remove(combinedPeptideEntry);
    }

    /**
     * Returns an ArrayList of CombinedPeptideEntry objects.
     *
     * @return ArrayList of CombinedPeptideEntry objects.
     */
    public final ArrayList<CombinedPeptideEntry> getUniquePeptideList() {
        return combinedPeptideCollection;
    }
    
    /**
     * Compares the first entries of the accessionList with each other.
     * 
     * @return Integer based on the first entry in the accessionList.
     */
    static Comparator<CombinedPeptideEntry> getSingleProteinAccessionComparator() {
        return new Comparator<CombinedPeptideEntry>() {
            @Override
            public int compare(CombinedPeptideEntry o1, CombinedPeptideEntry o2) {
                return o1.getAccessionList().get(0).compareTo(o1.getAccessionList().get(0));
            }
        };
    }

    /**
     * Sorts the collection based on the first entry in the accessionList.
     */
    public final void sortOnSingleProteinAccession() {
        Collections.sort(this.combinedPeptideCollection, getSingleProteinAccessionComparator());
    }
    
    /**
     * Compares the peptide sequences of CombinedPeptideEntry objects with eachother.
     * 
     * @return Integer value based on the peptide sequence.
     */
    static Comparator<CombinedPeptideEntry> sortOnPeptideSequenceComparator() {
        return new Comparator<CombinedPeptideEntry>() {
            @Override
            public int compare(CombinedPeptideEntry o1, CombinedPeptideEntry o2) {
                return o1.getSequence().compareTo(o2.getSequence());
            }
        };
    }

    /**
     * Sorts the collection based on peptide sequences.
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.combinedPeptideCollection, sortOnPeptideSequenceComparator());
    }
}
