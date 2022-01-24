package uk.ac.ebi.uniprot.variation;

public interface VCFMetaInfo {
    String getId();

    String getDescription();

    String getNumber();

    String getType();

    String getSource();

    String getVersion();
}
