package uk.ac.ebi.uniprot.variation.hgvs.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;
import uk.ac.ebi.uniprot.variation.exception.InvalidadHgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;
import uk.ac.ebi.uniprot.variation.hgvs.impl.HgvsImpl;

public final class Hgvss {
    // Old regular expression "([\\w.-]+)(\\:)([cgmnpr])(\\.)(.+)"
    public final static String HGVS = "([\\w.\\-\\:]+)(\\:[cgmnpr]\\.)(.+)"; // ENSMUST00000082421.1:c.115G>A;
                                                                             // SPAC1805.18.1:pep.1:p.Arg78Trp
    public final static Pattern HGVS_PATTERN = Pattern.compile(HGVS);

    public static Hgvs from(String hgvsString, boolean threeLett) {
        Matcher matcher = Hgvss.HGVS_PATTERN.matcher(hgvsString);
        if (matcher.matches()) {
            String sequenceId = matcher.group(1);
            HgvsType seqType = HgvsType.getType(matcher.group(2).replaceAll("[\\:\\.]", "")); // strip unwanted
                                                                                              // characters
            String description = matcher.group(3);
            return new HgvsImpl(seqType, sequenceId, parseDescription(seqType, description, threeLett));
        } else {
            throw new InvalidHgvsException(hgvsString + " cannot be parsed");
        }
    }

//	public static VariationLocation hgvs2VariationLocation(Hgvs hgvs) {
//		if(!hgvs.getDescription().isParsed()) {
//			throw new InvalidadHgvsDescription("invalid hgvs description: "+ hgvs.getDescription().getValue());
//		}
//		 VariationLocationImpl.VariationLocationBuilder builder = VariationLocationImpl.builder();
//	        builder.locationType(hgvs.getType())
//	                .sequenceId(hgvs.getSequenceId())
//	                .wildType(hgvs.getDescription().getWildType())
//	                .varType(hgvs.getDescription().getVarType())
//	                .start(hgvs.getDescription().getStart());
//	        if(hgvs.getDescription().getEnd() !=null) {
//	        		builder.end(hgvs.getDescription().getEnd());
//	        }else {
//	        	builder.end(hgvs.getDescription().getStart());
//	        }
//	        return builder.build();
//	}
    public static HgvsDescription parseDescription(HgvsType type, String description, boolean threeLett) {
        switch (type) {
        case GENOME:
        case CDNA:
        case MDNA:
        case NDNA:
            return HgvsDnaDescriptions.parseHgvsDescription(description);
        case RNA:
            return HgvsRnaDescriptions.parseHgvsDescription(description);
        case PROTEIN:
            return HgvsProteinDescriptions.parseHgvsDescription(description, threeLett);
        default:
            throw new InvalidadHgvsDescription(description + " cannot be parsed.");
        }
    }

}
