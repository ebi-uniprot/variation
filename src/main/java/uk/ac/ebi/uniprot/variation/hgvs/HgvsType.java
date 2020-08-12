package uk.ac.ebi.uniprot.variation.hgvs;

public enum HgvsType {
	PROTEIN("p", "Protein"), GENOME("g", "Genome"), RNA("r", "RNA"), CDNA("c", "cDNA"), MDNA("m", "mDNA"), NDNA("n",
			"nDNA"),CDS("cds", "CDS"),UNKNOWN("unknown", "UNKNOWN");
    private final String id;
    private final String name;
    HgvsType(String id, String name){
        this.id = id;
        this.name= name;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public static HgvsType getType(String val) {
    		for(HgvsType type: values()) {
    			if(type.getId().equalsIgnoreCase(val))
    				return type;
    		}
    		throw new IllegalArgumentException ("SequenceType: " + val + " does not exist.");
    }
}
