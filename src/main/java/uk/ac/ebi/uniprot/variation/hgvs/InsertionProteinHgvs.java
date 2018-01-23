package uk.ac.ebi.uniprot.variation.hgvs;


import uk.ac.ebi.uniprot.variation.LocationType;
import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.impl.VariationLocationImpl;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

import java.util.regex.Matcher;

public class InsertionProteinHgvs extends AbstractProteinHgvs {
  

    public InsertionProteinHgvs() {
        super(ProteinHgvsType.INSERTION);

    }

    @Override
    public VariationLocation convert2Location() {
    
        VariationLocationImpl.VariationLocationBuilder builder = VariationLocationImpl.builder();
        builder.locationType(LocationType.PROTEIN)
                .sequenceId(this.getPrimaryId())
                .wildType(this.getWildType())
                .varType(this.getVarType())
                .start((long)this.getStart())
                .end((long)this.getSecondStart());
        return builder.build();
    }
    @Override
    public boolean hasSecond() {  
        return true;
    }
    
    @Override
    public boolean hasNumberOfOccurrences() {      
        return this.getNumberOfOccurrences() !=null;
    }
    
    public static InsertionProteinHgvs from(String hgvs){
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_INSERT_PATTERN.matcher(hgvs);
        if(!matcher.matches())
            return null;
        InsertionProteinHgvs proteinHgvs = new InsertionProteinHgvs();
        if(matcher.group(1) !=null){
            proteinHgvs.setPredicted(true);
        }
               
        String wildType = matcher.group(2);
        proteinHgvs.setWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(wildType));
        proteinHgvs.setStart(Integer.parseInt(matcher.group(3)));
        
        String secondWildType = matcher.group(5);
        String secondStart = matcher.group(6);
        proteinHgvs.setSecondWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(secondWildType));
        proteinHgvs.setSecondStart(Integer.parseInt(secondStart));
        
        String varType =  matcher.group(8);
        if(varType !=null) {
            if(varType.equals(varType.toUpperCase())){
                proteinHgvs.setVarType(varType);
            }else{
                proteinHgvs.setVarType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(varType));
            }
        }
        String occurrence =  matcher.group(9);
        if(occurrence !=null){
            proteinHgvs.setNumberOfOccurrences(Integer.parseInt(occurrence));
        }
       
        return proteinHgvs;
    }
}
