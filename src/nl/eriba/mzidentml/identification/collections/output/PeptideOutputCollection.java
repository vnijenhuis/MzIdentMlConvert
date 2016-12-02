/*
 * @author Vikthor Nijenhuis
 * @project PeptideItem mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.output.PeptideOutput;

/**
 * Defines a PeptideEntryCollection object.
 *
 * @author vnijenhuis
 */
public class PeptideOutputCollection {

    /**
     * Creates an ArrayList for PeptideOutput objects.
     */
    private final ArrayList<PeptideOutput> psmList;

    /**
     * Creates a new ArrayList.
     */
    public PeptideOutputCollection() {
        psmList = new ArrayList<>();
    }

    /**
     * Adds PeptideOutput objects to the ArrayList.
     *
     * @param peptideEntry PeptideOutput object.
     */
    public final void addPeptideEntry(final PeptideOutput peptideEntry) {
        psmList.add(peptideEntry);
    }

    /**
     * Removes PeptideOutput values from the ArrayList.
     *
     * @param peptideEntry PeptideOutput object.
     */
    public final void removePeptideEntry(final PeptideOutput peptideEntry) {
        psmList.remove(peptideEntry);
    }

    /**
     * Returns the ArrayList.
     *
     * @return ArrayList of PeptideOutput objects.
     */
    public final ArrayList<PeptideOutput> getPeptideEntryList() {
        return psmList;
    }
    
    /**
     * Compares the peptide score of PeptideOutput objects.
     *
     * @return Integer value based on the peptide score.
     */
    static Comparator<PeptideOutput> getScoreComparator() {
        return new Comparator<PeptideOutput>() {
            @Override
            public int compare(PeptideOutput o1, PeptideOutput o2) {
                return o1.getPeptideScore().compareTo(o2.getPeptideScore());
            }
        };
    }

    /**
     * Sorts the collection based on peptide score.
     */
    public final void sortOnPeptideScore() {
        Collections.sort(this.psmList, getScoreComparator());
    }
}
