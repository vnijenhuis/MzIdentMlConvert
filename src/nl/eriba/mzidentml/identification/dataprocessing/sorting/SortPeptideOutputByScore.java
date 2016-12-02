/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.dataprocessing.sorting;

import java.util.Comparator;
import nl.eriba.mzidentml.identification.objects.output.PeptideOutput;

/**
 *
 * @author f103013
 */
public class SortPeptideOutputByScore implements Comparator<PeptideOutput> {

    @Override
    public int compare(PeptideOutput o1, PeptideOutput o2) {
        return o1.getPeptideScore().compareTo(o2.getPeptideScore());
    }

    
}
