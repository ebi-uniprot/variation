package uk.ac.ebi.uniprot.variation.hgvs.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.variation.SequenceType;
import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;
import uk.ac.ebi.uniprot.variation.exception.InvalidadHgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.impl.HgvsImpl;
import uk.ac.ebi.uniprot.variation.impl.VariationLocationImpl;

public final class Hgvss {
	public final static String HGVS = "([\\w.]+)(\\:)([cgmnpr])(\\.)(.+)"; // ENSMUST00000082421.1:c.115G>A;
	public final static Pattern HGVS_PATTERN = Pattern.compile(HGVS);

	public static Hgvs from(String hgvsString) {
		Matcher matcher = Hgvss.HGVS_PATTERN.matcher(hgvsString);
		if (matcher.matches()) {
			String sequenceId = matcher.group(1);
			SequenceType seqType = SequenceType.getType(matcher.group(3));
			String description = matcher.group(5);
			return new HgvsImpl(sequenceId, seqType, parseDescription(seqType, description));
		} else {
			throw new InvalidHgvsException(hgvsString + " cannot be parsed");
		}
	}
	
	public static VariationLocation hgvs2VariationLocation(Hgvs hgvs) {
		if(!hgvs.getDescription().isParsed()) {
			throw new InvalidadHgvsDescription("invalid hgvs description: "+ hgvs.getDescription().getValue());
		}
		 VariationLocationImpl.VariationLocationBuilder builder = VariationLocationImpl.builder();
	        builder.locationType(hgvs.getSequenceType())
	                .sequenceId(hgvs.getSequenceId())
	                .wildType(hgvs.getDescription().getWildType())
	                .varType(hgvs.getDescription().getVarType())
	                .start(hgvs.getDescription().getStart());
	        if(hgvs.getDescription().getEnd() !=null) {
	        		builder.end(hgvs.getDescription().getEnd());
	        }else {
	        	builder.end(hgvs.getDescription().getStart());
	        }
	        return builder.build();
	}
	private static HgvsDescription parseDescription(SequenceType type, String description) {
		switch (type) {
		case GENOME:
		case CDNA:
		case MDNA:
		case NDNA:
			return HgvsDnaDescriptions.parseHgvsDescription(description);
		case RNA:
			return HgvsRnaDescriptions.parseHgvsDescription(description);
		case PROTEIN:
			return HgvsProteinDescriptions.parseHgvsDescription(description);
		default:
			throw new InvalidadHgvsDescription(description + " cannot be parsed.");
		}
	}

}