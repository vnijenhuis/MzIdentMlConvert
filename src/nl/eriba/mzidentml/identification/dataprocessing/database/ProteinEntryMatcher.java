/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.dataprocessing.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import nl.eriba.mzidentml.identification.collections.general.CombinedDatabaseReferenceCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdProteinDetectionHypothesisCollection;
import nl.eriba.mzidentml.identification.collections.output.ProteinOutputCollection;
import nl.eriba.mzidentml.identification.objects.general.CombinedDatabaseReference;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdProteinDetectionHypothesis;
import nl.eriba.mzidentml.identification.objects.output.ProteinOutput;
import nl.eriba.mzidentml.tools.CalculationTools;

/**
 *
 * @author Vikthor
 */
public class ProteinEntryMatcher implements Callable {
    /**
     * 
     */
    private final ProteinOutputCollection proteinEntryCollection;
    
    /**
     * 
     */
    private final CombinedDatabaseReference databaseReference;
    
    /**
     * 
     */
    private final HashMap<Character, MzIdProteinDetectionHypothesisCollection> proteinHypothesisCollectionMap;


    /**
     * 
     * @param databaseReference
     * @param proteinEntryCollection 
     */
    public ProteinEntryMatcher(final CombinedDatabaseReference databaseReference, ProteinOutputCollection proteinEntryCollection, final HashMap<Character, MzIdProteinDetectionHypothesisCollection> proteinHypothesisCollectionMap) {
        this.proteinEntryCollection = proteinEntryCollection;
        this.databaseReference = databaseReference;
        this.proteinHypothesisCollectionMap = proteinHypothesisCollectionMap;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    @Override
    public Object call() throws Exception {
        ArrayList<Object> collection = new ArrayList<>();
        CalculationTools tools = new CalculationTools();
        char charAt = databaseReference.getProteinAccession().charAt(0);
        if (proteinHypothesisCollectionMap.keySet().contains(charAt)) {
            MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection = proteinHypothesisCollectionMap.get(charAt);
            if (!proteinHypothesisCollectionMap.isEmpty()) {
                outerLoop: for (MzIdProteinDetectionHypothesis proteinHypothesis: proteinHypothesisCollection.getProteinDetectionHypothesisList()) {
                    if (databaseReference.getProteinAccession().equals(proteinHypothesis.getProteinAccession())) {
                        Integer proteinLength = Collections.max(databaseReference.getEvidenceIdList());
                        Double proteinCoverage = tools.calculateProteinCoverage(databaseReference.getStartIndexList(), databaseReference.getEndIndexList(), proteinLength);
                        //Average protein mass is not available in the mzid file. Requires a database of protein sequences to acquire information.
                        Double averageMass = 0.0;
                        tools.roundDouble(averageMass, 2);
                        String proteinAccession = databaseReference.getProteinAccession();
                        String description = databaseReference.getProteinDescription();
                        Integer peptideCount = databaseReference.getTotalPeptideCount();
                        Integer uniquePeptideCount = databaseReference.getUniquePeptideCount();
                        String score = proteinHypothesis.getCvParamList().get(0).getValue();
                        Double proteinScore = Double.parseDouble(score);
                        Integer proteinGroup = proteinHypothesis.getProteinGroup();
                        String proteinId = proteinHypothesis.getProteinId();
                        String postTranslationalModification = "";
                        for (String modification: databaseReference.getPostTranslationalModifications()) {
                            if (postTranslationalModification.isEmpty()) {
                                postTranslationalModification += modification;
                            } else { 
                                postTranslationalModification += ":" + modification;
                            }
                        }
                        ProteinOutput proteinEntry = new ProteinOutput(proteinGroup, proteinId, proteinAccession, proteinScore, proteinCoverage, peptideCount, uniquePeptideCount, postTranslationalModification, averageMass, description);
                        proteinEntryCollection.addProteinEntry(proteinEntry);
                        // collection.add(protein);
                        if (databaseReference.getProteinAccession().contains("ADIDVSGPK")) {
                            System.out.println(proteinEntry.toString());
                        }
                        break outerLoop;
                    }
                }
            }
        }
        collection.add(proteinEntryCollection);
        return collection;
    }

    /**
     * 
     * @param proteinHypothesisCollection
     * @param combinedDatabaseReferenceCollection
     * @param threads
     * @return
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public final ProteinOutputCollection createProteinEntryCollection(MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection, final CombinedDatabaseReferenceCollection combinedDatabaseReferenceCollection, final Integer threads) throws InterruptedException, ExecutionException, IOException {
        MzIdProteinDetectionHypothesisCollection filteredCollection = new MzIdProteinDetectionHypothesisCollection();
        MzIdProteinDetectionHypothesisCollection reversedCollection = new MzIdProteinDetectionHypothesisCollection();
        CombinedDatabaseReferenceCollection reversedReferenceCollection = new CombinedDatabaseReferenceCollection();
        CombinedDatabaseReferenceCollection filteredReferenceCollection = new CombinedDatabaseReferenceCollection();
        for (MzIdProteinDetectionHypothesis hypothesisEntry: proteinHypothesisCollection.getProteinDetectionHypothesisList()) {
            if (hypothesisEntry.isPassThreshold()) {
                if (hypothesisEntry.getProteinAccession().contains("_REVERSED")) {
                    reversedCollection.addProteinDetectionHypothesis(hypothesisEntry);
                } else {
                    filteredCollection.addProteinDetectionHypothesis(hypothesisEntry);
                }
            }
        }
        for (CombinedDatabaseReference reference: combinedDatabaseReferenceCollection.getDatabaseReferenceList()) {
            if (reference.getProteinAccession().contains("_REVERSED")) {
                reversedReferenceCollection.addDatabaseReference(reference);
            } else {
                filteredReferenceCollection.addDatabaseReference(reference);
            }
        }
        ProteinOutputCollection finalProteinOutputCollection = new ProteinOutputCollection();
        finalProteinOutputCollection = matchReferencesToDatabase(finalProteinOutputCollection, reversedCollection, reversedReferenceCollection, threads);
        finalProteinOutputCollection = matchReferencesToDatabase(finalProteinOutputCollection, filteredCollection, filteredReferenceCollection, threads);
        return finalProteinOutputCollection;
    }

    /**
     * Matches the ProteinHypothesisCollection and CombinedDatabaseReferenceCollection to acquire protein data without database matching.
     *
     * @param proteinEntryCollection collection of ProteinEntry objects.
     * @param proteinHypothesisCollection collection of ProteinHypothesis objects.
     * @param combinedDatabaseReferenceCollection collection of CombinedDatabaseReference objects.
     * @param threads amount of threads used for multithreading purpose.
     * @return updated collection of ProteinEntry objects.
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public final ProteinOutputCollection matchReferencesToDatabase(ProteinOutputCollection proteinEntryCollection, MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection,
            CombinedDatabaseReferenceCollection combinedDatabaseReferenceCollection, final Integer threads) throws InterruptedException, ExecutionException, IOException {
        proteinHypothesisCollection.sortOnProteinAccession();
        combinedDatabaseReferenceCollection.sortOnAccession();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        HashMap<Character, MzIdProteinDetectionHypothesisCollection> proteinHypothesisMap = createProteinHypothesisMap(proteinHypothesisCollection);
        int count = 0;
        for (CombinedDatabaseReference combinedDatabaseReference: combinedDatabaseReferenceCollection.getDatabaseReferenceList()) {
            count++;
            Callable<ArrayList<Object> > callable = new ProteinEntryMatcher(combinedDatabaseReference, proteinEntryCollection, proteinHypothesisMap);
            Future<ArrayList<Object>> future = executor.submit(callable);
            if (!future.get().isEmpty()) {
                proteinEntryCollection = (ProteinOutputCollection) future.get().get(0);
            }
            if (count % 2000 == 0) {
                System.out.println("Processed " + count + " of " + combinedDatabaseReferenceCollection.getDatabaseReferenceList().size() + " database references.");
            }
        }
        executor.shutdown();
        proteinEntryCollection.sortOnProteinGroup();
        return proteinEntryCollection;
    }

    /**
     * 
     * @return 
     */
    private HashMap<Character, MzIdProteinDetectionHypothesisCollection> createProteinHypothesisMap(MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection) {
        HashMap<Character, MzIdProteinDetectionHypothesisCollection> proteinHypothesisMap = new HashMap<>();
        for (MzIdProteinDetectionHypothesis proteinHypothesis: proteinHypothesisCollection.getProteinDetectionHypothesisList()) {
            Boolean newEntry = true;
            if (!proteinHypothesisMap.isEmpty()) {
                for (Map.Entry<Character, MzIdProteinDetectionHypothesisCollection> y: proteinHypothesisMap.entrySet()) {
                    if (y.getKey() == proteinHypothesis.getProteinAccession().charAt(0)) {
                        y.getValue().addProteinDetectionHypothesis(proteinHypothesis);
                        newEntry = false;
                        break;
                    }
                }
            }
            if (newEntry) {
                MzIdProteinDetectionHypothesisCollection newHypothesisCollection = new MzIdProteinDetectionHypothesisCollection();
                newHypothesisCollection.addProteinDetectionHypothesis(proteinHypothesis);
                proteinHypothesisMap.put(proteinHypothesis.getProteinAccession().charAt(0), newHypothesisCollection);
            }
        }
        return proteinHypothesisMap;
    }
}
