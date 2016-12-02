/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import nl.eriba.mzidentml.identification.objects.general.AminoAcidMassMap;

/**
 * Class containing tools for calculation purposes.
 *
 * @author vnijenhuis
 */
public class CalculationTools {

    /**
     * Can round Double values.
     *
     * @param doubleToRound Double value.
     * @param decimals amount of decimals to round to.
     * @return rounded Double value.
     */
    public Double roundDouble(Double doubleToRound, Integer decimals) {
        BigDecimal bd = new BigDecimal(doubleToRound);
        bd = bd.setScale(decimals, RoundingMode.HALF_UP);
        Double roundedDouble = bd.doubleValue();
        return roundedDouble;
    }

    /**
     * Method to calculate the coverage of the protein sequence by using the start/stop locations of each unique peptide sequence.
     * Duplicate start/stop entries of the same peptide are not present.
     * 
     * @param startIndexList list of start index values.
     * @param endIndexList list of end index values.
     * @param sequenceLength length of the protein sequence.
     * @return protein coverage value as Double.
     */
    public Double calculateProteinCoverage(ArrayList<Integer> startIndexList, ArrayList<Integer> endIndexList, Integer sequenceLength) {
        ArrayList<Integer> indexCoverage = new ArrayList<>();
        Integer currentStartIndex = 0;
        Integer currentEndIndex = 0;
        //Single peptide entry.
        if (startIndexList.size() == 1) {
            Integer coverage = endIndexList.get(0) - startIndexList.get(0);
            indexCoverage.add(coverage);
        } else {
            //Multiple peptide entries.
            for (int i = 0; i < startIndexList.size(); i++) {
                Integer startIndex = startIndexList.get(i);
                Integer endIndex = endIndexList.get(i);
                //First start/end index
                if (currentStartIndex == 0) {
                    currentStartIndex = startIndex;
                    currentEndIndex = endIndex;
                //Other indices.
                } else { 
                    //Filters out any overlapping entries.
                    if (startIndex <= currentEndIndex && endIndex >= currentEndIndex) {
                        currentEndIndex = endIndex;
                    } else {
                        Integer coverage = currentEndIndex - currentStartIndex;
                        indexCoverage.add(coverage);
                        currentStartIndex = startIndex;
                        currentEndIndex = endIndex;
                    }
                }
            }
        }
        //Calculate coverage in percentages.
        Integer totalSequenceCoverage = 0;
        for (Integer coverage: indexCoverage) {
            totalSequenceCoverage += coverage;
        }
        double length = sequenceLength.doubleValue();
        double seqCoverage = totalSequenceCoverage.doubleValue();
        Double proteinCoverage = (seqCoverage / length) * 100;
        proteinCoverage = roundDouble(proteinCoverage, 2);
        return proteinCoverage;
    }

    /**
     * Calculate the mass of a protein amino acid sequence based on the values in the AminoAcidMassMap.
     * 
     * @param proteinSequence protein amino acid sequence as String.
     * @return protein mass as Double.
     */
    public Double calculateProteinMass(final String proteinSequence) {
        double proteinMass = 0.0;
        String[] sequenceArray = proteinSequence.split("(?!^)");
        AminoAcidMassMap aminoAcidMap = new AminoAcidMassMap();
        for (String aminoAcid: sequenceArray) {
            double mass = aminoAcidMap.getAminoAcidMap().get(aminoAcid);
            proteinMass += mass;
        }
        proteinMass = roundDouble(proteinMass, 2);
        return proteinMass;
    }
}
