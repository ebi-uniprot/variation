package uk.ac.ebi.uniprot.variation.util;

import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsProteinDescripton;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;
import uk.ac.ebi.uniprot.variation.hgvs.impl.HgvsProteinDescriptionImpl;

public class VariationUtil {
    
    private static final String DEL2 = "Del";
    private static final String DASH = "-";
    private static final String DEL = "del";

    public static String convertThreeLetterAminoAcid2OneLetter(String aa) {
        if (aa.equals(DEL2))
            return DASH;
        if (aa.length() < 3) {
            return DASH;
        }
        int start = 0;
        int end = 3;
        StringBuilder sb = new StringBuilder();
        while (start < aa.length()) {
            String threeLetter = aa.substring(start, end);
            AminoAcid aminoAcid = AminoAcid.valueOfThreeLetterCode(threeLetter);
            if (aminoAcid == AminoAcid.UNKNOWN) {
                sb.append(DASH);
            } else {
                sb.append(aminoAcid.getOneLetterCode());
            }
            start = end;
            if(start>=aa.length()) {
            		break;
            }
            end += 3;
            if(end> aa.length()) {
            	throw new InvalidHgvsException(aa + " is not proper hreeLetterAminoAcid");
           
            }
        }
        return sb.toString();
    }

    public static String convertOneLetterAminoAcid2ThreeLetters(String aa) {
        if (aa.equals(DASH) || aa.isEmpty()) {
            return DEL;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aa.length(); i++) {
            String val = "" + aa.charAt(i);
            AminoAcid aminoAcid = AminoAcid.valueOfOneLetterCode(val);
            sb.append(aminoAcid.getHumanReadable_Three_LetterCode());
        }
        return sb.toString();
    }

    

	public static String FormatAAWildType(HgvsProteinDescripton hgvsDescription, boolean threeLett) {
		
		String wildType = hgvsDescription.getWildType();
		if(threeLett) {
			wildType = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(wildType);
		}
		StringBuilder sb = new StringBuilder(wildType);

		if(null != hgvsDescription.getSecondWildType()) {
			String secWildType = hgvsDescription.getSecondWildType();
			if(threeLett) {
				secWildType = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(secWildType);
			}
			sb.append("_").append(secWildType);
		}
		return sb.toString();
	}
	
	
	
	public static String addRepeat(HgvsProteinDescripton hgvs,boolean bracket) {
		StringBuilder sb = new StringBuilder();
		if(bracket) {
			sb.append("[");
		}
		sb.append(hgvs.getRepeats().get(0).getKey()).append(hgvs.getRepeats().get(0).getValue());
		if(bracket) {
			sb.append("]");
		}
		return sb.toString();
	}
	

	public static String FormatAAVarType(HgvsProteinDescripton hgvs, boolean threeLett) {
		StringBuilder sb = new StringBuilder();
		
		String varType = hgvs.getVarType();
		if(threeLett) {
			varType = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(varType);
		}
		
		if(VariantType.REPEAT.equals(hgvs.getVariantType())) {
			return addRepeat(hgvs,true);
		}
		sb.append(varType);
		if(VariantType.EXTENSION.equals(hgvs.getVariantType())) {
			sb.append("ext");
			if(!hgvs.getRepeats().isEmpty()) {
				sb.append(addRepeat(hgvs,false));
			}
		 return sb.toString();	
		}
		
		if(hgvs.hasFrameShift()) {
			sb.append("fs");
		}
		if(hgvs.hasTer()) {
			if("Ter".equals(hgvs.getStop()) && !threeLett) {
				sb.append(VariationUtil.convertThreeLetterAminoAcid2OneLetter(hgvs.getStop()));
			} else {
				sb.append(hgvs.getStop());
			}
		}
		
		return sb.toString();
	}
	
	
	
	
	
	
    
    
  }
