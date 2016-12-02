/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.output;

/**
 * Defines a ProteinPeptideOutput object.
 *
 * @author vnijenhuis
 */
public class ProteinPeptideOutput {

    /**
     * Protein group.
     */
    private final Integer proteinGroup;

    /**
     * Protein group id.
     */
    private final String proteinId;

    /**
     * Accession of the protein sequence.
     */
    private final String proteinAccession;

    /**
     * Peptide amino acid sequence.
     */
    private final String peptideSequence;

    /**
     * Peptide sequence -10LogP score.
     */
    private final Double psmScore;

    /**
     * Theoretical mass-to-charge value of the peptide sequence.
     */
    private final Double theoreticalMassToCharge;

    /**
     * Experimental mass-to-charge value of the peptide sequence.
     */
    private final Double calculatedMassToCharge;

    /**
     * Length of the peptide sequence.
     */
    private final Integer sequenceLength;

    /**
     * Parts per million value.
     */
    private final Double partsPerMillion;

    /**
     * Retention time of the peptide sequence.
     */
    private final String retentionTime;

    /**
     * Scan number of the spectrum.
     */
    private final String scanNumber;

    /**
     * End position of the sequence on the protein sequence.
     */
    private final Integer endIndex;

    /**
     * Start position of the sequence on the protein sequence.
     */
    private final Integer startIndex;

    /**
     * Post-translational modification value.
     */
    private final String ptm;

    /**
     * AScore value.
     */
    private final String aScore;

    /**
     * Amount of peptide spectra counted.
     */
    private Integer spectraCount;

    /**
     * Uniqueness flag of the peptide.
     */
    private final String uniquenessFlag;

    /**
     * Defines protein peptide data in a ProteinPeptideEntry object.
     *
     * @param proteinGroup the protein group.
     * @param proteinId the protein id.
     * @param proteinAccession the protein accession id.
     * @param peptideSequence the peptide amino acid sequence.
     * @param uniquenessFlag the uniqueness flag.
     * @param score the -10LogP score.
     * @param theoreticalMassToCharge theoretical m/z value.
     * @param calculatedMassToCharge calculated m/z value.
     * @param sequenceLength length of the sequence.
     * @param partsPerMillion ppm value.
     * @param retentionTime retention time of the peptide.
     * @param scanNumber scan number that was assigned to this spectrum.
     * @param spectraCount amount of peptide spectra with similar results.
     * @param startIndex starting index of the peptide in the protein sequence.
     * @param endIndex end index of the peptide in the protein sequence.
     * @param ptm post-translational modification value.
     * @param aScore aScore value.
     * @param evidenceCount amount of peptide evidences found for the given peptide sequence.
     */
    public ProteinPeptideOutput(final Integer proteinGroup, final String proteinId, final String proteinAccession, final String peptideSequence, final String uniquenessFlag, final Double score,
            final Double theoreticalMassToCharge, final Double calculatedMassToCharge, final Integer sequenceLength, final Double partsPerMillion, final String retentionTime, final String scanNumber,
            final Integer spectraCount, final Integer startIndex, final Integer endIndex, final String ptm, final String aScore) {
        this.proteinGroup = proteinGroup;
        this.proteinId = proteinId;
        this.proteinAccession = proteinAccession;
        this.peptideSequence = peptideSequence;
        this.uniquenessFlag = uniquenessFlag;
        this.psmScore = score;
        this.theoreticalMassToCharge = theoreticalMassToCharge;
        this.calculatedMassToCharge = calculatedMassToCharge;
        this.sequenceLength = sequenceLength;
        this.partsPerMillion = partsPerMillion;
        this.retentionTime = retentionTime;
        this.scanNumber = scanNumber;
        this.spectraCount = spectraCount;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ptm = ptm;
        this.aScore = aScore;
    }

    /**
     * @return the proteinGroup as Integer.
     */
    public Integer getProteinGroup() {
        return proteinGroup;
    }

    /**
     * @return the proteinId as Integer.
     */
    public String getProteinId() {
        return proteinId;
    }

    /**
     * @return the proteinAccession id as String.
     */
    public String getProteinAccession() {
        return proteinAccession;
    }

    /**
     * @return the peptide amino acid sequence as String.
     */
    public String getPeptideSequence() {
        return peptideSequence;
    }

    /**
     * @return the -10LogP score value as String.
     */
    public double getPeptideScore() {
        return psmScore;
    }

    /**
     * @return the theoretical mass-to-charge ratio.
     */
    public Double getTheoreticalMassToCharge() {
        return theoreticalMassToCharge;
    }

    /**
     * @return the peptide sequence length as Integer.
     */
    public Integer getSequenceLength() {
        return sequenceLength;
    }

    /**
     * @return the parts per million as Double.
     */
    public Double getPartsPerMillion() {
        return partsPerMillion;
    }

    /**
     * @return the retention time as String.
     */
    public String getRetentionTime() {
        return retentionTime;
    }

    /**
     * @return the scan number as String.
     */
    public String getScanNumber() {
        return scanNumber;
    }

    /**
     * @return the spectra count as Integer.
     */
    public Integer getSpectraCount() {
        return spectraCount;
    }

    /**
     * @return the ptm as String.
     */
    public String getPostTranslationalModification() {
        return ptm;
    }

    /**
     * @return the aScore as String.
     */
    public String getAScore() {
        return aScore;
    }

    /**
     * @return the calculated mass-to-charge ratio as Double.
     */
    public Double getCalculatedMassToCharge() {
        return calculatedMassToCharge;
    }

    /**
     * Sets the spectra count to the new value.
     *
     * @param spectraCount spectra count value.
     */
    public void set(Integer spectraCount) {
        this.spectraCount = spectraCount;
    }

    /**
     * @return the end index as Integer.
     */
    public Integer getEndIndex() {
        return endIndex;
    }

    /**
     * @return the start index as Integer.
     */
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     * @return uniqueness flag as String.
     */
    public String getUniqueness() {
        return uniquenessFlag;
    }

    /**
     * toString() function of a ProteinPeptideOutput object.
     *
     * @return ProteinPeptideOutput object values as String.
     */
    @Override
    public String toString() {
        return "Protein Group; " + this.getProteinGroup() + ", Protein ID; " + this.getProteinId() + ", Accession; " + this.getProteinAccession() + ", Sequence; " + this.getPeptideSequence() + ", Uniqueness; " + this.getUniqueness() + ", logP; " + this.getPeptideScore() + ", mass; " + this.getTheoreticalMassToCharge()
                + ", Length; " + this.getSequenceLength() + ", Parts per million ; " + this.getPartsPerMillion() + ", m/z; " + this.getCalculatedMassToCharge() + ", RT; " + this.getRetentionTime() + ", Scan; " + this.getScanNumber() + ", PTM; " + this.getPostTranslationalModification() + ", AScore; " + this.getAScore();
    }
}
