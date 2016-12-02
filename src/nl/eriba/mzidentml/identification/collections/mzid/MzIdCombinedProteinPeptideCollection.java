/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.mzid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdCombinedProteinPeptide;

/**
 *
 * @author vnijenhuis
 */
public class MzIdCombinedProteinPeptideCollection {
    
    /**
     * Creates an ArrayList for MzIdProteinPeptide objects.
     */
    private final ArrayList<MzIdCombinedProteinPeptide> proteinPeptideList;

    /**
     * ArrayList of MzIdPeptideEvidence objects.
     */
    public MzIdCombinedProteinPeptideCollection() {
        proteinPeptideList = new ArrayList<>();
    }

    /**
     * Adds a MzIdProteinPeptide object to the ArrayList.
     *
     * @param peptideEvidence MzIdProteinPeptide object.
     */
    public final void addProteinPeptide(final MzIdCombinedProteinPeptide peptideEvidence) {
        proteinPeptideList.add(peptideEvidence);
    }

    /**
     * Removes a MzIdProteinPeptide object from the ArrayList.
     *
     * @param peptideEvidence MzIdProteinPeptide object.
     */
    public final void removeProteinPeptide(final MzIdCombinedProteinPeptide peptideEvidence) {
        proteinPeptideList.remove(peptideEvidence);
    }

    /**
     * Returns an ArrayList of MzIdProteinPeptide objects.
     *
     * @return ArrayList of MzIdProteinPeptide objects.
     */
    public final ArrayList<MzIdCombinedProteinPeptide> getProteinPeptideList() {
        return proteinPeptideList;
    }
    
    /**
     * 
     * @return 
     */
    static Comparator<MzIdCombinedProteinPeptide> getProteinGroupSorter() {
        return new Comparator<MzIdCombinedProteinPeptide>() {
            @Override
            public int compare(MzIdCombinedProteinPeptide o1, MzIdCombinedProteinPeptide o2) {
                return o1.getProteinGroup().compareTo(o2.getProteinGroup());
            }
        };
    }

    /**
     * 
     */
    public final void sortOnProteinGroup() {
        Collections.sort(this.proteinPeptideList, getProteinGroupSorter());
    }
}

