/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.eriba.mzidentml.identification.dataprocessing.spectra;

import nl.eriba.mzidentml.identification.collections.output.ScanIdOutputCollection;
import nl.eriba.mzidentml.identification.objects.output.ScanIdOutput;
import java.util.ArrayList;

/**
 * Separates scan entries based on results from the getEntryFlag() function.
 *
 * @author vnijenhuis
 */
public class ScanIdCollectionSeparator {

    /**
     * Separates the scan entries based on the flag parameter.
     *
     * @param scanCollection collection of ScanId objects.
     * @return two collections with differently flagged ScanID objects.
     */
    public final ArrayList<ScanIdOutputCollection> separateScanEntries(final ScanIdOutputCollection scanCollection) {
        ScanIdOutputCollection nonFlaggedScans = new ScanIdOutputCollection();
        ScanIdOutputCollection flaggedScans = new ScanIdOutputCollection();
        //A flag of 0 means that there are no differences between the peptide sequences of each dataset.
        //Flag 0 sequences are marked as non-interesting and will be moved to a different file then those with a higher flag.
        for (ScanIdOutput scanId : scanCollection.getScanIdEntryList()) {
            if (scanId.getEntryFlag() == 0) {
                nonFlaggedScans.addScanIdEntry(scanId);
            } else {
                flaggedScans.addScanIdEntry(scanId);
            }
        }
        ArrayList<ScanIdOutputCollection> separatedEntries = new ArrayList<>();
        //Add different collections to the list.
        separatedEntries.add(nonFlaggedScans);
        separatedEntries.add(flaggedScans);
        return separatedEntries;
    }
}
