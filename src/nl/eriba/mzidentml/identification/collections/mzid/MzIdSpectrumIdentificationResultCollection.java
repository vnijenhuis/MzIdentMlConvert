/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module *
 */
package nl.eriba.mzidentml.identification.collections.mzid;

import java.util.ArrayList;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdSpectrumIdentificationResult;

/**
 * Defines a MzIdSpectrumIdentificationResult object collection.
 *
 * @author vnijenhuis
 */
public class MzIdSpectrumIdentificationResultCollection {

    /**
     * Creates an ArrayList for SpectrumIdentificationResult objects.
     */
    private final ArrayList<MzIdSpectrumIdentificationResult> peptideEvidenceList;

    /**
     * ArrayList of SpectrumIdentificationResult objects.
     */
    public MzIdSpectrumIdentificationResultCollection() {
        peptideEvidenceList = new ArrayList<>();
    }

    /**
     * Adds a SpectrumIdentificationResult object to the ArrayList.
     *
     * @param spectrumIdentificationItem SpectrumIdentificationResult object.
     */
    public final void addSpectrumIdentificationResult(final MzIdSpectrumIdentificationResult spectrumIdentificationItem) {
        peptideEvidenceList.add(spectrumIdentificationItem);
    }

    /**
     * Removes a SpectrumIdentificationResult object from the ArrayList.
     *
     * @param spectrumIdentificationItem SpectrumIdentificationResult object.
     */
    public final void removeSpectrumIdentificationResult(final MzIdSpectrumIdentificationResult spectrumIdentificationItem) {
        peptideEvidenceList.remove(spectrumIdentificationItem);
    }

    /**
     * Returns an ArrayList of SpectrumIdentificationResult objects.
     *
     * @return ArrayList of SpectrumIdentificationResult objects.
     */
    public final ArrayList<MzIdSpectrumIdentificationResult> getSpectrumIdentificationItemList() {
        return peptideEvidenceList;
    }
}
