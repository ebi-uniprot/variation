package uk.ac.ebi.uniprot.variation.hgvs;

public enum VariantType {
	SUBSTITUTION("substitution"),
    DELETION("deletion"),
    DUPLICATION("duplication"),
    INSERTION("insertion"),
    DELETION_INSERTION("deletion insertion"),
    FRAMESHIFT("frameshift"),
    REPEAT("repeat"),
    EXTENSION("extension"),
    INVERSION("inversion"),
    INSERTION_INVERSION("insertion inversion"),
    CONVERSION("conversion"),
    MISSENSE("missense"),
    NONSENSE("nonsense"),
    STOPLOST("stop lost"),
    STOPGAINED("stop gained"),
    SILENT("silent"),
    UNKNOWN("unknown");
	
	private final String consequenceName;
	
	VariantType(String name) {
		this.consequenceName = name;
	}
	
	public String getConsequenceType() {
		return consequenceName;
	}
	
	public static VariantType getVariantType(String variantType) {
		for (VariantType v : VariantType.values()) {
			if(variantType.equalsIgnoreCase(v.getConsequenceType()))
			  return v;
		}
		return UNKNOWN;
	}
	
}
