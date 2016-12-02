/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.writer.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import nl.eriba.mzidentml.identification.collections.output.ScanIdOutputCollection;
import nl.eriba.mzidentml.identification.objects.output.ScanIdOutput;
import nl.eriba.mzidentml.tools.InputTools;

/**
 * Generates a csv file with data from the MzIdentML files.
 *
 * @author vnijenhuis
 */
public class MzIdCsvWriter {

    /**
     * Writes the output csv file.
     *
     * @param outputDirectory directory to write the file to
     * @param scanIdEntryCollection collection of ScanIdOutput objects.
     * @param datasets list of dataset names.
     * @throws java.io.IOException could not find or access the given file.
     */
    public final void writeCsv(String outputDirectory, final ScanIdOutputCollection scanIdEntryCollection,
            final ArrayList<String> datasets) throws IOException {
        InputTools test = new InputTools();
        FileWriter writer;
        if (test.isFile(outputDirectory)) {
            writer = new FileWriter(outputDirectory, true);
            System.out.println("Writing data to existing file: " + outputDirectory);
        } else {
            writer = new FileWriter(outputDirectory);
            System.out.println("Writing output to " + outputDirectory);
        }
        String delimiter = ",";
        String lineEnding = "\n";
        String header = generateCsvHeader(scanIdEntryCollection.getScanIdEntryList().get(0), lineEnding, delimiter, datasets);
        writer.append(header);
        for (ScanIdOutput scanObject : scanIdEntryCollection.getScanIdEntryList()) {
            String row = generateCsvRow(scanObject, lineEnding, delimiter);
            writer.append(row);
        }
        writer.flush();
        writer.close();
        System.out.println("Finished writing to " + outputDirectory);
    }

    /**
     * Generates a header row for the csv file.
     *
     * @param scanObject ScanID object with mzid data.
     * @param lineEnding line ending for each csv row.
     * @param delimiter delimiter for each csv column.
     * @return header of the csv file as String.
     */
    private String generateCsvHeader(final ScanIdOutput scanObject, final String lineEnding, final String delimiter, final ArrayList<String> datasets) {
        String header = "";
        header += "Scan ID" + delimiter;
        for (String dataset : datasets) {
            header += dataset + "Peptide Sequence" + delimiter;
        }
        for (String dataset : datasets) {
            header += dataset + " PSM -10lgP" + delimiter;
        }
        if (scanObject.getEntryFlag() > 0) {
            for (String dataset : datasets) {
                header += dataset + " Database Flag" + delimiter;
            }
        }
        header += "Flag" + lineEnding;
        return header;
    }

    /**
     * Generates a data row for the csv file.
     *
     * @param scanObject ScanID object with mzid data.
     * @param lineEnding line ending for each csv row.
     * @param delimiter delimiter for each csv column.
     * @return ScanID data row as String.
     */
    private String generateCsvRow(final ScanIdOutput scanObject, final String lineEnding, final String delimiter) {
        String row = "";
        row += scanObject.getScanId() + delimiter;
        for (String sequence : scanObject.getPeptideSequenceList()) {
            row += sequence + delimiter;
        }
        for (int i = 0; i < scanObject.getPsmScoreList().size(); i++) {
            row += scanObject.getPsmScoreList().get(i) + delimiter;
        }
        if (scanObject.getEntryFlag() > 0) {
            for (String flag : scanObject.getDatabaseFlagList()) {
                row += flag + delimiter;
            }
        }
        row += scanObject.getEntryFlag() + lineEnding;
        return row;
    }
}
