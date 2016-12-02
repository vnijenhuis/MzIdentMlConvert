/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.identification.dataprocessing.spectra;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdCombinedProteinPeptideCollection;
import nl.eriba.mzidentml.identification.collections.mzid.MzIdProteinPeptideCollection;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdCombinedProteinPeptide;
import nl.eriba.mzidentml.identification.objects.mzid.MzIdProteinPeptide;

/**
 *
 * @author vnijenhuis
 */
public class CombineProteinPeptideCollection {
    
        /**
     * Creates a collection of MzIdPeptideEvidence objects.
     *
     * @param proteinPeptideCollection
     * @return collection of MzIdPeptideEvidence objects.
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public final MzIdCombinedProteinPeptideCollection createCombinedProteinPeptideCollection(final MzIdProteinPeptideCollection proteinPeptideCollection) throws InterruptedException, ExecutionException {
        System.out.println("Combining protein-peptide data...");
        proteinPeptideCollection.sortOnEvidenceID();
        MzIdCombinedProteinPeptideCollection combinedProteinPeptideCollection = new MzIdCombinedProteinPeptideCollection();
        MzIdProteinPeptide firstEntry = proteinPeptideCollection.getProteinPeptideList().get(0);
        String targetSequence = firstEntry.getPeptideSequence();
        String accession = firstEntry.getProteinAccession();
        ArrayList<Integer> evidenceIdList = new ArrayList<>();
        String preAminoAcid = firstEntry.getPreAminoAcid();
        String postAminoAcid = firstEntry.getPostAminoAcid();
        Integer startIndex = firstEntry.getStartIndex();
        Integer endIndex = firstEntry.getEndIndex();
        Integer proteinGroup = firstEntry.getProteinGroup();
        String proteinId = firstEntry.getProteinId();
        Double highestProteinScore = firstEntry.getProteinGroupScore();
        Integer collectionSize = proteinPeptideCollection.getProteinPeptideList().size();
        for (int index = 0; index < collectionSize - 1; index++) {
            MzIdProteinPeptide proteinPeptide = proteinPeptideCollection.getProteinPeptideList().get(index);
            if (proteinPeptide.getPeptideSequence().equals(targetSequence) && proteinPeptide.getProteinAccession().equals(accession) &&
                    proteinPeptide.getPreAminoAcid().equals(preAminoAcid) && proteinPeptide.getPostAminoAcid().equals(postAminoAcid) &&
                    proteinPeptide.getStartIndex().equals(startIndex) && proteinPeptide.getEndIndex().equals(endIndex) &&
                    proteinGroup.equals(proteinPeptide.getProteinGroup()) && proteinPeptide.getProteinId().equals(proteinId)) {
                evidenceIdList.add(proteinPeptide.getPeptideEvidenceId());
                if (proteinPeptide.getProteinGroupScore() > highestProteinScore) {
                    highestProteinScore = proteinPeptide.getProteinGroupScore();
                }
            } else {
                MzIdCombinedProteinPeptide combinedProteinPeptide = new MzIdCombinedProteinPeptide(evidenceIdList, targetSequence, accession, proteinGroup, proteinId, Boolean.TRUE, Boolean.FALSE, startIndex, endIndex, preAminoAcid, postAminoAcid, highestProteinScore);
                combinedProteinPeptideCollection.addProteinPeptide(combinedProteinPeptide);
                evidenceIdList.clear();
                targetSequence = proteinPeptide.getPeptideSequence();
                accession = proteinPeptide.getProteinAccession();
                evidenceIdList.add(proteinPeptide.getPeptideEvidenceId());
                preAminoAcid = proteinPeptide.getPreAminoAcid();
                postAminoAcid = proteinPeptide.getPostAminoAcid();
                startIndex = proteinPeptide.getStartIndex();
                endIndex = proteinPeptide.getEndIndex();
                proteinGroup = proteinPeptide.getProteinGroup();
                proteinId = proteinPeptide.getProteinId();
                highestProteinScore = proteinPeptide.getProteinGroupScore();
                if (index == collectionSize) {
                    combinedProteinPeptide = new MzIdCombinedProteinPeptide(evidenceIdList, targetSequence, accession, proteinGroup, proteinId, Boolean.TRUE, Boolean.FALSE, startIndex, endIndex, preAminoAcid, postAminoAcid, highestProteinScore);
                    combinedProteinPeptideCollection.addProteinPeptide(combinedProteinPeptide);
                }
            }
        }
        combinedProteinPeptideCollection.sortOnProteinGroup();
        return combinedProteinPeptideCollection;
    }
}
