/*
 * @author Vikthor Nijenhuis
 * @project PeptideItem mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.filereader;

import nl.eriba.mzidentml.identification.collections.mzid.MzIdProteinPeptideCollection;
import nl.eriba.mzidentml.identification.collections.output.DatabaseSearchPsmOutputCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdDatabaseSequenceCollection;
import nl.eriba.mzidentml.identification.collections.output.ScanIdOutputCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdPeptideCollection;
import nl.eriba.mzidentml.identification.collections.output.PeptideOutputCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdPeptideEvidenceCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdProteinDetectionHypothesisCollection;
import nl.eriba.mzidentml.identification.collections.output.ProteinPeptideOutputCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdMainElementCollection;
import nl.eriba.mzidentml.identification.collections.general.CombinedPeptideEntryCollection;
import nl.eriba.mzidentml.identification.dataprocessing.sorting.SortSpectrumResultBySequence;
import nl.eriba.mzidentml.identification.dataprocessing.sorting.SortPeptideEvidenceCollectionOnSequence;
import nl.eriba.mzidentml.identification.dataprocessing.spectra.MzIdUnmarshaller;
import nl.eriba.mzidentml.identification.dataprocessing.spectra.SingleSpectrumDataCollector;
import nl.eriba.mzidentml.identification.objects.output.DatabaseSearchPsmOutput;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdCvParam;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdDatabaseSequence;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdModification;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdPeptide;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdPeptideEvidence;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdProteinDetectionHypothesis;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdSubstituteModification;
import nl.eriba.mzidentml.identification.objects.output.PeptideOutput;
import nl.eriba.mzidentml.identification.objects.output.ProteinPeptideOutput;
import nl.eriba.mzidentml.identification.objects.output.ScanIdOutput;
import nl.eriba.mzidentml.identification.objects.general.MatchedIonSeries;
import nl.eriba.mzidentml.identification.objects.general.ProteinPeptideEntry;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdIonFragment;
import nl.eriba.mzidentml.identification.objects.general.PeptideEntry;
import nl.eriba.mzidentml.identification.objects.general.BestSpectrumEntry;
import nl.eriba.mzidentml.identification.objects.general.SpectrumIdentificationItemEntry;
import nl.eriba.mzidentml.identification.objects.general.SpectrumIdentificationResultEntry;
import nl.eriba.mzidentml.identification.collections.general.CombinedDatabaseReferenceCollection;
import nl.eriba.mzidentml.identification.collections.general.SingleDatabaseReferenceCollection;
import nl.eriba.mzidentml.identification.objects.general.SingleDatabaseReference;
import nl.eriba.mzidentml.identification.objects.general.CombinedPeptideEntry;
import nl.eriba.mzidentml.tools.CalculationTools;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.model.mzidml.Modification;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinAmbiguityGroup;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionList;
import uk.ac.ebi.jmzidml.model.mzidml.SequenceCollection;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItemRef;
import uk.ac.ebi.jmzidml.model.mzidml.SubstitutionModification;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import nl.eriba.mzidentml.identification.collections.general.MatchedIonSeriesCollection;
import nl.eriba.mzidentml.identification.dataprocessing.spectra.CombineDatabaseReferenceInformation;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdProteinPeptide;
import uk.ac.ebi.jmzidml.model.mzidml.FragmentArray;
import uk.ac.ebi.jmzidml.model.mzidml.IonType;

/**
 * Reads files with the .mzid extension and xml layout.
 *
 * @author vnijenhuis
 */
public class MzIdFileReader implements Callable {

    /**
     * Collection of ScanID objects.
     */
    private final ScanIdOutputCollection scanCollection;

    /**
     * Current index of the dataset list.
     */
    private final Integer currentIndex;

    /**
     * Total index of the dataset list.
     */
    private final Integer maximumIndex;

    /**
     * Object that contains SpectrumIdentificationResult parameter data from the mzid file.
     */
    private final SpectrumIdentificationResult spectrumResultItem;

    /**
     * Collection of MzIdPeptide objects.
     */
    private final MzIdPeptideCollection peptideCollection;

    /**
     * List of file numbers that were entered to create output files.
     */
    private final ArrayList<Integer> numbers;

    /**
     * Map of spectra counts of each peptide sequence.
     */
    private final HashMap<String, Integer> spectrumCountMap;

    /**
     * Collection of MzIdProteinDetectionHypothesis objects.
     */
    private final MzIdProteinPeptideCollection proteinPeptideCollection;

    /**
     * Intensity threshold used to filter low intensity peaks.
     */
    private final Double intensityThreshold;

    /**
     * Map of sequences and the corresponding higest scoring sequence.
     */
    private final ArrayList<BestSpectrumEntry> bestSpectrumList;

    /**
     * Collection of CombinedPeptideEntry objects.
     */
    private final CombinedPeptideEntryCollection combinedPeptideEntryCollection;

    /**
     * Contains a set of tools designed for calculation purposes.
     */
    private CalculationTools toolSet;
    
    /**
     * SpectrumIdentificationItem parameter from the mzid file.
     */
    private final SpectrumIdentificationItem spectrumIdItem;

    /**
     * mzid format file reader.
     *
     * @param spectrumResultItem SpectrumIdentificationResult parameter from the mzid file.
     * @param spectrumIdItem SpectrumIdentificationItem parameter from the mzid file.
     * @param peptideCollection collection of MzIdPeptid objects.
     * @param combinedProteinPeptideCollection collection of MzIdCombinedProteinPeptide objects.
     * @param collection ScanIdCollection to store ScanID objects.
     * @param combinedPeptideEntryCollection collection of CombinedPeptideEntry objects.
     * @param spectraCountMap HashMap containing the amount of spectra per peptide sequence.
     * @param inputNumbers input numbers that determin which data should be processed.
     * @param bestSpectrumList list with the highest spectrum score per peptide sequence.
     * @param maximumIndex maximum index of the dataset list.
     * @param currentIndex current index of the dataset list.
     * @param intensityThreshold standard or user specified intensity threshold value.
     */
    public MzIdFileReader(final SpectrumIdentificationResult spectrumResultItem, final SpectrumIdentificationItem spectrumIdItem, final MzIdPeptideCollection peptideCollection, MzIdProteinPeptideCollection combinedProteinPeptideCollection,
            final ScanIdOutputCollection collection, final CombinedPeptideEntryCollection combinedPeptideEntryCollection, final HashMap<String, Integer> spectraCountMap, ArrayList<BestSpectrumEntry> bestSpectrumList,
            final ArrayList<Integer> inputNumbers, final Integer currentIndex, final Integer maximumIndex, Double intensityThreshold) {
        this.spectrumIdItem = spectrumIdItem;
        this.spectrumResultItem = spectrumResultItem;
        this.peptideCollection = peptideCollection;
        this.scanCollection = collection;
        this.spectrumCountMap = spectraCountMap;
        this.currentIndex = currentIndex;
        this.maximumIndex = maximumIndex;
        this.numbers = inputNumbers;
        this.proteinPeptideCollection = combinedProteinPeptideCollection;
        this.combinedPeptideEntryCollection = combinedPeptideEntryCollection;
        this.intensityThreshold = intensityThreshold;
        this.bestSpectrumList = bestSpectrumList;
    }

    /**
     * Collects mzid data by storing the data into a collection of ScanID objects.
     *
     * @param mzidFile file to read the data from.
     * @param inputNumbers input numbers that determin which data should be processed.
     * @param scanIdEntryCollection collection of ScanIdEntry objets.
     * @param currentIndex current index of the dataset list.
     * @param totalIndex total index of the dataset index.
     * @param threads amount of threads used for the program.
     * @param intensityThreshold standard or user specified intensity threshold value.
     * @return returns a collection of ScanID objects.
     * @throws InterruptedException process was interrupted by another process.
     * @throws ExecutionException an error was encountered which prevents the execution.
     */
    public ArrayList<Object> collectPeptideShakerScanIDs(final String mzidFile,ScanIdOutputCollection scanIdEntryCollection, final ArrayList<Integer> inputNumbers,
            final Integer currentIndex, final Integer totalIndex, final Integer threads, final Double intensityThreshold) throws InterruptedException, ExecutionException {
        System.out.println("Reading " + mzidFile);
        MzIdUnmarshaller unmarshalMzIdFile = new MzIdUnmarshaller();
        MzIdMainElementCollection unmarshalCollection = unmarshalMzIdFile.unmarshalMzIdFile(mzidFile);
        //Unmarshaller that transforms storage data format to a memory format
        DatabaseSearchPsmOutputCollection searchPsmEntryCollection = new DatabaseSearchPsmOutputCollection();
        ProteinPeptideOutputCollection proteinPeptideEntryCollection = new ProteinPeptideOutputCollection();
        PeptideOutputCollection peptideOutputCollection = new PeptideOutputCollection();
        MatchedIonSeriesCollection matchedIonSeriesCollection = new MatchedIonSeriesCollection();
        //Get spectrum identification data
        SpectrumIdentificationList spectrumIdList = unmarshalCollection.getSpectrumIdentificationList();
        //Remove low threshold entries.
        ArrayList<BestSpectrumEntry> generateBestSpectrumList = generateBestSpectrumList(spectrumIdList);
        //Get sequence data
        SequenceCollection sequenceCollection = unmarshalCollection.getSequenceCollection();
        //Get peptide data
        MzIdPeptideCollection peptides = createPeptideCollection(sequenceCollection.getPeptide());
        //Get peptide evidence data
        List<PeptideEvidence> peptideEvidenceList = sequenceCollection.getPeptideEvidence();
        MzIdPeptideEvidenceCollection evidenceCollection = createPeptideEvidenceCollection(peptideEvidenceList);
        //Group data of database sequence objects into different collections
        MzIdDatabaseSequenceCollection dbSequenceCollection = createDatabaseSequenceCollection(sequenceCollection.getDBSequence());
        //Create SingleDatabaseReference objects that are used to create the CombinedPeptidEntry and CombinedDatabaseReference collections.
        SingleDatabaseReferenceCollection singleDatabaseReferenceCollection = createSingleDatabaseReferenceCollection(peptideEvidenceList, peptides);
        CombinedPeptideEntryCollection combinedPeptides = createCombinedPeptideCollection(singleDatabaseReferenceCollection);
        //Combined SingleDatabaseReference objects to represent all unique protein hits per peptide sequence.
        CombineDatabaseReferenceInformation combineInformation = new CombineDatabaseReferenceInformation(null, null);
        CombinedDatabaseReferenceCollection combinedDatabaseReferenceCollection = combineInformation.combineDatabaseReferenceData(singleDatabaseReferenceCollection, dbSequenceCollection, combinedPeptides, threads);
        //Create map with spectra counts per sequence
        HashMap<String, Integer> spectraCountMap = determineSpectraCounts(peptideEvidenceList);
        //Get protein hypothesis data
        ProteinDetectionList proteinHypothesisList = unmarshalCollection.getProteinHypothesisCollection();
        MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection = createProteinHypothesisCollection(proteinHypothesisList);
        MzIdProteinPeptideCollection mzidProteinPeptideCollection = combineProteinHypothesisWithPeptideEvidence(evidenceCollection, proteinHypothesisCollection);
        //Remove hits that didn't pass the initial threshold and hits that are tagged as a decoy sequence.
        mzidProteinPeptideCollection = removeLowThresholdSequences(mzidProteinPeptideCollection);
        mzidProteinPeptideCollection = removeDecoySequences(mzidProteinPeptideCollection);
        mzidProteinPeptideCollection.sortOnPeptideSequence();
        //Filter evidence collection based on evidences present in protein hypotheses
        Collections.sort(spectrumIdList.getSpectrumIdentificationResult(), new SortSpectrumResultBySequence());
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        Integer count = 0;
        System.out.println("Starting identification of spectrum results.");
        for (SpectrumIdentificationResult spectrumIdResult : spectrumIdList.getSpectrumIdentificationResult()) {
            count++;
            for (SpectrumIdentificationItem spectrumIdentificationItem: spectrumIdResult.getSpectrumIdentificationItem()) {
                //Test if the initial threshold is passed.
                if (spectrumIdentificationItem.isPassThreshold()) {
                    Callable<ArrayList<Object>> callable = new MzIdFileReader(spectrumIdResult, spectrumIdentificationItem, peptides, mzidProteinPeptideCollection, scanIdEntryCollection, combinedPeptides,  spectraCountMap, generateBestSpectrumList, inputNumbers, currentIndex, totalIndex, intensityThreshold);
                    //Collects the output from the call function
                    Future<ArrayList<Object>> future = executor.submit(callable);
                    for (Integer number : numbers) {
                        //Add data to respective collection if number is present.
                        if (null != number) {
                            switch (number) {
                                case 1:
                                    //Create ScanIdEntry collection for database matching purposes.
                                    //Displays peptide sequence and PEAKS scan id.
                                    scanIdEntryCollection = (ScanIdOutputCollection) future.get().get(0);
                                    break;
                                case 2:
                                    //Gathers DatabaseSearchPsm objects to define peptide spectrum match data.
                                    DatabaseSearchPsmOutputCollection psmObjects = (DatabaseSearchPsmOutputCollection) future.get().get(1);
                                    for (DatabaseSearchPsmOutput object : psmObjects.getDatabaseSearchPsmEntryList()) {
                                        searchPsmEntryCollection.addDatabaseSearchPsmEntry(object);
                                    }
                                    break;
                                case 3:
                                    //Gathers PeptideOutput objects to define peptide data.
                                    PeptideOutputCollection peptideObjects = (PeptideOutputCollection) future.get().get(2);
                                    for (PeptideOutput object : peptideObjects.getPeptideEntryList()) {
                                        peptideOutputCollection.addPeptideEntry(object);
                                    }
                                    //Gathers MatchedIonSeries objects to define the ion series and quality of the best spectrum matches.
                                    MatchedIonSeriesCollection matchedIonSeries = (MatchedIonSeriesCollection) future.get().get(3);
                                    for (MatchedIonSeries ionSeries: matchedIonSeries.getMatchedIonSeriesList()) {
                                        matchedIonSeriesCollection.addMatchedIonSeries(ionSeries);
                                    }
                                    break;
                                case 4:
                                    //Gathers ProteinPeptideOutput objects to define protein-peptide data.
                                    ProteinPeptideOutputCollection proteinPeptideObjects = (ProteinPeptideOutputCollection) future.get().get(4);
                                    mzidProteinPeptideCollection = (MzIdProteinPeptideCollection) future.get().get(5);
                                    for (ProteinPeptideOutput object : proteinPeptideObjects.getProteinPeptideEntryList()) {
                                        proteinPeptideEntryCollection.addProteinPeptideEntry(object);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
            if (count % 2000 == 0) {
                System.out.println("Matched data for " + count + " entries.");
            }
        }
        System.out.println("Matched data for " + count + " entries.");
        //Sort collections 
        System.out.println("Sorting collections...");
        searchPsmEntryCollection.sortOnPeptideScore();
        proteinPeptideEntryCollection.sortOnProteinGroup();
        peptideOutputCollection.sortOnPeptideScore();   
        combinedDatabaseReferenceCollection.sortOnAccession();
        //Add collections to list.
        ArrayList<Object> collections = new ArrayList<>();
        collections.add(scanIdEntryCollection);
        collections.add(searchPsmEntryCollection);
        collections.add(peptideOutputCollection);
        collections.add(matchedIonSeriesCollection);
        collections.add(proteinPeptideEntryCollection);
        collections.add(proteinHypothesisCollection);
        collections.add(combinedDatabaseReferenceCollection);
        //Shutdown executor to remove the created threadpool.
        executor.shutdown();
        return collections;
    }

    /**
     * Call function that is used by a thread to process SpectrumIdentificationResult objects.
     *
     * @return return ScanIdCollection with added/updated ScanID object.
     * @throws java.lang.InterruptedException process was interrupted.
     * @throws java.util.concurrent.ExecutionException any exception encountered during execution.
     */
    @Override
    public Object call() throws InterruptedException, ExecutionException {
        //Call class that can gather data from single spectra.
        SingleSpectrumDataCollector collector = new SingleSpectrumDataCollector();
        //Gather data from SpectrumIdentificationItem.
        SpectrumIdentificationItemEntry spectrumItemData = collector.getSpectrumIdentificationItemData(spectrumResultItem, spectrumIdItem);
        String sequence = spectrumItemData.getPeptideSequence();
        String score = spectrumItemData.getPsmScore();
        Double peptideScore = Double.parseDouble(score);
        Double calculatedMassToCharge = spectrumItemData.getCalculatedMassToCharge();
        Double experimentalMassToCharge = spectrumItemData.getExperimentalMassToCharge();
        Double theoreticalMassToCharge = spectrumItemData.getTheoreticalMassToCharge();
        Integer length = spectrumItemData.getSequenceLength();
        //Gather data from SpectrumIdentificationResult item.
        SpectrumIdentificationResultEntry resultItemData = collector.getSpectrumIdentificationResultData(spectrumResultItem);
//        Integer index = resultItemData.getIndex();
        String retentionTime = resultItemData.getRetentionTime();
        String scanNumber = resultItemData.getScanNumber();
        String scanId = resultItemData.getScanId();
//        String scanID = resultItemData.getScanID();
        Double partsPerMillion = ((calculatedMassToCharge - experimentalMassToCharge) / calculatedMassToCharge) * 1000000;
        toolSet = new CalculationTools();
        partsPerMillion = toolSet.roundDouble(partsPerMillion, 2);
        //Gther data from Peptide item.
        PeptideEntry peptideData = collector.getPeptideCollectionData(peptideCollection, sequence);
        String aScore = peptideData.getAScore();
        String modifiedSequence = peptideData.getModifiedSequence();
        String postTranslationalModification = peptideData.getPostTranslationalModification();
        //Create lists for each object.
        ArrayList<Object> collectionList = new ArrayList<>();
        ProteinPeptideOutputCollection proteinPeptideList = new ProteinPeptideOutputCollection();
        DatabaseSearchPsmOutputCollection databaseSearchPsmList = new DatabaseSearchPsmOutputCollection();
        PeptideOutputCollection peptideOutputCollection = new PeptideOutputCollection();
        MatchedIonSeriesCollection matchedIonSeriesCollection = new MatchedIonSeriesCollection();
        //Adds ScanID entry to the scanCollection
        if (numbers.contains(1)) {
            addEntryToScanCollection(scanId, sequence, peptideScore);
        }
        //If one of the file numbers corresponds to
        ProteinPeptideEntry proteinPeptideData = matchProteinPeptideData(sequence);
        String accessions = proteinPeptideData.getAccessions();
        String unique = proteinPeptideData.getUniqueness();
        //Match sequence to protein peptide data.
        Integer spectraCount = spectrumCountMap.get(sequence);
        if (numbers.contains(2)) {
            DatabaseSearchPsmOutput dbsObject = new DatabaseSearchPsmOutput(modifiedSequence, peptideScore, theoreticalMassToCharge, length, partsPerMillion, calculatedMassToCharge, retentionTime, scanNumber, accessions, postTranslationalModification, aScore, spectraCount);
            databaseSearchPsmList.addDatabaseSearchPsmEntry(dbsObject);
        }
        Double highestScore = 0.0;
        for (BestSpectrumEntry spectrum : bestSpectrumList) {
            if (spectrum.getSequence().equals(sequence) && !spectrum.getFlag()) {
                highestScore = spectrum.getScore();
                spectrum.setFlag(true);
                break;
            }
        }
        if (numbers.contains(3)) {
            if (Objects.equals(peptideScore, highestScore)) {
                MatchedIonSeries matchedIonSeries = createFragmentIndexList(spectrumResultItem, spectrumIdItem, intensityThreshold, sequence, peptideScore, accessions);
                matchedIonSeriesCollection.getMatchedIonSeriesList().add(matchedIonSeries);
                // 0 not complete coverage of sequence, 1 = y-ion coverage, 2 = b-ion coverage 3 = combined ion coverage
                PeptideOutput peptideObject = new PeptideOutput(modifiedSequence, peptideScore, theoreticalMassToCharge, length, partsPerMillion, calculatedMassToCharge, retentionTime, scanNumber, accessions, spectraCount, postTranslationalModification, aScore);
                peptideOutputCollection.addPeptideEntry(peptideObject);
            }
        }
        if (numbers.contains(4)) {
            if (Objects.equals(peptideScore, highestScore)) {
                ArrayList<MzIdProteinPeptide> removeableEvidence = new ArrayList<>();
                Boolean allowMismatch = false;
                for (MzIdProteinPeptide proteinPeptide: proteinPeptideCollection.getProteinPeptideList()) {
                    if (proteinPeptide.getPeptideSequence().equals(sequence)) {
                        removeableEvidence.add(proteinPeptide);
                        Integer start = proteinPeptide.getStartIndex();
                        Integer end = proteinPeptide.getEndIndex();
                        String pre = proteinPeptide.getPreAminoAcid();
                        String post = proteinPeptide.getPostAminoAcid();
                        Integer proteinGroup = proteinPeptide.getProteinGroup();
                        String proteinId = proteinPeptide.getProteinId();
                        String finalSequence = "";
                        if (finalSequence.isEmpty()) {
                            if (!pre.matches("[A-Z]")) {
                                finalSequence = modifiedSequence + "." + post;
                            } else if (!post.matches("[A-Z]")) {
                                finalSequence = pre + "." + modifiedSequence;
                            } else {
                                finalSequence = pre + "." + modifiedSequence + "." + post;
                            }
                        }
                        String accession = proteinPeptide.getProteinAccession();
                        ProteinPeptideOutput proteinPeptideObject = new ProteinPeptideOutput(proteinGroup, proteinId, accession, finalSequence, unique, peptideScore, theoreticalMassToCharge, calculatedMassToCharge, length, partsPerMillion, retentionTime, scanNumber, spectraCount, start, end, postTranslationalModification, aScore);
                        proteinPeptideList.addProteinPeptideEntry(proteinPeptideObject);
                        allowMismatch = true;
                    } else if (allowMismatch) {
                       break;
                    }
                }
                for (MzIdProteinPeptide entry: removeableEvidence) {
                    proteinPeptideCollection.removeProteinPeptide(entry);
                }
            }
        }
        collectionList.add(scanCollection);
        collectionList.add(databaseSearchPsmList);
        collectionList.add(peptideOutputCollection);
        collectionList.add(matchedIonSeriesCollection);
        collectionList.add(proteinPeptideList);
        collectionList.add(proteinPeptideCollection);
        return collectionList;
    }

    /**
     * Creates a collection of MzIdDatabaseSequence objects.
     *
     * @param dbSequenceList list of DatabaseSequence objects.
     * @return collection of MzIdDatabaseSequence objects.
     */
    private MzIdDatabaseSequenceCollection createDatabaseSequenceCollection(final List<DBSequence> dbSequenceList) {
        System.out.println("Creating MzIdDatabaseSequence object collection...");
        MzIdDatabaseSequenceCollection databaseSequenceCollection = new MzIdDatabaseSequenceCollection();
        for (int index = 0; index < dbSequenceList.size() - 1; index++) {
            DBSequence databaseSequence = dbSequenceList.get(index);
            String id = databaseSequence.getId();
            String proteinAccession = databaseSequence.getAccession();
            String databaseReference = databaseSequence.getSearchDatabaseRef();
            String description = "";
            String reversedDescription = "";
            String paramValue = databaseSequence.getCvParam().get(0).getValue();
            if (paramValue != null) {
                description = paramValue;
            }
            index++;
            DBSequence reveresedDatabaseSequence = dbSequenceList.get(index);
            String reversedProteinAccession = reveresedDatabaseSequence.getAccession();
            paramValue = databaseSequence.getCvParam().get(0).getValue();
            if (paramValue != null) {
                reversedDescription = paramValue;
            }
            description = description.replaceAll(",", ".");
            reversedDescription = reversedDescription.replaceAll(",", ".");
            MzIdDatabaseSequence mzidDatabaseSequence = new MzIdDatabaseSequence(id, proteinAccession, reversedProteinAccession, databaseReference, description, reversedDescription);
            databaseSequenceCollection.addDatabaseSequence(mzidDatabaseSequence);
        }
        return databaseSequenceCollection;
    }

    /**
     * Creates a SingleDatabaseReferenceCollection containing data of single entry hits.
     * 
     * @param peptideEvidenceList list of PeptideEvidence objects.
     * @param peptideCollection list of MzIdPeptide objects.
     * @return collection of SingleDatabaseReference objects.
     */
    private SingleDatabaseReferenceCollection createSingleDatabaseReferenceCollection(final List<PeptideEvidence> peptideEvidenceList, final MzIdPeptideCollection peptideCollection) {
        System.out.println("Creating SequenceDatabaseReference object collection...");
        SingleDatabaseReferenceCollection sequenceDatabaseReferenceCollection = new SingleDatabaseReferenceCollection();
        Collections.sort(peptideEvidenceList, new SortPeptideEvidenceCollectionOnSequence());
        peptideCollection.sortOnPeptideSequence();
        for (PeptideEvidence peptideEvidence : peptideEvidenceList) {
            if (!peptideEvidence.isIsDecoy()) {
                ArrayList<MzIdPeptide> removeablePeptideEntries = new ArrayList<>();
                String proteinAccession = peptideEvidence.getDBSequenceRef();
                Integer start = peptideEvidence.getStart();
                Integer end = peptideEvidence.getEnd();
                String peptideSequence = peptideEvidence.getPeptideRef();
                String pre = peptideEvidence.getPre();
                String post = peptideEvidence.getPost();
                String id = peptideEvidence.getId().split("_")[1];
                Integer evidenceId = Integer.parseInt(id);
                MzIdPeptide newEntry = null;
                ArrayList<String> modifications = new ArrayList<>();
                for (MzIdPeptide entry: peptideCollection.getPeptides()) {
                    if (entry.getModifications().isEmpty() && entry.getSubstituteModifications().isEmpty()) {
                        removeablePeptideEntries.add(entry);
                    } else if (entry.getPeptideSequence().equals(peptideSequence)) {
                        newEntry = entry;
                        for (MzIdModification modification: entry.getModifications()) {
                            for (String name: modification.getNames()) {
                                if (!modifications.contains(name)) {
                                    modifications.add(name);
                                }
                            }
                        }
                    }
                }
                removeablePeptideEntries.add(newEntry);
                for (MzIdPeptide x: removeablePeptideEntries) {
                    peptideCollection.getPeptides().remove(x);
                }
                SingleDatabaseReference sequenceDatabaseReference = new SingleDatabaseReference(proteinAccession, evidenceId, peptideSequence, start, end, pre, post, modifications);
                sequenceDatabaseReferenceCollection.addDatabaseReference(sequenceDatabaseReference);
            }
        }
        return sequenceDatabaseReferenceCollection;
    }
    
    /**
     * Creates a collection of CombinedPeptideEntry objects.
     * 
     * @param singleDatabaseReferenceCollection collection of SingleDatabaseReference objects.
     * @return collection of CombinedPeptideEntry objects.
     */
    private CombinedPeptideEntryCollection createCombinedPeptideCollection(final SingleDatabaseReferenceCollection singleDatabaseReferenceCollection) {
        System.out.println("Creating list for peptide data objects.");
        //Determine if a sequence is unique to one accession.
        CombinedPeptideEntryCollection combinedPeptideCollection = new CombinedPeptideEntryCollection();
        //Sort collections on sequence. This causes UniquePeptideEntry list to be sorted on sequence as well.
        singleDatabaseReferenceCollection.sortOnPeptideSequence();
        //Get first entry sequence.
        ArrayList<String> accessionList = new ArrayList<>();
        String targetSequence = singleDatabaseReferenceCollection.getDatabaseSequenceReferenceList().get(0).getPeptideSequence();
        for (SingleDatabaseReference databaseReference: singleDatabaseReferenceCollection.getDatabaseSequenceReferenceList()) {
            //Check if current sequence matches the targetSequence and add accession to given sequence.
            if (databaseReference.getPeptideSequence().equals(targetSequence)) {
                //if accession is not present add it to the list. Duplicate accessions are not necessary.
                if (!accessionList.contains(databaseReference.getProteinAccession())) {
                    accessionList.add(databaseReference.getProteinAccession());
                }
                if (!accessionList.contains(databaseReference.getProteinAccession())) {
                    accessionList.add(databaseReference.getProteinAccession());
                }
            } else {
                //UniquePeptideAccessionCount object is created that contains the target sequence and the list of accession ids.
                Collections.sort(accessionList);
                CombinedPeptideEntry combinedPeptide = new CombinedPeptideEntry(targetSequence, accessionList);
                combinedPeptideCollection.addCombinedPeptideEntry(combinedPeptide);
                accessionList = new ArrayList<>();
                accessionList.add(databaseReference.getProteinAccession());
                targetSequence = databaseReference.getPeptideSequence();
            }
        }
        return combinedPeptideCollection;
    }

    /**
     * Determines the spectra count per peptide sequence by coutning the peptide evidences per peptide sequence.
     *
     * @param peptideEvidenceList determines the amount of peptide spectra of each peptide sequence.
     * @return HashMap with peptide sequence as key and count as value.
     */
    private HashMap<String, Integer> determineSpectraCounts(final List<PeptideEvidence> peptideEvidenceList) {
        System.out.println("Creating spectra count map...");
        HashMap<String, Integer> spectraCountMap = new HashMap<>();
        //Loops through list of PeptideEvidence objects.
        for (PeptideEvidence peptideEvidence : peptideEvidenceList) {
            String peptideRef = peptideEvidence.getPeptideRef();
            if (!spectraCountMap.isEmpty() && spectraCountMap.containsKey(peptideRef)) {
                Integer spectraCount = spectraCountMap.get(peptideRef) + 1;
                spectraCountMap.put(peptideRef, spectraCount);
            } else {
                spectraCountMap.put(peptideRef, 1);
            }
        }
        return spectraCountMap;
    }

    /**
     * Creates a collection of MzIdPeptide objects.
     *
     * @param peptides list of PeptideItem objects.
     * @return collection of MzIdPeptide objects.
     */
    private MzIdPeptideCollection createPeptideCollection(final List<Peptide> peptides) {
        System.out.println("Creating MzIdPeptide object collection...");
        MzIdPeptideCollection newPeptideCollection = new MzIdPeptideCollection();
        //Loops through list of all PeptideItem objects.
        for (Peptide peptide : peptides) {
            String id = peptide.getId();
            String sequence = peptide.getPeptideSequence();
            List<Modification> mods = peptide.getModification();
            List<SubstitutionModification> subMods = peptide.getSubstitutionModification();
            ArrayList<MzIdSubstituteModification> subModificationList = new ArrayList<>();
            ArrayList<MzIdModification> modificationList = new ArrayList<>();
            //Create list of Modification objects.
            for (Modification modification : mods) {
                Integer location = modification.getLocation();
                ArrayList<String> nameList = new ArrayList<>();
                for (CvParam param : modification.getCvParam()) {
                    String name = param.getName();
                    nameList.add(name);
                }
                Double monoMassDelta = modification.getMonoisotopicMassDelta();
                List<String> modResidues = modification.getResidues();
                ArrayList<String> residueList = new ArrayList<>();
                for (String residue : modResidues) {
                    residueList.add(residue);
                }
                //Stores data in new Modification object.
                MzIdModification modificationObject = new MzIdModification(monoMassDelta, location, residueList, nameList);
                //Adds objects to the list.
                modificationList.add(modificationObject);
            }
            //Create list of SubstituteModification objects.
            for (SubstitutionModification subModification : subMods) {
                Double monoMassDelta = subModification.getMonoisotopicMassDelta();
                Integer location = subModification.getLocation();
                String originalResidue = subModification.getOriginalResidue();
                String replacedResidue = subModification.getReplacementResidue();
                //Stores data in new SubstituteModification object.
                MzIdSubstituteModification subModificationObject = new MzIdSubstituteModification(monoMassDelta, location, originalResidue, replacedResidue);
                //Adds objects to the list.
                subModificationList.add(subModificationObject);
            }
            //Stores data in new PeptideItem object.
            MzIdPeptide peptideObject = new MzIdPeptide(id, sequence, modificationList, subModificationList);
            //Adds objects to the MzIdPeptideCollection.
            newPeptideCollection.addPeptide(peptideObject);
        }
        return newPeptideCollection;
    }

    /**
     * Creates a collection of MzIdPeptideHypothesis objects.
     *
     * @param hypothesisList list of PeptideHypothesis parameters from the MzIdentML data.
     * @return list of MzIdPeptideHypothesis objects.
     */
    private MzIdProteinDetectionHypothesisCollection createProteinHypothesisCollection(final ProteinDetectionList proteinHypothesisList) {
        System.out.println("Creating MzIdProteinDetectionHypothesis object collection...");
        MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection = new MzIdProteinDetectionHypothesisCollection();
        //Loops throughe list of ProteinDetectionHypothesis objects.
        List<ProteinAmbiguityGroup> proteinAmbiguityGroup = proteinHypothesisList.getProteinAmbiguityGroup();
        for (ProteinAmbiguityGroup ambiguity : proteinAmbiguityGroup) {
            String ambiguityId = ambiguity.getId();
            String group = ambiguityId.split("_")[1];
            Integer proteinGroup = Integer.parseInt(group);
            String score = ambiguity.getCvParam().get(0).getValue();
            Double proteinscore = Double.parseDouble(score);
            for (ProteinDetectionHypothesis proteinHypothesis : ambiguity.getProteinDetectionHypothesis()) {
                String proteinId = proteinHypothesis.getId();
                String dbSeqRef = proteinHypothesis.getDBSequenceRef();
                Boolean passThreshold = proteinHypothesis.isPassThreshold();
                Double sequenceCoverage = 0.0;
                List<CvParam> paramList = ambiguity.getCvParam();
                for (CvParam parameter: paramList) {
                    //Gets sequence coverage if available.
                    if (parameter.getName().contains("coverage")) {
                        sequenceCoverage = Double.parseDouble(parameter.getValue());
                    }
                }
                ArrayList<MzIdCvParam> mzIdParamList = new ArrayList<>();
                //Creates list of cvParam objects.
                for (CvParam param : paramList) {
                    String accession = param.getAccession();
                    String name = param.getName();
                    String value = param.getValue();
                    //Stores data in new cvParam object.
                    MzIdCvParam mzIdCvParam = new MzIdCvParam(name, accession, value);
                    mzIdParamList.add(mzIdCvParam);
                }
                //Creates list of PeptideHypothesis objects.
                for (PeptideHypothesis peptideHypothesis : proteinHypothesis.getPeptideHypothesis()) {
                    String peptideEvidenceRef = peptideHypothesis.getPeptideEvidenceRef();
                    Integer peptideEvidenceId = Integer.parseInt(peptideEvidenceRef.split("_")[1]);
                    List<SpectrumIdentificationItemRef> spectrumItemRefList = peptideHypothesis.getSpectrumIdentificationItemRef();
                    ArrayList<String> idReferenceList = new ArrayList<>();
                    //Creates a list of SpectrumReference objects.
                    for (SpectrumIdentificationItemRef spectrumItemRef : spectrumItemRefList) {
                        idReferenceList.add(spectrumItemRef.getSpectrumIdentificationItemRef());
                    }
                    MzIdProteinDetectionHypothesis proteinDetectionHypothesis = new MzIdProteinDetectionHypothesis(passThreshold, proteinId, proteinGroup, dbSeqRef, peptideEvidenceId, proteinscore, sequenceCoverage, idReferenceList, mzIdParamList);
                    proteinHypothesisCollection.addProteinDetectionHypothesis(proteinDetectionHypothesis);
                }
            }
        }
        //Sorts the ProteinDetectionList based on the PeptideEvidence id.
        proteinHypothesisCollection.sortOnPeptideEvidence();
        return proteinHypothesisCollection;
    }

    /**
     * Creates and adds a ScanID object to the ScanIdCollection.
     *
     * @param scanID ID of the scan.
     * @param sequence peptide amino acid sequence.
     * @param psmScore peptide score (-10LogP).
     */
    public final void addEntryToScanCollection(final String scanID, final String sequence, final Double psmScore) {
        ArrayList<String> sequences = new ArrayList(maximumIndex);
        ArrayList<Double> psmScores = new ArrayList(maximumIndex);
        ArrayList<String> databaseFlags = new ArrayList<>(maximumIndex);
        for (int i = 0; i <= maximumIndex; i++) {
            if (i == currentIndex) {
                sequences.add(sequence);
                psmScores.add(psmScore);
                databaseFlags.add("");
            } else {
                sequences.add("");
                psmScores.add(0.0);
                databaseFlags.add("");
            }
        }
        Boolean newScanID = true;
        if (!scanCollection.getScanIdEntryList().isEmpty()) {
            //Loop through entries of the HashMap.
            if (currentIndex != 0) {
                for (ScanIdOutput object : scanCollection.getScanIdEntryList()) {
                    //Check for duplicates.
                    if (object.getScanId().equals(scanID)) {
                        object.getPeptideSequenceList().set(currentIndex, sequence);
                        object.getPsmScoreList().set(currentIndex, psmScore);
                        newScanID = false;
                        break;
                    }
                }
            }
        }
        //Add a new entry to the collection.
        if (newScanID) {
            ScanIdOutput scanObject = new ScanIdOutput(scanID, sequences, psmScores, databaseFlags);
            scanCollection.addScanIdEntry(scanObject);
        }
    }

    /**
     * Gathers all accessions per peptide sequence and determines if the peptide is unique to a protein sequence.
     *
     * @param sequence peptide sequence.
     * @return EvidenceData with a list of accessions and uniqueness flag.
     */
    public final ProteinPeptideEntry matchProteinPeptideData(final String sequence) {
        String accessions = "";
        String unique = "N";
        for (CombinedPeptideEntry peptide : combinedPeptideEntryCollection.getUniquePeptideList()) {
            if (peptide.getSequence().equals(sequence)) {
                if (peptide.getAccessionList().size() == 1) {
                    unique = "Y";
                }
                for (String accession : peptide.getAccessionList()) {
                    if (accessions.isEmpty()) {
                        accessions += accession;
                    } else {
                        accessions += ":" + accession;
                    }
                }
                break;
            }
        }
        ProteinPeptideEntry matchedProteinPeptide = new ProteinPeptideEntry(sequence, accessions, unique);
        return matchedProteinPeptide;
    }

    /**
     * Creates a list of peptide ion fragments and the covered ion indices.
     *
     * @param spectrumItemData contains SpectrumItemData.
     * @param sequence peptide sequence.
     * @return IndexItemData consisting of a list of ion fragment indices and the given ionSerieFlag.
     */
    private MatchedIonSeries createFragmentIndexList(final SpectrumIdentificationResult spectrumIdentificationResult, final SpectrumIdentificationItem spectrumItem, Double userIntensityThreshold, final String peptideSequence, final Double peptideScore, final String accessions) {
        ArrayList<MzIdIonFragment> ionFragmentList = new ArrayList<>();
        List<IonType> fragmentList = spectrumItem.getFragmentation().getIonType();
        //Process all ion fragments of the given peptide amino acid sequence.
        for (IonType ionType : fragmentList) {
            List<Integer> indexList = ionType.getIndex();
            ArrayList<Double> measuredMassToChargeValues = new ArrayList<>();
            ArrayList<Boolean> passedIntensityThreshold = new ArrayList<>();
            FragmentArray fragmentArray = ionType.getFragmentArray().get(0);
            //Gathers measured mass to charge values of each fragment array.
            for (float fragmentValue : fragmentArray.getValues()) {
                double measuredMassToCharge = fragmentValue;
                measuredMassToChargeValues.add(measuredMassToCharge);
            }
            //Set standard threshold to 5%.
            Double standardThreshold = 0.05;
            // Calculate the user defined intensity threshold value per peptide sequence.
            FragmentArray measureIntensity = ionType.getFragmentArray().get(1);
            if (userIntensityThreshold >= standardThreshold) {
                standardThreshold = userIntensityThreshold;
            }
            Double highestPeakIntensity = 0.0;
            //Test peakIntensity versus the specified threshold.
            for (Float intensityValue : measureIntensity.getValues()) {
                double intensity = intensityValue;
                Double peakIntensity = intensity * standardThreshold;
                if (peakIntensity > highestPeakIntensity) {
                    highestPeakIntensity = peakIntensity;
                }
            }
            //Test if ion intensity passes the user specified threshold.
            for (Float intensityValue : measureIntensity.getValues()) {
                double intensity = intensityValue;
                if (intensity >= highestPeakIntensity) {
                    passedIntensityThreshold.add(true);
                } else {
                    passedIntensityThreshold.add(false);
                }
            }
            //Create MzIdIonFragment object containing the name, indices, m/z values and a true/false list for passing the intensity threshold.
            String name = ionType.getCvParam().getName();
            MzIdIonFragment fragment = new MzIdIonFragment(name, indexList, measuredMassToChargeValues, passedIntensityThreshold);
            ionFragmentList.add(fragment);
        }
        Integer sequenceLength = peptideSequence.length();
        ArrayList<Integer> bIonIndices = new ArrayList<>(sequenceLength);
        ArrayList<Integer> yIonIndices = new ArrayList<>(sequenceLength);
        ArrayList<Integer> combinedIonIndices = new ArrayList<>(sequenceLength);
        ArrayList<Integer> combinedAllIonIndices = new ArrayList<>();
        ArrayList<Integer> finalIndexList = new ArrayList<>();
        for (MzIdIonFragment ionFragment : ionFragmentList) {
            String name = ionFragment.getName();
            List<Integer> indexList = ionFragment.getIndexList();
            ArrayList<Boolean> intensityValues = ionFragment.getItensityValues();
            //Removes hits with different index and intensity counts. (size should be the same to process data.
           if (intensityValues.size() == indexList.size()) {
                for (int i = 0; i < indexList.size(); i++) {
                    Integer listIndex = indexList.get(i);
                    Integer sequenceIndex = listIndex;
                    if (intensityValues.get(i)) {
                        if (name.matches("frag: y ion")) {
                            if (!yIonIndices.contains(sequenceIndex)) {
                                yIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        } else if (name.matches("(frag: y ion -).*")) {
                            if (!combinedIonIndices.contains(sequenceIndex)) {
                                combinedIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        } else if (name.matches("frag: b ion")) {
                            sequenceIndex = sequenceLength - listIndex;
                            if (!bIonIndices.contains(sequenceIndex)) {
                                bIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        } else if (name.matches("(frag: b ion -).*")) {
                            if (!combinedIonIndices.contains(sequenceIndex)) {
                                combinedIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        } else if (name.contains("immonium")) {
                            if (!combinedIonIndices.contains(sequenceIndex)) {
                                combinedIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        }
                    }
                }
            }
        }
        //Flag 0 for incomplete ion serie, flag 1 for complete b ion serie, flag 2 for complete y ion serie, flag 3 for combined ion series (includes immonium)
        Integer ionSerieFlag = 0;
        sequenceLength--;
        if (sequenceLength == bIonIndices.size()) {
            ionSerieFlag = 1;
            finalIndexList = bIonIndices;
        } else if (sequenceLength == yIonIndices.size()) {
            ionSerieFlag = 2;
            finalIndexList = yIonIndices;
        } else if (sequenceLength == combinedIonIndices.size()) {
            ionSerieFlag = 3;
            finalIndexList = combinedIonIndices;
        } else if (sequenceLength == combinedAllIonIndices.size()) {
            ionSerieFlag = 4;
            finalIndexList = combinedAllIonIndices;
        }
        Collections.sort(finalIndexList);
        MatchedIonSeries ionSeries = new MatchedIonSeries(peptideSequence, peptideScore, accessions, combinedIonIndices, bIonIndices, yIonIndices, combinedAllIonIndices, finalIndexList, ionSerieFlag);
        return ionSeries;
    }

    /**
     * Generates a list of BestSpectrumEntry objects that define the highest scoring peptide spectrum per peptide sequence.
     *
     * @param spectrumIdList list of SpectrumIdentificationResult items.
     * @return returns a list containing BestSpectrumEntry objects.
     */
    private ArrayList<BestSpectrumEntry> generateBestSpectrumList(SpectrumIdentificationList spectrumIdList) {
        System.out.println("Generating spectrum ID map");
        ArrayList<BestSpectrumEntry> spectrumScoreList = new ArrayList<>();
        for (SpectrumIdentificationResult spectrumIdResult : spectrumIdList.getSpectrumIdentificationResult()) {
            for (SpectrumIdentificationItem spectrumIdentificationItem : spectrumIdResult.getSpectrumIdentificationItem()) {
                Boolean isPassThreshold = spectrumIdentificationItem.isPassThreshold();
                //If an item passes the threshol the call() function is executed by a thread from the threadpool
                Double psmScore = 0.0;
                if (isPassThreshold) {
                    String peptideSequence = spectrumIdentificationItem.getPeptideRef();
                    Boolean newReference = true;
                    List<CvParam> paramList = spectrumIdentificationItem.getCvParam();
                    for (CvParam param : paramList) {
                        if (param.getName().contains("PSM score")) {
                            psmScore = Double.parseDouble(param.getValue());
                        }
                    }
                    for (BestSpectrumEntry spectrum : spectrumScoreList) {
                        if (spectrum.getSequence().equals(peptideSequence)) {
                            if (spectrum.getScore() > psmScore) {
                                spectrumScoreList.remove(spectrum);
                                spectrum.setScore(psmScore);
                                spectrum.setFlag(false);
                                spectrumScoreList.add(spectrum);
                            }
                            break;
                        }
                    }
                    //New peptide sequence and highest score.
                    if (newReference) {
                        BestSpectrumEntry spectrum = new BestSpectrumEntry(peptideSequence, psmScore, false);
                        spectrumScoreList.add(spectrum);
                    }
                }
            }
        }
        return spectrumScoreList;
    }

    /**
     * Combines data from MzIdProteinHypothesis with MzIdPeptideEvidence.
     *
     * @param mzIdPeptideEvidenceCollection collection of MzIdPeptideEvidence objects.
     * @param mzIdProteinHypothesisCollection collection of MzIdProteinHypothesis objects.
     * @return MzIdProteinPeptideCollection containing MzIdProteinPeptide objects.
     */
    public final MzIdProteinPeptideCollection combineProteinHypothesisWithPeptideEvidence(MzIdPeptideEvidenceCollection mzIdPeptideEvidenceCollection, MzIdProteinDetectionHypothesisCollection mzIdProteinHypothesisCollection) {
        System.out.println("combining protein hypothesis data with peptide evidence data...");
        MzIdProteinPeptideCollection mzidProteinPeptideCollection = new MzIdProteinPeptideCollection();
        for (MzIdProteinDetectionHypothesis mzIdProteinDetectionHypothesis : mzIdProteinHypothesisCollection.getProteinDetectionHypothesisList()) {
            Integer id = mzIdProteinDetectionHypothesis.getPeptideEvidenceId();
            Integer indexOfEvidenceId = id - 1;
            MzIdPeptideEvidence evidence = mzIdPeptideEvidenceCollection.getPeptideEvidenceItems().get(indexOfEvidenceId);
            String peptideSequence = evidence.getPeptideSequence();
            Boolean isDecoySequence = evidence.isDecoySequence();
            Boolean isPassThreshold = mzIdProteinDetectionHypothesis.isPassThreshold();
            Integer endIndex = evidence.getEndIndex();
            Integer startIndex = evidence.getStartIndex();
            String preAminoAcid = evidence.getPreAminoAcid();
            String postAminoAcid = evidence.getPostAminoAcid();
            String proteinAccession = mzIdProteinDetectionHypothesis.getProteinAccession();
            Integer proteinGroup = mzIdProteinDetectionHypothesis.getProteinGroup();
            String proteinId = proteinGroup + "_" + mzIdProteinDetectionHypothesis.getProteinId().split("_")[2];
            Double proteinGroupScore = mzIdProteinDetectionHypothesis.getProteinScore();
            MzIdProteinPeptide proteinPeptide = new MzIdProteinPeptide(id, peptideSequence, proteinAccession, proteinGroup, proteinId, isDecoySequence, isPassThreshold, startIndex, endIndex, preAminoAcid, postAminoAcid, proteinGroupScore);
            mzidProteinPeptideCollection.addProteinPeptide(proteinPeptide);
        }
        return mzidProteinPeptideCollection;
    }

    /**
     * Creates a collection of MzIdPeptideEvidence objects using the PeptideEvidence list of the mzid file.
     * 
     * @param peptideEvidenceList list of PeptideEvidence objects.
     * @return returns the MzIdPeptideEvidenceCollection.
     */
    private MzIdPeptideEvidenceCollection createPeptideEvidenceCollection(List<PeptideEvidence> peptideEvidenceList) {
        System.out.println("Creating peptide evidence collection...");
        MzIdPeptideEvidenceCollection mzIdPeptideEvidenceCollection = new MzIdPeptideEvidenceCollection();
        for (PeptideEvidence peptideEvidence: peptideEvidenceList) {
            String id = peptideEvidence.getId().split("_")[1];
            Integer isAsInteger = Integer.parseInt(id);
            Boolean isDecoySequence = peptideEvidence.isIsDecoy();
            String proteinAccession = peptideEvidence.getDBSequenceRef();
            Integer startIndex = peptideEvidence.getStart();
            Integer endIndex = peptideEvidence.getEnd();
            String preAminoAcid = peptideEvidence.getPre();
            String postAminoAcid = peptideEvidence.getPost();
            String peptideSequence = peptideEvidence.getPeptideRef();
            MzIdPeptideEvidence mzIdPeptideEvidence = new MzIdPeptideEvidence(isAsInteger, proteinAccession, peptideSequence, isDecoySequence, startIndex, endIndex, preAminoAcid, postAminoAcid);
            mzIdPeptideEvidenceCollection.addPeptideEvidence(mzIdPeptideEvidence);
        }
        return mzIdPeptideEvidenceCollection;
    }
    
    /**
     * Removes MzIdProteinPeptide entries that do not pass the threshold
     * 
     * @param mzIdProteinPeptideCollection collection of MzIdProteinPeptide objects.
     * @return returns the filtered MzIdProteinPeptideCollection.
     */
    private MzIdProteinPeptideCollection removeLowThresholdSequences(MzIdProteinPeptideCollection mzIdProteinPeptideCollection) {
        System.out.println("Removing low threshold and decoy sequences...");
        MzIdProteinPeptideCollection filteredMzIdProteinPeptideCollection = new MzIdProteinPeptideCollection();
        for (MzIdProteinPeptide proteinPeptide: mzIdProteinPeptideCollection.getProteinPeptideList()) {
            //Remove low threshold and decoy 
            if (proteinPeptide.isPassThreshold()) {
                filteredMzIdProteinPeptideCollection.addProteinPeptide(proteinPeptide);
            }
        }
        return filteredMzIdProteinPeptideCollection;
    }
    
    /**
     * Removes MzIdProteinPeptide entries that qualify as a decoy sequence.
     * 
     * @param mzIdProteinPeptideCollection collection of MzIdProteinPeptide objects.
     * @return returns the filtered MzIdProteinPeptideCollection.
     */
    private MzIdProteinPeptideCollection removeDecoySequences(MzIdProteinPeptideCollection mzIdProteinPeptideCollection) {
        System.out.println("Removing low threshold and decoy sequences...");
        MzIdProteinPeptideCollection filteredMzIdProteinPeptideCollection = new MzIdProteinPeptideCollection();
        for (MzIdProteinPeptide proteinPeptide: mzIdProteinPeptideCollection.getProteinPeptideList()) {
            //Remove low threshold and decoy 
            if (!proteinPeptide.isDecoySequence()) {
                filteredMzIdProteinPeptideCollection.addProteinPeptide(proteinPeptide);
            }
        }
        return filteredMzIdProteinPeptideCollection;
    }
}
