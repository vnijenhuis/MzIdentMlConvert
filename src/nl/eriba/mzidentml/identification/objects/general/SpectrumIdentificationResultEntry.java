/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.general;

/**
 * Defines MzIdSpectrumIdentificationResult data output.
 *
 * @author vnijenhuis
 */
public class SpectrumIdentificationResultEntry {

    /**
     * Spectrum index value.
     */
    private final Integer spectrumIndex;

    /**
     * Spectrum scan number.
     */
    private final String scanNumber;

    /**
     * Spectrum scan Id number.
     */
    private final String scanId;

    /**
     * Spectrum retention time.
     */
    private final String retentionTime;

    /**
     * Defines a SpectrumIdentificationResultOutput object.
     *
     * @param spectrumIndex Spectrum index value.
     * @param scanNumber Spectrum scan number.
     * @param scanId Spectrum scan Id number.
     * @param retentionTime Spectrum retention time.
     */
    public SpectrumIdentificationResultEntry(final Integer spectrumIndex, final String scanNumber, final String scanId, final String retentionTime) {
        this.spectrumIndex = spectrumIndex;
        this.scanNumber = scanNumber;
        this.scanId = scanId;
        this.retentionTime = retentionTime;
    }

    /**
     * @return the index as Integer.
     */
    public Integer getSpectrumIndex() {
        return spectrumIndex;
    }

    /**
     * @return the scan as String.
     */
    public String getScanNumber() {
        return scanNumber;
    }

    /**
     * @return the scanId as String.
     */
    public String getScanId() {
        return scanId;
    }

    /**
     * @return the retention time as String.
     */
    public String getRetentionTime() {
        return retentionTime;
    }

    /**
     * toString() function for the given object.
     *
     * @return object values as String.
     */
    @Override
    public String toString() {
        return "SpectrumIdentificationResultOutput{Index: " + this.getSpectrumIndex() + ", scan: " + this.getScanNumber() + ", scanID: " + this.getScanId() + ", retention: " + this.getRetentionTime() + "}";
    }
}
