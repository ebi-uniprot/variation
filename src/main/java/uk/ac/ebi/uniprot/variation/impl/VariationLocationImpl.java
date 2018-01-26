package uk.ac.ebi.uniprot.variation.impl;

import uk.ac.ebi.uniprot.variation.SequenceType;
import uk.ac.ebi.uniprot.variation.VariationLocation;

import lombok.Builder;
import lombok.Data;

@Builder(builderClassName = "VariationLocationBuilder")
@Data
public class VariationLocationImpl implements VariationLocation {
    private final SequenceType locationType;
    private final Long start;
    private final Long end;
    private final String varType;
    private final String wildType;
    private final String sequenceId;   
}
