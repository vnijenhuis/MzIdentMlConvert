/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.dataprocessing.spectra;

import nl.eriba.mzidentml.identification.collections.mzid.MzIdMainElementCollection;
import java.io.File;
import java.util.ArrayList;
import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionList;
import uk.ac.ebi.jmzidml.model.mzidml.SequenceCollection;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

/**
 * Unmarshalling class for the mzid format.
 * @author vnijenhuis
 */
public class MzIdUnmarshaller {

    /**
     * Unmarshals the .mzid file and adds the used mzid data to a single collection.
     * @param mzIdFile xml file with the .mzid extension.
     * @return collection of unmarshalled mzid elements and/or classes.
     */
    public final MzIdMainElementCollection unmarshalMzIdFile(final String mzIdFile) {
        File mzIdentMLFile = new File(mzIdFile);
        System.out.println("Reading given file: " + mzIdFile);
        //Unmarshaller that transforms storage data format to a memory format
        MzIdentMLUnmarshaller unmarshaller = new MzIdentMLUnmarshaller(mzIdentMLFile);
        System.out.println("Collecting info from mzid parameter <SpectrumIdentificationList>");
        SpectrumIdentificationList spectrumIdList = unmarshaller.unmarshal(MzIdentMLElement.SpectrumIdentificationList);
        System.out.println("Collecting info from mzid parameter <SequenceCollection>");
        SequenceCollection sequenceCollection = unmarshaller.unmarshal(SequenceCollection.class);
        System.out.println("Collecting info from mzid parameter <ProteinDetectionList>");
        ProteinDetectionList proteinHypothesisList = unmarshaller.unmarshal(MzIdentMLElement.ProteinDetectionList);
        MzIdMainElementCollection unmarshalCollection = new MzIdMainElementCollection(spectrumIdList, sequenceCollection, proteinHypothesisList);
        return unmarshalCollection;
    }
}
