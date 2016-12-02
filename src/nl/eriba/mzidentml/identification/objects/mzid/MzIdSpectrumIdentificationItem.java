/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.mzid;

import java.util.ArrayList;

/**
 * Defines a MzIdSpectrumIdentificationItem Object.
 *
 * @author vnijenhuis
 */
public class MzIdSpectrumIdentificationItem {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * SpectrumIdentificationItem id.
     */
    private final String itemId;

    /**
     * Calculated mass-to-charge ratio.
     */
    private final Double calculatedMassToCharge;

    /**
     * Expected mass-to-charge ratio.
     */
    private final Double expectedMassToCharge;

    /**
     * Charge state.
     */
    private final Integer chargeState;

    /**
     * Rank.
     */
    private final Integer rank;

    /**
     * Peptide id reference.
     */
    private final String peptideReference;

    /**
     * List of MzIdIonFragment objects.
     */
    private final ArrayList<MzIdIonFragment> ionFragment;

    /**
     * List of peptide evidence references.
     */
    private final ArrayList<String> peptideEvidenceReference;

    /**
     * List of cvParam objects.
     */
    private final ArrayList<MzIdCvParam> mzIdParamList;

    /**
     * SpectrumIdentificationItem object from the MzIdentML file.
     *
     * @param itemId id of the SpectrumIdentificationItem.
     * @param calculatedMassToCharge calculated mass-to-charge ratio.
     * @param expectedMassToCharge expected mass-to-charge ratio.
     * @param chargeState charge state.
     * @param rank rank of the SpectrumIdentificationItem.
     * @param peptideRef reference to the MzIdentMLPeptide id.
     * @param peptideEvidenceRef list of peptide evidence references.
     * @param ionFragments list of MzIdIonFragment objects.
     * @param mzIdParamList
     */
    public MzIdSpectrumIdentificationItem(final String itemId, final Double calculatedMassToCharge, final Double expectedMassToCharge,
            final Integer chargeState, final Integer rank, final String peptideRef,
            final ArrayList<String> peptideEvidenceRef,
            final ArrayList<MzIdIonFragment> ionFragments, final ArrayList<MzIdCvParam> mzIdParamList) {
        this.objectName = "SpectrumIdentificationItem";
        this.itemId = itemId;
        this.calculatedMassToCharge = calculatedMassToCharge;
        this.expectedMassToCharge = expectedMassToCharge;
        this.chargeState = chargeState;
        this.rank = rank;
        this.peptideReference = peptideRef;
        this.peptideEvidenceReference = peptideEvidenceRef;
        this.ionFragment = ionFragments;
        this.mzIdParamList = mzIdParamList;
    }

    /**
     * Returns the ID of the spectrum identification item.
     *
     * @return the id
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Returns the calculated mass-to-charge (m/z) of the peptide sequence
     *
     * @return the calculatedMassToCharge as Double.
     */
    public Double getCalculatedMassToCharge() {
        return calculatedMassToCharge;
    }

    /**
     * Returns the expected mass-to-charge (m/z) of the peptide sequence.
     *
     * @return the expectedMassToCharge as Double
     */
    public Double getExpectedMassToCharge() {
        return expectedMassToCharge;
    }

    /**
     * Returns the charge state of the ion.
     *
     * @return the chargeState as Integer.
     */
    public Integer getChargeState() {
        return chargeState;
    }

    /**
     * Returns the rank of the spectrum identification item.
     *
     * @return the rank as Integer.
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Returns the reference to the peptide object.
     *
     * @return the peptideReference as String.
     */
    public String getPeptideReference() {
        return peptideReference;
    }

    /**
     * Returns the list of ion fragments.
     *
     * @return list of MzIdIonFragment objects.
     */
    public ArrayList<MzIdIonFragment> getIonFragment() {
        return ionFragment;
    }

    /**
     * Returns the reference of the peptide evidence object.
     *
     * @return list of peptide evidence references as String.
     */
    public ArrayList<String> getPeptideEvidenceReference() {
        return peptideEvidenceReference;
    }

    /**
     * Returns the list of cvParameters.
     *
     * @return list of MzIdCvParam objects.
     */
    public ArrayList<MzIdCvParam> getCvParamList() {
        return this.mzIdParamList;
    }

    /**
     * toString function of the MzIdSpectrumIdentificationItem object.
     *
     * @return MzIdSpectrumIdentificationItem object as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Item id: " + this.getItemId() + ", calculatedMassToCharge: " + this.getCalculatedMassToCharge()
                + ", experimentalMassToCharge: " + this.getExpectedMassToCharge() + ", chargeState: " + this.getChargeState()
                + ", rank: " + this.getRank() + ", peptide_ref: " + this.getPeptideReference() + ", peptideEvidence_ref: "
                + this.getPeptideEvidenceReference() + ", Fragmentation: " + this.getIonFragment().toString()
                + ", cvParam" + this.getCvParamList() + "}";
    }
}
