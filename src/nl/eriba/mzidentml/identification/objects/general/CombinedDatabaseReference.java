/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.general;

import java.util.ArrayList;

/**
 * Defines a CombinedDatabaseReference object that contains data of multiple
 * database references.
 *
 * @author vnijenhuis
 */
public class CombinedDatabaseReference {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Database Sequence reference. Refers to the MzIdentMLDatabaseSequence
     * object id.
     */
    private String proteinAccession;

    /**
     * Peptide Reference. Refers to the Peptide Object id.
     */
    private final ArrayList<String> peptideSequenceList;

    /**
     * Peptide start index.
     */
    private final ArrayList<Integer> startIndexList;

    /**
     * Peptide end index.
     */
    private final ArrayList<Integer> endIndexList;

    /**
     * Amino acid residue 1 index in front of the peptide sequence.
     */
    private final ArrayList<String> preAminoAcidList;

    /**
     * Amino acid residue 1 index behind the peptide sequence.
     */
    private final ArrayList<String> postAminoAcidList;

    /**
     * The protein description.
     */
    private String proteinDescripion;

    /**
     * Number of total peptide sequences for a given protein accession.
     */
    private final Integer totalPeptideCount;

    /**
     * Number of unique peptide sequences for a given protein accession.
     */
    private Integer uniquePeptideCount;

    /**
     * ArrayList of post-translational modifications.
     */
    private final ArrayList<String> postTransModificationList;

    /**
     * List if peptide evidence ids.
     */
    private final ArrayList<Integer> evidenceIdList;

    /**
     * Defines a SequenceDatabaseReference object.
     *
     * @param proteinAccession the protein accession as String.
     * @param evidenceIdList ArrayList of PeptideEvidence ids.
     * @param peptideSequenceList ArrayList of peptide sequences as String.
     * @param startIndexList ArrayList of start positions as Integer.
     * @param endIndexList ArrayList of end positions as Integer.
     * @param preAminoAcidList ArrayList of pre amino acids as String.
     * @param postAminoAcidList ArrayList of post amino acids as String.
     * @param postTransModificationList ArrayList of post-translational
     * modifications as String.
     */
    public CombinedDatabaseReference(final String proteinAccession, final ArrayList<Integer> evidenceIdList, final ArrayList<String> peptideSequenceList, final ArrayList<Integer> startIndexList,
            final ArrayList<Integer> endIndexList, final ArrayList<String> preAminoAcidList, final ArrayList<String> postAminoAcidList, final ArrayList<String> postTransModificationList) {
        this.objectName = "CombinedSequenceDatabaseReference";
        this.proteinAccession = proteinAccession;
        this.evidenceIdList = evidenceIdList;
        this.peptideSequenceList = peptideSequenceList;
        this.startIndexList = startIndexList;
        this.endIndexList = endIndexList;
        this.preAminoAcidList = preAminoAcidList;
        this.postAminoAcidList = postAminoAcidList;
        this.totalPeptideCount = this.peptideSequenceList.size();
        this.uniquePeptideCount = 0;
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
     * Provides a list of peptide sequences.
     *
     * @return ArrayList of peptide sequences as String.
     */
    public ArrayList<String> getPeptideSequenceList() {
        return peptideSequenceList;
    }

    /**
     * Returns an ArrayList of start positions as Integer.
     *
     * @return ArrayList of start positions as Integer.
     */
    public ArrayList<Integer> getStartIndexList() {
        return startIndexList;
    }

    /**
     * Returns an ArrayList of end positions as Integer.
     *
     * @return ArrayList of end positions as Integer.
     */
    public ArrayList<Integer> getEndIndexList() {
        return endIndexList;
    }

    /**
     * Returns an ArrayList of pre amino acids as String.
     *
     * @return ArrayList of pre amino acids as String.
     */
    public ArrayList<String> getPreAminoAcidList() {
        return preAminoAcidList;
    }

    /**
     * Returns an ArrayList of post amino acids as String.
     *
     * @return ArrayList of post amino acids as String.
     */
    public ArrayList<String> getPostAminoAcidList() {
        return postAminoAcidList;
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
     * Adds a start index to the list.
     *
     * @param start start index as Integer.
     */
    public void addToStartIndexList(Integer start) {
        this.startIndexList.add(start);
    }

    /**
     * Adds a start end to the list.
     *
     * @param end end index as Integer.
     */
    public void addToEndIndexList(Integer end) {
        this.endIndexList.add(end);
    }

    /**
     * Adds a pre amino acid to the list.
     *
     * @param pre pre amino acid as Integer.
     */
    public void addToPreAminoAcidList(String pre) {
        this.preAminoAcidList.add(pre);
    }

    /**
     * Adds a post amino acid to the list.
     *
     * @param post post amino acid as Integer.
     */
    public void addToPostAminoAcidList(String post) {
        this.postAminoAcidList.add(post);
    }

    /**
     * Adds a peptide amino acid sequence to the list.
     *
     * @param sequence peptide amino acid sequence as String.
     */
    public void addToPeptideSequenceList(String sequence) {
        this.peptideSequenceList.add(sequence);
    }

    /**
     * Returns total peptide count number.
     *
     * @return total peptide count as Integer.
     */
    public Integer getTotalPeptideCount() {
        return totalPeptideCount;
    }

    /**
     * Adds one to the counter of unique peptides.
     */
    public void addToUniquePeptideCount() {
        this.uniquePeptideCount = this.uniquePeptideCount + 1;
    }

    /**
     * Returns unique peptide count number.
     *
     * @return unique peptide count as Integer.
     */
    public Integer getUniquePeptideCount() {
        return this.uniquePeptideCount;
    }

    /**
     * Adds a post-translational modification to the list.
     *
     * @param postTranslationalModification post-translational modification as
     * String.
     */
    public void addToPostTranslationalModifications(ArrayList<String> postTranslationalModification) {
        this.postTransModificationList.addAll(postTranslationalModification);
    }

    /**
     * Returns the post-translational modification values of this object.
     *
     * @return ArrayList of post-translational modifications as String.
     */
    public ArrayList<String> getPostTranslationalModifications() {
        return this.postTransModificationList;
    }

    /**
     * Set the current protein accession to a reversed protein accession id.
     *
     * @param reversedAccession
     */
    public void setReversedAccession(String reversedAccession) {
        this.proteinAccession = reversedAccession;
    }

    /**
     * Returns the list of PpeptideEvidence ids.
     *
     * @return ArrayList of PeptideEvidence ids as Integer.
     */
    public final ArrayList<Integer> getEvidenceIdList() {
        return this.evidenceIdList;
    }

    /**
     * toString function of the MzIdPeptideEvidence object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Protein accession: " + this.getProteinAccession() + ", Peptide sequence list: " + this.getPeptideSequenceList()  + ", Start index: " + this.getStartIndexList()
                + ", End index: " + this.getEndIndexList() + ", Pre amino acid: "  + this.getPreAminoAcidList() + ", Post amino acid: " + this.getPostAminoAcidList() + ", Protein Description: "
                + this.getProteinDescription() + ", Total peptide count: " + this.getTotalPeptideCount() + ", Unique peptide count: " + this.getUniquePeptideCount() + "}";
    }
}
