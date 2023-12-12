package uk.ac.ebi.uniprot.variation.hgvs.impl;

//import lombok.Data;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;

//@Data
public class HgvsImpl implements Hgvs {
    private final HgvsType type;
    private final String sequenceId;
    private final HgvsDescription description;

    public HgvsImpl(HgvsType type, String sequenceId, HgvsDescription description) {
        this.type = type;
        this.sequenceId = sequenceId;
        this.description = description;
    }

    @Override
    public String toString() {
        if((sequenceId ==null) || sequenceId.isBlank()) {
            return  type.getId() + "." + description.getValue();
        }else
        return sequenceId + ":" + type.getId() + "." + description.getValue();
    }

    @Override
    public String getValue() {
        return toString();
    }

    @Override
    public HgvsType getType() {
        return type;
    }

    @Override
    public String getSequenceId() {
        return sequenceId;
    }

    @Override
    public HgvsDescription getDescription() {
        return description;
    }
}
