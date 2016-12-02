/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.objects.general;

import java.util.HashMap;

/**
 * Defines an amino acid molecular mass HashMap.
 *
 * @author vnijenhuis
 */
public class AminoAcidMassMap {

    /**
     * HashMap for amino acids and their molecular value.
     */
    HashMap<String, Double> aminoAcidMap;

    /**
     * Map of amino acids and their molecular mass.
     */
    public AminoAcidMassMap() {
        this.aminoAcidMap = new HashMap<>();
        aminoAcidMap.put("G", 57.0215);
        aminoAcidMap.put("A", 71.0371);
        aminoAcidMap.put("S", 87.0320);
        aminoAcidMap.put("P", 97.0528);
        aminoAcidMap.put("V", 99.0684);
        aminoAcidMap.put("T", 101.0477);
        aminoAcidMap.put("C", 103.0092);
        aminoAcidMap.put("L", 113.0841);
        aminoAcidMap.put("I", 113.0841);
        aminoAcidMap.put("N", 114.0429);
        aminoAcidMap.put("D", 115.0270);
        aminoAcidMap.put("Q", 128.0586);
        aminoAcidMap.put("K", 128.0950);
        aminoAcidMap.put("E", 129.0426);
        aminoAcidMap.put("M", 131.0405);
        aminoAcidMap.put("H", 137.0589);
        aminoAcidMap.put("F", 147.0684);
        aminoAcidMap.put("R", 156.1011);
        aminoAcidMap.put("Y", 163.0633);
        aminoAcidMap.put("W", 186.0793);
        //Extra amino acids: http://www.genome.jp/kegg/catalog/codes1.html
        aminoAcidMap.put("X", 0.0);         // Unknown amino acids.
        aminoAcidMap.put("O", 255.1583);    // Pyrrolysine
        aminoAcidMap.put("U", 168.9642);    // Selenocysteine
        aminoAcidMap.put("J", 113.0841);    // Isoleucine or Leucine (both have same weight).
        aminoAcidMap.put("Z", 128.5506);    // Q or E: average mass = (129.0426 + 128.0586) / 2 = 128.5506
        aminoAcidMap.put("B", 114.53495);   // N or D: average mass = (114.0429 + 115.0270) / 2 = 114.53495
    }

    /**
     * @return HashMap with String as key and Double as Value.
     */
    public final HashMap<String, Double> getAminoAcidMap() {
        return this.aminoAcidMap;
    }
}
