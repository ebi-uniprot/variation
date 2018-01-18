package uk.ac.ebi.uniprot.variation.parser;

import uk.ac.ebi.uniprot.variation.VariantCallFormat;
import uk.ac.ebi.uniprot.variation.impl.VariantCallFormatImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class VCFFileParser {

    private static final String TAB = "\t";
    private static final String STOP = ".";
    private static final String TRUE = "TRUE";

    public static VariantCallFormat parseVCFLine(String data) {
        String[] tokens = data.split(TAB);
        VariantCallFormatImpl.VariantCallFormatBuilder builder = VariantCallFormatImpl.builder();
        builder.chromosome(transform(tokens[0]))
                .position(Integer.parseInt(tokens[1]))
                .id(transform(tokens[2]))
                .referenceBase(transform(tokens[3]))
                .alternativeAlleles(transform(tokens[4]));

        if (tokens.length > 5) {
            if (!STOP.equals(tokens[5])) {
                builder.qualityScore(Double.parseDouble(tokens[5]));
            }
        }
        if (tokens.length > 6) {
            builder.filter(transform(tokens[6]));
        }
        if (tokens.length > 7) {
            builder.info(transformInfoStr(tokens[7]));
        }
        if (tokens.length > 8) {
            builder.sampleFormat(transform(tokens[8]));
        }
        List<String> samples = new ArrayList<>();
        for (int i = 9; i < tokens.length; i++) {
            samples.add(tokens[i]);
        }
        builder.samples(samples);
        return builder.build();
    }

    private static String transform(String val) {
        if (STOP.equals(val))
            return "";
        else
            return val;
    }

    private static Map<String, String> transformInfoStr(String infoStr) {
    
        if ((infoStr == null) || (infoStr.isEmpty()) ||STOP.equals(infoStr) )
            return Collections.emptyMap();
        String[] pairs = infoStr.split(";");
        Map<String, String> info = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                info.put(keyValue[0], keyValue[1]);
            } else {
                info.put(keyValue[0], TRUE);
            }
        }
        return info;
    }

}
