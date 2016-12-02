/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.general;

/**
 * Defines a protein object.
 *
 * @author vnijenhuis
 */
public class ProteinDatabaseSequence {

    /**
     * Amino acid sequence of the protein.
     */
    private final String proteinSequence;

    /**
     * ProteinDatabaseSequence Accession ID.
     */
    private final String proteinAccession;

    /**
     * Average mass of the protein sequence.
     */
    private final Double averageMass;

    /**
     * Length of the sequence.
     */
    private final int sequenceLength;

    /**
     * Creates a protein object with a sequence.
     *
     * @param proteinSequence contains the amino acid protein sequence.
     * @param proteinAccession protein accession id.
     * @param mass sequence mass.
     */
    public ProteinDatabaseSequence(final String proteinSequence, final String proteinAccession, final Double mass) {
        this.proteinSequence = proteinSequence;
        this.proteinAccession = proteinAccession;
        this.averageMass = mass;
        this.sequenceLength = proteinSequence.length();
    }

    /**
     * Returns the amino acid sequence.
     *
     * @return amino acid sequence as String.
     */
    public final String getProteinSequence() {
        return this.proteinSequence;
    }

    /**
     * Returns the protein accession ID.
     *
     * @return protein accession ID as String.
     */
    public final String getProteinAccession() {
        return this.proteinAccession;
    }

    /**
     * Returns the average mass of the protein sequence.
     *
     * @return average mass as Double.
     */
    public final Double getAverageMass() {
        return this.averageMass;
    }

    /**
     * Returns the amino acid sequence length.
     *
     * @return sequence length as Integer.
     */
    public final Integer getSequenceLength() {
        return this.sequenceLength;
    }

    /**
     * To string function.
     *
     * @return protein object as String.
     */
    @Override
    public final String toString() {
        return "ProteinDatabaseEntry{Sequence; " + this.getProteinSequence() + ", Accession; " + this.getProteinAccession() + ", Average mass: " + this.getAverageMass() + "}";
    }
}
