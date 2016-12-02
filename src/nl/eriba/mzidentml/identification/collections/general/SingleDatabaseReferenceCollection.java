/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.general.SingleDatabaseReference;

/**
 * Defines a collection of SingleDatabaseReference objects.
 *
 * @author vnijenhuis
 */
public class SingleDatabaseReferenceCollection {
    /**
     * Creates an ArrayList for SingleDatabaseReference objects.
     */
    private final ArrayList<SingleDatabaseReference> databaseSequences;

    /**
     * ArrayList of SingleDatabaseReference objects.
     */
    public SingleDatabaseReferenceCollection() {
        databaseSequences = new ArrayList<>();
    }

    /**
     * Adds a SingleDatabaseReference object to the ArrayList.
     *
     * @param databaseSequenceReference SingleDatabaseReference object.
     */
    public final void addDatabaseReference(final SingleDatabaseReference databaseSequenceReference) {
        databaseSequences.add(databaseSequenceReference);
    }

    /**
     * Removes a SingleDatabaseReference object from the ArrayList.
     *
     * @param databaseSequenceReference SingleDatabaseReference object.
     */
    public final void removeDatabaseReference(final SingleDatabaseReference databaseSequenceReference) {
        databaseSequences.remove(databaseSequenceReference);
    }

    /**
     * Returns an ArrayList of SingleDatabaseReference objects.
     *
     * @return ArrayList of SingleDatabaseReference objects.
     */
    public final ArrayList<SingleDatabaseReference> getDatabaseSequenceReferenceList() {
        return databaseSequences;
    }
    
        /**
     * Returns an ArrayList of SingleDatabaseReference objects.
     */
    public final void clearDatabaseSequenceReferenceList() {
        this.databaseSequences.clear();
    }
    
    /**
     * Compares the protein accession of SingleDatabaseReference objects with each other.
     * 
     * @return Integer based on the value of the protein accessions.
     */
    static Comparator<SingleDatabaseReference> getProteinAccessionComparator() {
        return new Comparator<SingleDatabaseReference>() {
            @Override
            public int compare(SingleDatabaseReference o1, SingleDatabaseReference o2) {
                return o1.getProteinAccession().compareTo(o2.getProteinAccession());
            }
        };
    }

    /**
     * Sort collection based on protein accession.
     */
    public final void sortOnProteinAccession() {
        Collections.sort(this.databaseSequences, getProteinAccessionComparator());
    }
    
    /**
     * Compare peptide sequences of SingleDatabaseReferences with eachother.
     * 
     * @return Integer based on the peptide sequences.
     */
    static Comparator<SingleDatabaseReference> getPeptideSequenceComparator() {
        return new Comparator<SingleDatabaseReference>() {
            @Override
            public int compare(SingleDatabaseReference o1, SingleDatabaseReference o2) {
                return o1.getPeptideSequence().compareTo(o2.getPeptideSequence());
            }
        };
    }

    /**
     * Sort collection based on the peptide sequences.
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.databaseSequences, getPeptideSequenceComparator());
    }
}

