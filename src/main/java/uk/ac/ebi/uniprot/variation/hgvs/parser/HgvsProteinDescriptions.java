package uk.ac.ebi.uniprot.variation.hgvs.parser;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import uk.ac.ebi.uniprot.variation.hgvs.HgvsProteinDescripton;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;
import uk.ac.ebi.uniprot.variation.hgvs.impl.HgvsProteinDescriptionImpl;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

public class HgvsProteinDescriptions {
	public final static Pattern HGVS_SUBSTITUTION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z*]+)(\\d+)([a-zA-Z*=\\?]+)(\\))?");

	public final static Pattern HGVS_DELETION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(del)(\\))?");

	public final static Pattern HGVS_DUPLICATION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(dup)(\\))?");

	public final static Pattern HGVS_INSERTION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z*]+)(\\d+)(_)([a-zA-Z*]+)(\\d+)(ins)([a-zA-Z*]+)?(\\d+)?(\\))?");

	public final static Pattern HGVS_DELETION_INSERTION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z*]+)(\\d+)((_)([a-zA-Z*]+)(\\d+))?(delins)([a-zA-Z*]+)(\\))?");

	public final static Pattern HGVS_REPEAT_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(\\[)(\\d+)(\\])(\\))?");

	public final static Pattern HGVS_REPEAT_BASE_PATTERN = Pattern.compile("(.+)(\\[)(.+)(\\])");

	public final static Pattern HGVS_FRAMESHIFT_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z*]+)(\\d+)([a-zA-Z*]+)?(fs)(([a-zA-Z*]+)(\\d+))?(\\))?");

	public final static Pattern HGVS_EXTENSION_MET_PATTERN = Pattern
			.compile("(\\()?(Met)(\\d+)([a-zA-Z]+)?(ext)((-)?\\d+)(\\))?");
	public final static Pattern HGVS_EXTENSION_TER_PATTERN = Pattern
			.compile("(\\()?(\\*|Ter)(\\d+)([a-zA-Z]+)(ext)([a-zA-Z]+)?(\\*|Ter)(\\d+|\\?)?(\\))?");

	public static HgvsProteinDescripton parseHgvsDescription(String hgvsDescription, boolean threeLett) {

		HgvsProteinDescripton proteinHgvs = parseDeletionDescription(hgvsDescription, threeLett);

		if (proteinHgvs == null) {
			proteinHgvs = parseDuplicationDescription(hgvsDescription, threeLett);
		}

		if (proteinHgvs == null) {
			proteinHgvs = parseInsertionDescription(hgvsDescription, threeLett);
		}

		if (proteinHgvs == null) {
			proteinHgvs = parseDeletionInsertionDescription(hgvsDescription, threeLett);
		}
		if (proteinHgvs == null) {
			proteinHgvs = parseExtensionDescription(hgvsDescription,threeLett);
		}
		if (proteinHgvs == null) {
			proteinHgvs = parseFrameshiftDescription(hgvsDescription, threeLett);
		}
		if (proteinHgvs == null) {
			proteinHgvs = parseSubstitutionDescription(hgvsDescription, threeLett);
		}
		if (proteinHgvs == null) {
			proteinHgvs = parseRepeatDescription(hgvsDescription, threeLett);
		}

		if (proteinHgvs == null) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			builder.parsed(false).value(hgvsDescription).variantType(VariantType.UNKNOWN);
			return builder.build();
		} else

			return proteinHgvs;

	}
	
	
	
	private static Function<String,String> convertAAs = aa -> {
		return VariationUtil.convertThreeLetterAminoAcid2OneLetter(aa);
	};
	
	
//(\\()?([a-zA-Z]+)(\\d+)([a-zA-Z*=\\?]+)(\\))?
	private static HgvsProteinDescripton parseSubstitutionDescription(String val, boolean threeLett) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_SUBSTITUTION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			String wt = threeLett ? convertAAs.apply(matcher.group(2)) : matcher.group(2);
			String vt = threeLett ? convertAAs.apply(matcher.group(4)) : matcher.group(4);
			builder.predicted(matcher.group(1) != null)
			.wildType(wt)
					.start(Long.parseLong(matcher.group(3)))
					.end(Long.parseLong(matcher.group(3)))
					.varType(vt)
					.variantType(VariantType.SUBSTITUTION)
					.value(val).parsed(true);
			return builder.build();
		} else {
			return null;
		}
	}

	private static HgvsProteinDescripton parseDeletionDescription(String val, boolean threeLett) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_DELETION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			String wt = threeLett ? convertAAs.apply(matcher.group(2)) : matcher.group(2);
			builder.value(val).variantType(VariantType.DELETION).predicted(matcher.group(1) != null)
					.wildType(wt)
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			if (matcher.group(6) != null) {
				String wt2 = threeLett ? convertAAs.apply(matcher.group(6)) : matcher.group(6);
				builder.secondWildType(wt2);
			}

			if (matcher.group(7) != null) {
				builder.end(Long.parseLong(matcher.group(7)));
			}

			return builder.build();
		} else {
			return null;

		}
	}

	private static HgvsProteinDescripton parseDuplicationDescription(String val, boolean threeLett) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_DUPLICATION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			String wt = threeLett ? convertAAs.apply(matcher.group(2)) : matcher.group(2);
			builder.value(val).variantType(VariantType.DUPLICATION).predicted(matcher.group(1) != null)
					.wildType(wt)
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			if (matcher.group(6) != null) {
				String wt2 = threeLett ? convertAAs.apply(matcher.group(6)) : matcher.group(6);
				builder.secondWildType(wt2);
			}

			if (matcher.group(7) != null) {
				builder.end(Long.parseLong(matcher.group(7)));
			}

			return builder.build();
		} else {
			return null;
		}
	}
	// "(\\()?([a-zA-Z*]+)(\\d+)(_)([a-zA-Z]+)(\\d+)(ins)([a-zA-Z*]+)?(\\d+)?(\\))?"
	private static HgvsProteinDescripton parseInsertionDescription(String val, boolean threeLett) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_INSERTION_PATTERN.matcher(val);
		
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			String wt = threeLett ? convertAAs.apply(matcher.group(2)) : matcher.group(2);
			String wt2 = threeLett ? convertAAs.apply(matcher.group(5)) : matcher.group(5);
			builder.value(val).variantType(VariantType.INSERTION).predicted(matcher.group(1) != null)
					.wildType(wt)
					.start(Long.parseLong(matcher.group(3))).parsed(true)
					.secondWildType(wt2)
					.end(Long.parseLong(matcher.group(6)));
			String varType = matcher.group(8);
			if (varType != null) {
				String vt = threeLett ? convertAAs.apply(varType) : varType;
				builder.varType(vt);
			}
			String occurrence = matcher.group(9);
			if (occurrence != null) {
				builder.repeat(new AbstractMap.SimpleEntry<>("", Integer.parseInt(occurrence)));
			}
			return builder.build();
		} else {
			return null;
		}
	}
	//(\\()?([a-zA-Z*]+)(\\d+)((_)([a-zA-Z*]+)(\\d+))?(delins)([a-zA-Z*]+)(\\))?
	private static HgvsProteinDescripton parseDeletionInsertionDescription(String val, boolean threeLett) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_DELETION_INSERTION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			String wt = threeLett ? convertAAs.apply(matcher.group(2)) : matcher.group(2);
			builder.value(val).variantType(VariantType.DELETION_INSERTION).predicted(matcher.group(1) != null)
					.wildType(wt)
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			String secondWildType = matcher.group(6);
			String secondStart = matcher.group(7);
			if (secondWildType != null) {
				String vt = threeLett ? convertAAs.apply(secondWildType) : secondWildType;
				builder.secondWildType(vt)
				.end(Long.parseLong(secondStart));
			}
			String vt = threeLett ? convertAAs.apply(matcher.group(9)) : matcher.group(9);
			builder.varType(vt);

			return builder.build();
		} else {
			return null;
		}

	}

	private static HgvsProteinDescripton parseRepeatDescription(String val, boolean threeLett) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_REPEAT_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			String wt = threeLett ? convertAAs.apply(matcher.group(2)) : matcher.group(2);
			builder.value(val).variantType(VariantType.REPEAT).predicted(matcher.group(1) != null)
					.wildType(wt)
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			if (matcher.group(7) != null) {
				String vt = threeLett ? convertAAs.apply(matcher.group(6)) : matcher.group(6);
				builder.secondWildType(vt)
				.end(Long.parseLong(matcher.group(7)));
			}
			Map.Entry<String, Integer> repeat = new AbstractMap.SimpleEntry<>("", Integer.parseInt(matcher.group(9)));
			builder.repeat(repeat);
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsProteinDescriptions.HGVS_REPEAT_BASE_PATTERN, VariantType.REPEAT);
		}
	}

	//"(\\()?([a-zA-Z]+)(\\d+)([a-zA-Z]+)?(fs)(([a-zA-Z*]+)(\\d+))?(\\))?"
	private static HgvsProteinDescripton parseFrameshiftDescription(String val, boolean threeLett) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_FRAMESHIFT_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			String wt = threeLett ? convertAAs.apply(matcher.group(2)) : matcher.group(2);
			builder.value(val).variantType(VariantType.FRAMESHIFT).predicted(matcher.group(1) != null)
					.wildType(wt)
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			builder.frameShift(matcher.group(5));
			
			if (matcher.group(4) != null) {
				String vt = threeLett ? convertAAs.apply(matcher.group(4)) : matcher.group(4);
				builder.varType(vt);
			} else
				builder.varType("*");
			
			if(null != matcher.group(7) && (matcher.group(7).contains("Ter") || matcher.group(7).contains("*"))) {
				String end = "";
				if (matcher.group(8) != null) {
					end = matcher.group(8);
				}
				if(threeLett) {
					builder.stop("Ter" + end);
				} else {
				    builder.stop("*" + end);
				}
			}

			return builder.build();
		} else {
			return null;
		}

	}

	private static HgvsProteinDescripton parseExtensionDescription(String val, boolean threeLett) {
		HgvsProteinDescripton proteinHgvs = parseExtensionDescriptionFromMet(val,threeLett);
		if (proteinHgvs == null) {
			proteinHgvs = parseExtensionDescriptionFromTer(val,threeLett);
		}
		return proteinHgvs;

	}

	
	
	private static HgvsProteinDescripton parseExtensionDescriptionFromMet(String val, boolean threeLett) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_EXTENSION_MET_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			String wt = threeLett ? convertAAs.apply(matcher.group(2)) : matcher.group(2);
			builder.value(val).variantType(VariantType.EXTENSION).predicted(matcher.group(1) != null)
					.wildType(wt)
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			if (matcher.group(6) != null) {
				builder.repeat(new AbstractMap.SimpleEntry<>("*", Integer.parseInt(matcher.group(6))));
			}
			if (matcher.group(4) != null) {
				String vt = threeLett ? convertAAs.apply(matcher.group(4)) : matcher.group(4);
				builder.varType(vt);
			} else
				builder.varType("?");

			return builder.build();
		} else {
			return null;
		}

	}

	//"(\\()?(\\*|Ter)(\\d+)([a-zA-Z]+)(ext)([a-zA-Z]+)?(\\*|Ter)(\\d+|\\?)?(\\))?"
	private static HgvsProteinDescripton parseExtensionDescriptionFromTer(String val,boolean threeLett) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_EXTENSION_TER_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			String wt = threeLett ? convertAAs.apply(matcher.group(2)) : matcher.group(2);
			builder.value(val).variantType(VariantType.EXTENSION).predicted(matcher.group(1) != null)
					.wildType(wt).start(Long.parseLong(matcher.group(3))).parsed(true);

			if (matcher.group(4) != null) {
				String vt = threeLett ? convertAAs.apply(matcher.group(4)) : matcher.group(4);
				builder.varType(vt);
			} else
				builder.varType("?");
			if (matcher.group(6) != null) {
				String rt = threeLett ? convertAAs.apply(matcher.group(6)) : matcher.group(6);
				builder.repeat(new AbstractMap.SimpleEntry<>(rt, 1));
			}
			String stop = matcher.group(7);
			if(null != stop) {
				if(!threeLett && "Ter".equals(stop)) {
					stop = VariationUtil.convertThreeLetterAminoAcid2OneLetter(stop);
				}
				builder.stop(stop);
			}
			String end = matcher.group(8);
			if (end != null) {
				
				if (end.equals("?")) {
					builder.repeat(new AbstractMap.SimpleEntry<>("*", -1));
				} else {
					builder.repeat(new AbstractMap.SimpleEntry<>("*", Integer.parseInt(end)));
				}
			}

			return builder.build();
		} else {
			return null;
		}

	}

	private static HgvsProteinDescripton parsDescriptionBase(String val, Pattern pattern, VariantType type) {
		Matcher matcher = pattern.matcher(val);
		if (matcher.matches()) {
			HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
			builder.value(val).variantType(type);
			return builder.build();
		} else {
			return null;
		}
	}

}
