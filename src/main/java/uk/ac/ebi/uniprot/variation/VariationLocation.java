package uk.ac.ebi.uniprot.variation;

public interface VariationLocation{
    SequenceType getLocationType();
    Long getStart();
    Long getEnd();
    String getVarType();
    String getWildType();
    String getSequenceId();
    
}
