package uk.ac.ebi.uniprot.variation.hgvs;

import uk.ac.ebi.uniprot.variation.VariantLocation;

public interface ProteinHgvs {
    ProteinHgvsType getType();

    boolean isPredicted();

    String getPrimaryId();

    void setPredicted(boolean predicted);

    void setPrimaryId(String primaryId);

    VariantLocation convert2Location();

    String getWildType();

    void setWildType(String wildType);

    String getVarType();

    void setVarType(String varType);

    int getStart();

    void setStart(int start);

    Integer getSecondStart();

    void setSecondStart(Integer secondStart);

    String getSecondWildType();

    void setSecondWildType(String secondWildType);

    boolean hasSecond();

    Integer getNumberOfOccurrences();

    void setNumberOfOccurrences(Integer numberOfOccurrences);

    boolean hasNumberOfOccurrences();

}
