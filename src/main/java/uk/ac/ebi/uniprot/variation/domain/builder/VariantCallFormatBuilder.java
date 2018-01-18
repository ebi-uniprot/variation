package uk.ac.ebi.uniprot.variation.domain.builder;

import uk.ac.ebi.uniprot.variation.VariantCallFormat;
import uk.ac.ebi.uniprot.variation.impl.VariantCallFormatImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class VariantCallFormatBuilder {
    private static final String TRUE = "TRUE";
    private String chromosome;
    private int position;
    private String id;
    private String referenceBase;
    private String alternativeAlleles;
    private Double qualityScore;
    private String filter;
    private Map<String, String> info;
    private String sampleFormat;
    private List<String> samples;

    public static VariantCallFormatBuilder newInstance(){
        return new VariantCallFormatBuilder();
    }
//    public VariantCallFormat build() {
//        return new VariantCallFormatImpl(chromosome, position, id,
//                referenceBase, alternativeAlleles, qualityScore,
//                filter, info, sampleFormat,  samples);
//    }

    public VariantCallFormatBuilder chromosome(String chromosome) {
        this.chromosome = chromosome;
        return this;
    }

    public VariantCallFormatBuilder position(int position) {
        this.position = position;
        return this;
    }

    public VariantCallFormatBuilder id(String id) {
        this.id = id;
        return this;
    }

    public VariantCallFormatBuilder referenceBase(String referenceBase) {
        this.referenceBase = referenceBase;
        return this;
    }

    public VariantCallFormatBuilder alternativeAlleles(String alternativeAlleles) {
        this.alternativeAlleles = alternativeAlleles;
        return this;
    }

    public VariantCallFormatBuilder qualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
        return this;
    }

    public VariantCallFormatBuilder filter(String filter) {
        this.filter = filter;
        return this;
    }

    public VariantCallFormatBuilder info(Map<String, String> info) {
        this.info = info;
        return this;
    }

    // NS=3;DP=14;AF=0.5;DB;H2
    public VariantCallFormatBuilder info(String infoStr) {
        if ((infoStr == null) || (infoStr.isEmpty()))
            return this;
        String[] pairs = infoStr.split(";");
        info = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                info.put(keyValue[0], keyValue[1]);
            }else{
                info.put(keyValue[0], TRUE);
            }
        }
        return this;
    }

    public VariantCallFormatBuilder samples(List<String> samples) {
        this.samples = samples;
        return this;
    }
    
    public VariantCallFormatBuilder sampleFormat(String sampleFormat) {
        this.sampleFormat = sampleFormat;
        return this;
    }
}
