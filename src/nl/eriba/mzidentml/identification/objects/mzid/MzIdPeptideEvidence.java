/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module *
 */
package nl.eriba.mzidentml.identification.objects.mzid;

/**
 * Defines a MzIdPeptideEvidence object.
 *
 * @author vnijenhuis
 */
public class MzIdPeptideEvidence {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Peptide Evidence id.
     */
    private final Integer evidenceId;

    /**
     * Database Sequence reference. Refers to the MzIdentMLDatabaseSequence object id.
     */
    private final String proteinAccession;

    /**
     * Peptide Reference. Refers to the Peptide Object id.
     */
    private final String peptideReference;

    /**
     * Peptide start index.
     */
    private final Integer startIndex;

    /**
     * Peptide end index.
     */
    private final Integer endIndex;

    /**
     * Amino acid residue 1 index in front of the peptide sequence.
     */
    private final String preAminoAcid;

    /**
     * Amino acid residue 1 index behind the peptide sequence.
     */
    private final String postAminoAcid;
    
    /**
     * Test if the peptide sequence is a decoy sequence.
     */
    private final Boolean isDecoy;

    /**
     * PeptideEvidence object from the mzIdentML file.
     *
     * @param evidenceId if of the peptide evidence.
     * @param proteinAccession reference to the Database Sequence object.
     * @param peptideRef reference to the id of the Peptide object.
     * @param isDecoy test if the peptide sequence is a decoy sequence.
     * @param startIndex start of the peptide sequence on the protein sequence.
     * @param endIndex end of the peptide sequence on the protein sequence.
     * @param preAminoAcid amino acid residue 1 index in front of the peptide sequence.
     * @param postAminoAcid amino acid residue 1 index behind the peptide sequence.
     */
    public MzIdPeptideEvidence(final Integer evidenceId, final String proteinAccession, final String peptideRef, final Boolean isDecoy, final Integer startIndex,
            final Integer endIndex, final String preAminoAcid, final String postAminoAcid) {
        this.objectName = "PeptideEvidence";
        this.evidenceId = evidenceId;
        this.proteinAccession = proteinAccession;
        this.peptideReference = peptideRef;
        this.isDecoy = isDecoy;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.preAminoAcid = preAminoAcid;
        this.postAminoAcid = postAminoAcid;
    }

    /**
     * Returns the ID of the peptide evidence parameter.
     *
     * @return the id as String.
     */
    public Integer getPeptideEvidenceId() {
        return this.evidenceId;
    }

    /**
     * Returns the database sequence reference (database sequence id = database sequence reference).
     *
     * @return the databaseSequenceReference as String.
     */
    public String getProteinAccession() {
        return this.proteinAccession;
    }

    /**
     * Link to the MzIdPeptide object sequence parameter (peptide reference = peptide sequence).
     *
     * @return the peptideReference as String.
     */
    public String getPeptideSequence() {
        return this.peptideReference;
    }

    /**
     * Returns the start position of the peptide sequence.
     *
     * @return the start position as Integer.
     */
    public Integer getStartIndex() {
        return this.startIndex;
    }

    /**
     * Returns the end position of the peptide sequence.
     *
     * @return the end position as Integer.
     */
    public Integer getEndIndex() {
        return this.endIndex;
    }

    /**
     * Returns the amino acid that is pre the peptide sequence (1 index before the actual sequence).
     *
     * @return the pre amino acid as String.
     */
    public String getPreAminoAcid() {
        return this.preAminoAcid;
    }

    /**
     * Returns the amino acid that is post the peptide sequence (1 index after the actual sequence).
     *
     * @return the post amino acid as String.
     */
    public String getPostAminoAcid() {
        return this.postAminoAcid;
    }

    /**
     * Returns a boolean wether the sequence is a decoy sequence or not.
     * 
     * @return true/false Boolean to determine if a sequence is a decoy sequence.
     */
    public Boolean isDecoySequence() {
        return this.isDecoy;
    }

    /**
     * toString function of the MzIdPeptideEvidence object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Evidence id: " + this.getPeptideEvidenceId() + ", Database sequence reference: " + this.getProteinAccession()  + ", Peptide reference: "
                + this.getPeptideSequence() + ", Is decoy sequence: " + this.isDecoySequence() + ", Start index: " + this.getStartIndex() + ", End index: " + this.getEndIndex()
                + ", Pre amino acid: " + this.getPreAminoAcid() + ", Post amino acid: " + this.getPostAminoAcid() + "}";
    }
}
