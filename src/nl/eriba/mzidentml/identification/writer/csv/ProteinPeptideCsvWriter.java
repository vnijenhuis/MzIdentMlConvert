/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.writer.csv;

import java.io.FileWriter;
import java.io.IOException;
import nl.eriba.mzidentml.identification.collections.output.ProteinPeptideOutputCollection;
import nl.eriba.mzidentml.identification.objects.output.ProteinPeptideOutput;

/**
 * Writes a protein-peptides.csv file.
 *
 * @author vnijenhuis
 */
public class ProteinPeptideCsvWriter {

    /**
     * Writes a protein-peptides.csv file.
     *
     * @param directory target directory and file to write the data to.
     * @param proteinPeptideEntryCollection collections of ProteinPeptideOutput objects to write to each row.
     * @throws IOException can't find or open the given file.
     */
    public final void writeProteinPeptideCsv(final String directory, ProteinPeptideOutputCollection proteinPeptideEntryCollection) throws IOException {
        String outputDirectory = directory + "protein-peptides.csv";
        System.out.println("Writing output to " + outputDirectory);
        try (FileWriter writer = new FileWriter(outputDirectory)) {
            String lineEnding = "\n";
            String delimiter = ",";
            String header = writeHeader(delimiter, lineEnding);
            writer.write(header);
            int count = 0;
            for (ProteinPeptideOutput proteinPeptideEntry : proteinPeptideEntryCollection.getProteinPeptideEntryList()) {
                String row = writeRow(delimiter, lineEnding, proteinPeptideEntry);
                writer.write(row);
                count++;
            }
            writer.flush();
            writer.close();
        }
    }

    /**
     * Writes the header for the protein-peptides.csv file.
     *
     * @param delimiter delimiter used to define the row separator.
     * @param lineEnding lineEnding for end of each row.
     * @return header for a protein-peptides.csv file.
     */
    private String writeHeader(String delimiter, String lineEnding) {
        String header = "";
        header += "Protein Group" + delimiter;
        header += "Protein ID" + delimiter;
        header += "Protein Accession" + delimiter;
        header += "Peptide Sequence" + delimiter;
        header += "Unique" + delimiter;
        header += "Score (-10lgP)" + delimiter;
        header += "Mass" + delimiter;
        header += "Length" + delimiter;
        header += "ppm" + delimiter;
        header += "m/z" + delimiter;
        header += "RT" + delimiter;
        header += "Scan" + delimiter;
        header += "#Spec" + delimiter;
        header += "Start" + delimiter;
        header += "End" + delimiter;
        header += "PTM" + delimiter;
        header += "AScore" + lineEnding;
        return header;
    }

    /**
     * Writes the rows for the protein-peptides.csv file.
     *
     * @param delimiter delimiter used to define the row separator.
     * @param lineEnding lineEnding for end of each row.
     * @param databaseSearchPsmEntry DatabaseSearchPsmEntry object.
     * @return row containing DatabaseSearchPsmEntry data.
     */
    private String writeRow(String delimiter, String lineEnding, ProteinPeptideOutput proteinPeptideEntry) {
        String row = "";
        row += proteinPeptideEntry.getProteinGroup() + delimiter;
        row += proteinPeptideEntry.getProteinId() + delimiter;
        row += proteinPeptideEntry.getProteinAccession() + delimiter;
        row += proteinPeptideEntry.getPeptideSequence() + delimiter;
        row += proteinPeptideEntry.getUniqueness() + delimiter;
        row += proteinPeptideEntry.getPeptideScore() + delimiter;
        row += proteinPeptideEntry.getTheoreticalMassToCharge() + delimiter;
        row += proteinPeptideEntry.getSequenceLength() + delimiter;
        row += proteinPeptideEntry.getPartsPerMillion() + delimiter;
        row += proteinPeptideEntry.getCalculatedMassToCharge() + delimiter;
        row += proteinPeptideEntry.getRetentionTime() + delimiter;
        row += proteinPeptideEntry.getScanNumber() + delimiter;
        row += proteinPeptideEntry.getSpectraCount() + delimiter;
        row += proteinPeptideEntry.getStartIndex() + delimiter;
        row += proteinPeptideEntry.getEndIndex() + delimiter;
        row += proteinPeptideEntry.getPostTranslationalModification() + delimiter;
        row += proteinPeptideEntry.getAScore() + lineEnding;
        return row;
    }
}
