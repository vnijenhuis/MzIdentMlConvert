/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.mzid;

/**
 * Defines the MzIdDatabaseSequence object.
 *
 * @author vnijenhuis
 */
public class MzIdDatabaseSequence {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Id of the Database Sequence.
     */
    private final String sequenceId;

    /**
     * Accession id of the Database Sequence.
     */
    private final String proteinAccession;

    /**
     * Search Database Reference of the Database Sequence.
     */
    private final String searchDatabaseReference;

    /**
     * Description of the protein.
     */
    private final String proteinDescription;

    /**
     * Reversed protein accession.
     */
    private final String reversedProteinAccession;

    /**
     * Description of the reversed protein.
     */
    private final String reversedProteinDescription;

    /**
     * DatabaseSequence parameter of the MzIdentML file.
     *
     * @param sequenceId protein id.
     * @param proteinAccession protein accession id.
     * @param reversedProteinAccession reversed protein accession id.
     * @param searchDatabaseReference reference to the Search Database.
     * @param proteinDescription description of the protein.
     * @param reversedDescription description of the reversed protein.
     */
    public MzIdDatabaseSequence(String sequenceId, String proteinAccession, String reversedProteinAccession, String searchDatabaseReference, String proteinDescription, String reversedDescription) {
        this.objectName = "DBSequence";
        this.sequenceId = sequenceId;
        this.proteinAccession = proteinAccession;
        this.reversedProteinAccession = reversedProteinAccession;
        this.searchDatabaseReference = searchDatabaseReference;
        this.proteinDescription = proteinDescription;
        this.reversedProteinDescription = reversedDescription;
    }

    /**
     * Returns the database sequence id.
     *
     * @return the id as String.
     */
    public String getSequenceId() {
        return this.sequenceId;
    }

    /**
     * Returns the accession id.
     *
     * @return the accession as String.
     */
    public String getProteinAccession() {
        return this.proteinAccession;
    }

    /**
     * Returns the accession id.
     *
     * @return the accession as String.
     */
    public String getReversedProteinAccession() {
        return this.reversedProteinAccession;
    }

    /**
     * Returns the reference of the search database for this sequence.
     *
     * @return the searchDatabaseReference as String.
     */
    public String getSearchDatabaseReference() {
        return searchDatabaseReference;
    }

    /**
     * Returns the description for this protein sequence.
     *
     * @return the description as String.
     */
    public String getProteinDescription() {
        return proteinDescription;
    }

    /**
     * Returns the description for this protein sequence.
     *
     * @return the description as String.
     */
    public String getReversedProteinDescription() {
        return this.reversedProteinDescription;
    }

    /**
     * toString function of the MzIdDatabaseSequence object.
     *
     * @return MzIdDatabaseSequence as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Id: " + this.getSequenceId() + ", Accession: " + this.getProteinAccession() + ", SearchDatabase Reference: "
                + this.getSearchDatabaseReference() + ", Description: " + this.getProteinDescription() + "}";
    }
}
