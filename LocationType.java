package uk.ac.ebi.uniprot.variation;

public enum LocationType {
    PROTEIN("P", "Protein"),
    GENOME("G", "Genome"),
    RNA("R", "RNA"),
    CDNA("c", "cDNA");
    private final String id;
    private final String name;
    LocationType(String id, String name){
        this.id = id;
        this.name= name;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    
}
