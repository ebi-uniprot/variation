package uk.ac.ebi.uniprot.variation.hgvs;


import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

import java.util.regex.Matcher;

public class RepeatProteinHgvs extends AbstractProteinHgvs {
   
    public RepeatProteinHgvs() {
        super(ProteinHgvsType.REPEAT);
    }
    @Override
    public boolean hasSecond() {  
        return this.getSecondWildType() !=null;
    }
    
    @Override
    public boolean hasNumberOfOccurrences() {      
        return this.getNumberOfOccurrences() !=null;
    }
    
  
    public static RepeatProteinHgvs from(String hgvs){
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_REPEAT_PATTERN.matcher(hgvs);
        if(!matcher.matches())
            return null;
        RepeatProteinHgvs proteinHgvs = new RepeatProteinHgvs();
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
        String occurrence =  matcher.group(9);
        if(occurrence !=null){
            proteinHgvs.setNumberOfOccurrences(Integer.parseInt(occurrence));
        }
       
        return proteinHgvs;
    }
    
    
    @Override
    public VariationLocation convert2Location() {
        throw new UnsupportedOperationException("");
    }

}
