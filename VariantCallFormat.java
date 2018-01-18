package uk.ac.ebi.uniprot.variation;

import java.util.List;
import java.util.Map;

public interface VariantCallFormat {
    String getChromosome();
    int getPosition();
    String getId();
    String getReferenceBase();
    String getAlternativeAlleles();
    Double getQualityScore();
    String getFilter();
    Map<String, String> getInfo();
    String getSampleFormat();
    List<String> getSamples();
}
