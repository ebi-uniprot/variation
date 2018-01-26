package uk.ac.ebi.uniprot.variation.hgvs.protein;

import uk.ac.ebi.uniprot.variation.VariationLocation;

public class AbstractProteinHgvs implements ProteinHgvs {
    private final ProteinHgvsType type;
    private boolean predicted;
    private String primaryId;
    private String wildType;
    private String varType;
    private int start;
    private String secondWildType;
    private Integer secondStart;
    private Integer numberOfOccurrences;

    public AbstractProteinHgvs(ProteinHgvsType type) {
        this.type = type;
    }

    @Override
    public ProteinHgvsType getType() {
        return type;
    }

    @Override
    public boolean isPredicted() {
        return predicted;
    }

    @Override
    public String getSequenceId() {
        return primaryId;
    }

    public void setPredicted(boolean predicted) {
        this.predicted = predicted;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    @Override
    public VariationLocation convert2Location() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getWildType() {
      return this.wildType;
    }

    @Override
    public void setWildType(String wildType) {
        this.wildType = wildType;
    }

    @Override
    public String getVarType() {
        return this.varType;
    }

    @Override
    public void setVarType(String varType) {
        this.varType = varType;
    }

    @Override
    public int getStart() {
       return start;
    }
    @Override
    public void setStart(int start) {
        this.start =start;
    }

    
    @Override
    public Integer getSecondStart() {
        return this.secondStart;
    }

    @Override
    public String getSecondWildType() {
        return this.secondWildType;
    }

    @Override
    public boolean hasSecond() {  
        return this.secondWildType !=null;
    }

    @Override
    public void setSecondStart(Integer secondStart) {
       this.secondStart = secondStart;
        
    }

    @Override
    public void setSecondWildType(String secondWildType) {
        this.secondWildType = secondWildType;
    }

    @Override
    public Integer getNumberOfOccurrences() {
        return this.numberOfOccurrences;
    }

    @Override
    public void setNumberOfOccurrences(Integer numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
        
    }

    @Override
    public boolean hasNumberOfOccurrences() {      
        return false;
    }

}
