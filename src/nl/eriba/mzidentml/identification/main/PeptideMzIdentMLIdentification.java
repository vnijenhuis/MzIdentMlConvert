/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identification Module
 */
package nl.eriba.mzidentml.identification.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import nl.eriba.mzidentml.identification.collections.output.DatabaseSearchPsmOutputCollection;
import nl.eriba.mzidentml.identification.collections.output.PeptideOutputCollection;
import nl.eriba.mzidentml.identification.collections.general.ProteinDatabaseSequenceCollection;
import nl.eriba.mzidentml.identification.collections.output.ProteinOutputCollection;
import nl.eriba.mzidentml.identification.collections.output.ProteinPeptideOutputCollection;
import nl.eriba.mzidentml.identification.collections.output.ScanIdOutputCollection;
import nl.eriba.mzidentml.identification.collections.general.CombinedDatabaseReferenceCollection;
import nl.eriba.mzidentml.identification.collections.general.MatchedIonSeriesCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdProteinDetectionHypothesisCollection;
import nl.eriba.mzidentml.tools.InputTools;
import nl.eriba.mzidentml.identification.dataprocessing.database.ProteinSequenceDatabaseMap;
import nl.eriba.mzidentml.identification.dataprocessing.database.ProteinEntryDatabaseMatcher;
import nl.eriba.mzidentml.identification.dataprocessing.database.ProteinEntryMatcher;
import nl.eriba.mzidentml.identification.dataprocessing.spectra.ScanIdCollectionFlagger;
import nl.eriba.mzidentml.identification.dataprocessing.spectra.ScanIdCollectionSeparator;
import nl.eriba.mzidentml.identification.dataprocessing.database.ScanIdDatabaseMatcher;
import nl.eriba.mzidentml.identification.writer.csv.DBSearchPsmCsvWriter;
import nl.eriba.mzidentml.identification.filereader.EntryFileReader;
import nl.eriba.mzidentml.identification.writer.csv.MzIdCsvWriter;
import nl.eriba.mzidentml.identification.writer.csv.PeptideCsvWriter;
import nl.eriba.mzidentml.identification.filereader.MzIdFileReader;
import nl.eriba.mzidentml.identification.writer.csv.IonSeriesCsvWriter;
import nl.eriba.mzidentml.identification.writer.csv.ProteinCsvWriter;
import nl.eriba.mzidentml.identification.writer.csv.ProteinPeptideCsvWriter;
import nl.eriba.mzidentml.tools.GeneralTools;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Conversion of the MzIdentML format to CSV format.
 *
 * @author vnijenhuis
 * @dependencies https://code.google.com/archive/p/jmzidentml/downloads
 */
public class PeptideMzIdentMLIdentification {

    /**
     * List of commandline options.
     */
    private final Options commandlineOptions;

    /**
     * File reader for mzIdML files.
     */
    public MzIdFileReader mzidFormatFileReader;

    /**
     * Writes mzid data to a csv file.
     */
    private final MzIdCsvWriter mzidScanIdCsvWriter;

    /**
     * Provides acess to functions for directory and input testing purposes.
     */
    private final InputTools inputTools;

    /**
     * Pattern of the file separator. \ on windows and / on linux.
     */
    private String separator;

    /**
     * Threshold for peak intensity.
     */
    private Double intensityThreshold;
    
    /**
     * Provides acess to functions for general testing purposes.
     */
    private final GeneralTools generalTools;

    /**
     * Defines the PeptideMzIdentMLIdentification class.
     */
    private PeptideMzIdentMLIdentification() {
        //Create new commandline options.
        commandlineOptions = new Options();
        //Creates a help parameter.
        Option help = Option.builder("h")
                .longOpt("help")
                .hasArg(false)
                .desc("Help function to display all commandline options.")
                .optionalArg(true)
                .build();
        commandlineOptions.addOption(help);
        //Path to the uniprot mRNASeq data.
        Option mzidMainInput = Option.builder("mzid")
                .hasArg()
                .desc("Path to master file. (C:/Users/f103013/Documents/vnijenhuis_docs/1D2DCombined/Entry/mzid_main_entry.txt)")
                .build();
        commandlineOptions.addOption(mzidMainInput);
        Option databaseMainInput = Option.builder("databases")
                .hasArg()
                .optionalArg(true)
                .desc("Path to master file. (C:/Users/f103013/Documents/vnijenhuis_docs/1D2DCombined/Entry/databases_entry.txt)")
                .build();
        commandlineOptions.addOption(databaseMainInput);
        //Path and name of the output file.
        Option output = Option.builder("output")
                .hasArg()
                .desc("Path to write output file(s) to.  (C:/Users/f103013/Documents/Output).")
                .build();
        commandlineOptions.addOption(output);
        //Amount of threads used for this program.
        Option thread = Option.builder("threads")
                .hasArg()
                .optionalArg(true)
                .desc("Amount of threads to use for this execution. (DEFAULT: 1 thread)")
                .build();
        commandlineOptions.addOption(thread);
        Option outputType = Option.builder("outputFiles")
                .hasArgs()
                .desc("Requires integers(s) as input to determine the output files of MzIdentification.")
                .build();
        commandlineOptions.addOption(outputType);
        Option intensity = Option.builder("intensity")
                .hasArgs()
                .desc("Requires integers(s) as input to determine the output files of MzIdentification.")
                .build();
        commandlineOptions.addOption(intensity);
        //Implements the MzIdCsvWriter class.
        mzidScanIdCsvWriter = new MzIdCsvWriter();
        //Implements the input tools class.
        inputTools = new InputTools();
        //Implements the general tools class.
        generalTools = new GeneralTools();
    }

    /**
     * Main class of the project.
     *
     * @param args commandline arguments.
     * @throws IOException could not access the given file.
     * @throws InterruptedException process was interrupted.
     * @throws ExecutionException execution failed.
     * @throws org.apache.commons.cli.ParseException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, ParseException, SAXException, ParserConfigurationException {
        PeptideMzIdentMLIdentification peptideIdentification = new PeptideMzIdentMLIdentification();
        peptideIdentification.StartMzIdentMLIdentification(args);
    }

    /**
     * Starts the identification process of mzid data.
     *
     * @param args commandline arguments.
     * @throws IOException could not find or open the file specified.
     * @throws InterruptedException process was interrupted by another process.
     * @throws ExecutionException could not execute the process.
     * @throws org.apache.commons.cli.ParseException exception encountered in the commandline parser.
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public final void StartMzIdentMLIdentification(final String[] args) throws IOException, ParseException, InterruptedException, ExecutionException, SAXException, ParserConfigurationException {
        //Initiates the commandline parser.
        CommandLineParser parser = new BasicParser();
        //Gather arguments from the given options of the commandline.
        CommandLine cmd = parser.parse(commandlineOptions, args);
        System.out.println("Starting mzid identification!");
        //Help function.
        if (Arrays.toString(args).toLowerCase().contains("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Peptide scan collector", commandlineOptions);
            System.exit(0);
        } else {
            //Allocate command line input to variables.
            String inputFile = cmd.getOptionValue("mzid");
            String databaseTextFile = "";
            Boolean matchToDatabases = true;
            if (cmd.hasOption("databases")) {
                databaseTextFile = cmd.getOptionValue("databases");
            }
            EntryFileReader reader = new EntryFileReader();
            String outputDirectory = cmd.getOptionValue("output");
            String[] outputType = cmd.getOptionValues("outputFiles");
            ArrayList<Integer> inputFileFlags = new ArrayList<>();
            for (String num : outputType) {
                try {
                    Integer number = Integer.parseInt(num);
                    inputFileFlags.add(number);
                } catch (NumberFormatException e) {
                    System.out.println("Given input " + num + " is not a number. Please use numbers as input. Error: " + e.getMessage());
                }
            }
            //Set the amount of threads to be used.
            Integer threads = getThreads(cmd);
            //Set the standard threshold value to 5% (0.05).
            intensityThreshold = generalTools.getIntensityThreshold(cmd);
            //Determine path separator.
            inputTools.isDirectory(outputDirectory);
            separator = getSeparator();
            //Read input file
            if (inputTools.isTxtFile(inputFile) && matchToDatabases) {
                //Run with databases
                LinkedHashMap<String, ArrayList<String>> databaseEntryMap = new LinkedHashMap<>();
                ArrayList<String> databaseList = reader.readMainTextFile(databaseTextFile);
                databaseEntryMap = reader.createDatabaseHashMap(databaseList, separator);
                String[] folders = inputFile.split(separator);
                String method = folders[folders.length - 2];
                ArrayList<String> entryFileList = reader.readMainTextFile(inputFile);
                LinkedHashMap<String, ArrayList<String>> mzidEntryMap = reader.createMzIdHashMap(entryFileList, separator);
                processMzIdWithDatabases(outputDirectory, method, mzidEntryMap, databaseEntryMap, matchToDatabases, inputFileFlags, threads);
            } else if (inputTools.isTxtFile(inputFile) && !matchToDatabases) {
                String[] folders = inputFile.split(separator);
                String method = folders[folders.length - 2];
                ArrayList<String> entryFileList = reader.readMainTextFile(inputFile);
                LinkedHashMap<String, ArrayList<String>> mzidEntryMap = reader.createMzIdHashMap(entryFileList, separator);
                processMzIdWithoutDatabases(outputDirectory, method, mzidEntryMap, inputFileFlags, threads);
            } else {
                System.out.println("WARNING: given file is not a .txt file: " + inputFile);
            }
        }
    }

    /**
     * Identifies MS/MS peptide Scan IDs.
     *
     * @param mzidEntryMap LinkedHashMap with database as key and ArrayList of mzid files as value.
     * @param method name of the mass spec method.
     * @param databaseEntryMap LinkedHashMap with database as key and ArrayList of database files as value.
     * @param outputDirectory path to write the output file to.
     * @param matchToDatabases boolean used to see if database(s) were provided as input.
     * @param inputFileFlags 
     * @param threads amount of threads used for multithreaded classes.
     * @throws IOException could not find or open the file specified.
     * @throws InterruptedException process was interrupted by another process.
     * @throws ExecutionException could not execute the process.
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public final void processMzIdWithDatabases(final String outputDirectory, final String method, final LinkedHashMap<String, ArrayList<String>> mzidEntryMap,
            final LinkedHashMap<String, ArrayList<String>> databaseEntryMap, final Boolean matchToDatabases, final ArrayList<Integer> inputFileFlags, final Integer threads)
            throws IOException, InterruptedException, ExecutionException, SAXException, ParserConfigurationException {
        System.out.println("Starting identification of PeptideShaker mzid data...");
        long startTime = System.currentTimeMillis() / 1000;
        HashMap<String, HashMap<Integer, ProteinDatabaseSequenceCollection>> proteinSequenceDatabaseMap = null;
        if (matchToDatabases) {
            ProteinSequenceDatabaseMap databaseMap = new ProteinSequenceDatabaseMap();
            proteinSequenceDatabaseMap = databaseMap.createProteinSequenceDatabaseMap(databaseEntryMap);
        }
        ArrayList<String> rnaSeqDatabaseKeys = new ArrayList<>();
        Integer maximumIndex = mzidEntryMap.keySet().size() - 1;
        for (String rnaSeq : databaseEntryMap.keySet()) {
            rnaSeqDatabaseKeys.add(rnaSeq);
        }
        Integer sampleSize = 0;
        for (Map.Entry<String, ArrayList<String>> entry : mzidEntryMap.entrySet()) {
            if (sampleSize <= entry.getValue().size()) {
                sampleSize = entry.getValue().size();
            }
        }
        sampleSize = 6;
        for (Integer currentSampleIndex = 5; currentSampleIndex < sampleSize; currentSampleIndex++) {
            ScanIdOutputCollection scanIdOutputCollection = new ScanIdOutputCollection();
            for (int currentIndex = 0; currentIndex < rnaSeqDatabaseKeys.size(); currentIndex++) {
                String database = rnaSeqDatabaseKeys.get(currentIndex);
                ProteinOutputCollection proteinOutputCollection = new ProteinOutputCollection();
                ArrayList<String> list = mzidEntryMap.get(rnaSeqDatabaseKeys.get(currentIndex));
                String file = list.get(currentSampleIndex);
                //Get correct file.
                String sampleOutputDirectory = generateOutputDirectory(file, outputDirectory);
                //Process the mzid files.
                mzidFormatFileReader = new MzIdFileReader(null, null, null, null, scanIdOutputCollection, null, null, null, inputFileFlags, currentIndex, maximumIndex, intensityThreshold);
                ArrayList<Object> collections = mzidFormatFileReader.collectPeptideShakerScanIDs(file, scanIdOutputCollection, inputFileFlags, currentIndex, maximumIndex, threads, intensityThreshold);
                //Get data from returned collection and write data to corresponding files.
                System.out.println("Processin data from sample " + currentSampleIndex + " " + database);
                int index = 0;
                for (Integer num : inputFileFlags) {
                    switch (num) {
                        case 1:
                            ScanIdOutputCollection collection = (ScanIdOutputCollection) collections.get(index);
                            scanIdOutputCollection.getScanIdEntryList().addAll(collection.getScanIdEntryList());
                            break;
                        case 2:
                            DatabaseSearchPsmOutputCollection psmCollection = (DatabaseSearchPsmOutputCollection) collections.get(index);
                            createDatabaseSearchOutput(psmCollection, sampleOutputDirectory);
                            break;
                        case 3:
                            PeptideOutputCollection peptideCollection = (PeptideOutputCollection) collections.get(index);
                            createPeptideOutput(peptideCollection, (MatchedIonSeriesCollection) collections.get(index), sampleOutputDirectory);
                            break;
                        case 4:
                            ProteinPeptideOutputCollection proteinPeptideCollection = (ProteinPeptideOutputCollection) collections.get(index);
                            createProteinPeptideOutput(proteinPeptideCollection, sampleOutputDirectory);
                            break;
                        case 5:
                            MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection = (MzIdProteinDetectionHypothesisCollection) collections.get(index);
                            CombinedDatabaseReferenceCollection combinedReferenceCollection = (CombinedDatabaseReferenceCollection) collections.get(index);
                            ProteinDatabaseSequenceCollection proteinDatabase = getProteinDatabase(currentSampleIndex, currentIndex, proteinSequenceDatabaseMap, rnaSeqDatabaseKeys);
                            createProteinOutputWithDatabase(proteinHypothesisCollection, combinedReferenceCollection, proteinDatabase, sampleOutputDirectory, threads);
                            break;
                        default:
                            break;
                    }
                    index++;
                }
            }
            if (inputFileFlags.contains(1)) {
                //Set ScanID flag depending on sequence status.
                ScanIdCollectionFlagger flagger = new ScanIdCollectionFlagger();
                ScanIdOutputCollection finalScanCollection = flagger.setFlags(scanIdOutputCollection, maximumIndex);
                //Separate ScanID objects depending on the given flag.
                ScanIdCollectionSeparator scanIdEntrySeparator = new ScanIdCollectionSeparator();
                ArrayList<ScanIdOutputCollection> scanIdEntryCollectionList = scanIdEntrySeparator.separateScanEntries(finalScanCollection);
                //Use database matching on the flagged ScanIds.
                ScanIdOutputCollection flaggedCollection = scanIdEntryCollectionList.get(1);
                int entryCount = 0;
                for (Map.Entry<String, HashMap<Integer, ProteinDatabaseSequenceCollection>> mapEntry : proteinSequenceDatabaseMap.entrySet()) {
                    if (!flaggedCollection.getScanIdEntryList().isEmpty()) {
                        for (Map.Entry<Integer, ProteinDatabaseSequenceCollection> proteinEntry : mapEntry.getValue().entrySet()) {
                            //Match key to the current index of the size. -1 for single database files.
                            if (Objects.equals(proteinEntry.getKey(), currentSampleIndex) || proteinEntry.getKey() == -1) {
                                ScanIdDatabaseMatcher matcher = new ScanIdDatabaseMatcher(null, null, rnaSeqDatabaseKeys.get(entryCount), proteinEntry.getValue());
                                flaggedCollection = matcher.matchSequencesToDatabase(flaggedCollection, rnaSeqDatabaseKeys.get(entryCount), proteinEntry.getValue(), threads);
                                scanIdEntryCollectionList.set(1, flaggedCollection);
                            }
                        }
                    } else {
                        System.out.println("WARNING: No scan IDs available in the flagged scan ID collection.");
                    }
                    entryCount++;
                }
                String directory = outputDirectory + method + "_mzid_non_flagged_scanIDs.csv";
                mzidScanIdCsvWriter.writeCsv(directory, scanIdEntryCollectionList.get(0), rnaSeqDatabaseKeys);
                directory = outputDirectory + method + "_mzid_flagged_scanIDs.csv";
                mzidScanIdCsvWriter.writeCsv(directory, scanIdEntryCollectionList.get(1), rnaSeqDatabaseKeys);
            }
            long endTime = System.currentTimeMillis() / 1000;
            System.out.println("Process took " + (endTime - startTime) + " seconds.");
        }
    }

    /**
     * Identifies MS/MS peptide Scan IDs.
     *
     * @param mzidEntryMap LinkedHashMap with database as key and ArrayList of mzid files as value.
     * @param method name of the mass spec method.
     * @param outputDirectory path to write the output file to.
     * @param inputFileFlags 
     * @param threads amount of threads used for multithreaded classes.
     * @throws IOException could not find or open the file specified.
     * @throws InterruptedException process was interrupted by another process.
     * @throws ExecutionException could not execute the process.
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public final void processMzIdWithoutDatabases(final String outputDirectory, final String method, final LinkedHashMap<String, ArrayList<String>> mzidEntryMap,
            final ArrayList<Integer> inputFileFlags, final Integer threads) throws IOException, InterruptedException, ExecutionException, SAXException, ParserConfigurationException {
        System.out.println("Starting identification of PeptideShaker mzid data...");
        long startTime = System.currentTimeMillis() / 1000;
        HashMap<String, HashMap<Integer, ProteinDatabaseSequenceCollection>> proteinSequenceDatabaseMap = null;
        ArrayList<String> rnaSeqDatabaseKeys = new ArrayList<>();
        Integer maximumIndex = mzidEntryMap.keySet().size() - 1;
        Integer sampleSize = 0;
        for (Map.Entry<String, ArrayList<String>> entry : mzidEntryMap.entrySet()) {
            if (sampleSize <= entry.getValue().size()) {
                sampleSize = entry.getValue().size();
            }
        }
        sampleSize = 6;
        for (Integer currentSampleIndex = 5; currentSampleIndex < sampleSize; currentSampleIndex++) {
            ScanIdOutputCollection scanIdOutputCollection = new ScanIdOutputCollection();
            for (int currentIndex = 0; currentIndex < rnaSeqDatabaseKeys.size(); currentIndex++) {
                String database = rnaSeqDatabaseKeys.get(currentIndex);
                ArrayList<String> list = mzidEntryMap.get(rnaSeqDatabaseKeys.get(currentIndex));
                String file = list.get(currentSampleIndex);
                String sampleOutputDirectory = generateOutputDirectory(file, outputDirectory);
                mzidFormatFileReader = new MzIdFileReader(null, null, null, null, scanIdOutputCollection, null, null, null, inputFileFlags, currentIndex, maximumIndex, intensityThreshold);
                ArrayList<Object> collections = mzidFormatFileReader.collectPeptideShakerScanIDs(file, scanIdOutputCollection, inputFileFlags, currentIndex, maximumIndex, threads, intensityThreshold);
                System.out.println("Processin data from sample " + currentSampleIndex + " " + database);
                int index = 0;
                for (Integer num : inputFileFlags) {
                    switch (num) {
                        case 1:
                            ScanIdOutputCollection collection = (ScanIdOutputCollection) collections.get(index);
                            scanIdOutputCollection.getScanIdEntryList().addAll(collection.getScanIdEntryList());
                            break;
                        case 2:
                            DatabaseSearchPsmOutputCollection psmCollection = (DatabaseSearchPsmOutputCollection) collections.get(index);
                            createDatabaseSearchOutput(psmCollection, sampleOutputDirectory);
                            break;
                        case 3:
                            PeptideOutputCollection peptideCollection = (PeptideOutputCollection) collections.get(index);
                            createPeptideOutput(peptideCollection, (MatchedIonSeriesCollection) collections.get(index), sampleOutputDirectory);
                            break;
                        case 4:
                            ProteinPeptideOutputCollection proteinPeptideCollection = (ProteinPeptideOutputCollection) collections.get(index);
                            createProteinPeptideOutput(proteinPeptideCollection, sampleOutputDirectory);
                            break;
                        case 5:
                            MzIdProteinDetectionHypothesisCollection proteinHypothesisCollection = (MzIdProteinDetectionHypothesisCollection) collections.get(index);
                            CombinedDatabaseReferenceCollection combinedReferenceCollection = (CombinedDatabaseReferenceCollection) collections.get(index);
                            createProteinOutputNoDatabase(proteinHypothesisCollection, combinedReferenceCollection, sampleOutputDirectory, threads);
                            break;
                        default:
                            break;
                    }
                }
                index++;
            }
            if (inputFileFlags.contains(1)) {
                //Set ScanID flag depending on sequence status.
                ScanIdCollectionFlagger flagger = new ScanIdCollectionFlagger();
                ScanIdOutputCollection finalScanCollection = flagger.setFlags(scanIdOutputCollection, maximumIndex);
                //Separate ScanID objects depending on the given flag.
                ScanIdCollectionSeparator scanIdEntrySeparator = new ScanIdCollectionSeparator();
                ArrayList<ScanIdOutputCollection> scanIdEntryCollectionList = scanIdEntrySeparator.separateScanEntries(finalScanCollection);
                //Use database matching on the flagged ScanIds.
                ScanIdOutputCollection flaggedCollection = scanIdEntryCollectionList.get(1);
                int entryCount = 0;
                for (Map.Entry<String, HashMap<Integer, ProteinDatabaseSequenceCollection>> mapEntry : proteinSequenceDatabaseMap.entrySet()) {
                    if (!flaggedCollection.getScanIdEntryList().isEmpty()) {
                        for (Map.Entry<Integer, ProteinDatabaseSequenceCollection> proteinEntry : mapEntry.getValue().entrySet()) {
                            //Match key to the current index of the size. -1 for single database files.
                            if (Objects.equals(proteinEntry.getKey(), currentSampleIndex) || proteinEntry.getKey() == -1) {
                                ScanIdDatabaseMatcher matcher = new ScanIdDatabaseMatcher(null, null, rnaSeqDatabaseKeys.get(entryCount), proteinEntry.getValue());
                                flaggedCollection = matcher.matchSequencesToDatabase(flaggedCollection, rnaSeqDatabaseKeys.get(entryCount), proteinEntry.getValue(), threads);
                                scanIdEntryCollectionList.set(1, flaggedCollection);
                            }
                        }
                    } else {
                        System.out.println("WARNING: No scan IDs available in the flagged scan ID collection.");
                    }
                    entryCount++;
                }
                String directory = outputDirectory + method + "_mzid_non_flagged_scanIDs.csv";
                mzidScanIdCsvWriter.writeCsv(directory, scanIdEntryCollectionList.get(0), rnaSeqDatabaseKeys);
                directory = outputDirectory + method + "_mzid_flagged_scanIDs.csv";
                mzidScanIdCsvWriter.writeCsv(directory, scanIdEntryCollectionList.get(1), rnaSeqDatabaseKeys);
            }
            long endTime = System.currentTimeMillis() / 1000;
            System.out.println("Process took " + (endTime - startTime) + " seconds.");
        }
    }

    /**
     * Returns the amount of threads used for multithreading.
     *
     * @param cmd commandline arguments.
     * @return amount of threads as Integer.
     */
    private Integer getThreads(CommandLine cmd) {
        Integer threads = 1;
        if (cmd.hasOption("threads")) {
            try {
                threads = Integer.parseInt(cmd.getOptionValue("threads"));
            } catch (Exception e) {
                System.out.println("Please enter a number as input instead of " + cmd.getOptionValue("threads")
                        + ".\nCurrent input results in error: " + e.getMessage());
            }
        }
        return threads;
    }

    /**
     * Returns the folder separator based on the system environment.
     * 
     * @return separator as String.
     */
    private String getSeparator() {
        String os = System.getProperties().getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            separator = "\\\\"; //windows
        } else if (os.contains("linux") || os.contains("unix")) {
            separator = "/"; //linux
        }
        return separator;
    }

    /**
     * Generates an output directory based on the initial output directory and the file/sample name.
     * 
     * @param file file name.
     * @param outputDirectory output directory.
     * @return ouput directory as String.
     */
    private String generateOutputDirectory(final String file, final String outputDirectory) {
        String[] split = file.split(separator);
        String fileName = split[split.length - 1];
        fileName = fileName.substring(0, fileName.indexOf(".mzid"));
        String folder = split[split.length - 2];
        //Generate directory if it does not exist.
        String directory = outputDirectory + folder + separator + fileName + separator;
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println("WARNING: could not create a directory for the given mzid file: " + file + "\nError: " + e);
                System.out.println("Creating path: " + path + " failed. Process will terminate.");
                System.exit(0);
            }
        }
        return directory;
    }

    /**
     * 
     * 
     * @param currentSample
     * @param currentIndex
     * @param proteinDataMap
     * @param rnaSeqDatabaseKeys
     * @return 
     */
    private ProteinDatabaseSequenceCollection getProteinDatabase(final int currentSample, final int currentIndex, final HashMap<String, HashMap<Integer, ProteinDatabaseSequenceCollection>> proteinDataMap, final ArrayList<String> rnaSeqDatabaseKeys) {
        ProteinDatabaseSequenceCollection proteinDatabase = new ProteinDatabaseSequenceCollection();
        for (Map.Entry<String, HashMap<Integer, ProteinDatabaseSequenceCollection>> mapEntry : proteinDataMap.entrySet()) {
            if (mapEntry.getKey().equals(rnaSeqDatabaseKeys.get(currentIndex))) {
                for (Map.Entry<Integer, ProteinDatabaseSequenceCollection> proteinEntry : mapEntry.getValue().entrySet()) {
                    //Match key to the current index of the size. -1 for single database files.
                    if (proteinEntry.getKey() == currentSample || proteinEntry.getKey() == -1) {
                        proteinDatabase = proteinEntry.getValue();
                    }
                }
            }
        }
        return proteinDatabase;
    }

    /**
     * 
     * @param databaseSearchPsmOutputCollection
     * @param outputDirectory 
     */
    private void createDatabaseSearchOutput(final DatabaseSearchPsmOutputCollection databaseSearchPsmOutputCollection, final String outputDirectory) throws IOException {
        DBSearchPsmCsvWriter dbsWriter = new DBSearchPsmCsvWriter();
        dbsWriter.writeDatabaseSearchPsmCsv(outputDirectory, databaseSearchPsmOutputCollection);
    }

    /**
     * 
     * @param peptideOutputCollection
     * @param matchedIonSeriesCollection
     * @param sampleOutputDirectory
     * @throws IOException 
     */
    private void createPeptideOutput(PeptideOutputCollection peptideOutputCollection, MatchedIonSeriesCollection matchedIonSeriesCollection, String sampleOutputDirectory) throws IOException {
        PeptideCsvWriter peptideWriter = new PeptideCsvWriter();
        peptideWriter.writePeptideCsv(sampleOutputDirectory, peptideOutputCollection);
        IonSeriesCsvWriter ionWriter = new IonSeriesCsvWriter();
        ionWriter.writeCsv(sampleOutputDirectory, matchedIonSeriesCollection);
    }

    /**
     * 
     * @param proteinPeptideOutputCollection
     * @param sampleOutputDirectory
     * @throws IOException 
     */
    private void createProteinPeptideOutput(ProteinPeptideOutputCollection proteinPeptideOutputCollection, String sampleOutputDirectory) throws IOException {
        ProteinPeptideCsvWriter proPepWriter = new ProteinPeptideCsvWriter();
        proPepWriter.writeProteinPeptideCsv(sampleOutputDirectory, proteinPeptideOutputCollection);
    }

    /**
     * 
     * @param mzIdProteinDetectionHypothesisCollection
     * @param combinedDatabaseReferenceCollection
     * @param sampleOutputDirectory 
     */
    private void createProteinOutputWithDatabase(final MzIdProteinDetectionHypothesisCollection mzIdProteinDetectionHypothesisCollection, final CombinedDatabaseReferenceCollection combinedDatabaseReferenceCollection,
            final ProteinDatabaseSequenceCollection proteinDatabase, final String sampleOutputDirectory, final Integer threads) throws IOException, InterruptedException, ExecutionException {
        ProteinEntryDatabaseMatcher proteinEntryMatcher = new ProteinEntryDatabaseMatcher(null, null, null, null);
        ProteinOutputCollection proteinOutputCollection = proteinEntryMatcher.createProteinEntryCollection(proteinDatabase, mzIdProteinDetectionHypothesisCollection, combinedDatabaseReferenceCollection, threads);
        ProteinCsvWriter proteinWriter = new ProteinCsvWriter();
        proteinWriter.writeProteinCsv(sampleOutputDirectory, proteinOutputCollection);
    }
    
        /**
     * 
     * @param mzIdProteinDetectionHypothesisCollection
     * @param combinedDatabaseReferenceCollection
     * @param sampleOutputDirectory 
     */
    private void createProteinOutputNoDatabase(final MzIdProteinDetectionHypothesisCollection mzIdProteinDetectionHypothesisCollection, final CombinedDatabaseReferenceCollection combinedDatabaseReferenceCollection,
            final String sampleOutputDirectory, final Integer threads) throws ExecutionException, InterruptedException, IOException {
        ProteinEntryMatcher proteinEntryMatcher = new ProteinEntryMatcher(null, null, null);
        ProteinOutputCollection proteinOutputCollection = proteinEntryMatcher.createProteinEntryCollection(mzIdProteinDetectionHypothesisCollection, combinedDatabaseReferenceCollection, threads);
        ProteinCsvWriter proteinWriter = new ProteinCsvWriter();
        proteinWriter.writeProteinCsv(sampleOutputDirectory, proteinOutputCollection);
    }
}
