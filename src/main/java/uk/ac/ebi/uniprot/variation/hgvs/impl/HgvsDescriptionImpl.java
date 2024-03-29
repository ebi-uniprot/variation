package uk.ac.ebi.uniprot.variation.hgvs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;

public class HgvsDescriptionImpl implements HgvsDescription {
    private final boolean predicted;
    private final String wildType;
    private final String varType;
    private final Long start;
    private final Long startCross;
    private final Long end;
    private final Long endCross;
    private final String secondWildType;

    private final VariantType variantType;
    protected final String value;
    private final boolean parsed;
    private final String conversionSeqId;
    private final List<Map.Entry<String, Integer>> repeats;

    public static Builder<?> builder() {
        return new Builder<>();
    }

    protected HgvsDescriptionImpl(Builder<?> builder) {
        this.predicted = builder.predicted;
        this.wildType = builder.wildType;
        this.varType = builder.varType;
        this.start = builder.start;
        this.startCross = builder.startCross;
        this.end = builder.end;
        this.endCross = builder.endCross;
        this.secondWildType = builder.secondWildType;
        this.variantType = builder.variantType;
        this.value = builder.value;
        this.parsed = builder.parsed;
        this.conversionSeqId = builder.conversionSeqId;
        this.repeats = new ArrayList<>();
        this.repeats.addAll(builder.repeats);
    }

    public static class Builder<T extends Builder<T>> {
        private boolean predicted;
        private String wildType;
        private String varType;
        private Long start;
        private Long startCross;
        private Long end;
        private Long endCross;
        private String secondWildType;
        private VariantType variantType;
        private String value;
        private boolean parsed;
        private String conversionSeqId;
        private List<Map.Entry<String, Integer>> repeats = new ArrayList<>();

        public HgvsDescription build() {
            return new HgvsDescriptionImpl(this);
        }

        public Builder<T> predicted(boolean predicted) {
            this.predicted = predicted;
            return this;
        }

        public Builder<T> wildType(String wildType) {
            this.wildType = wildType;
            return this;
        }

        public Builder<T> varType(String varType) {
            this.varType = varType;
            return this;
        }

        public Builder<T> start(Long start) {
            this.start = start;
            return this;
        }

        public Builder<T> startCross(Long startCross) {
            this.startCross = startCross;
            return this;
        }

        public Builder<T> end(Long end) {
            this.end = end;
            return this;
        }

        public Builder<T> endCross(Long endCross) {
            this.endCross = endCross;
            return this;
        }

        public Builder<T> secondWildType(String secondWildType) {
            this.secondWildType = secondWildType;
            return this;
        }

        public Builder<T> variantType(VariantType variantType) {
            this.variantType = variantType;
            return this;
        }

        public Builder<T> value(String value) {
            this.value = value;
            return this;
        }

        public Builder<T> parsed(boolean parsed) {
            this.parsed = parsed;
            return this;
        }

        public Builder<T> conversionSeqId(String conversionSeqId) {
            this.conversionSeqId = conversionSeqId;
            return this;
        }

        public Builder<T> repeat(Map.Entry<String, Integer> repeat) {
            this.repeats.add(repeat);
            return this;
        }

    }

    @Override
    public boolean isPredicted() {
        return predicted;
    }

    @Override
    public String getWildType() {
        return wildType;
    }

    @Override
    public String getVarType() {
        return varType;
    }

    @Override
    public Long getStart() {
        return start;
    }

    @Override
    public Long getStartCross() {
        return startCross;
    }

    @Override
    public Long getEnd() {
        return end;
    }

    @Override
    public Long getEndCross() {
        return endCross;
    }

    @Override
    public String getConversionSeqId() {
        return conversionSeqId;
    }

    @Override
    public String getSecondWildType() {
        return secondWildType;
    }

    @Override
    public VariantType getVariantType() {
        return variantType;
    }

    @Override
    public String getValue() {
        if (null == value || value.isEmpty()) {
            return getDisplayValue();
        }
        return value;
    }

    @Override
    public boolean isParsed() {
        return parsed;
    }

    @Override
    public List<Entry<String, Integer>> getRepeats() {
        return repeats;
    }

    private String getDisplayValue() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.start);
        if (this.startCross != null)
            sb.append(PLUS).append(this.startCross);
        if (!Objects.equals(this.start, this.end))
            sb.append(UNDERSCORE).append(this.end);
        if (this.endCross != null)
            sb.append(PLUS).append(this.endCross);
        String disVarType = this.varType;
        VariantType variantType = this.getVariantType();
        switch (variantType) {
        case INSERTION:
            sb.append(INSERTION).append(disVarType);
            break;
        case DELETION:
            sb.append(DELETION);
            break;
        case SUBSTITUTION:
            sb.append(this.wildType).append(GREATER_THAN).append(disVarType);
            break;
        case DUPLICATION:
            sb.append(DUPLICATION);
            break;
        case DELETION_INSERTION:
            sb.append(DELETION_INSERTION).append(disVarType);
            break;
        case INVERSION:
            sb.append(INVERSION);
            break;
        default:
            if (this.wildType != null && disVarType != null)
                sb.append(this.wildType).append(GREATER_THAN).append(disVarType);
            break;
        }
        return sb.toString();

    }

}
