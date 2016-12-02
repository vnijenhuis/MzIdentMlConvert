/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.writer.csv;

import java.io.FileWriter;
import java.io.IOException;
import nl.eriba.mzidentml.identification.collections.output.ProteinOutputCollection;
import nl.eriba.mzidentml.identification.objects.output.ProteinOutput;

/**
 * Writes a proteins.csv file.
 *
 * @author vnijenhuis
 */
public class ProteinCsvWriter {

    /**
     * Writes a proteins.csv file.
     *
     * @param directory target directory and file to write the data to.
     * @param proteinEntryCollection collections of ProteinOutput objects to write to each row.
     * @throws IOException can't find or open the given file.
     */
    public final void writeProteinCsv(final String directory, ProteinOutputCollection proteinEntryCollection) throws IOException {
        String output = directory + "proteins.csv";
        System.out.println("Writing output to " + output);
        try (FileWriter writer = new FileWriter(output)) {
            String lineEnding = "\n";
            String delimiter = ",";
            String header = writeHeader(delimiter, lineEnding);
            writer.write(header);
            for (ProteinOutput proteinEntry : proteinEntryCollection.getProteinEntryList()) {
                String row = writeRow(delimiter, lineEnding, proteinEntry);
                writer.write(row);
            }
            writer.flush();
            writer.close();
        }
    }

    /**
     * Writes the header for the proteins.csv file.
     *
     * @param delimiter delimiter used to define the row separator.
     * @param lineEnding lineEnding for end of each row.
     * @return header for a proteins.csv file.
     */
    private String writeHeader(String delimiter, String lineEnding) {
        String header = "";
        header += "Protein Group" + delimiter;
        header += "Protein ID" + delimiter;
        header += "Accession" + delimiter;
        header += "-10lgP" + delimiter;
        header += "Coverage (%)" + delimiter;
        header += "#Peptides" + delimiter;
        header += "#Unique" + delimiter;
        header += "PTM" + delimiter;
        header += "Avg. Mass" + delimiter;
        header += "Description" + lineEnding;
        return header;
    }

    /**
     * Writes the rows for the proteins.csv file.
     *
     * @param delimiter delimiter used to define the row separator.
     * @param lineEnding lineEnding for end of each row.
     * @param proteinEntry ProteinOutput object.
     * @return row containing DatabaseSearchPsmEntry data.
     */
    private String writeRow(String delimiter, String lineEnding, ProteinOutput proteinEntry) {
        String row = "";
        row += proteinEntry.getProteinGroup() + delimiter;
        row += proteinEntry.getProteinId() + delimiter;
        row += proteinEntry.getProteinAccession() + delimiter;
        row += proteinEntry.getTotalScore() + delimiter;
        row += proteinEntry.getProteinCoverage() + delimiter;
        row += proteinEntry.getPeptideCount() + delimiter;
        row += proteinEntry.getUniquePeptideCount() + delimiter;
        row += proteinEntry.getPostTranslationalModification() + delimiter;
        row += proteinEntry.getAverageSequenceMass() + delimiter;
        row += proteinEntry.getProteinDescription() + lineEnding;
        return row;
    }
}
