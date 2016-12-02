/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.output;

/**
 * Defines a ProteinOutput object.
 *
 * @author vnijenhuis
 */
public class ProteinOutput {

    /**
     * The protein group number.
     */
    private final Integer proteinGroup;

    /**
     * The protein group id.
     */
    private final String proteinId;

    /**
     * The protein accession id.
     */
    private final String proteinAccession;

    /**
     * The total -10LogP score.
     */
    private final Double totalScore;

    /**
     * The protein coverage in percentages.
     */
    private final Double proteinCoverage;

    /**
     * Total peptide count.
     */
    private final Integer peptideCount;

    /**
     * Unique peptide count.
     */
    private final Integer uniquePeptideCount;

    /**
     * Present post-translational modifications.
     */
    private final String postTranslationalModification;

    /**
     * Average mass of the protein sequence.
     */
    private final Double averageSequenceMass;

    /**
     * Description of the protein.
     */
    private final String proteinDescription;

    /**
     * Defines a ProteinEntry object.
     *
     * @param proteinGroup The protein group number.
     * @param proteinId The protein group id.
     * @param proteinAccession The protein accession id.
     * @param totalScore The total -10LogP score.
     * @param proteinCoverage The protein coverage in percentages.
     * @param peptideCount Total peptide count.
     * @param uniquePeptideCount Unique peptide count.
     * @param postTranslationalModification Present post-translational modifications.
     * @param averageSequenceMass Average mass of the protein sequence.
     * @param proteinDescription Description of the protein.
     */
    public ProteinOutput(Integer proteinGroup, String proteinId, String proteinAccession, Double totalScore, Double proteinCoverage, Integer peptideCount, Integer uniquePeptideCount,
            String postTranslationalModification, Double averageSequenceMass, String proteinDescription) {
        this.proteinGroup = proteinGroup;
        this.proteinId = proteinId;
        this.proteinAccession = proteinAccession;
        this.totalScore = totalScore;
        this.proteinCoverage = proteinCoverage;
        this.peptideCount = peptideCount;
        this.uniquePeptideCount = uniquePeptideCount;
        this.postTranslationalModification = postTranslationalModification;
        this.averageSequenceMass = averageSequenceMass;
        this.proteinDescription = proteinDescription;
    }

    /**
     * Returns the protein group.
     *
     * @return the protein group as String.
     */
    public Integer getProteinGroup() {
        return proteinGroup;
    }

    /**
     * Returns the protein id.
     *
     * @return the protein id as String.
     */
    public String getProteinId() {
        return proteinId;
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
     * Returns the total -10LogP score of the protein.
     *
     * @return the total score as Double.
     */
    public Double getTotalScore() {
        return totalScore;
    }

    /**
     * Returns the percentage of protein coverage.
     *
     * @return the protein coverage as Double.
     */
    public Double getProteinCoverage() {
        return proteinCoverage;
    }

    /**
     * Returns the total peptide count.
     *
     * @return the peptide count as Integer.
     */
    public Integer getPeptideCount() {
        return peptideCount;
    }

    /**
     * Returns the unique peptide count.
     *
     * @return the unique peptide count as Integer.
     */
    public Integer getUniquePeptideCount() {
        return uniquePeptideCount;
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
     * Returns the average sequence mass.
     *
     * @return the average sequence mass as Double.
     */
    public Double getAverageSequenceMass() {
        return averageSequenceMass;
    }

    /**
     * Returns the protein description.
     *
     * @return protein description as String.
     */
    public String getProteinDescription() {
        return proteinDescription;
    }

    /**
     * toString() function of a ProteinOutput object.
     *
     * @return ProteinOutput object values as String.
     */
    @Override
    public String toString() {
        return "ProteinDataItem{Protein group: " + this.getProteinGroup() + ", Protein id: " + this.getProteinId() + ", Protein accession: " + this.getProteinAccession() + ", Total Score: " + this.getTotalScore() + ", Protein coverage: " + this.getProteinCoverage() + ", Peptide count: " + this.getPeptideCount()
                + ", Unique peptide count: " + this.getUniquePeptideCount() + ", Post-translational modification: " + this.getPostTranslationalModification() + ", Average sequence mass: " + this.getAverageSequenceMass() + ", Protein description: " + this.getProteinDescription();
    }
}
