/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.dataprocessing.spectra;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import nl.eriba.mzidentml.identification.collections.general.CombinedDatabaseReferenceCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdDatabaseSequenceCollection;
import nl.eriba.mzidentml.identification.collections.general.SingleDatabaseReferenceCollection;
import nl.eriba.mzidentml.identification.collections.general.CombinedPeptideEntryCollection;
import nl.eriba.mzidentml.identification.objects.general.CombinedDatabaseReference;
import nl.eriba.mzidentml.identification.objects.general.SingleDatabaseReference;
import nl.eriba.mzidentml.identification.objects.general.CombinedPeptideEntry;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdDatabaseSequence;

/**
 * Creates a CombineDatabaseReferenceCollection by combining information of SingleDatabaseReference objects.
 * @author vnijenhuis
 */
public class CombineDatabaseReferenceInformation implements Callable {

    /**
     * Collection of MzIdDatabaseSequence objects.
     */
    private final MzIdDatabaseSequenceCollection databaseSequenceCollection;

    /**
     * CombinedDatabaseReference object.
     */
    private final CombinedDatabaseReference reference;

    /**
     * 
     * @param databaseSequenceCollection collection of MzIdDatabaseSequence objects.
     * @param databaseReference CombinedDatabaseReference object.
     */
     public CombineDatabaseReferenceInformation(MzIdDatabaseSequenceCollection databaseSequenceCollection, CombinedDatabaseReference databaseReference) {
         this.databaseSequenceCollection = databaseSequenceCollection;
         this.reference = databaseReference;
     }

     /**
      * Matches the reference accession to the database sequence collection.
      * 
      * @return updated reference and database sequence collection.
      */
    @Override
    public Object call() {
        ArrayList<Object> objectList = new ArrayList<>();
        ArrayList<MzIdDatabaseSequence> databaseSequenceList = new ArrayList<>();
        for (int i = 0; i < databaseSequenceCollection.getDatabaseSequenceList().size() - 1; i++) {
            MzIdDatabaseSequence databaseSequence = databaseSequenceCollection.getDatabaseSequenceList().get(i);
            if (reference.getProteinAccession().equals(databaseSequence.getProteinAccession())) {
                if (databaseSequence.getProteinDescription() == null || databaseSequence.getProteinDescription().isEmpty()) {
                    reference.setProteinDescription("Missing");
                } else {
                    reference.setProteinDescription(databaseSequence.getProteinDescription());
                }
                databaseSequenceList.add(databaseSequence);
                break;
            } else if (reference.getProteinAccession().equals(databaseSequence.getReversedProteinAccession())) {
                if (databaseSequence.getReversedProteinDescription()== null || databaseSequence.getProteinDescription().isEmpty()) {
                    reference.setProteinDescription("Missing");
                } else {
                    reference.setProteinDescription(databaseSequence.getReversedProteinDescription());
                }
                databaseSequenceList.add(databaseSequence);
                break;
            }
        }
        for (MzIdDatabaseSequence sequence: databaseSequenceList) {
            databaseSequenceCollection.getDatabaseSequenceList().remove(sequence);
        }
        objectList.add(reference);
        objectList.add(databaseSequenceCollection);
        return objectList;
    }

    /**
     * Combines database reference info.
     * 
     * @param sequenceDatabaseReferenceCollection SingleDatabaseReferenceCollection of SingleDatabaseReference objects.
     * @param databaseSequenceCollection MzIdDatabaseSequenceCollection of MzIdDatabaseSequence objects.
     * @param uniquePeptides UniquePeptideCollection containing UniquePeptideEntry objects.
     * @param threads amount of threads used for this process.
     * @return CombinedDatabaseReferenceCollection containing CombinedDatabaseReference objects.
     * @throws java.lang.InterruptedException 
     * @throws java.util.concurrent.ExecutionException 
     */
    public CombinedDatabaseReferenceCollection combineDatabaseReferenceData(SingleDatabaseReferenceCollection sequenceDatabaseReferenceCollection, MzIdDatabaseSequenceCollection databaseSequenceCollection, CombinedPeptideEntryCollection uniquePeptides, Integer threads) throws InterruptedException, ExecutionException {
        System.out.println("Combining database reference collection entries");
        CombinedDatabaseReferenceCollection combinedReferenceCollection = createCombinedReferenceCollection(sequenceDatabaseReferenceCollection);
        sequenceDatabaseReferenceCollection.sortOnProteinAccession();
        //Match peptides with unique accession to the list of references.
        combinedReferenceCollection.sortOnAccession();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CombinedDatabaseReferenceCollection updatedDatabaseReferenceCollection = new CombinedDatabaseReferenceCollection();
        for (CombinedDatabaseReference databaseReference: combinedReferenceCollection.getDatabaseReferenceList()) {
            Callable<ArrayList<Object>> callable = new CombineDatabaseReferenceInformation(databaseSequenceCollection, databaseReference);
            Future<ArrayList<Object>> future = executor.submit(callable);
            CombinedDatabaseReference combinedReference = (CombinedDatabaseReference) future.get().get(0);
            databaseSequenceCollection = (MzIdDatabaseSequenceCollection) future.get().get(1);
            updatedDatabaseReferenceCollection.addDatabaseReference(combinedReference);
        }
        executor.shutdown();
        updatedDatabaseReferenceCollection = addUniquePeptideCount(uniquePeptides, updatedDatabaseReferenceCollection);
        updatedDatabaseReferenceCollection.sortOnAccession();
        return updatedDatabaseReferenceCollection;
    }

    /**
     * Adds the unique peptide count to the CombinedDatabaseReference object.
     * 
     * @param uniquePeptides list of UniquePeptideEntry objects.
     * @param combinedReferenceCollection collection of CombinedDatabaseReference objects.
     * @return a collection of CombinedDatabaseReference objects.
     */
    private CombinedDatabaseReferenceCollection addUniquePeptideCount(CombinedPeptideEntryCollection combinedPeptides, CombinedDatabaseReferenceCollection combinedReferenceCollection) {
        CombinedPeptideEntryCollection combinedPeptideCollection = new CombinedPeptideEntryCollection();
        for (CombinedPeptideEntry entry: combinedPeptides.getUniquePeptideList()) {
            if (entry.getAccessionCount() == 1) {
                combinedPeptideCollection.addCombinedPeptideEntry(entry);
            }
        }
        combinedPeptideCollection.sortOnSingleProteinAccession();
        for (CombinedDatabaseReference databaseReference: combinedReferenceCollection.getDatabaseReferenceList()) {
            for (int i = 0; i < combinedPeptideCollection.getUniquePeptideList().size() - 1; i++) {
                CombinedPeptideEntry uniqueEntry = combinedPeptideCollection.getUniquePeptideList().get(i);
                //Only one accession is present, hence why we take index of zero.
                if (uniqueEntry.getAccessionList().get(0).equals(databaseReference.getProteinAccession())) {
                    databaseReference.addToUniquePeptideCount();
                    break;
                }
            }
        }
        return combinedReferenceCollection;
    }

    /**
     * Creates a collection of CombinedDatabaseReference objects.
     * 
     * @param sequenceDatabaseReferenceCollection collection of SingleDatabaseReference objects.
     * @return CombinedDatabaseReferenceCollection.
     */
    private CombinedDatabaseReferenceCollection createCombinedReferenceCollection(SingleDatabaseReferenceCollection sequenceDatabaseReferenceCollection) {
        sequenceDatabaseReferenceCollection.sortOnProteinAccession();
        ArrayList<String> sequenceList = new ArrayList<>();
        ArrayList<Integer> startList = new ArrayList<>();
        ArrayList<Integer> endList = new ArrayList<>();
        ArrayList<String> preList = new ArrayList<>();
        ArrayList<String> postList = new ArrayList<>();
        ArrayList<String> modificationList = new ArrayList<>();
        ArrayList<Integer> evidenceList = new ArrayList<>();
        String firstAccession = sequenceDatabaseReferenceCollection.getDatabaseSequenceReferenceList().get(0).getProteinAccession();
        CombinedDatabaseReferenceCollection combinedDatabaseReferenceCollection = new CombinedDatabaseReferenceCollection();
        Integer collectionSize = sequenceDatabaseReferenceCollection.getDatabaseSequenceReferenceList().size() - 1;
        for (int index = 0; index < collectionSize - 1; index++) {
            SingleDatabaseReference databaseReference = sequenceDatabaseReferenceCollection.getDatabaseSequenceReferenceList().get(index);
            String accession = databaseReference.getProteinAccession();
            if (accession.equals(firstAccession)) {
                Boolean newEntry = true;
                //Check for duplicates. Allows for easier calculations of protein coverage.
                if (sequenceList.contains(databaseReference.getPeptideSequence())) {
                    int matchIndex = sequenceList.indexOf(databaseReference.getPeptideSequence());
                    if (startList.get(matchIndex).equals(databaseReference.getStartIndex()) && endList.get(matchIndex).equals(databaseReference.getEndIndex())) {
                        newEntry = false;
                    }
                }
                if (newEntry) {
                    sequenceList.add(databaseReference.getPeptideSequence());
                    startList.add(databaseReference.getStartIndex());
                    endList.add(databaseReference.getEndIndex());
                    preList.add(databaseReference.getPreAminoAcid());
                    postList.add(databaseReference.getPostAminoAcid());
                    evidenceList.add(databaseReference.getEvidenceId());
                    modificationList.addAll(databaseReference.getPostTranslationalModification());
                } else {
                    evidenceList.add(databaseReference.getEvidenceId());
                    modificationList.addAll(databaseReference.getPostTranslationalModification());
                }
            } else {
                CombinedDatabaseReference newReference = new CombinedDatabaseReference(firstAccession, evidenceList, sequenceList, startList, endList, preList, postList, modificationList);
                combinedDatabaseReferenceCollection.addDatabaseReference(newReference);
                firstAccession = accession;
                sequenceList = new ArrayList<>();
                startList = new ArrayList<>();
                endList = new ArrayList<>();
                preList = new ArrayList<>();
                postList = new ArrayList<>();
                modificationList = new ArrayList<>();
                evidenceList = new ArrayList<>();
                sequenceList.add(databaseReference.getPeptideSequence());
                startList.add(databaseReference.getStartIndex());
                endList.add(databaseReference.getEndIndex());
                preList.add(databaseReference.getPreAminoAcid());
                postList.add(databaseReference.getPostAminoAcid());
                modificationList.addAll(databaseReference.getPostTranslationalModification());
                evidenceList.add(databaseReference.getEvidenceId());
            } 
        }
        return combinedDatabaseReferenceCollection;
    }
}
