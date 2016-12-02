/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.dataprocessing.database;

import nl.eriba.mzidentml.identification.collections.general.ProteinDatabaseSequenceCollection;
import nl.eriba.mzidentml.identification.collections.output.ScanIdOutputCollection;
import nl.eriba.mzidentml.identification.objects.output.ScanIdOutput;
import nl.eriba.mzidentml.identification.objects.general.ProteinDatabaseSequence;
import nl.eriba.peptide.identification.boyer.Boyer;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Uses multi-threading to allow for a faster collection matching.
 *
 * @author vnijenhuis
 */
public class ScanIdDatabaseMatcher implements Callable {

    /**
     * Collection of proteins.
     */
    private final ProteinDatabaseSequenceCollection proteins;

    /**
     * Current index of the sequence array list.
     */
    private final String databaseName;

    /**
     * Single ScanIdOutput object that is being processed.
     */
    private final ScanIdOutput scanObject;

    /**
     * Collection of ScanIdOutput objects.
     */
    private final ScanIdOutputCollection collection;

    /**
     * Multi-tread database matcher.
     *
     * @param scanObject
     * @param finalScans
     * @param databaseName
     * @param proteins
     */
    public ScanIdDatabaseMatcher(final ScanIdOutput scanObject, ScanIdOutputCollection finalScans, final String databaseName,
            final ProteinDatabaseSequenceCollection proteins) {
        this.collection = finalScans;
        this.scanObject = scanObject;
        this.proteins = proteins;
        this.databaseName = databaseName;
    }

    /**
     * Collects matched peptides and returns these peptides in a new collection.
     *
     * @param scanCollection collection of ScanIDs.
     * @param databaseName name of the protein database.
     * @param proteins collection of proteins.
     * @param threads amount of threads used.
     * @return collection of matched peptides.
     * @throws InterruptedException process was interrupted.
     * @throws ExecutionException could not execute the call function.
     */
    public ScanIdOutputCollection matchSequencesToDatabase(final ScanIdOutputCollection scanCollection, final String databaseName,
            final ProteinDatabaseSequenceCollection proteins, final Integer threads)
            throws InterruptedException, ExecutionException {
        ScanIdOutputCollection finalScans = new ScanIdOutputCollection();
        //Creates a new execution service and sets the amount of threads to use. (if available)
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        System.out.println("Matching scan IDs from sample " + databaseName + " to the corresponding protein database");
        int count = 0;
        for (ScanIdOutput scanId : scanCollection.getScanIdEntryList()) {
            count++;
            Callable<ScanIdOutputCollection> callable = new ScanIdDatabaseMatcher(scanId, finalScans, databaseName, proteins);
            //Submits callable to an available thread.
            Future<ScanIdOutputCollection> future = executor.submit(callable);
            //Adds the output to finalPeptides.
            finalScans = future.get();
            if (count % 2000 == 0) {
                System.out.println("Matched " + count + " ScanId objects to the corresponding protein database.");
            }
        }
        System.out.println("Matched " + count + " ScanId objects to the corresponding protein database.");
        //Shutdown command for the pool to prevent the script from running infinitely.
        executor.shutdown();
        return finalScans;
    }

    /**
     * Call function which matches the protein and peptide collections.
     *
     * @return returns a peptide collection with peptides that did NOT match to the protein database.
     */
    @Override
    public Object call() {
        //Gather sequence from ScanID object.
        ArrayList<String> sequeceList = scanObject.getPeptideSequenceList();
        for (int currentIndex = 0; currentIndex < sequeceList.size(); currentIndex++) {
            if (sequeceList.get(currentIndex).isEmpty()) {
                if (scanObject.getDatabaseFlag(currentIndex).isEmpty()) {
                    scanObject.addDatabaseFlag(currentIndex, databaseName + "_absent");
                } else {
                    scanObject.addDatabaseFlag(currentIndex, ":" + databaseName + "_absent");
                }
            } else {
                boolean match = false;
                String sequence = sequeceList.get(currentIndex);
                sequence = sequence.replaceAll("\\(\\+[0-9]+\\.[0-9]+\\)", "");
                //Use the Boyer algorithm to quickly find peptide sequences in larger protein targets.
                Boyer peptideSequence = new Boyer(sequence);
                for (ProteinDatabaseSequence protein : proteins.getProteinCollection()) {
                    //If a match is found, set the flag to present.
                    if (peptideSequence.searchPattern(protein.getProteinSequence())) {
                        if (scanObject.getDatabaseFlag(currentIndex).isEmpty()) {
                            scanObject.addDatabaseFlag(currentIndex, databaseName + "_present");
                        } else {
                            scanObject.addDatabaseFlag(currentIndex, ":" + databaseName + "_present");
                        }
                        match = true;
                        break;
                    }
                }
                //If no match was found, set the flag to absent.
                if (!match) {
                    if (scanObject.getDatabaseFlag(currentIndex).isEmpty()) {
                        scanObject.addDatabaseFlag(currentIndex, databaseName + "_absent");
                    } else {
                        scanObject.addDatabaseFlag(currentIndex, ":" + databaseName + "_absent");
                    }
                    break;
                }
            }
        }
        //Adds updates ScanId object to the ScanIdCollection.
        collection.addScanIdEntry(scanObject);
        return collection;
    }
}
