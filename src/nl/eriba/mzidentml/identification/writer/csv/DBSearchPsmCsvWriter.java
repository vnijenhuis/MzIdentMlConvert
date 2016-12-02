/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.writer.csv;

import java.io.FileWriter;
import java.io.IOException;
import nl.eriba.mzidentml.identification.collections.output.DatabaseSearchPsmOutputCollection;
import nl.eriba.mzidentml.identification.objects.output.DatabaseSearchPsmOutput;

/**
 * Writes a DB search psm.csv file.
 *
 * @author vnijenhuis
 */
public class DBSearchPsmCsvWriter {

    /**
     * Writes a DB search psm.csv file.
     *
     * @param directory target directory and file to write the data to.
     * @param databaseSearchCollection collections of DatabaseSearchPsmOutput objects to write to each row.
     * @throws IOException can't find or open the given file.
     */
    public final void writeDatabaseSearchPsmCsv(final String directory, DatabaseSearchPsmOutputCollection databaseSearchCollection) throws IOException {
        String output = directory + "DB search psm.csv";
        System.out.println("Writing output to " + output);
        try (FileWriter writer = new FileWriter(output)) {
            String lineEnding = "\n";
            String delimiter = ",";
            String header = writeHeader(delimiter, lineEnding);
            writer.write(header);
            for (DatabaseSearchPsmOutput databaseSearchPsmEntry : databaseSearchCollection.getDatabaseSearchPsmEntryList()) {
                String row = writeRow(delimiter, lineEnding, databaseSearchPsmEntry);
                writer.write(row);
            }
            writer.flush();
            writer.close();
        }
    }

    /**
     * Writes the header for the DB search psm.csv file.
     *
     * @param delimiter delimiter used to define the row separator.
     * @param lineEnding lineEnding for end of each row.
     * @return header for a DB search psm.csv file.
     */
    private String writeHeader(String delimiter, String lineEnding) {
        String header = "";
        header += "Peptide Sequence" + delimiter;
        header += "Score (-10lgP)" + delimiter;
        header += "Mass" + delimiter;
        header += "Length" + delimiter;
        header += "ppm" + delimiter;
        header += "m/z" + delimiter;
        header += "RT" + delimiter;
        header += "Scan" + delimiter;
        header += "Accession" + delimiter;
        header += "PTM" + delimiter;
        header += "AScore" + delimiter;
        header += "Evidence Count" + lineEnding;
        return header;
    }

    /**
     * Writes the rows for the DB search psm.csv file.
     *
     * @param delimiter delimiter used to define the row separator.
     * @param lineEnding lineEnding for end of each row.
     * @param databaseSearchPsmEntry DatabaseSearchPsmOutput object.
     * @return row containing DatabaseSearchPsmOutput data.
     */
    private String writeRow(String delimiter, String lineEnding, DatabaseSearchPsmOutput databaseSearchPsmEntry) {
        String row = "";
        row += databaseSearchPsmEntry.getPeptideSequence() + delimiter;
        row += databaseSearchPsmEntry.getPeptideScore() + delimiter;
        row += databaseSearchPsmEntry.getTheoreticalMassToCharge() + delimiter;
        row += databaseSearchPsmEntry.getSequenceLength() + delimiter;
        row += databaseSearchPsmEntry.getPartsPerMillion() + delimiter;
        row += databaseSearchPsmEntry.getCalculatedMassToCharge() + delimiter;
        row += databaseSearchPsmEntry.getRetentionTime() + delimiter;
        row += databaseSearchPsmEntry.getScanNumber() + delimiter;
        row += databaseSearchPsmEntry.getProteinAccession() + delimiter;
        row += databaseSearchPsmEntry.getPostTranslationalModification() + delimiter;
        row += databaseSearchPsmEntry.getAScore() + delimiter;
        row += databaseSearchPsmEntry.getEvidenceCount() + lineEnding;
        return row;
    }
}
