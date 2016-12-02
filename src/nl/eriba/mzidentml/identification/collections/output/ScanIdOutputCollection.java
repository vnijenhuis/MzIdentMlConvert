/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.output;

import java.util.ArrayList;
import nl.eriba.mzidentml.identification.objects.output.ScanIdOutput;

/**
 * Defines a collection of ScanIdOutput objects.
 *
 * @author vnijenhuis
 */
public class ScanIdOutputCollection {

    /**
     * ArrayList that contains ScanIdOutput objects.
     */
    private final ArrayList<ScanIdOutput> scanIdEntryCollection;

    /**
     * Creates a new ArrayList.
     */
    public ScanIdOutputCollection() {
        this.scanIdEntryCollection = new ArrayList<>();
    }

    /**
     * Adds a ScanIdOutput object to the ArrayList.
     *
     * @param result target ScanIdOutput object.
     */
    public final void addScanIdEntry(final ScanIdOutput result) {
        this.scanIdEntryCollection.add(result);
    }

    /**
     * Removes a ScanIdOutput object from the ArrayList.
     *
     * @param result target ScanIdOutput object.
     */
    public final void removeScanIdEntry(final ScanIdOutput result) {
        this.scanIdEntryCollection.remove(result);
    }

    /**
     * Returns a list of ScanIdOutput objects.
     *
     * @return ArrayList of ScanIdOutput objects.
     */
    public final ArrayList<ScanIdOutput> getScanIdEntryList() {
        return this.scanIdEntryCollection;
    }
}
