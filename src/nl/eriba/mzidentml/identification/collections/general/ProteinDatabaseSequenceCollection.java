/*
 * @author Vikthor Nijenhuis
 * @project peptide spectrum identification quality control  * 
 */
package nl.eriba.mzidentml.identification.collections.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.general.ProteinDatabaseSequence;

/**
 * Defines a collection of ProteinDatabaseSequence objects.
 *
 * @author vnijenhuis
 */
public class ProteinDatabaseSequenceCollection {

    /**
     * ArrayList that contains ProteinDatabaseSequence objects.
     */
    private final ArrayList<ProteinDatabaseSequence> proteins;

    /**
     * Creates a new ArrayList.
     */
    public ProteinDatabaseSequenceCollection() {
        this.proteins = new ArrayList();
    }

    /**
     * Adds a ProteinDatabaseSequence object to the ArrayList.
     *
     * @param protein ProteinDatabaseSequence object.
     */
    public final void addProtein(final ProteinDatabaseSequence protein) {
        this.proteins.add(protein);
    }

    /**
     * Removes a ProteinDatabaseSequence object from the ArrayList.
     *
     * @param protein ProteinDatabaseSequence object.
     */
    public final void removeProtein(final ProteinDatabaseSequence protein) {
        this.proteins.remove(protein);
    }

    /**
     * Removes a ProteinDatabaseSequence object from the ArrayList.
     *
     * @param index index as Integer.
     */
    public final void removeProtein(final Integer index) {
        this.proteins.remove(index);
    }

    /**
     * Returns the ArrayList of ProteinDatabaseSequence objects.
     *
     * @return ArrayList of ProteinDatabaseSequence objects.
     */
    public final ArrayList<ProteinDatabaseSequence> getProteinCollection() {
        return this.proteins;
    }
    
    /**
     * Compares the protein accessions of ProteinDatabaseSeqence objects.
     * 
     * @return Integer value based on the protein accession.
     */
    static Comparator<ProteinDatabaseSequence> getProteinAccessionComparator() {
        return new Comparator<ProteinDatabaseSequence>() {
            @Override
            public int compare(ProteinDatabaseSequence o1, ProteinDatabaseSequence o2) {
                return o1.getProteinAccession().compareTo(o2.getProteinAccession());
            }
        };
    }

    /**
     * Sorts the collection based on the protein accession.
     */
    public final void sortOnProteinAccession() {
        Collections.sort(this.proteins, getProteinAccessionComparator());
    }
}
