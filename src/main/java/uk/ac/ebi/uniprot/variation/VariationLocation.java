package uk.ac.ebi.uniprot.variation;

import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;

public interface VariationLocation{
    HgvsType getLocationType();
    Long getStart();
    Long getEnd();
    String getVarType();
    String getWildType();
    String getSequenceId();
    
}
