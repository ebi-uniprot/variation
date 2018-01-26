package uk.ac.ebi.uniprot.variation.hgvs.parser;

import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;
import uk.ac.ebi.uniprot.variation.hgvs.impl.HgvsDescriptionImpl;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;

public class HgvsProteinDescriptions {
	public final static Pattern HGVS_SUBSTITUTION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)([a-zA-Z*=\\?]+)(\\))?");

	public final static Pattern HGVS_DELETION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(del)(\\))?");

	public final static Pattern HGVS_DUPLICATION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(dup)(\\))?");

	public final static Pattern HGVS_INSERTION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)(_)([a-zA-Z]+)(\\d+)(ins)([a-zA-Z]+)?(\\d+)?(\\))?");

	public final static Pattern HGVS_DELETION_INSERTION_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(delins)([a-zA-Z]+)(\\))?");

	public final static Pattern HGVS_REPEAT_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(\\[)(\\d+)(\\])(\\))?");

	public final static Pattern HGVS_REPEAT_BASE_PATTERN = Pattern.compile("(.+)(\\[)(.+)(\\])");

	public final static Pattern HGVS_FRAMESHIFT_PATTERN = Pattern
			.compile("(\\()?([a-zA-Z]+)(\\d+)([a-zA-Z]+)?(fs)(([a-zA-Z*]+)(\\d+))?(\\))?");

	public final static Pattern HGVS_EXTENSION_MET_PATTERN = Pattern
			.compile("(\\()?(Met)(\\d+)([a-zA-Z]+)?(ext)((-)?\\d+)(\\))?");
	public final static Pattern HGVS_EXTENSION_TER_PATTERN = Pattern
			.compile("(\\()?(\\*|Ter)(\\d+)([a-zA-Z]+)(ext)([a-zA-Z]+)?(\\*|Ter)(\\d+|\\?)?(\\))?");

	public static HgvsDescription parseHgvsDescription(String hgvsDescription) {

		HgvsDescription proteinHgvs = parseDeletionDescription(hgvsDescription);

		if (proteinHgvs == null) {
			proteinHgvs = parseDuplicationDescription(hgvsDescription);
		}

		if (proteinHgvs == null) {
			proteinHgvs = parseInsertionDescription(hgvsDescription);
		}

		if (proteinHgvs == null) {
			proteinHgvs = parseDeletionInsertionDescription(hgvsDescription);
		}
		if (proteinHgvs == null) {
			proteinHgvs = parseExtensionDescription(hgvsDescription);
		}
		if (proteinHgvs == null) {
			proteinHgvs = parseFrameshiftDescription(hgvsDescription);
		}
		if (proteinHgvs == null) {
			proteinHgvs = parseSubstitutionDescription(hgvsDescription);
		}
		if (proteinHgvs == null) {
			proteinHgvs = parseRepeatDescription(hgvsDescription);
		}

		if (proteinHgvs == null) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			return builder.parsed(false).value(hgvsDescription).type(HgvsType.UNKNOWN).build();
		} else

			return proteinHgvs;

	}

	private static HgvsDescription parseSubstitutionDescription(String val) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_SUBSTITUTION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.predicted(matcher.group(1) != null)
			.wildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(2)))
					.start(Long.parseLong(matcher.group(3)))
					.varType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(4)))
					.type(HgvsType.SUBSTITUTION)
					.value(val).parsed(true);
			return builder.build();
		} else {
			return null;
		}
	}

	private static HgvsDescription parseDeletionDescription(String val) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_DELETION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.value(val).type(HgvsType.DELETION).predicted(matcher.group(1) != null)
					.wildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(2)))
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			if (matcher.group(6) != null) {
				builder.secondWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(6)));
			}

			if (matcher.group(7) != null) {
				builder.end(Long.parseLong(matcher.group(7)));
			}

			return builder.build();
		} else {
			return null;

		}
	}

	private static HgvsDescription parseDuplicationDescription(String val) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_DUPLICATION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.value(val).type(HgvsType.DUPLICATION).predicted(matcher.group(1) != null)
					.wildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(2)))
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			if (matcher.group(6) != null) {
				builder.secondWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(6)));
			}

			if (matcher.group(7) != null) {
				builder.end(Long.parseLong(matcher.group(7)));
			}

			return builder.build();
		} else {
			return null;
		}
	}

	private static HgvsDescription parseInsertionDescription(String val) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_INSERTION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.value(val).type(HgvsType.INSERTION).predicted(matcher.group(1) != null)
					.wildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(2)))
					.start(Long.parseLong(matcher.group(3))).parsed(true)
					.secondWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(5)))
					.end(Long.parseLong(matcher.group(6)));
			String varType = matcher.group(8);
			if (varType != null) {
				builder.varType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(varType));
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

	private static HgvsDescription parseDeletionInsertionDescription(String val) {

		Matcher matcher = HgvsProteinDescriptions.HGVS_DELETION_INSERTION_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.value(val).type(HgvsType.DELETION_INSERTION).predicted(matcher.group(1) != null)
					.wildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(2)))
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			String secondWildType = matcher.group(6);
			String secondStart = matcher.group(7);
			if (secondWildType != null) {
				builder.secondWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(secondWildType))
				.end(Long.parseLong(secondStart));
			}
			builder.varType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(9)));

			return builder.build();
		} else {
			return null;
		}

	}

	private static HgvsDescription parseRepeatDescription(String val) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_REPEAT_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.value(val).type(HgvsType.REPEAT).predicted(matcher.group(1) != null)
					.wildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(2)))
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			if (matcher.group(7) != null) {
				builder.secondWildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(6)))
				.end(Long.parseLong(matcher.group(7)));
			}
			Map.Entry<String, Integer> repeat = new AbstractMap.SimpleEntry<>("", Integer.parseInt(matcher.group(9)));
			builder.repeat(repeat);
			return builder.build();
		} else {
			return parsDescriptionBase(val, HgvsProteinDescriptions.HGVS_REPEAT_BASE_PATTERN, HgvsType.REPEAT);
		}
	}

	private static HgvsDescription parseFrameshiftDescription(String val) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_FRAMESHIFT_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.value(val).type(HgvsType.FRAMESHIFT).predicted(matcher.group(1) != null)
					.wildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(2)))
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			if (matcher.group(8) != null) {
				builder.end(Long.parseLong(matcher.group(8)));
			}
			if (matcher.group(4) != null) {
				builder.varType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(4)));
			} else
				builder.varType("*");

			return builder.build();
		} else {
			return null;
		}

	}

	private static HgvsDescription parseExtensionDescription(String val) {
		HgvsDescription proteinHgvs = parseExtensionDescriptionFromMet(val);
		if (proteinHgvs == null) {
			proteinHgvs = parseExtensionDescriptionFromTer(val);
		}
		return proteinHgvs;

	}

	private static HgvsDescription parseExtensionDescriptionFromMet(String val) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_EXTENSION_MET_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.value(val).type(HgvsType.EXTENSION).predicted(matcher.group(1) != null)
					.wildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(2)))
					.start(Long.parseLong(matcher.group(3))).parsed(true);
			if (matcher.group(6) != null) {
				builder.repeat(new AbstractMap.SimpleEntry<>("*", Integer.parseInt(matcher.group(6))));
			}
			if (matcher.group(4) != null) {
				builder.varType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(4)));
			} else
				builder.varType("?");

			return builder.build();
		} else {
			return null;
		}

	}

	private static HgvsDescription parseExtensionDescriptionFromTer(String val) {
		Matcher matcher = HgvsProteinDescriptions.HGVS_EXTENSION_TER_PATTERN.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			builder.value(val).type(HgvsType.EXTENSION).predicted(matcher.group(1) != null)
					.wildType(VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(2))							
							).start(Long.parseLong(matcher.group(3))).parsed(true);

			if (matcher.group(4) != null) {
				builder.varType(
						VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(4)));
			} else
				builder.varType("?");
			if (matcher.group(6) != null) {
				builder.repeat(new AbstractMap.SimpleEntry<>(
						VariationUtil.convertThreeLetterAminoAcid2OneLetter(matcher.group(6))
						, 1));
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

	private static HgvsDescription parsDescriptionBase(String val, Pattern pattern, HgvsType type) {
		Matcher matcher = pattern.matcher(val);
		if (matcher.matches()) {
			HgvsDescriptionImpl.HgvsDescriptionBuilder builder = HgvsDescriptionImpl.builder();
			return builder.value(val).type(type).parsed(false).build();
		} else {
			return null;
		}
	}

}
