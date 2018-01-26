package uk.ac.ebi.uniprot.variation.hgvs.protein;


import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;
import uk.ac.ebi.uniprot.variation.impl.VariationLocationImpl;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

import java.util.regex.Matcher;

public class DeletionProteinHgvs extends AbstractProteinHgvs {

    public DeletionProteinHgvs() {
        super(ProteinHgvsType.DELETION);
    }

    @Override
    public VariationLocation convert2Location() {
    
        VariationLocationImpl.VariationLocationBuilder builder = VariationLocationImpl.builder();
        builder.locationType(HgvsType.PROTEIN)
                .sequenceId(this.getSequenceId())
                .wildType(this.getWildType())
                .varType(this.getVarType())
                .start((long)this.getStart());
        if(this.getSecondStart() !=null)
            builder.end((long)this.getSecondStart());
        else
            builder.end((long)this.getStart());
        return builder.build();
    }

    public static DeletionProteinHgvs from(String hgvs) {
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_DELETION_PATTERN.matcher(hgvs);
        if (!matcher.matches())
            return null;
        DeletionProteinHgvs proteinHgvs = new DeletionProteinHgvs();
        if (matcher.group(1) != null) {
            proteinHgvs.setPredicted(true);
        }

        String wildType = matcher.group(2);
        proteinHgvs.setWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(wildType));
        proteinHgvs.setStart(Integer.parseInt(matcher.group(3)));

        String secondWildType = matcher.group(6);
        String secondStart = matcher.group(7);
        if (secondWildType != null) {
            proteinHgvs.setSecondWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(secondWildType));
            proteinHgvs.setSecondStart(Integer.parseInt(secondStart));

        }
        String varType = matcher.group(8);
        proteinHgvs.setVarType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(varType));
        return proteinHgvs;
    }
}
