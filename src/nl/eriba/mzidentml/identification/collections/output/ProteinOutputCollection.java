/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module *
 */
package nl.eriba.mzidentml.identification.collections.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.output.ProteinOutput;

/**
 * Defines a collection of ProteinOutput objects.
 *
 * @author vnijenhuis
 */
public class ProteinOutputCollection {

    /**
     * Creates a List of ProteinOutput objects.
     */
    private final ArrayList<ProteinOutput> proteinEntryList;

    /**
     * Creates a new ArrayList.
     */
    public ProteinOutputCollection() {
        proteinEntryList = new ArrayList<>();
    }

    /**
     * Adds ProteinOutput objects to the ArrayList.
     *
     * @param proteinEntry ProteinOutput object.
     */
    public final void addProteinEntry(final ProteinOutput proteinEntry) {
        proteinEntryList.add(proteinEntry);
    }

    /**
     * Removes ProteinOutput objects from the ArrayList.
     *
     * @param proteinEntry ProteinOutput object.
     */
    public final void removeProteinEntry(final ProteinOutput proteinEntry) {
        proteinEntryList.remove(proteinEntry);
    }

    /**
     * Returns the ArrayList.
     *
     * @return ArrayList of ProteinOutput objects.
     */
    public final ArrayList<ProteinOutput> getProteinEntryList() {
        return proteinEntryList;
    }

    /**
     * Compares the protein group of ProteinOutput objects.
     *
     * @return Integer based on the protein group.
     */
    static Comparator<ProteinOutput> getProteinGroupSorter() {
        return new Comparator<ProteinOutput>() {
            @Override
            public int compare(ProteinOutput o1, ProteinOutput o2) {
                return o1.getProteinGroup().compareTo(o2.getProteinGroup());
            }
        };
    }

    /**
     * Sorts the collection based on the protein group.
     */
    public final void sortOnProteinGroup() {
        Collections.sort(this.proteinEntryList, getProteinGroupSorter());
    }
}
