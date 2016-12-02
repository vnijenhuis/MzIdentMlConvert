/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.dataprocessing.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import nl.eriba.mzidentml.identification.collections.general.ProteinDatabaseSequenceCollection;
import nl.eriba.mzidentml.identification.filereader.ProteinFileReader;

/**
 * Creates a HashMap of protein database fasta files.
 *
 * @author vnijenhuis
 */
public class ProteinSequenceDatabaseMap {

    /**
     * Reads a HashMap of database files and creates protein collections of the given files.
     *
     * @param databaseEntryMap HashMap with database index as key and a list of files as value.
     * @return HashMap with database index as Key and a HashMap as value. This HashMap has sample name as key and a
     * ProteinDatabaseSequenceCollection as value.
     */
    public final HashMap<String, HashMap<Integer, ProteinDatabaseSequenceCollection>> createProteinSequenceDatabaseMap(
            LinkedHashMap<String, ArrayList<String>> databaseEntryMap) {
        ProteinFileReader proteinReader = new ProteinFileReader();
        //Gather files for each index.
        HashMap<String, HashMap<Integer, ProteinDatabaseSequenceCollection>> databaseMap = new HashMap<>();
        for (Map.Entry<String, ArrayList<String>> entryMap : databaseEntryMap.entrySet()) {
            Integer currentIndex = 0;
            //Set value to -1 for single databases. These databases are matched to all samples. Uniprot would be one such database.
            if (entryMap.getValue().size() == 1) {
                currentIndex = -1;
            }
            HashMap<Integer, ProteinDatabaseSequenceCollection> proteinList = new HashMap<>();
            for (String file : entryMap.getValue()) {
                ProteinDatabaseSequenceCollection proteins = new ProteinDatabaseSequenceCollection();
                proteinReader.createCollection(file, proteins);
                proteinList.put(currentIndex, proteins);
                currentIndex++;
            }
            databaseMap.put(entryMap.getKey(), proteinList);
        }
        return databaseMap;
    }
}
