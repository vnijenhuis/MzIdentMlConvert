/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.general;

/**
 * Defines the ProteinPeptideEntry object.
 *
 * @author vnijenhuis
 */
public class ProteinPeptideEntry {

    /**
     * Series of protein accession ids.
     */
    private final String accessions;

    /**
     * Defines if the peptide sequence is unique or non-unique to a single protein accession.
     */
    private final String uniqueness;

    /**
     * The peptide amino acid sequence.
     */
    private final String sequence;

    /**
     * Defines the ProteinPeptideOutput
     *
     * @param sequence The peptide amino acid sequence.
     * @param accessions Series of protein accession ids.
     * @param unique Defines if the peptide sequence is unique or non-unique to a single protein accession.
     */
    public ProteinPeptideEntry(String sequence, String accessions, String unique) {
        this.sequence = sequence;
        this.accessions = accessions;
        this.uniqueness = unique;
    }

    /**
     * @return the accession ids as String.
     */
    public String getAccessions() {
        return accessions;
    }

    /**
     * @return the uniqueness as String.
     */
    public String getUniqueness() {
        return uniqueness;
    }

    /**
     * @return the peptide amino acid sequence as String.
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * toString() function for the ProteinPeptideEntry object.
     *
     * @return ProteinPeptideEntry values as String.
     */
    @Override
    public String toString() {
        return "EvidenceData{Sequence: " + this.getSequence() + ", all Accesions: " + this.getAccessions() + ", Uniqueness: " + this.getUniqueness() + "}";
    }
}
