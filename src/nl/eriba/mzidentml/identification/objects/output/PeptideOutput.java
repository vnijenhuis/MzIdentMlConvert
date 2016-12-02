/*
 * @author Vikthor Nijenhuis
 * @project PeptideItem mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.output;

/**
 * Defines a PeptideOutput object.
 *
 * @author vnijenhuis
 */
public class PeptideOutput {

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
     * Total spectra count.
     */
    private final Integer spectraCount;

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
     * Defines a PeptideEntry object.
     *
     * @param peptideSequence The amino acid peptide sequence.
     * @param peptideScore The -10LogP score value of the sequence.
     * @param theoreticalMassToCharge The theoretical mass-to-charge ratio of the peptide.
     * @param sequenceLength The length of the peptide sequence.
     * @param partsPerMillion The parts per million of the peptide.
     * @param calculatedMassToCharge The calculated mass-to-charge ratio of the peptide.
     * @param retentionTime The retention time of the peptide.
     * @param scanNumber The scan id number of the peptide.
     * @param proteinAccession The protein accession id of the protein that this peptide belongs to.
     * @param spectraCount Total spectra count.
     * @param postTranslationalModification The post-translational modification for this peptide.
     * @param aScore The aScore of the post-translational modification for this peptide.
     * @param ionSeriesFlag Flag of the ion series.
     * @param evidenceCount MzIdPeptideEvidence count for this peptide.
     * @param ionSeriesIndexList List of ion series indices.
     */
    public PeptideOutput(final String peptideSequence, final Double peptideScore, final Double theoreticalMassToCharge, final Integer sequenceLength, final Double partsPerMillion, final Double calculatedMassToCharge, final String retentionTime, final String scanNumber, final String proteinAccession, final Integer spectraCount,
            final String postTranslationalModification, final String aScore) {
        this.peptideSequence = peptideSequence;
        this.peptideScore = peptideScore;
        this.theoreticalMassToCharge = theoreticalMassToCharge;
        this.sequenceLength = sequenceLength;
        this.partsPerMillion = partsPerMillion;
        this.calculatedMassToCharge = calculatedMassToCharge;
        this.retentionTime = retentionTime;
        this.scanNumber = scanNumber;
        this.proteinAccession = proteinAccession;
        this.spectraCount = spectraCount;
        this.postTranslationalModification = postTranslationalModification;
        this.aScore = aScore;
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
     * Returns the aScore of the peptide.
     *
     * @return the aScore as String.
     */
    public Integer getSpectraCount() {
        return spectraCount;
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
     * toString() function of a PeptideOutput object.
     *
     * @return PeptideOutput object values as String.
     */
    @Override
    public String toString() {
        return "Sequence; " + this.getPeptideSequence() + ", -10LogP; " + this.getPeptideScore() + ", mass; " + this.getTheoreticalMassToCharge() + ", Length; " + this.getSequenceLength() + ", Parts per million; " + this.getPartsPerMillion()
                + ", m/z; " + this.getCalculatedMassToCharge() + ", RT; " + this.getRetentionTime() + ", Scan; " + this.getScanNumber() + ", #Spec; " + this.getSpectraCount() + ", Accession; " + this.getProteinAccession() + ", PTM; "
                + this.getPostTranslationalModification() + ", AScore; " + this.getAScore();
    }
}
