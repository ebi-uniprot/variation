package uk.ac.ebi.uniprot.variation.hgvs;


import uk.ac.ebi.uniprot.variation.LocationType;
import uk.ac.ebi.uniprot.variation.VariantLocation;
import uk.ac.ebi.uniprot.variation.impl.VariantLocationImpl;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

import java.util.regex.Matcher;

public class DeletionProteinHgvs extends AbstractProteinHgvs {

    public DeletionProteinHgvs() {
        super(ProteinHgvsType.DELETION);
    }

    @Override
    public VariantLocation convert2Location() {
    
        VariantLocationImpl.VariantLocationBuilder builder = VariantLocationImpl.builder();
        builder.locationType(LocationType.PROTEIN)
                .sequenceId(this.getPrimaryId())
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
