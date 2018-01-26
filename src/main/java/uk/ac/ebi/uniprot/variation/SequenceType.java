package uk.ac.ebi.uniprot.variation;

public enum SequenceType {
	PROTEIN("P", "Protein"), GENOME("G", "Genome"), RNA("R", "RNA"), CDNA("c", "cDNA"), MDNA("m", "mDNA"), NDNA("n",
			"nDNA");
    private final String id;
    private final String name;
    SequenceType(String id, String name){
        this.id = id;
        this.name= name;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public static SequenceType getType(String val) {
    		for(SequenceType type: values()) {
    			if(type.getId().equalsIgnoreCase(val))
    				return type;
    		}
    		throw new IllegalArgumentException ("SequenceType: " + val + " does not exist.");
    }
}
