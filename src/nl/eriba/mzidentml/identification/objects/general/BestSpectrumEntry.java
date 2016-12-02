/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.general;

/**
 * Object to define the best spectrum score per peptide sequence.
 *
 * @author vnijenhuis
 */
public class BestSpectrumEntry {

    /**
     * Peptide sequence.
     */
    private final String sequence;

    /**
     * Peptide sequence score.
     */
    private Double score;

    /**
     * Flag used to see if the highest scoring peptide has been found.
     */
    private Boolean flag;

    /**
     * Defines a SpectrumSequenceScoreFlag object.
     *
     * @param sequence peptide sequence.
     * @param score peptide sequence score.
     * @param flag peptide flag.
     */
    public BestSpectrumEntry(final String sequence, final Double score, final Boolean flag) {
        this.sequence = sequence;
        this.score = score;
        this.flag = flag;
    }

    /**
     * Returns the peptide sequence.
     *
     * @return peptide sequence as String.
     */
    public final String getSequence() {
        return this.sequence;
    }

    /**
     * Returns the peptide sequence score.
     *
     * @return peptide score as Double.
     */
    public final Double getScore() {
        return this.score;
    }

    /**
     * Returns the peptide flag.
     *
     * @return peptide flag as Boolean.
     */
    public final Boolean getFlag() {
        return this.flag;
    }

    /**
     * Sets the flag to a new value.
     *
     * @param newFlag Boolean.
     */
    public final void setFlag(Boolean newFlag) {
        this.flag = newFlag;
    }

    /**
     * Sets the score to a new value.
     *
     * @param psmScore Double.
     */
    public void setScore(Double psmScore) {
        this.score = psmScore;
    }

    /**
     * toString() function of a BestSpectrumEntry object.
     *
     * @return BestSpectrumEntry object values as String.
     */
    @Override
    public String toString() {
        return "BestSpectrumScore{Peptide sequence: " + this.getSequence() + ", Spectrum match score: " + this.getScore() + ", Flag: " + this.getFlag() + "}";
    }
}
