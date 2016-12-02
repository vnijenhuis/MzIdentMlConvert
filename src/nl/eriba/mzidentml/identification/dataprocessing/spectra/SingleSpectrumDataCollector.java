/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.dataprocessing.spectra;

import java.util.ArrayList;
import java.util.List;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdPeptideCollection;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdIonFragment;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdModification;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdPeptide;
import nl.eriba.mzidentml.identification.objects.general.PeptideEntry;
import nl.eriba.mzidentml.identification.objects.general.SpectrumIdentificationResultEntry;
import nl.eriba.mzidentml.identification.objects.general.SpectrumIdentificationItemEntry;
import nl.eriba.mzidentml.tools.CalculationTools;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.FragmentArray;
import uk.ac.ebi.jmzidml.model.mzidml.IonType;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;

/**
 * Processes data for a single peptide spectrum.
 *
 * @author vnijenhuis
 */
public class SingleSpectrumDataCollector {

    /**
     * Defines the classes used in SingleSpectrumDataCollector.
     */
    public SingleSpectrumDataCollector() {
        toolSet = new CalculationTools();
    }

    /**
     * Defines the InputTools class.
     */
    public final CalculationTools toolSet;

    /**
     * Gathers the most important data from SpectrumIdentificationResult objects.
     *
     * @param spectrumIdentificationResult contains a SpectrumIdentificationResult object.
     * @return object with most important SpectrumIdentificationResult parameters.
     */
    public final SpectrumIdentificationResultEntry getSpectrumIdentificationResultData(final SpectrumIdentificationResult spectrumIdentificationResult) {
        String spectrumId = spectrumIdentificationResult.getSpectrumID();
        String index = spectrumId.split("=")[1];
        Integer spectrumIndex = Integer.parseInt(index);
        String scanId = spectrumIndex + ":";
        //Get scanId from the first cvParam of the SpectrumResult object.
        List<CvParam> resultParamList = spectrumIdentificationResult.getCvParam();
        String scanParameter = resultParamList.get(0).getValue();
        String retentionTime = resultParamList.get(1).getValue();
        String scan = scanParameter.substring(scanParameter.lastIndexOf("scan=") + 5).replaceAll("\"", "");
        scanId = scanId + scan;
        SpectrumIdentificationResultEntry spectrumResultOutput = new SpectrumIdentificationResultEntry(spectrumIndex, scan, scanId, retentionTime);
        return spectrumResultOutput;
    }

    /**
     * Gathers the most important data from SpectrumIdentificationResult objects.
     *
     * @param spectrumIdentificationResult contains a SpectrumIdentificationResult object.
     * @return SpectrumIdentificationItemEntry containing sequences, mass-to-charge, score and ion fragments.
     */
    public final SpectrumIdentificationItemEntry getSpectrumIdentificationItemData(final SpectrumIdentificationResult spectrumIdentificationResult, final SpectrumIdentificationItem spectrumItem) {
        String psmScore = "";
        CalculationTools calculation = new CalculationTools();
        Double calculatedMassToCharge = spectrumItem.getCalculatedMassToCharge();
        Double experimentalMassToCharge = spectrumItem.getExperimentalMassToCharge();
        Double theoreticalMassToCharge = 0.0;
        String peptideSequence = spectrumItem.getPeptideRef();
        String reversedPeptideSequence = new StringBuilder(peptideSequence).reverse().toString();
        //Gather psmScore from the cvParam list.
        List<CvParam> parameterList = spectrumItem.getCvParam();
        for (CvParam parameter : parameterList) {
            if (parameter.getName().contains("PSM score")) {
                psmScore = parameter.getValue();
            }
            if (parameter.getName().toLowerCase().contains("theoretical mass")) {
                theoreticalMassToCharge = Double.parseDouble(parameter.getValue());
            }
        }
        //Determines the length of the actual peptide sequence.
        Integer sequenceLength;
        if (peptideSequence.contains("_")) {
            sequenceLength = peptideSequence.split("_")[0].length();
        } else {
            sequenceLength = peptideSequence.length();
        }
        calculation.roundDouble(calculatedMassToCharge, 2);
        calculation.roundDouble(experimentalMassToCharge, 2);
        calculation.roundDouble(theoreticalMassToCharge, 2);
        //Create SpectrumIdentificationItemEntry object containing the most important SpectrumIdentificationItem data.
        SpectrumIdentificationItemEntry spectrumItemData = new SpectrumIdentificationItemEntry(peptideSequence, reversedPeptideSequence, psmScore, calculatedMassToCharge, experimentalMassToCharge, theoreticalMassToCharge, sequenceLength);
        return spectrumItemData;
    }

    /**
     * Gathers the most important data from MzIdPeptide objects depending on the given peptide sequence.
     *
     * @param peptideCollection collection of MzIdPeptide objects.
     * @param peptideSequence current peptide sequence.
     * @return PeptideEntry containing post-translational modification, aScore and the modified peptide sequence.
     */
    public final PeptideEntry getPeptideCollectionData(final MzIdPeptideCollection peptideCollection, final String peptideSequence) {
        String postTranslationalModification = "";
        String aScore = "";
        String modifiedPeptideSequence;
        //Remove modification values from the end of the sequence to create a plain peptide amino acid sequence.
        if (peptideSequence.contains("_")) {
            modifiedPeptideSequence = peptideSequence.split("_")[0];
        } else {
            modifiedPeptideSequence = peptideSequence;
        }
        //The modification increases the sequence length through the added mass change value. This integer is used to put modifications at the right index.
        Integer locationShift = 0;
        for (MzIdPeptide peptide : peptideCollection.getPeptides()) {
            if (peptide.getPeptideSequence().equals(modifiedPeptideSequence)) {
                ArrayList<Integer> locations = new ArrayList<>();
                for (MzIdModification modification : peptide.getModifications()) {
                    //Gather modification and aScore data and create the modified sequence.
                    for (int i = 0; i < modification.getNames().size(); i++) {
                        Double monoIsotopicMassDelta = toolSet.roundDouble(modification.getMonoIsotopicMassDelta(), 2);
                        String residue = modification.getResidues().get(i);
                        Integer residueLocation = modification.getLocation();
                        String modificationName = modification.getNames().get(i);
                        if (i == 0) {
                            aScore += residue + 1 + ":" + modificationName;
                            postTranslationalModification += modificationName;
                        } else {
                            aScore += ";" + residue + residueLocation + ":" + modificationName;
                            postTranslationalModification += ";" + modificationName;
                        }
                        //Update residue location with locationShift value.
                        residueLocation = residueLocation + locationShift;
                        //Add modification to the peptide sequence.
                        if (!locations.contains(residueLocation)) {
                            String sequenceSubstringOne = modifiedPeptideSequence.substring(0, residueLocation);
                            String sequenceSubstringTwo = modifiedPeptideSequence.substring(residueLocation);
                            String massDeltaModification = "(" + monoIsotopicMassDelta + ")";
                            modifiedPeptideSequence = sequenceSubstringOne + massDeltaModification + sequenceSubstringTwo;
                            //Add length of modification to locationShift to get the correct peptide sequence index for the next modification.
                            locationShift += massDeltaModification.length();
                            locations.add(residueLocation);
                        }
                    }
                }
            }
        }
        //Add important data to the PeptideEntry object.
        PeptideEntry peptideOutput = new PeptideEntry(postTranslationalModification, aScore, modifiedPeptideSequence);
        return peptideOutput;
    }
}
