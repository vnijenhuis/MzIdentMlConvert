/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.output.ProteinPeptideOutput;

/**
 * Defines a ProteinPeptideOutput object collection.
 *
 * @author vnijenhuis
 */
public class ProteinPeptideOutputCollection {

    /**
     * Creates an ArrayList of ProteinPeptideOutput objects.
     */
    private final ArrayList<ProteinPeptideOutput> proteinPeptideEntryList;

    /**
     * Creates a new ArrayList.
     */
    public ProteinPeptideOutputCollection() {
        proteinPeptideEntryList = new ArrayList<>();
    }

    /**
     * Adds ProteinPeptideOutput object to the ArrayList.
     *
     * @param proteinPeptide ProteinPeptideOutput object.
     */
    public final void addProteinPeptideEntry(final ProteinPeptideOutput proteinPeptide) {
        proteinPeptideEntryList.add(proteinPeptide);
    }

    /**
     * Remove ProteinPeptideOutput object from the ArrayList.
     *
     * @param proteinPeptide ProteinPeptideOutput object.
     */
    public final void removeProteinPeptideEntry(final ProteinPeptideOutput proteinPeptide) {
        proteinPeptideEntryList.remove(proteinPeptide);
    }

    /**
     * Returns the ArrayList.
     *
     * @return ArrayList of ProteinPeptide objects.
     */
    public final ArrayList<ProteinPeptideOutput> getProteinPeptideEntryList() {
        return proteinPeptideEntryList;
    }
    
    /**
     * Compares the protein group of ProteinPeptideOutput objects.
     * 
     * @return Integer based on the protein group.
     */
    static Comparator<ProteinPeptideOutput> getProteinGroupComparator() {
        return new Comparator<ProteinPeptideOutput>() {
            @Override
            public int compare(ProteinPeptideOutput o1, ProteinPeptideOutput o2) {
                return o1.getProteinGroup().compareTo(o2.getProteinGroup());
            }
        };
    }

    /**
     * Sorts the collection based on the protein group.
     */
    public final void sortOnProteinGroup() {
        Collections.sort(this.proteinPeptideEntryList, getProteinGroupComparator());
    }
}
