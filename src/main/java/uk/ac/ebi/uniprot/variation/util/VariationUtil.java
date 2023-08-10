package uk.ac.ebi.uniprot.variation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsProteinDescripton;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;

public class VariationUtil {

    // private static final String DEL2 = "Del";
    private static final String DASH = "-";
    private static final String DEL = "del";
    private static final String EQUALS = "=";
    private static final String EMPTY = "";

    private VariationUtil() {
    }

    public static String convertThreeLetterAminoAcid2OneLetter(String aa) {
        if (aa.equalsIgnoreCase(DEL))
            return DEL;
        if (aa.equals(EQUALS)) {
            return EQUALS;
        }
        if (aa.length() < 3) {
            AminoAcid aminoAcid = AminoAcid.valueOfOneLetterCode(aa);
            return validateAminoAcid(aminoAcid, aa);
        }
        int start = 0;
        int end = 3;
        StringBuilder sb = new StringBuilder();
        while (start < aa.length()) {
            String threeLetter = aa.substring(start, end);
            AminoAcid aminoAcid = AminoAcid.valueOfThreeLetterCode(threeLetter);
            sb.append(validateAminoAcid(aminoAcid, aa));

            start = end;
            if (start >= aa.length()) {
                break;
            }
            end += 3;
            if (end > aa.length()) {
                end = aa.length();

            }
        }
        return sb.toString();
    }

    private static String validateAminoAcid(AminoAcid aa, String original) {
        if (aa == AminoAcid.UNKNOWN) {
            throw new InvalidHgvsException(aa + " is not proper threeLetterAminoAcid it was found in " + original
                    + " when converting 3 letter code to 1 letter code.");
        } else {
            return aa.getOneLetterCode();
        }
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

    public static String formatAAWildType(HgvsProteinDescripton hgvsDescription, boolean threeLett) {

        String wildType = hgvsDescription.getWildType();
        if (threeLett) {
            wildType = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(wildType);
        }
        StringBuilder sb = new StringBuilder(wildType);

        if (null != hgvsDescription.getSecondWildType()) {
            String secWildType = hgvsDescription.getSecondWildType();
            if (threeLett) {
                secWildType = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(secWildType);
            }
            sb.append("_").append(secWildType);
        }
        return sb.toString();
    }

    public static String addRepeat(HgvsProteinDescripton hgvs, boolean bracket) {
        StringBuilder sb = new StringBuilder();
        if (bracket) {
            sb.append("[");
        }
        sb.append(hgvs.getRepeats().get(0).getKey()).append(hgvs.getRepeats().get(0).getValue());
        if (bracket) {
            sb.append("]");
        }
        return sb.toString();
    }

    public static String formatAAVarType(HgvsProteinDescripton hgvs, boolean threeLett) {
        StringBuilder sb = new StringBuilder();

        String varType = hgvs.getVarType();
        if (threeLett) {
            varType = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(varType);
        }

        if (VariantType.REPEAT.equals(hgvs.getVariantType())) {
            return addRepeat(hgvs, true);
        }
        sb.append(varType);
        if (VariantType.EXTENSION.equals(hgvs.getVariantType())) {
            sb.append("ext");
            if (!hgvs.getRepeats().isEmpty()) {
                sb.append(addRepeat(hgvs, false));
            }
            return sb.toString();
        }

        if (hgvs.hasFrameShift()) {
            sb.append("fs");
        }
        if (hgvs.hasTer()) {
            if ("Ter".equals(hgvs.getStop()) && !threeLett) {
                sb.append(VariationUtil.convertThreeLetterAminoAcid2OneLetter(hgvs.getStop()));
            } else {
                sb.append(hgvs.getStop());
            }
        }

        return sb.toString();
    }

}
