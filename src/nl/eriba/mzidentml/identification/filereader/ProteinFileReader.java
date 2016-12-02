/*
 * @author Vikthor Nijenhuis
 * @project peptide spectrum identification quality control  *
 */
package nl.eriba.mzidentml.identification.filereader;

import nl.eriba.mzidentml.identification.objects.general.ProteinDatabaseSequence;
import nl.eriba.mzidentml.identification.collections.general.ProteinDatabaseSequenceCollection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;
import nl.eriba.mzidentml.tools.CalculationTools;
/**
 * Creates a collection of protein objects.
 *
 * @author vnijenhuis
 */
public class ProteinFileReader {

    /**
     * Buffered reader for file reading.
     */
    private BufferedReader dbReader;

    /**
     * Reads proteins.fasta files or database file such as the uniprot-database.fasta.gz and creates a protein collection.
     *
     * @param database database file.
     * @param proteins protein collection.
     * @return returns a collection of proteins.
     */
    public final ProteinDatabaseSequenceCollection createCollection(final String database,
            final ProteinDatabaseSequenceCollection proteins) {
        CalculationTools tools = new CalculationTools();
        try {
            System.out.println("Loading database proteins from " + database);
            File file = new File(database);
            //Read database files. Can read .fasta and .fasta.gz files.
            if (database.matches(".*\\.fa(sta){0,1}\\.gz")) {
                InputStream fileStream = new FileInputStream(file);
                InputStream gzipStream = new GZIPInputStream(fileStream);
                Reader decoder = new InputStreamReader(gzipStream, "US-ASCII");
                dbReader = new BufferedReader(decoder);
            } else if (database.matches(".*\\.fa(sta){0,1}")) {
                FileReader fr = new FileReader(file);
                dbReader = new BufferedReader(fr);
            }
            String line;
            boolean firstLine = true;
            String proteinSequence = "";
            String proteinAccession = "";
            //Create protein objects with a sequence and a protein accession.
            while ((line = dbReader.readLine()) != null) {
                if (line.startsWith(">") && firstLine) {
                    String id = line.split(" ")[0].replace(">", "");
                    if (id.contains("|")) {
                        proteinAccession = id.split("\\|")[1];
                    } else {
                        proteinAccession = id;
                    }
                    firstLine = false;
                } else if (line.startsWith(">")) {
                    //Calculate protein mass.
                    Double proteinMass = tools.calculateProteinMass(proteinSequence);
                    ProteinDatabaseSequence protein = new ProteinDatabaseSequence(proteinSequence, proteinAccession, proteinMass);
                    proteins.addProtein(protein);
                    String id = line.split(" ")[0].replace(">", "");
                    if (id.contains("|")) {
                        proteinAccession = id.split("\\|")[1];
                    } else {
                        proteinAccession = id;
                    }
                    proteinSequence = "";
                } else {
                    proteinSequence += line.trim();
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File nout found: " + database + ".\nError: " + ex.getMessage());
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("Encountered IO Exception for file " + database + " ; " + ex.getMessage());
            System.exit(0);
        }
        proteins.sortOnProteinAccession();
        //Return the protein collection.
        System.out.println("Loaded " + proteins.getProteinCollection().size() + " proteins from " + database);
        return proteins;
    }
}
