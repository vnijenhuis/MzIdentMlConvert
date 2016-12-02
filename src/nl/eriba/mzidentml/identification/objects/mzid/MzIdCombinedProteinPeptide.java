/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.mzid;

import java.util.ArrayList;

/**
 * Defines a CombinedProteinPeptide object which contains protein and peptide data related to eachother.
 * 
 * @author vnijenhuis
 */
public class MzIdCombinedProteinPeptide {
  
    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Id of the Peptide Evidence.
     */
    private final ArrayList<Integer> evidenceIdList;

    /**
     * Database Sequence reference. Refers to the MzIdentMLDatabaseSequence object id.
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
    private final Boolean isDecoySequence;
    private final Integer proteinGroup;
    private final String proteinId;
    private final Double proteinGroupScore;
    private final Boolean isPassThreshold;

    /**
     * PeptideEvidence object from the mzIdentML file.
     *
     * @param evidenceIdList list of PeptideEvidence ids.
     * @param accession protein accession id.
     * @param peptideSequence peptide amino acid sequence.
     * @param proteinGroup
     * @param proteinId
     * @param isDecoySequence
     * @param isPassThreshold
     * @param startIndex start of the peptide sequence on the protein sequence.
     * @param endIndex end of the peptide sequence on the protein sequence.
     * @param preAminoAcid amino acid residue 1 index in front of the peptide sequence.
     * @param postAminoAcid amino acid residue 1 index behind the peptide sequence.
     * @param proteinGroupScore
     */
    public MzIdCombinedProteinPeptide(final ArrayList<Integer> evidenceIdList,  final String peptideSequence, final String accession, final Integer proteinGroup, final String proteinId,
            final Boolean isDecoySequence, final Boolean isPassThreshold, final Integer startIndex, final Integer endIndex, final String preAminoAcid, final String postAminoAcid, final Double proteinGroupScore) {
        this.objectName = "CombinedProteinPeptide";
        this.evidenceIdList = evidenceIdList;
        this.proteinAccession = accession;
        this.proteinGroup = proteinGroup;
        this.proteinId = proteinId;
        this.peptideSequence = peptideSequence;
        this.isPassThreshold = isPassThreshold;
        this.isDecoySequence = isDecoySequence;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.preAminoAcid = preAminoAcid;
        this.postAminoAcid = postAminoAcid;
        this.proteinGroupScore = proteinGroupScore;
    }

    /**
     * Returns the ID of the peptide evidence parameter.
     *
     * @return the id as String.
     */
    public ArrayList<Integer> getPeptideEvidenceIdList() {
        return this.evidenceIdList;
    }

    /**
     * Returns the database sequence reference (database sequence id = database sequence reference).
     *
     * @return the databaseSequenceReference as String.
     */
    public String getProteinAccession() {
        return this.proteinAccession;
    }

    public Integer getProteinGroup() {
        return this.proteinGroup;
    }

    public String getProteinId() {
        return this.proteinId;
    }

    /**
     * Link to the MzIdPeptide object sequence parameter (peptide reference = peptide sequence).
     *
     * @return the peptideReference as String.
     */
    public String getPeptideSequence() {
        return this.peptideSequence;
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
        return this.isDecoySequence;
    }

    /**
     * Returns a boolean wether the sequence is a decoy sequence or not.
     * 
     * @return true/false Boolean to determine if a sequence is a decoy sequence.
     */
    public Boolean isPassThreshold() {
        return this.isPassThreshold;
    }

    public Double getProteinGroupScore() {
        return this.proteinGroupScore;
    }

    /**
     * toString function of the MzIdPeptideEvidence object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Combined protein-peptide: " + this.getPeptideEvidenceIdList()+ ", Protein accession: " + this.getProteinAccession()+ ", Protein group: " + this.getProteinGroup()
                + ", Protein id: " + this.getProteinId() + ", Peptide reference: " + this.getPeptideSequence() + ", Is decoy sequence: " + this.isDecoySequence() + ", Passed threshold: "  + this.isPassThreshold()
                + ", Start index: "  + this.getStartIndex() + ", End index: " + this.getEndIndex() + ", Pre amino acid: " + this.getPreAminoAcid() + ", Post amino acid: " + this.getPostAminoAcid()
                + ", Protein group score: " + this.getProteinGroupScore() + "}";
    }
}
