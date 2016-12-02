/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module *
 */
package nl.eriba.mzidentml.identification.collections.mzid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdDatabaseSequence;

/**
 * Defines a collection of MzIdDatabaseSequence objects.
 *
 * @author vnijenhuis
 */
public class MzIdDatabaseSequenceCollection {

    /**
     * Creates an ArrayList for MzIdDatabaseSequence objects.
     */
    private final ArrayList<MzIdDatabaseSequence> databaseSequences;

    /**
     * ArrayList of MzIdDatabaseSequence objects.
     */
    public MzIdDatabaseSequenceCollection() {
        databaseSequences = new ArrayList<>();
    }

    /**
     * Adds a MzIdDatabaseSequence object to the ArrayList.
     *
     * @param databaseSequence MzIdDatabaseSequence object.
     */
    public final void addDatabaseSequence(final MzIdDatabaseSequence databaseSequence) {
        databaseSequences.add(databaseSequence);
    }

    /**
     * Removes a MzIdDatabaseSequence object from the ArrayList.
     *
     * @param databaseSequence MzIdDatabaseSequence object.
     */
    public final void removeDatabaseSequence(final MzIdDatabaseSequence databaseSequence) {
        databaseSequences.remove(databaseSequence);
    }

    /**
     * Returns an ArrayList of MzIdDatabaseSequence objects.
     *
     * @return ArrayList of MzIdDatabaseSequence objects.
     */
    public final ArrayList<MzIdDatabaseSequence> getDatabaseSequenceList() {
        return databaseSequences;
    }
    
    /**
     * Compares protein accession of MzIdDatabaseSequence objects.
     * 
     * @return Integer based on the protein accession.
     */
    static Comparator<MzIdDatabaseSequence> getProteinAccessionComparator() {
        return new Comparator<MzIdDatabaseSequence>() {
            @Override
            public int compare(MzIdDatabaseSequence o1, MzIdDatabaseSequence o2) {
                return o1.getProteinAccession().compareTo(o2.getProteinAccession());
            }
        };
    }

    /**
     * Sorts the collection based on the protein accession id.
     */
    public final void sortOnProteinAccession() {
        Collections.sort(this.databaseSequences, getProteinAccessionComparator());
    }
}
