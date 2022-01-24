package uk.ac.ebi.uniprot.variation.hgvs.parser;

import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;
import uk.ac.ebi.uniprot.variation.hgvs.impl.HgvsDescriptionImpl;

public class HgvsRnaDescriptions {

    public final static Pattern HGVS_SUBSTITUTION_PATTERN = Pattern
            .compile("(\\()?(\\d+)([a-zA-Z]+)(>)([a-zA-Z]+)(\\))?");

    public final static Pattern HGVS_DELETION_BASE_PATTERN = Pattern.compile("(.+)del");
    public final static Pattern HGVS_DELETION_PATTERN = Pattern.compile("(\\()?(\\d+)((_)(\\d+))?(\\))?del");

    public final static Pattern HGVS_DUPLICATION_BASE_PATTERN = Pattern.compile("(.+)dup");
    public final static Pattern HGVS_DUPLICATION_PATTERN = Pattern.compile("(\\d+)((_)(\\d+))?dup");

    public final static Pattern HGVS_INSERTION_BASE_PATTERN = Pattern.compile("(.+)ins(.+)");
    public final static Pattern HGVS_INSERTION_PATTERN = Pattern.compile("(\\()?(\\d+)(_)(\\d+)(\\))?ins([a-zA-Z]+)");

    public final static Pattern HGVS_INVERSION_BASE_PATTERN = Pattern.compile("(.+)inv");
    public final static Pattern HGVS_INVERSION_PATTERN = Pattern.compile("(\\d+)(_)(\\d+)inv");

    public final static Pattern HGVS_CONVERSION_BASE_PATTERN = Pattern.compile("(.+)con(.+)");
    public final static Pattern HGVS_CONVERSION_PATTERN = Pattern
            .compile("(\\d+)(_)(\\d+)con((.+)(:))?(\\d+)(_)(\\d+)");

    public final static Pattern HGVS_DELETION_INSERTION_BASE_PATTERN = Pattern.compile("(.+)delins(.+)");
    public final static Pattern HGVS_DELETION_INSERTION_PATTERN = Pattern
            .compile("(\\d+)((_)(\\d+))?delins([a-zA-Z]+)");

    public final static Pattern HGVS_REPEAT_BASE_PATTERN = Pattern.compile("(.+)(\\[)(.+)(\\])");
    public final static Pattern HGVS_REPEAT_PATTERN = Pattern
            .compile("((\\-)?(\\d+))((_)((\\-)?(\\d+)))?([a-zA-Z]+)?(\\[)(\\d+)(\\])");

    public static HgvsDescription parseHgvsDescription(String val) {

        Matcher matcher = HgvsDnaDescriptions.HGVS_DELETION_INSERTION_BASE_PATTERN.matcher(val);
        if (matcher.matches()) {
            return parseDeletionInsertionDescription(val);
        }
        matcher = HgvsDnaDescriptions.HGVS_CONVERSION_BASE_PATTERN.matcher(val);
        if (matcher.matches()) {
            return parseConversionDescription(val);
        }

        matcher = HgvsDnaDescriptions.HGVS_INVERSION_BASE_PATTERN.matcher(val);
        if (matcher.matches()) {
            return parseInversionDescription(val);
        }
        matcher = HgvsDnaDescriptions.HGVS_INSERTION_BASE_PATTERN.matcher(val);
        if (matcher.matches()) {
            return parseInsertionDescription(val);
        }

        matcher = HgvsDnaDescriptions.HGVS_DUPLICATION_BASE_PATTERN.matcher(val);
        if (matcher.matches()) {
            return parseDuplicationDescription(val);
        }

        matcher = HgvsDnaDescriptions.HGVS_DELETION_BASE_PATTERN.matcher(val);
        if (matcher.matches()) {
            return parseDeletionDescription(val);
        }

        matcher = HgvsDnaDescriptions.HGVS_REPEAT_BASE_PATTERN.matcher(val);
        if (matcher.matches()) {
            return parseRepeatDescription(val);
        }
        return parseSubstitutionDescription(val);
    }

    private static HgvsDescription parseSubstitutionDescription(String val) {
        Matcher matcher = HgvsRnaDescriptions.HGVS_SUBSTITUTION_PATTERN.matcher(val);
        if (matcher.matches()) {

            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            builder.predicted(matcher.group(1) != null).start(Long.parseLong(matcher.group(2)))
                    .wildType(matcher.group(3)).varType(matcher.group(5)).variantType(VariantType.SUBSTITUTION)
                    .value(val).parsed(true);
            return builder.build();
        } else {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            return builder.value(val).variantType(VariantType.UNKNOWN).parsed(false).build();
        }
    }

    private static HgvsDescription parseDeletionDescription(String val) {
        Matcher matcher = HgvsRnaDescriptions.HGVS_DELETION_PATTERN.matcher(val);
        if (matcher.matches()) {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            builder.value(val).variantType(VariantType.DELETION).start(Long.parseLong(matcher.group(2))).parsed(true);
            if (matcher.group(5) != null) {
                builder.end(Long.parseLong(matcher.group(5)));
            }
            if (matcher.group(1) != null) {
                builder.predicted(true);
            }

            return builder.build();
        } else {
            return parsDescriptionBase(val, HgvsRnaDescriptions.HGVS_DELETION_BASE_PATTERN, VariantType.DELETION);
        }
    }

    private static HgvsDescription parseDuplicationDescription(String val) {
        Matcher matcher = HgvsRnaDescriptions.HGVS_DUPLICATION_PATTERN.matcher(val);
        if (matcher.matches()) {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            builder.value(val).variantType(VariantType.DUPLICATION).start(Long.parseLong(matcher.group(1)))
                    .parsed(true);
            if (matcher.group(4) != null) {
                builder.end(Long.parseLong(matcher.group(4)));
            }
            return builder.build();
        } else {
            return parsDescriptionBase(val, HgvsRnaDescriptions.HGVS_DUPLICATION_BASE_PATTERN, VariantType.DUPLICATION);

        }
    }

    private static HgvsDescription parseInsertionDescription(String val) {
        Matcher matcher = HgvsRnaDescriptions.HGVS_INSERTION_PATTERN.matcher(val);
        if (matcher.matches()) {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            builder.value(val).variantType(VariantType.INSERTION).start(Long.parseLong(matcher.group(2))).parsed(true)
                    .end(Long.parseLong(matcher.group(4))).varType(matcher.group(6));
            if (matcher.group(1) != null) {
                builder.predicted(true);
            }
            return builder.build();
        } else {
            return parsDescriptionBase(val, HgvsRnaDescriptions.HGVS_INSERTION_BASE_PATTERN, VariantType.INSERTION);
        }
    }

    private static HgvsDescription parseInversionDescription(String val) {
        Matcher matcher = HgvsRnaDescriptions.HGVS_INVERSION_PATTERN.matcher(val);
        if (matcher.matches()) {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            builder.value(val).variantType(VariantType.INVERSION).start(Long.parseLong(matcher.group(1))).parsed(true)
                    .end(Long.parseLong(matcher.group(3)));
            return builder.build();
        } else {
            return parsDescriptionBase(val, HgvsRnaDescriptions.HGVS_INVERSION_BASE_PATTERN, VariantType.INVERSION);
        }
    }

    private static HgvsDescription parseConversionDescription(String val) {
        Matcher matcher = HgvsRnaDescriptions.HGVS_CONVERSION_PATTERN.matcher(val);
        if (matcher.matches()) {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            builder.value(val).variantType(VariantType.CONVERSION).start(Long.parseLong(matcher.group(1))).parsed(true)
                    .startCross(Long.parseLong(matcher.group(3))).end(Long.parseLong(matcher.group(7)))
                    .endCross(Long.parseLong(matcher.group(9)));
            if (matcher.group(5) != null) {
                builder.conversionSeqId(matcher.group(5));
            }
            return builder.build();
        } else {
            return parsDescriptionBase(val, HgvsRnaDescriptions.HGVS_CONVERSION_BASE_PATTERN, VariantType.CONVERSION);
        }
    }

    private static HgvsDescription parseDeletionInsertionDescription(String val) {
        Matcher matcher = HgvsRnaDescriptions.HGVS_DELETION_INSERTION_PATTERN.matcher(val);
        if (matcher.matches()) {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            builder.value(val).variantType(VariantType.DELETION_INSERTION).start(Long.parseLong(matcher.group(1)))
                    .parsed(true).varType(matcher.group(5));
            if (matcher.group(4) != null) {
                builder.end(Long.parseLong(matcher.group(4)));
            } else {
                builder.end(Long.parseLong(matcher.group(1)));
            }
            return builder.build();
        } else {
            return parsDescriptionBase(val, HgvsRnaDescriptions.HGVS_DELETION_INSERTION_BASE_PATTERN,
                    VariantType.DELETION_INSERTION);

        }
    }

    private static HgvsDescription parseRepeatDescription(String val) {
        Matcher matcher = HgvsRnaDescriptions.HGVS_REPEAT_PATTERN.matcher(val);
        if (matcher.matches()) {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            builder.value(val).variantType(VariantType.REPEAT).start(Long.parseLong(matcher.group(1))).parsed(true);
            if (matcher.group(6) != null) {
                builder.end(Long.parseLong(matcher.group(6)));
            }
            String key = matcher.group(9) == null ? "" : matcher.group(9);
            Map.Entry<String, Integer> repeat = new AbstractMap.SimpleEntry<>(key, Integer.parseInt(matcher.group(11)));
            builder.repeat(repeat);
            return builder.build();
        } else {
            return parsDescriptionBase(val, HgvsDnaDescriptions.HGVS_REPEAT_BASE_PATTERN, VariantType.REPEAT);

        }
    }

    private static HgvsDescription parsDescriptionBase(String val, Pattern pattern, VariantType type) {
        Matcher matcher = pattern.matcher(val);
        if (matcher.matches()) {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            return builder.value(val).variantType(type).parsed(false).build();
        } else {
            HgvsDescriptionImpl.Builder builder = HgvsDescriptionImpl.builder();
            return builder.value(val).variantType(VariantType.UNKNOWN).parsed(false).build();
        }
    }
}
