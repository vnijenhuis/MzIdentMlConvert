/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.mzid;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the MzIdIonFragment object.
 *
 * @author vnijenhuis
 */
public class MzIdIonFragment {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Ion type (b-ion, y-ion, immonium ion.
     */
    private final String ionType;

    /**
     * List of indexes.
     */
    private final List<Integer> indices;

    /**
     * List that contains intensities for each fragment.
     */
    private final ArrayList<Double> measureValues;

    /**
     * List that shows if an index passed the specified threshold.
     */
    private final ArrayList<Boolean> passIntensityThreshold;

    /**
     * Creates an MzIdIonType object with corresponding data.
     *
     * @param ionType name of the ion.
     * @param indexList list of indices.
     * @param measureValues measured intensity values.
     * @param passedIntensityThreshold list that shows if an index passed the specified threshold.
     */
    public MzIdIonFragment(final String ionType, final List<Integer> indexList,
            final ArrayList<Double> measureValues, final ArrayList<Boolean> passedIntensityThreshold) {
        this.objectName = "MzIdIonFragment";
        this.ionType = ionType;
        this.indices = indexList;
        this.measureValues = measureValues;
        this.passIntensityThreshold = passedIntensityThreshold;
    }

    /**
     * Returns the charge state of this ion.
     *
     * @return charge state of the ion as Integer.
     */
    public final String getName() {
        return this.ionType;
    }

    /**
     * Returns the indices of each fragment of this ion.
     *
     * @return a List of Integers.
     */
    public final List<Integer> getIndexList() {
        return this.indices;
    }

    /**
     * Returns a list of ion series intensity values.
     *
     * @return an ArrayList with Double values.
     */
    public final ArrayList<Double> getMeasureValues() {
        return this.measureValues;
    }

    /**
     * Returns a list of booleans to determine if a ion intensity passed the threshold.
     *
     * @return an ArrayList with Boolean values.
     */
    public final ArrayList<Boolean> getItensityValues() {
        return this.passIntensityThreshold;
    }

    /**
     * toString function of the MzIdIonFragment object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Name: " + this.getName() + ", index: " + this.getIndexList() + ", Measured m/z: " + this.getMeasureValues() + ", Intensities: " + this.getItensityValues() + "}";
    }
}
