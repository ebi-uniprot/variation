package uk.ac.ebi.uniprot.variation.hgvs.protein;


import uk.ac.ebi.uniprot.variation.SequenceType;
import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.impl.VariationLocationImpl;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

import java.util.regex.Matcher;

public class SubstitutionProteinHgvs extends AbstractProteinHgvs{
 //   public final static String HGVS_PROTEIN_SUBSTITUTION="(\\()?([a-zA-Z]+)(\\d+)([a-zA-Z*=]+)(\\))?";   
  //  public final static Pattern HGVS_PATTERN = Pattern.compile(HGVS_PROTEIN_SUBSTITUTION);
    
    public SubstitutionProteinHgvs() {
        super(ProteinHgvsType.SUBSTITUTION);
    }

    @Override
    public boolean hasSecond() {  
        return false;
    }

    @Override
    public VariationLocation convert2Location() {
        VariationLocationImpl.VariationLocationBuilder builder = VariationLocationImpl.builder();
        builder.locationType(SequenceType.PROTEIN)
                .sequenceId(this.getSequenceId())
                .wildType(this.getWildType())
                .varType(this.getVarType())
                .start((long)this.getStart())
                .end((long)this.getStart());
        return builder.build();
    }

  
    
    public static SubstitutionProteinHgvs from(String hgvsRepresent){
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_SUBSTITUTION_PATTERN.matcher(hgvsRepresent);
        if(!matcher.matches()){
            return null;
        }
        SubstitutionProteinHgvs proteinHgvs = new SubstitutionProteinHgvs();
        if(matcher.group(1) !=null){
            proteinHgvs.setPredicted(true);
        }
        String wildType = matcher.group(2);
        proteinHgvs.setWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(wildType));
        proteinHgvs.setStart(Integer.parseInt(matcher.group(3)));
        String varType =  matcher.group(4);
      //  S
        proteinHgvs.setVarType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(varType));
        return proteinHgvs;
    }
    
}
