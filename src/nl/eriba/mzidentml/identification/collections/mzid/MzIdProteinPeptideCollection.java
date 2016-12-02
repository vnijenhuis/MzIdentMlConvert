/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.collections.mzid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdProteinPeptide;

/**
 *
 * @author f103013
 */
public class MzIdProteinPeptideCollection {
    
    /**
     * Creates an ArrayList for MzIdProteinPeptide objects.
     */
    private final ArrayList<MzIdProteinPeptide> proteinPeptideList;

    /**
     * ArrayList of MzIdPeptideEvidence objects.
     */
    public MzIdProteinPeptideCollection() {
        proteinPeptideList = new ArrayList<>();
    }

    /**
     * Adds a MzIdProteinPeptide object to the ArrayList.
     *
     * @param peptideEvidence MzIdProteinPeptide object.
     */
    public final void addProteinPeptide(final MzIdProteinPeptide peptideEvidence) {
        proteinPeptideList.add(peptideEvidence);
    }

    /**
     * Removes a MzIdProteinPeptide object from the ArrayList.
     *
     * @param peptideEvidence MzIdProteinPeptide object.
     */
    public final void removeProteinPeptide(final MzIdProteinPeptide peptideEvidence) {
        proteinPeptideList.remove(peptideEvidence);
    }

    /**
     * Returns an ArrayList of MzIdProteinPeptide objects.
     *
     * @return ArrayList of MzIdProteinPeptide objects.
     */
    public final ArrayList<MzIdProteinPeptide> getProteinPeptideList() {
        return proteinPeptideList;
    }
    
        
    /**
     * Compares the protein accession of MzIdProteinPeptide objects.
     * 
     * @return Integer based on the protein accession;
     */
    static Comparator<MzIdProteinPeptide> getProteinAccessionComparator() {
        return new Comparator<MzIdProteinPeptide>() {
            @Override
            public int compare(MzIdProteinPeptide o1, MzIdProteinPeptide o2) {
                return o1.getProteinAccession().compareTo(o1.getProteinAccession());
            }
        };
    }

    /**
     * 
     */
    public final void sortOnSingleProteinAccession() {
        Collections.sort(this.proteinPeptideList, getProteinAccessionComparator());
    }
    
    /**
     * Compares the peptide sequence of MzIdProteinPeptide objects.
     * 
     * @return Integer based on the peptide sequence.
     */
    static Comparator<MzIdProteinPeptide> getPeptideSequenceComparator() {
        return new Comparator<MzIdProteinPeptide>() {
            @Override
            public int compare(MzIdProteinPeptide o1, MzIdProteinPeptide o2) {
                return o1.getPeptideSequence().compareTo(o2.getPeptideSequence());
            }
        };
    }

    /**
     * Sorts the collection based on the peptide sequence.
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.proteinPeptideList, getPeptideSequenceComparator());
    }
      
    /**
     * Compares the PeptideEvidence id of MzIdProteinPeptide objects.
     * 
     * @return Integer based on the PeptideEvidence id.
     */
    static Comparator<MzIdProteinPeptide> getPeptideEvidenceIDSorter() {
        return new Comparator<MzIdProteinPeptide>() {
            @Override
            public int compare(MzIdProteinPeptide o1, MzIdProteinPeptide o2) {
                return o1.getPeptideEvidenceId().compareTo(o2.getPeptideEvidenceId());
            }
        };
    }

    /**
     * Sorts the collection based on the PeptideEvidence id.
     */
    public final void sortOnEvidenceID() {
        Collections.sort(this.proteinPeptideList, getPeptideEvidenceIDSorter());
    }
}
