/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.general;

import java.util.ArrayList;

/**
 * Defines a SingleDatabaseReference object.
 *
 * @author vnijenhuis
 */
public class SingleDatabaseReference {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Database Sequence reference. Refers to the MzIdentMLDatabaseSequence
     * object id.
     */
    private final String proteinAccession;

    /**
     * Peptide Reference. Refers to the Peptide Object id.
     */
    private final String peptideSequence;

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
     * The protein description.
     */
    private String proteinDescripion;

    /**
     * List of post-translational modifications.
     */
    private final ArrayList<String> postTransModificationList;

    /**
     * PeptideEvidence id reference.
     */
    private final Integer evidenceId;

    /**
     * Defines a SequenceDatabaseReference object.
     *
     * @param proteinAccession the protein accession as String.
     * @param evidenceId peptide evidence id as Integer.
     * @param peptideSequence peptide sequence as String.
     * @param startIndex start position as Integer.
     * @param endIndex end position as Integer.
     * @param preAminoAcid pre amino acid as String.
     * @param postAminoAcid post amino acid as String.
     * @param postTransModificationList list of post-translational modifications.
     */
    public SingleDatabaseReference(final String proteinAccession, final Integer evidenceId, final String peptideSequence, final Integer startIndex,
            final Integer endIndex, final String preAminoAcid, final String postAminoAcid, final ArrayList<String> postTransModificationList) {
        this.objectName = "SingleDatabaseReference";
        this.evidenceId = evidenceId;
        this.proteinAccession = proteinAccession;
        this.peptideSequence = peptideSequence;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.preAminoAcid = preAminoAcid;
        this.postAminoAcid = postAminoAcid;
        this.postTransModificationList = postTransModificationList;
        this.proteinDescripion = "";
    }

    /**
     * Provides the accession id of a protein sequence.
     *
     * @return the protein accession as String.
     */
    public String getProteinAccession() {
        return proteinAccession;
    }

    /**
     * Provides a peptide amino acid sequence.
     *
     * @return peptide sequence as String.
     */
    public String getPeptideSequence() {
        return peptideSequence;
    }

    /**
     * Returns the of start position as Integer.
     *
     * @return start position as Integer.
     */
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     * Returns the end position as Integer.
     *
     * @return end position as Integer.
     */
    public Integer getEndIndex() {
        return endIndex;
    }

    /**
     * Returns an ArrayList of pre amino acids as String.
     *
     * @return ArrayList of pre amino acids as String.
     */
    public String getPreAminoAcid() {
        return preAminoAcid;
    }

    /**
     * Returns an ArrayList of post amino acids as String.
     *
     * @return ArrayList of post amino acids as String.
     */
    public String getPostAminoAcid() {
        return postAminoAcid;
    }

    /**
     * Returns the protein description value.
     *
     * @return description protein description as String.
     */
    public String getProteinDescription() {
        return this.proteinDescripion;
    }

    /**
     * Sets the protein description value.
     *
     * @param description protein description as String.
     */
    public void setProteinDescription(String description) {
        this.proteinDescripion = description;
    }

    /**
     * Returns the protein description value.
     *
     * @return description protein description as String.
     */
    public Integer getEvidenceId() {
        return this.evidenceId;
    }

    /**
     * Shows the list of post-translational modifications.
     *
     * @return ArrayList of post-translational modifications as String.
     */
    public ArrayList<String> getPostTranslationalModification() {
        return this.postTransModificationList;
    }

    /**
     * toString function of the MzIdPeptideEvidence object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Protein accession: " + this.getProteinAccession() + ", Peptide sequence list: " + this.getPeptideSequence()  + ", Start index: " + this.getStartIndex()
                + ", End index: " + this.getEndIndex() + ", Pre amino acid: " + this.getPreAminoAcid() + ", Post amino acid: " + this.getPostAminoAcid() + ", Protein Description: "
                + this.getProteinDescription() + ", Post-translational modifications: " + this.getPostTranslationalModification() + "}";
    }
}
