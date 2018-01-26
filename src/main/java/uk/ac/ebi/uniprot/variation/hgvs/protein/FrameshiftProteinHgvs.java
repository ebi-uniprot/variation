package uk.ac.ebi.uniprot.variation.hgvs.protein;


import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

import java.util.regex.Matcher;

/**
 * Format: “prefix”“amino_acid”position”new_amino_acid”“fs”“Ter”“position_termination_site”, e.g. p.(Arg123LysfsTer34)

“prefix” = reference sequence used = p.
“amino_acid” = first amino acid changed = Arg
“position” = position = 123
“new_amino_acid” = new amino acid = Lys
“fs” = type of change is a frame shift = fs
“Ter” = termination codon = Ter / *
“position_termination_site” = position new termination site = 34

 * @author jieluo
 * @date   7 Sep 2017
 * @time   16:27:29
 *
 */
public class FrameshiftProteinHgvs extends AbstractProteinHgvs {

    public FrameshiftProteinHgvs() {
        super(ProteinHgvsType.FRAMESHIFT);
    }

    @Override
    public VariationLocation convert2Location() {
        throw new UnsupportedOperationException("");
    }

    public static FrameshiftProteinHgvs from(String hgvs){
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_FRAMESHIFT_PATTERN.matcher(hgvs);
        if(!matcher.matches())
            return null;
        FrameshiftProteinHgvs proteinHgvs = new FrameshiftProteinHgvs();
        if(matcher.group(1) !=null){
            proteinHgvs.setPredicted(true);
        }

        String wildType = matcher.group(2);
        proteinHgvs.setWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(wildType));
        proteinHgvs.setStart(Integer.parseInt(matcher.group(3)));
        
        String secondStart = matcher.group(8);
        if(secondStart !=null){
            proteinHgvs.setSecondStart(Integer.parseInt(secondStart));
          
        }
        String varType =  matcher.group(4);
        if(varType !=null)
            proteinHgvs.setVarType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(varType));
        else{
            proteinHgvs.setVarType("*");
        }
        return proteinHgvs;
    }


}
