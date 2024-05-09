package uk.ac.ebi.uniprot.variation.util;

/**
 * Enum representation of amino acids. Nothing fancy, just the one and three
 * letter codes.
 *
 * @author Jules Jacobsen <jacobsen@ebi.ac.uk>
 */
public enum AminoAcid {

    ALANINE("ALA", "A", true, "Ala"), ASPARTIC_ACID("ASX", "B", true, "Asx"), CYSTEINE("CYS", "C", true, "Cys"),
    ASPARTATE("ASP", "D", true, "Asp"), GLUTAMATE("GLU", "E", true, "Glu"), PHENYLALANINE("PHE", "F", true, "Phe"),
    GLYCINE("GLY", "G", true, "Gly"), HISTIDINE("HIS", "H", true, "His"), ISOLEUCINE("ILE", "I", true, "Ile"),
    LYSINE("LYS", "K", true, "Lys"), LEUCINE("LEU", "L", true, "Leu"), METHIONINE("MET", "M", true, "Met"),
    ASPARAGINE("ASN", "N", true, "Asn"), PROLINE("PRO", "P", true, "Pro"), GLUTAMINE("GLN", "Q", true, "Gln"),
    ARGININE("ARG", "R", true, "Arg"), SERINE("SER", "S", true, "Ser"), THREONINE("THR", "T", true, "Thr"),
    VALINE("VAL", "V", true, "Val"), TRYPTOPHAN("TRP", "W", true, "Trp"), TYROSINE("TYR", "Y", true, "Tyr"),
    GLUTAMIC_ACID("GLX", "Z", true, "Glx"),

    // non-standards
    // MSE isn't natural, but for the purposes of X-ray crystallography, it is.
    SELENOMETHIONINE("MSE", "M", true, "Mse"), SELENOCYSTEINE("SEC", "U", false, "Sec"),
    PYRROLYSINE("PYR", "O", false, "Pyr"), TER("TER", "*", false, "Ter"), SILENT("=", "=", false, "="),
    QUESTION("?", "?", false, "?"),
    // for when you just don't know...
    UNKNOWN("UNK", "X", false, "Unk");

    private final String threeLetterCode;
    private final String oneLetterCode;
    private final boolean standard;
    private final String humanReadable3LetterCode;

    private AminoAcid(String threeLetter, String oneLetter, boolean standard, String hr3LetterCode) {
        this.threeLetterCode = threeLetter;
        this.oneLetterCode = oneLetter;
        this.standard = standard;
        this.humanReadable3LetterCode = hr3LetterCode;
    }

    public String getThreeLetterCode() {
        return threeLetterCode;
    }

    public String getOneLetterCode() {
        return oneLetterCode;
    }

    public boolean isStandardAminoAcid() {
        return standard;
    }

    public String getHumanReadable_Three_LetterCode() {
        return humanReadable3LetterCode;
    }

    /**
     * Returns an AminoAcid for a given one or thee letter code or the full name.
     *
     * @param aminoAcidSymbol
     * @return
     */
    public static AminoAcid valueOfIgnoreCase(String aminoAcidSymbol) {
        if (aminoAcidSymbol.length() == 3) {
            return valueOfThreeLetterCode(aminoAcidSymbol);
        } else if (aminoAcidSymbol.length() == 1) {
            return valueOfOneLetterCode(aminoAcidSymbol);
        } else {
            return valueOfName(aminoAcidSymbol);
        }
    }

    /**
     * Returns an AminoAcid for a given amino acid name e.g. 'alanine'.
     *
     * @param aminoAcidSymbol
     * @return
     */
    public static AminoAcid valueOfName(String name) {

        for (AminoAcid aminoAcid : AminoAcid.values()) {
            if (aminoAcid.toString().equalsIgnoreCase(name)) {
                return aminoAcid;
            }
        }
        return UNKNOWN;
    }

    /**
     * Returns an AminoAcid for a given thee letter code e.g. 'ALA'.
     *
     * @param threeLetterCode
     * @return
     */
    public static AminoAcid valueOfThreeLetterCode(String threeLetterCode) {

        for (AminoAcid aminoAcid : AminoAcid.values()) {
            if (aminoAcid.getThreeLetterCode().equalsIgnoreCase(threeLetterCode)) {
                return aminoAcid;
            }
        }
        return UNKNOWN;
    }

    /**
     * Returns an AminoAcid for a given one letter code e.g. 'A'.
     *
     * @param oneLetterCode
     * @return
     */
    public static AminoAcid valueOfOneLetterCode(String oneLetterCode) {

        for (AminoAcid aminoAcid : AminoAcid.values()) {
            if (aminoAcid.getOneLetterCode().equalsIgnoreCase(oneLetterCode)) {
                return aminoAcid;
            }
        }
        return UNKNOWN;
    }
}
