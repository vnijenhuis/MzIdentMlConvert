/*
 * @author Vikthor Nijenhuis
 * @project PeptideItem mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.writer.csv;

import java.io.FileWriter;
import java.io.IOException;
import nl.eriba.mzidentml.identification.collections.output.PeptideOutputCollection;
import nl.eriba.mzidentml.identification.objects.output.PeptideOutput;

/**
 * Writes a peptides.csv file.
 *
 * @author vnijenhuis
 */
public class PeptideCsvWriter {

    /**
     * Writes a peptides.csv file.
     *
     * @param directory target directory and file to write the data to.
     * @param peptideEntryCollection collections of PeptideOutput objects to write to each row.
     * @throws IOException can't find or open the given file.
     */
    public final void writePeptideCsv(String directory, PeptideOutputCollection peptideEntryCollection) throws IOException {
        String outputDirectory = directory + "peptides.csv";
        System.out.println("Writing data to " + outputDirectory);
        try (FileWriter writer = new FileWriter(outputDirectory)) {
            String lineEnding = "\n";
            String delimiter = ",";
            String header = writeHeader(delimiter, lineEnding);
            writer.write(header);
            for (PeptideOutput peptideEntry : peptideEntryCollection.getPeptideEntryList()) {
                String row = writeRow(delimiter, lineEnding, peptideEntry);
                writer.write(row);
            }
            writer.flush();
            writer.close();
        }
    }

    /**
     * Writes the header for the peptides.csv file.
     *
     * @param delimiter delimiter used to define the row separator.
     * @param lineEnding lineEnding for end of each row.
     * @return header for a peptides.csv file.
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
        header += "Spectra Count" + delimiter;
        header += "Accession" + delimiter;
        header += "PTM" + delimiter;
        header += "AScore" + delimiter;
        header += "Ion Serie Flag" + delimiter;
        header += "Covered Indices" + lineEnding;
        return header;
    }

    /**
     * Writes the rows for the peptides.csv file.
     *
     * @param delimiter delimiter used to define the row separator.
     * @param lineEnding lineEnding for end of each row.
     * @param peptideEntry PeptideOutput object.
     * @return row containing PeptideOutput data.
     */
    private String writeRow(String delimiter, String lineEnding, PeptideOutput peptideEntry) {
        String row = "";
        row += peptideEntry.getPeptideSequence() + delimiter;
        row += peptideEntry.getPeptideScore() + delimiter;
        row += peptideEntry.getTheoreticalMassToCharge() + delimiter;
        row += peptideEntry.getSequenceLength() + delimiter;
        row += peptideEntry.getPartsPerMillion() + delimiter;
        row += peptideEntry.getCalculatedMassToCharge() + delimiter;
        row += peptideEntry.getRetentionTime() + delimiter;
        row += peptideEntry.getScanNumber() + delimiter;
        row += peptideEntry.getSpectraCount() + delimiter;
        row += peptideEntry.getProteinAccession() + delimiter;
        row += peptideEntry.getPostTranslationalModification() + delimiter;
        row += peptideEntry.getAScore() + lineEnding;
        return row;
    }
}
