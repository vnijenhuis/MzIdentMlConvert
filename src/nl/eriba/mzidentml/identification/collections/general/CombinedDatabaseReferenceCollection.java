/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.general.CombinedDatabaseReference;

/**
 * Defines a collection for CombinedDatabaseReference objects.
 * @author vnijenhuis
 */
public class CombinedDatabaseReferenceCollection {
    /**
     * Creates an ArrayList for CombinedDatabaseReference objects.
     */
    private final ArrayList<CombinedDatabaseReference> databaseReferenceList;

    /**
     * ArrayList of CombinedDatabaseReference objects.
     */
    public CombinedDatabaseReferenceCollection() {
        this.databaseReferenceList = new ArrayList<>();
    }

    /**
     * Adds a CombinedDatabaseReference object to the ArrayList.
     *
     * @param databaseReference CombinedDatabaseReference object.
     */
    public final void addDatabaseReference(final CombinedDatabaseReference databaseReference) {
        this.databaseReferenceList.add(databaseReference);
    }

    /**
     * Removes a CombinedDatabaseReference object from the ArrayList.
     *
     * @param databaseReference CombinedDatabaseReference object.
     */
    public final void removeDatabaseReference(final CombinedDatabaseReference databaseReference) {
        this.databaseReferenceList.remove(databaseReference);
    }

    /**
     * Returns an ArrayList of CombinedDatabaseReference objects.
     *
     * @return ArrayList of CombinedDatabaseReference objects.
     */
    public final ArrayList<CombinedDatabaseReference> getDatabaseReferenceList() {
        return this.databaseReferenceList;
    }
    
    /**
     * Compares the accessions of two CombinedDatabaseReference objects.
     * 
     * @return value based on the comparison of the two accession ids.
     */
    static Comparator<CombinedDatabaseReference> getAccessionComparator() {
        return new Comparator<CombinedDatabaseReference>() {
            @Override
            public int compare(CombinedDatabaseReference o1, CombinedDatabaseReference o2) {
                return o1.getProteinAccession().compareTo(o2.getProteinAccession());
            }
        };
    }

    /**
     * Sorts this collection based on the accession id of each object.
     */
    public final void sortOnAccession() {
        Collections.sort(this.databaseReferenceList, getAccessionComparator());
    }
}
