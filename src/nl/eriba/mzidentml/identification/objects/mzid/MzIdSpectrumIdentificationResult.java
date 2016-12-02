/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.mzid;

import java.util.ArrayList;

/**
 * Defines the MzIdSpectrumIdentificationResult object.
 *
 * @author vnijenhuis
 */
public class MzIdSpectrumIdentificationResult {

    /**
     * Name of the object in the MzIdentML file.
     */
    private final String objectName;

    /**
     * Reference to the spectra data .mgf file.
     */
    private final String spectraDataReference;

    /**
     * Id of the mass spectrum.
     */
    private final String spectrumId;

    /**
     * Id of the SpectrumIdentificationResult object.
     */
    private final String resultId;

    /**
     * List of SpectrumIdentificationItem objects.
     */
    private final ArrayList<MzIdSpectrumIdentificationItem> spectrumIdItem;

    /**
     * List of cv parameters containing scan id and other spectra related information.
     */
    private final ArrayList<MzIdCvParam> cvParamList;

    /**
     * SpectrumIdentificationResult object from the mzIdentML file.
     *
     * @param resultId id of the SpectrumIdentificationResult object.
     * @param spectrumId id of the mass spectrum.
     * @param spectraDataReference reference to the spectra data .mgf file.
     * @param spectrumIdItem a SpectrumIdentificationItem object.
     * @param cvParamList list of cv parameters containing scan id and other spectra related information.
     */
    public MzIdSpectrumIdentificationResult(final String resultId, final String spectrumId, final String spectraDataReference,
            final ArrayList<MzIdSpectrumIdentificationItem> spectrumIdItem, ArrayList<MzIdCvParam> cvParamList) {
        this.objectName = "SpectrumIdentificationResult";
        this.spectraDataReference = spectraDataReference;
        this.spectrumId = spectrumId;
        this.resultId = resultId;
        this.spectrumIdItem = spectrumIdItem;
        this.cvParamList = cvParamList;
    }

    /**
     * Name of the object.
     *
     * @return name of the object as String.
     */
    public String getObjectName() {
        return this.objectName;
    }

    /**
     * Returns the spectrum data reference.
     *
     * @return spectra data reference as String.
     */
    public final String getSpectraDataReference() {
        return this.spectraDataReference;
    }

    /**
     * Returns the spectrum id of the SpectrumResult.
     *
     * @return spectrum id as String.
     */
    public final String getSpectrumId() {
        return this.spectrumId;
    }

    /**
     * Returns the id of the SpectrumResult.
     *
     * @return spectra data reference as String.
     */
    public final String getResultId() {
        return this.resultId;
    }

    /**
     * Returns the MzIdSpectrumIdentificationItem objects of this SpectrumResult object.
     *
     * @return list of MzIdSpectrumIdentificationItem objects.
     */
    public final ArrayList<MzIdSpectrumIdentificationItem> getSpectrumIdentificationItemList() {
        return this.spectrumIdItem;
    }

    /**
     * Returns the MzIdCvParam objects of this SpectrumResult object.
     *
     * @return list of MzIdCvParam objects.
     */
    public final ArrayList<MzIdCvParam> getCvParamList() {
        return this.cvParamList;
    }

    /**
     * toString function of the MzIdSpectrumIdentificationResult object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Result id; " + this.getResultId() + ", Spectrum id; " + this.getSpectrumId() + ", Spectra data reference; " + this.getSpectraDataReference() + ", SpectrumIdentificationItem; "
                + this.getSpectrumIdentificationItemList() + ", cv Parameter list; " + this.getCvParamList() + "}";
    }
}
