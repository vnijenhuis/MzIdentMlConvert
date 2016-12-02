/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.output.DatabaseSearchPsmOutput;

/**
 * Defines a DatabaseSearchPsmOutputCollection object.
 *
 * @author vnijenhuis
 */
public class DatabaseSearchPsmOutputCollection {

    /**
     * Creates an ArrayList for DatabaseSearchPsmOutput objects.
     */
    private final ArrayList<DatabaseSearchPsmOutput> databaseSearchPsmEntryList;

    /**
     * Creates a new ArrayList.
     */
    public DatabaseSearchPsmOutputCollection() {
        databaseSearchPsmEntryList = new ArrayList<>();
    }

    /**
     * Adds a DatabaseSearchPsmOutput object to the ArrayList.
     *
     * @param databaseSearchEntry DatabaseSearchPsmOutput object.
     */
    public final void addDatabaseSearchPsmEntry(final DatabaseSearchPsmOutput databaseSearchEntry) {
        databaseSearchPsmEntryList.add(databaseSearchEntry);
    }

    /**
     * Removes a DatabaseSearchPsmOutput object from the ArrayList.
     *
     * @param databaseSearchEntry DatabaseSearchPsmOutput object.
     */
    public final void removeDatabaseSearchPsmEntry(final DatabaseSearchPsmOutput databaseSearchEntry) {
        databaseSearchPsmEntryList.remove(databaseSearchEntry);
    }

    /**
     * Returns the ArrayList of DatabaseSearchPsmOutput objects.
     *
     * @return ArrayList of DatabaseSearchPsmOutput objects.
     */
    public final ArrayList<DatabaseSearchPsmOutput> getDatabaseSearchPsmEntryList() {
        return databaseSearchPsmEntryList;
    }
    
    /**
     * Compares the peptide score of DatabaseSearchPsmOutput objects.
     * 
     * @return Integer based on the peptide score.
     */
    static Comparator<DatabaseSearchPsmOutput> getScoreComparator() {
        return new Comparator<DatabaseSearchPsmOutput>() {
            @Override
            public int compare(DatabaseSearchPsmOutput o1, DatabaseSearchPsmOutput o2) {
                return o1.getPeptideScore().compareTo(o2.getPeptideScore());
            }
        };
    }

    /**
     * Sorts the collection based on the peptide score.
     */
    public final void sortOnPeptideScore() {
        Collections.sort(this.databaseSearchPsmEntryList, getScoreComparator());
    }
}
