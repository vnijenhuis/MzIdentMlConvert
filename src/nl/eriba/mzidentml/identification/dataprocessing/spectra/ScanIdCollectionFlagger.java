/*
 * @author Vikthor Nijenhuis
 * @project peptide fragmentation control
 */
package nl.eriba.mzidentml.identification.dataprocessing.spectra;

import java.util.ArrayList;
import nl.eriba.mzidentml.identification.collections.output.ScanIdOutputCollection;
import nl.eriba.mzidentml.identification.objects.output.ScanIdOutput;

/**
 * Flags ScanID objects depending on the difference between sequences.
 *
 * @author vnijenhuis
 */
public class ScanIdCollectionFlagger {

    /**
     * Sets the flags for the difference between dataset sequences.
     *
     * @param scanIdEntryCollection collection of ScanId objects.
     * @param sizeIndex size of the sequence list.
     * @return collection of ScanID objects with appropriate flags.
     */
    public final ScanIdOutputCollection setFlags(final ScanIdOutputCollection scanIdEntryCollection, final Integer sizeIndex) {
        //Standard flag is already set to 0. 
        for (ScanIdOutput scanObject : scanIdEntryCollection.getScanIdEntryList()) {
            for (int index = 0; index < scanObject.getPeptideSequenceList().size(); index++) {
                //Set flag for missing sequences to 1.
                if (scanObject.getPeptideSequenceList().get(index).isEmpty()) {
                    scanObject.setEntryFlag(1);
                    break;
                }
            }
            ArrayList<String> sequences = scanObject.getPeptideSequenceList();
            for (int index = 1; index < sequences.size(); index++) {
                //Remove all mass change matchesand similar matches that are present in some sequences. Example:(+15.99)  
                String mainSequence = sequences.get(0).replaceAll("\\(\\+[0-9]+\\.[0-9]+\\)", "");
                String sequence = sequences.get(index).replaceAll("\\(\\+[0-9]+\\.[0-9]+\\)", "");
                if (!mainSequence.equals(sequence)) {
                    char[] targetSequence = mainSequence.toCharArray();
                    if (mainSequence.length() == sequences.get(index).length()) {
                        char[] testSequence = sequence.toCharArray();
                        for (int j = 0; j < targetSequence.length; j++) {
                            //Matches sequences per character.
                            if (targetSequence[j] != testSequence[j]) {
                                String targetAmino = "" + targetSequence[j];
                                String testAmino = "" + testSequence[j];
                                //If Leucine/Isoleucine changes to Isoleucine/Leucine and no sequence is missing the flag is set to 2.
                                if (targetAmino.matches("I") && testAmino.matches("L")
                                        || targetAmino.matches("L") && testAmino.matches("I")) {
                                    scanObject.setEntryFlag(2);
                                } else {
                                    //All other differences.
                                    scanObject.setEntryFlag(3);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return scanIdEntryCollection;
    }
}
