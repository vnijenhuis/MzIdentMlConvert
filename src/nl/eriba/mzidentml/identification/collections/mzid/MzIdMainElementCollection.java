/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.mzid;

import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionList;
import uk.ac.ebi.jmzidml.model.mzidml.SequenceCollection;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;

/**
 * Defines the collection of main MzId elements.
 * @author vnijenhuis
 */
public class MzIdMainElementCollection {

    /**
     * List of SpectrumIdentificationResult objects.
     */
    private final SpectrumIdentificationList spectrumIdList;

    /**
     * 
     */
    private final SequenceCollection sequenceCollection;

    /**
     * 
     */
    private final ProteinDetectionList proteinHypothesisCollection;

    /**
     * 
     * @param spectrumIdList
     * @param sequenceCollection
     * @param proteinHypothesisList 
     */
    public MzIdMainElementCollection(SpectrumIdentificationList spectrumIdList, SequenceCollection sequenceCollection, ProteinDetectionList proteinHypothesisList) {
        this.spectrumIdList = spectrumIdList;
        this.sequenceCollection = sequenceCollection;
        this.proteinHypothesisCollection = proteinHypothesisList;
    }

    /**
     * Gets the list of SpectrumIdentificationResult objects.
     * 
     * @return list of SpectrumIdentificationResult objects.
     */
    public SpectrumIdentificationList getSpectrumIdentificationList() {
        return this.spectrumIdList;
    }

    /**
     * Gets the list of ProteinHypothesis objects.
     * 
     * @return 
     */
    public ProteinDetectionList getProteinHypothesisCollection() {
        return this.proteinHypothesisCollection;
    }

    /**
     * 
     * @return 
     */
    public SequenceCollection getSequenceCollection() {
        return this.sequenceCollection;
    }
}
