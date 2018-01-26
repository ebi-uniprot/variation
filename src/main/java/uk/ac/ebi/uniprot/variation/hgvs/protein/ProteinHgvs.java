package uk.ac.ebi.uniprot.variation.hgvs.protein;

import uk.ac.ebi.uniprot.variation.VariationLocation;

public interface ProteinHgvs {
    ProteinHgvsType getType();

    boolean isPredicted();

    String getSequenceId();

    void setPredicted(boolean predicted);

    void setPrimaryId(String primaryId);

    VariationLocation convert2Location();

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
