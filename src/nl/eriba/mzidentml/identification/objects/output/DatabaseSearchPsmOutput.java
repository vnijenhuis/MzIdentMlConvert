/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.output;

/**
 * Defines a DatabaseSearchPsmOutput object.
 *
 * @author vnijenhuis
 */
public class DatabaseSearchPsmOutput {

    /**
     * The amino acid peptide sequence.
     */
    private final String peptideSequence;

    /**
     * The -10LogP score value of the sequence.
     */
    private final Double peptideScore;

    /**
     * The theoretical mass-to-charge ratio of the peptide.
     */
    private final Double theoreticalMassToCharge;

    /**
     * The length of the peptide sequence.
     */
    private final Integer sequenceLength;

    /**
     * The parts per million of the peptide.
     */
    private final Double partsPerMillion;

    /**
     * The retention time of the peptide.
     */
    private final String retentionTime;

    /**
     * The scan id number of the peptide.
     */
    private final String scanNumber;

    /**
     * The post-translational modification for this peptide.
     */
    private final String postTranslationalModification;

    /**
     * The aScore of the post-translational modification for this peptide.
     */
    private final String aScore;

    /**
     * The calculated mass-to-charge ratio of the peptide.
     */
    private final Double calculatedMassToCharge;

    /**
     * The protein accession id of the protein that this peptide belongs to.
     */
    private final String proteinAccession;

    /**
     * MzIdPeptideEvidence count for this peptide.
     */
    private final Integer evidenceCount;

    /**
     * Defines a DatabaseSearchPsmEntry object.
     *
     * @param sequence The amino acid peptide sequence.
     * @param peptideScore The -10LogP score value of the sequence.
     * @param theoreticalMass The theoretical mass-to-charge ratio of the peptide.
     * @param length The length of the peptide sequence.
     * @param ppm The parts per million of the peptide.
     * @param calculatedMass The calculated mass-to-charge ratio of the peptide.
     * @param retention The retention time of the peptide.
     * @param scan The scan id number of the peptide.
     * @param accession The protein accession id of the protein that this peptide belongs to.
     * @param ptm The post-translational modification for this peptide.
     * @param aScore The aScore of the post-translational modification for this peptide.
     * @param evidenceCount MzIdPeptideEvidence count for this peptide.
     */
    public DatabaseSearchPsmOutput(final String sequence, final Double peptideScore, final Double theoreticalMass, final Integer length, final Double ppm, final Double calculatedMass,
            final String retention, final String scan, final String accession, final String ptm, final String aScore, final Integer evidenceCount) {
        this.peptideSequence = sequence;
        this.peptideScore = peptideScore;
        this.theoreticalMassToCharge = theoreticalMass;
        this.sequenceLength = length;
        this.partsPerMillion = ppm;
        this.calculatedMassToCharge = calculatedMass;
        this.retentionTime = retention;
        this.scanNumber = scan;
        this.proteinAccession = accession;
        this.postTranslationalModification = ptm;
        this.aScore = aScore;
        this.evidenceCount = evidenceCount;
    }

    /**
     * Returns the peptide amino acid sequence.
     *
     * @return the peptide sequence as String.
     */
    public String getPeptideSequence() {
        return peptideSequence;
    }

    /**
     * Returns the -10LogP score value.
     *
     * @return the logP as String.
     */
    public Double getPeptideScore() {
        return peptideScore;
    }

    /**
     * Returns the theoretical mass-to-charge ratio.
     *
     * @return the theoretical mass-to-charge as Double.
     */
    public Double getTheoreticalMassToCharge() {
        return theoreticalMassToCharge;
    }

    /**
     * Returns the peptide sequence length.
     *
     * @return the sequence length as Integer.
     */
    public Integer getSequenceLength() {
        return sequenceLength;
    }

    /**
     * Returns the parts per million.
     *
     * @return the parts per million as Double.
     */
    public Double getPartsPerMillion() {
        return partsPerMillion;
    }

    /**
     * Returns the calculated mass-to-charge ratio.
     *
     * @return the calculated mass-to-charge as Double.
     */
    public Double getCalculatedMassToCharge() {
        return calculatedMassToCharge;
    }

    /**
     * Returns the retention time of the peptide.
     *
     * @return the retention time as String.
     */
    public String getRetentionTime() {
        return retentionTime;
    }

    /**
     * Returns the number of the scan.
     *
     * @return the scan number as String.
     */
    public String getScanNumber() {
        return scanNumber;
    }

    /**
     * Returns the post-translational modification.
     *
     * @return the post-translational modification as String.
     */
    public String getPostTranslationalModification() {
        return postTranslationalModification;
    }

    /**
     * Returns the aScore of the peptide.
     *
     * @return the aScore as String.
     */
    public String getAScore() {
        return aScore;
    }

    /**
     * Returns the protein accession id.
     *
     * @return the protein accession as String.
     */
    public String getProteinAccession() {
        return proteinAccession;
    }

    /**
     * Returns the MzIdPeptideEvidence count.
     *
     * @return evidence count as Integer.
     */
    public Integer getEvidenceCount() {
        return this.evidenceCount;
    }

    /**
     * toString() function of a DatabaseSearchPsmOutput object.
     *
     * @return DatabaseSearchPsmOutput object values as String.
     */
    @Override
    public String toString() {
        return "DatabaseSearchPsmEntry{Sequence; " + this.getPeptideSequence() + ", -10LogP; " + this.getPeptideScore() + ", Calculated m/z; " + this.getCalculatedMassToCharge() + ", Theoretical m/z; " + this.getTheoreticalMassToCharge() + ", Sequence length; " + this.getSequenceLength() + ", Parts per million; " + this.getPartsPerMillion()
                + ", Retention Time; " + this.getRetentionTime() + ", Scan number; " + this.getScanNumber() + ", Protein accession; " + this.getProteinAccession() + ", Post-translational Modification; " + this.getPostTranslationalModification() + ", AScore; " + this.getAScore() + ", Evidence count: " + this.getEvidenceCount() + "}";
    }
}
