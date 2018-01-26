package uk.ac.ebi.uniprot.variation.hgvs.protein;


import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

import java.util.regex.Matcher;

public class ExtensionProteinHgvs extends AbstractProteinHgvs {
 
    public ExtensionProteinHgvs() {
        super(ProteinHgvsType.EXTENSION);
    }

    @Override
    public VariationLocation convert2Location() {
        throw new UnsupportedOperationException("");
    }
    
    public static ExtensionProteinHgvs from(String hgvs){
        ExtensionProteinHgvs proteinHgvs= fromMet(hgvs);
        if(proteinHgvs ==null){
            proteinHgvs =fromTer(hgvs);
        }
        return proteinHgvs;
       
    }
    
    private static  ExtensionProteinHgvs fromMet(String hgvs){
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_EXTENSION_MET_PATTERN.matcher(hgvs);
        if(!matcher.matches())
            return null;
        ExtensionProteinHgvs proteinHgvs = new ExtensionProteinHgvs();
        if(matcher.group(1) !=null){
            proteinHgvs.setPredicted(true);
        }
        String wildType = matcher.group(2);
        proteinHgvs.setWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(wildType));
        proteinHgvs.setStart(Integer.parseInt(matcher.group(3)));
        
        String varType =  matcher.group(4);
        if(varType !=null)
            proteinHgvs.setVarType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(varType));
        else
            proteinHgvs.setVarType("?");
     
        String secondStart = matcher.group(7);
        if(secondStart !=null){
            proteinHgvs.setSecondStart(Integer.parseInt(secondStart));     
        }
        return proteinHgvs; 
    }
    
    private static ExtensionProteinHgvs fromTer(String hgvs){
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_EXTENSION_TER_PATTERN.matcher(hgvs);
        if(!matcher.matches())
            return null;
        ExtensionProteinHgvs proteinHgvs = new ExtensionProteinHgvs();
        if(matcher.group(1) !=null){
            proteinHgvs.setPredicted(true);
        }
  
        String wildType = matcher.group(2);
        if(wildType.equals("*"))
            proteinHgvs.setWildType("*");
        else
            proteinHgvs.setWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(wildType));
        proteinHgvs.setStart(Integer.parseInt(matcher.group(3)));
        
        String varType =  matcher.group(4);
        proteinHgvs.setVarType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(varType));
 
     
        String secondWildType = matcher.group(6);
        if(secondWildType !=null){
            proteinHgvs.setSecondWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(secondWildType));
        }else{
            proteinHgvs.setSecondWildType("*");
        } 
        String secondStart = matcher.group(8);
        if(secondStart !=null){
            if(secondStart.equals("?")){
                proteinHgvs.setSecondStart(-1);  // unknown length
            }else{
                proteinHgvs.setSecondStart(Integer.parseInt(secondStart));
            }
        }  
        return proteinHgvs; 
    }
}

