/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.output;

import java.util.ArrayList;

/**
 * Defines a scanIdEntry object.
 *
 * @author vnijenhuis
 */
public class ScanIdOutput {

    /**
     * Contains the file number and ScanIdOutput.
     */
    private final String scanId;

    /**
     * Contains all peptide -10LogP scores for this ScanIdOutput.
     */
    private final ArrayList<Double> psmScoreList;

    /**
     * Contains all sequences for this ScanIdOutput.
     */
    private final ArrayList<String> peptideSequenceList;

    /**
     * Flag per peptide sequence determined by the ScanEntryFlagger class.
     */
    private Integer scanIdEntryFlag;

    /**
     * Database flags per peptide sequence entry determined by the DatabaseMatcher class.
     */
    private final ArrayList<String> databaseFlagList;

    /**
     * Creates a ScanIdEntry object.
     *
     * @param scanId ID and file Scan parameter from DB search psm.csv.
     * @param peptideSequenceList list of sequence scores.
     * @param psmScoreList list of amino acid sequences.
     * @param databaseFlagList list of database flags.
     */
    public ScanIdOutput(final String scanId, final ArrayList<String> peptideSequenceList, final ArrayList<Double> psmScoreList, final ArrayList<String> databaseFlagList) {
        this.scanId = scanId;
        this.peptideSequenceList = peptideSequenceList;
        this.psmScoreList = psmScoreList;
        this.databaseFlagList = databaseFlagList;
        this.scanIdEntryFlag = 0;
    }

    /**
     * @return scanId as String.
     */
    public final String getScanId() {
        return this.scanId;
    }

    /**
     * @return ArrayList of amino acid peptide sequences as String.
     */
    public final ArrayList<String> getPeptideSequenceList() {
        return this.peptideSequenceList;
    }

    /**
     * Sets the sequence to the index.
     *
     * @param currentIndex index as Integer.
     * @param sequence sequence as String.
     */
    public final void setPeptideSequence(final Integer currentIndex, final String sequence) {
        this.peptideSequenceList.set(currentIndex, sequence);
    }

    /**
     * @return ArrayList containing score values as String.
     */
    public final ArrayList<Double> getPsmScoreList() {
        return this.psmScoreList;
    }

    /**
     * Sets the score to the index.
     *
     * @param currentIndex index as Integer.
     * @param score score as String.
     */
    public final void setPsmScore(final Integer currentIndex, final Double score) {
        this.psmScoreList.set(currentIndex, score);
    }

    /**
     * @return the entryFlag value as Integer.
     */
    public final Integer getEntryFlag() {
        return this.scanIdEntryFlag;
    }

    /**
     * Sets the flag value.
     *
     * @param flag flag value as Integer.
     */
    public final void setEntryFlag(final Integer flag) {
        this.scanIdEntryFlag = flag;
    }

    /**
     * @return ArrayList containing sequence database flag values (absent/present).
     */
    public final ArrayList<String> getDatabaseFlagList() {
        return this.databaseFlagList;
    }

    /**
     * Adds a database flag to the given index of the list.
     *
     * @param currentIndex index of the peptide sequence.
     * @param flag flag value.
     */
    public final void addDatabaseFlag(final Integer currentIndex, final String flag) {
        String flags = this.databaseFlagList.get(currentIndex);
        flags += flag;
        this.databaseFlagList.set(currentIndex, flags);
    }

    /**
     * @param currentIndex gets the database flag for the given index number.
     * @return peptide sequence database flag as Integer.
     */
    public final String getDatabaseFlag(final Integer currentIndex) {
        return this.databaseFlagList.get(currentIndex);
    }

    /**
     * toString() function of the ScanIdOutput object.
     *
     * @return return ScanIdOutput string values.
     */
    @Override
    public final String toString() {
        return "ScanIdEntry{Scan ID; " + this.getScanId() + ", Sequences; " + this.getPeptideSequenceList() + ", Scores; " + this.getPsmScoreList() + ", Database Flag: " + this.getDatabaseFlagList()
                + ", Flag; " + this.getEntryFlag() + "}";
    }
}
