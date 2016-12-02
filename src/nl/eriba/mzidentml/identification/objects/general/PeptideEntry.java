/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.general;

/**
 * Defines the result to gather modified sequence and PTM values of a peptide sequence.
 *
 * @author vnijenhuis
 */
public class PeptideEntry {

    /**
     * Post-translational modification of the peptide sequence.
     */
    private final String postTranslationalModification;

    /**
     * AScore of the peptide.
     */
    private final String AScore;

    /**
     * Modified peptide sequence.
     */
    private final String modifiedSequence;

    /**
     * Defines the PeptideOutput object.
     *
     * @param postTranslationalModification contains the post-translational modification.
     * @param AScore contains the aScore.
     * @param modifiedSequence contains the modified peptide sequence.
     */
    public PeptideEntry(String postTranslationalModification, String AScore, String modifiedSequence) {
        this.postTranslationalModification = postTranslationalModification;
        this.AScore = AScore;
        this.modifiedSequence = modifiedSequence;
    }

    /**
     * @return the post-translational modification as String.
     */
    public String getPostTranslationalModification() {
        return postTranslationalModification;
    }

    /**
     * @return the AScore as String.
     */
    public String getAScore() {
        return AScore;
    }

    /**
     * @return the modifiedSequence as String.
     */
    public String getModifiedSequence() {
        return modifiedSequence;
    }

    /**
     * toString function of PeptideEntry object.
     *
     * @return PeptideEntry values as String.
     */
    @Override
    public String toString() {
        return "PeptideOutput{Modified sequence: " + getModifiedSequence() + ", aScore: " + getAScore() + ", Post-translational modification: " + getPostTranslationalModification() + "}";
    }
}
