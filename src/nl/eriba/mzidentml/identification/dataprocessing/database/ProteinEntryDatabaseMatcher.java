/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.dataprocessing.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import nl.eriba.mzidentml.identification.collections.output.ProteinOutputCollection;
import nl.eriba.mzidentml.identification.collections.general.CombinedDatabaseReferenceCollection;
import nl.eriba.mzidentml.identification.collections.general.ProteinDatabaseSequenceCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdProteinDetectionHypothesisCollection;
import nl.eriba.mzidentml.identification.objects.output.ProteinOutput;
import nl.eriba.mzidentml.identification.objects.general.CombinedDatabaseReference;
import nl.eriba.mzidentml.identification.objects.general.ProteinDatabaseSequence;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdProteinDetectionHypothesis;
import nl.eriba.mzidentml.tools.CalculationTools;

/**
 *
 * @author vnijenhuis
 */
public class ProteinEntryDatabaseMatcher implements Callable {
    /**
     * 
     */
    private final ProteinOutputCollection proteinEntryCollection;
    
    /**
     * 
     */
    private final HashMap<Character, ProteinDatabaseSequenceCollection> databaseSequenceCollectionMap;
    
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
    public ProteinEntryDatabaseMatcher(final CombinedDatabaseReference databaseReference, final HashMap<Character, ProteinDatabaseSequenceCollection> databaseSequenceCollectionMap, ProteinOutputCollection proteinEntryCollection, final HashMap<Character, MzIdProteinDetectionHypothesisCollection> proteinHypothesisCollectionMap) {
        this.proteinEntryCollection = proteinEntryCollection;
        this.databaseSequenceCollectionMap = databaseSequenceCollectionMap;
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
        if (databaseSequenceCollectionMap.keySet().contains(charAt) && proteinHypothesisCollectionMap.keySet().contains(charAt)) {
            ProteinDatabaseSequenceCollection proteinDatabaseCollection = databaseSequenceCollectionMap.get(charAt);
            MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection = proteinHypothesisCollectionMap.get(charAt);
            if (!proteinHypothesisCollectionMap.isEmpty()) {
                outerLoop: for (MzIdProteinDetectionHypothesis proteinHypothesis: proteinHypothesisCollection.getProteinDetectionHypothesisList()) {
                    if (databaseReference.getProteinAccession().equals(proteinHypothesis.getProteinAccession())) {
                        for (ProteinDatabaseSequence databaseSequence: proteinDatabaseCollection.getProteinCollection()) {
                            String reversedDatabaseProteinAccession = databaseSequence.getProteinAccession() + "_REVERSED";
                            //Stop as soon as first index does not match anymore. Collection is sorted, so first index matching allows for bypassing lots of unncessary matches.
                            if (databaseSequence.getProteinAccession().equals(proteinHypothesis.getProteinAccession()) || reversedDatabaseProteinAccession.equals(proteinHypothesis.getProteinAccession())) {
                                Integer proteinLength = databaseSequence.getProteinSequence().length();
                                Double proteinCoverage = tools.calculateProteinCoverage(databaseReference.getStartIndexList(), databaseReference.getEndIndexList(), proteinLength);
                                Double averageMass = databaseSequence.getAverageMass();
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
            }
        }
        collection.add(proteinEntryCollection);
        return collection;
    }

    /**
     * 
     * @param proteinDatabase
     * @param proteinEntryCollection
     * @param proteinHypothesisCollection
     * @param combinedDatabaseReferenceCollection
     * @param threads
     * @return
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public final ProteinOutputCollection createProteinEntryCollection(ProteinDatabaseSequenceCollection proteinDatabase, ProteinOutputCollection proteinEntryCollection, MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection, final CombinedDatabaseReferenceCollection combinedDatabaseReferenceCollection, final Integer threads) throws InterruptedException, ExecutionException, IOException {
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
        proteinEntryCollection = matchReferencesToDatabase(proteinDatabase, proteinEntryCollection, reversedCollection, reversedReferenceCollection, threads);
        proteinEntryCollection = matchReferencesToDatabase(proteinDatabase, proteinEntryCollection, filteredCollection, filteredReferenceCollection, threads);
        return proteinEntryCollection;
    }

    /**
     * Match collections to the protein database.
     * @param proteinDatabase
     * @param proteinEntryCollection
     * @param proteinHypothesisCollection
     * @param combinedDatabaseReferenceCollection
     * @param threads
     * @return
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public final ProteinOutputCollection matchReferencesToDatabase(ProteinDatabaseSequenceCollection proteinDatabase, ProteinOutputCollection proteinEntryCollection, MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection,
            CombinedDatabaseReferenceCollection combinedDatabaseReferenceCollection, final Integer threads) throws InterruptedException, ExecutionException, IOException {
        proteinHypothesisCollection.sortOnProteinAccession();
        combinedDatabaseReferenceCollection.sortOnAccession();
        proteinDatabase.sortOnProteinAccession();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        HashMap<Character, MzIdProteinDetectionHypothesisCollection> proteinHypothesisMap = createProteinHypothesisMap(proteinHypothesisCollection);
        HashMap<Character, ProteinDatabaseSequenceCollection> databaseSequenceMap = createDatabaseSequenceMap(proteinDatabase);
        int count = 0;
        for (CombinedDatabaseReference combinedDatabaseReference: combinedDatabaseReferenceCollection.getDatabaseReferenceList()) {
            count++;
            Callable<ArrayList<Object> > callable = new ProteinEntryDatabaseMatcher(combinedDatabaseReference, databaseSequenceMap, proteinEntryCollection, proteinHypothesisMap);
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

    /**
     * 
     * @param proteinDatabase
     * @return 
     */
    private HashMap<Character, ProteinDatabaseSequenceCollection> createDatabaseSequenceMap(ProteinDatabaseSequenceCollection proteinDatabase) {
        HashMap<Character, ProteinDatabaseSequenceCollection> databaseSequenceMap = new HashMap<>();
        for (ProteinDatabaseSequence databaseSequence: proteinDatabase.getProteinCollection()) {
            Boolean newEntry = true;
            if (!databaseSequenceMap.isEmpty()) {
                for (Map.Entry<Character, ProteinDatabaseSequenceCollection> y: databaseSequenceMap.entrySet()) {
                    if (y.getKey() == databaseSequence.getProteinAccession().charAt(0)) {
                        y.getValue().addProtein(databaseSequence);
                        newEntry = false;
                        break;
                    }
                }
            }
            if (newEntry) {
                ProteinDatabaseSequenceCollection newDatabaseCollection = new ProteinDatabaseSequenceCollection();
                newDatabaseCollection.addProtein(databaseSequence);
                databaseSequenceMap.put(databaseSequence.getProteinAccession().charAt(0), newDatabaseCollection);
            }
        }
        return databaseSequenceMap;
    }
}
