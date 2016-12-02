/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module *
 */
package nl.eriba.mzidentml.identification.objects.mzid;

import java.util.ArrayList;

/**
 * Defines the MzIdProteinDetectionHypothesis object.
 *
 * @author vnijenhuis
 */
public class MzIdProteinDetectionHypothesis {

    /**
     * Id of the ProteinDetectionHypothesis parameter.
     */
    private final String proteinId;

    /**
     * Reference to the Database Sequence id.
     */
    private final String proteinAccession;

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * List of spectrum item ids.
     */
    private final ArrayList<String> spectrumItemIdList;

    /**
     * List of MzIdProteinHypothesisCvParam objects that belong to this ProteinHypothesis.
     */
    private final ArrayList<MzIdCvParam> cvParamList;

    /**
     * Protein group that the Hypothesis belongs to.
     */
    private final Integer proteinGroup;

    /**
     * Peptide evidence id.
     */
    private final Integer peptideEvidenceId;

    /**
     * Threshold to filter bad hits.
     */
    private final Boolean passThreshold;
    
    /**
     * Score of the protein sequence.
     */
    private final Double proteinScore;
    
    /**
     * Protein coverage percentage.
     */
    private final Double coverage;

    /**
     * Defines a MzIdProteinDetectionHypothesis object.
     *
     * @param passThreshold true/false threshold.
     * @param proteinId id of the protein group.
     * @param proteinGroup protein group.
     * @param proteinAccession protein accession id.
     * @param peptideEvidenceId id of the MzIdPeptideEvidence object.
     * @param proteinScore score of th eprotein.
     * @param sequenceCoverage total coverage of the protein sequence by peptide sequences.
     * @param spectrumItemIdList list of spectrum item ids.
     * @param cvParamList ArrayList of MzIdCvParam objects.
     */
    public MzIdProteinDetectionHypothesis(final Boolean passThreshold, final String proteinId, final Integer proteinGroup, final String proteinAccession, final Integer peptideEvidenceId, final Double proteinScore,
             Double sequenceCoverage, final ArrayList<String> spectrumItemIdList, final ArrayList<MzIdCvParam> cvParamList) {
        this.objectName = "ProteinDetectionHypothesis";
        this.passThreshold = passThreshold;
        this.proteinGroup = proteinGroup;
        this.proteinId = proteinId;
        this.proteinAccession = proteinAccession;
        this.peptideEvidenceId = peptideEvidenceId;
        this.proteinScore = proteinScore;
        this.coverage = sequenceCoverage;
        this.spectrumItemIdList = spectrumItemIdList;
        this.cvParamList = cvParamList;
    }

    /**
     * @return the protein group id as Integer.
     */
    public String getProteinId() {
        return proteinId;
    }

    /**
     * @return the protein group as Integer.
     */
    public Integer getProteinGroup() {
        return proteinGroup;
    }

    /**
     * @return the database sequence reference as String.
     */
    public String getProteinAccession() {
        return proteinAccession;
    }

    /**
     * @return an ArrayList of MzIdCvParam objects.
     */
    public ArrayList<MzIdCvParam> getCvParamList() {
        return cvParamList;
    }

    /**
     * @return an ArrayList of spectrum ids as String.
     */
    public ArrayList<String> getSpectrumItemIdList() {
        return spectrumItemIdList;
    }

    /**
     * @return the peptideEvidenceId returns the MzIdPeptideEvidence id as Integer.
     */
    public Integer getPeptideEvidenceId() {
        return peptideEvidenceId;
    }

    /**
     * @return threshold value as Boolean.
     */
    public Boolean isPassThreshold() {
        return passThreshold;
    }

    /**
     * Returns the protein score.
     * 
     * @return protein score as Double.
     */
    public Double getProteinScore() {
        return this.proteinScore;
    }

    /**
     * Returns the protein coverage in percentages.
     * 
     * @return protein coverage as Double. 
     */
    public Double getProteinCoverage() {
        return this.coverage;
    }
    /**
     * toString function of the MzIdProteinDetectionHypothesis object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{PeptideEvidence: " + getPeptideEvidenceId() + "ProteinID: " + this.getProteinId() + ", group; " + this.getProteinGroup() + ", dBSequence_ref: " + this.getProteinAccession()
                + ", isPassThreshold: " + this.isPassThreshold() + ", specutrmItemList: " + getSpectrumItemIdList() + "}";
    }
}
