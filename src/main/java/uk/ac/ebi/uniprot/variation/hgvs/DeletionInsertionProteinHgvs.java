package uk.ac.ebi.uniprot.variation.hgvs;



import uk.ac.ebi.uniprot.variation.LocationType;
import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.impl.VariationLocationImpl;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

import java.util.regex.Matcher;

public class DeletionInsertionProteinHgvs extends AbstractProteinHgvs {
   
    public DeletionInsertionProteinHgvs() {
        super(ProteinHgvsType.DELETION_INSERTION);
    }
   
    @Override
    public VariationLocation convert2Location() {
      
        VariationLocationImpl.VariationLocationBuilder builder = VariationLocationImpl.builder();
        builder.locationType(LocationType.PROTEIN)
                .sequenceId(this.getPrimaryId())
                .varType(this.getVarType())
                .start((long)this.getStart());
        String wildType = this.getWildType();
        if(this.hasSecond()){
            if((this.getSecondStart().intValue()-this.getStart())>1)
                wildType += "_";
            wildType += this.getSecondWildType();
           
        }
        builder.wildType(wildType);
        if(this.getSecondStart() !=null)
            builder.end((long)this.getSecondStart());
        else
            builder.end((long)this.getStart());
        return builder.build();
    }
    @Override
    public boolean hasSecond() {  
        return this.getSecondWildType() !=null;
    }
    
    public static DeletionInsertionProteinHgvs from(String hgvs){
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_DELETION_INSERT_PATTERN.matcher(hgvs);
        if(!matcher.matches())
            return null;
        DeletionInsertionProteinHgvs proteinHgvs = new DeletionInsertionProteinHgvs();
        if(matcher.group(1) !=null){
            proteinHgvs.setPredicted(true);
        }
        String wildType = matcher.group(2);
        proteinHgvs.setWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(wildType));
        proteinHgvs.setStart(Integer.parseInt(matcher.group(3)));
        
        String secondWildType = matcher.group(6);
        String secondStart = matcher.group(7);
        if(secondWildType !=null){
            proteinHgvs.setSecondWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(secondWildType));
            proteinHgvs.setSecondStart(Integer.parseInt(secondStart));
          
        }
        String varType =  matcher.group(9);
        proteinHgvs.setVarType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(varType));
        return proteinHgvs;
    }

}
