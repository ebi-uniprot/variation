package uk.ac.ebi.uniprot.variation.util;

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
            end += 3;
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

  
}
