package uk.ac.ebi.uniprot.variation.impl;

import uk.ac.ebi.uniprot.variation.LocationType;
import uk.ac.ebi.uniprot.variation.VariantLocation;

import lombok.Builder;
import lombok.Data;

@Builder(builderClassName = "VariantLocationBuilder")
@Data
public class VariantLocationImpl implements VariantLocation {
    private final LocationType locationType;
    private final Long start;
    private final Long end;
    private final String varType;
    private final String wildType;
    private final String sequenceId;   
}
