package uk.ac.ebi.uniprot.variation.hgvs.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.hgvs.HgvsProteinDescripton;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;
import uk.ac.ebi.uniprot.variation.hgvs.impl.HgvsProteinDescriptionImpl;

public class HgvsProteinDescriptionParserTest {
	@Test
	public void testSubstitutionDescriptionMissense() {
		String val = "Trp24Cys";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(24l, hgvsDescription.getStart().longValue());
		assertEquals("W", hgvsDescription.getWildType());
		assertEquals("C", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());
	}
	
	@Test
	public void testSubstitutionDescriptionMissenseBuildDisplayValue() {
		String val = "Trp24Cys";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(24l).wildType("W").varType("C").variantType(VariantType.MISSENSE);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testSubstitutionDescriptionPred() {
		String val = "(Trp24Cys)";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(24l, hgvsDescription.getStart().longValue());
		assertEquals("W", hgvsDescription.getWildType());
		assertEquals("C", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());
	}

	@Test
	public void testMissenseDescriptionPredBuildDisplayValue() {
		String val = "(Trp24Cys)";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(24l).wildType("W").varType("C").variantType(VariantType.MISSENSE).predicted(true);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	
	
	@Test
	public void testSubstitutionDescriptionNonsense() {
		String val = "Trp24*";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(24l, hgvsDescription.getStart().longValue());
		assertEquals("W", hgvsDescription.getWildType());
		assertEquals("-", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());
	}
	
	@Test
	public void testSubstitutionDescriptionNonsenseBuildDisplayValue() {
		String val = "Trp24Ter";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.stop("Ter").start(24l).wildType("W").variantType(VariantType.MISSENSE).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testSubstitutionDescriptionSilent() {
		String val = "Cys188=";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(188l, hgvsDescription.getStart().longValue());
		assertEquals("C", hgvsDescription.getWildType());
		assertEquals("=", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SILENT, hgvsDescription.getVariantType());
	}
	
	
	@Test
	public void testSubstitutionDescriptionSilentRoundTrip() {
		String val = "Cys188=";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(188l).wildType("C").varType("=").variantType(VariantType.SILENT).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testSubstitutionDescriptionUnknow() {
		String val = "Cys188?";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(188l, hgvsDescription.getStart().longValue());
		assertEquals("C", hgvsDescription.getWildType());
		assertEquals("-", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testSubstitutionDescriptionUnknowRoundTrip() {
		String val = "Cys188?";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(188l).wildType("C").varType("?").variantType(VariantType.SUBSTITUTION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}
	

	@Test
	public void testUnsupportedHgvsDescriptionWrong() {
		String val = "33038255C/A";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isParsed());
		assertEquals(val, hgvsDescription.getValue());
		assertEquals(VariantType.UNKNOWN, hgvsDescription.getVariantType());

	}

	@Test
	public void testProteinDeletionSingle() {
		String val = "Ala3del";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("A", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testProteinDeletionSingleRoundTrip() {
		String val = "Ala3del";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(3l).wildType("A").variantType(VariantType.DELETION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}
	

	@Test
	public void testProteinDeletionSinglePred() {
		String val = "(Ala3del)";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("A", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());
	}
	
	
	@Test
	public void testProteinDeletionSinglePredRoundTrip() {
		String val = "(Ala3del)";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(3l).wildType("A").variantType(VariantType.DELETION).predicted(true);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinDeletionMulti() {
		String val = "Ala3_Ser5del";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("A", hgvsDescription.getWildType());
		assertEquals("S", hgvsDescription.getSecondWildType());
		assertEquals(5l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());
	}
	
	
	@Test
	public void testProteinDeletionMultiRoundTrip() {
		String val = "Ala3_Ser5del";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(3l).end(5l).wildType("A").secondWildType("S")
		.variantType(VariantType.DELETION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}
	

	@Test
	public void testProteinDeletionMulti2() {
		String val = "Gly2_Met46del";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(2l, hgvsDescription.getStart().longValue());
		assertEquals("G", hgvsDescription.getWildType());
		assertEquals("M", hgvsDescription.getSecondWildType());
		assertEquals(46l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());
	}
	
	
	@Test
	public void testProteinDeletionMulti2RoundTrip() {
		String val = "Gly2_Met46del";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(2l).end(46l).wildType("G").secondWildType("M")
		.variantType(VariantType.DELETION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	
	
	@Test
	public void testProteinDeletionMulti3() {
		String val = "Thr839_Lys862del";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(839l, hgvsDescription.getStart().longValue());
		assertEquals("T", hgvsDescription.getWildType());
		assertEquals("K", hgvsDescription.getSecondWildType());
		assertEquals(862l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());
	}
	
	
	@Test
	public void testProteinDeletionMulti3RoundTrip() {
		String val = "Thr839_Lys862del";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(839l).end(862l).wildType("T").secondWildType("K")
		.variantType(VariantType.DELETION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}
	

	@Test
	public void testProteinDuplicationSingle() {
		String val = "Ala3dup";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("A", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		// assertEquals(46l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DUPLICATION, hgvsDescription.getVariantType());
	}
	
	
	@Test
	public void testProteinDuplicationSingleRoundTrip() {
		String val = "Ala3dup";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(3l).wildType("A").variantType(VariantType.DUPLICATION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinDuplicationSinglePred() {
		String val = "(Ala3dup)";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("A", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		// assertEquals(46l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DUPLICATION, hgvsDescription.getVariantType());
	}
	
	
	@Test
	public void testProteinDuplicationSinglePredRoundTrip() {
		String val = "(Ala3dup)";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(3l).wildType("A").variantType(VariantType.DUPLICATION).predicted(true);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinDuplicationMulti() {
		String val = "Ala3_Ser5dup";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("A", hgvsDescription.getWildType());
		assertEquals("S", hgvsDescription.getSecondWildType());
		assertEquals(5l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DUPLICATION, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testProteinDuplicationMultiRoundTrip() {
		String val = "Ala3_Ser5dup";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(3l).end(5l).wildType("A").secondWildType("S")
		.variantType(VariantType.DUPLICATION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinInsert() {
		String val = "His4_Gln5insAlaSer";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(4l, hgvsDescription.getStart().longValue());
		assertEquals("H", hgvsDescription.getWildType());
		assertEquals("Q", hgvsDescription.getSecondWildType());
		assertEquals(5l, hgvsDescription.getEnd().longValue());
		assertEquals("AS", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.INSERTION, hgvsDescription.getVariantType());

	}
	
	@Test
	public void testProteinInsertRoundTrip() {
		String val = "His4_Gln5insAlaSer";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(4l).end(5l).wildType("H").secondWildType("Q").varType("AS")
		.variantType(VariantType.INSERTION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinInsert2() {
		String val = "(His4_Gln5insAla)";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(4l, hgvsDescription.getStart().longValue());
		assertEquals("H", hgvsDescription.getWildType());
		assertEquals("Q", hgvsDescription.getSecondWildType());
		assertEquals(5l, hgvsDescription.getEnd().longValue());
		assertEquals("A", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.INSERTION, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testProteinInsert2RoundTrip() {
		String val = "(His4_Gln5insAla)";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(4l).end(5l).wildType("H").secondWildType("Q").varType("A")
		.variantType(VariantType.INSERTION).predicted(true);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinInsertNumber() {
		String val = "Arg78_Gly79ins23";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(78l, hgvsDescription.getStart().longValue());
		assertEquals("R", hgvsDescription.getWildType());
		assertEquals("G", hgvsDescription.getSecondWildType());
		assertEquals(79l, hgvsDescription.getEnd().longValue());
		assertEquals(null, hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals(23, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.INSERTION, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testProteinInsertNumberRoundTrip() {
		String val = "Arg78_Gly79ins23";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		AbstractMap.SimpleEntry<String, Integer> r 
		= new AbstractMap.SimpleEntry<String, Integer>(null, 23);
		builder.start(78l).repeat(r).end(79l).wildType("R").secondWildType("G")
		.variantType(VariantType.INSERTION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinDeletionInsertSingle() {
		String val = "Cys28delinsTrpVal";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(28l, hgvsDescription.getStart().longValue());
		assertEquals("C", hgvsDescription.getWildType());
		// assertEquals("Gly", hgvsDescription.getSecondWildType());
		// assertEquals(79l, hgvsDescription.getEnd().longValue());
		assertEquals("WV", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION_INSERTION, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testProteinDeletionInsertSingleRoundTrip() {
		String val = "Cys28delinsTrpVal";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(28l).wildType("C").varType("WV")
		.variantType(VariantType.DELETION_INSERTION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinDeletionInsertMulti() {
		String val = "Cys28_Lys29delinsTrp";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(28l, hgvsDescription.getStart().longValue());
		assertEquals("C", hgvsDescription.getWildType());
		assertEquals("K", hgvsDescription.getSecondWildType());
		assertEquals(29l, hgvsDescription.getEnd().longValue());
		assertEquals("W", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION_INSERTION, hgvsDescription.getVariantType());

	}
	
	@Test
	public void testProteinDeletionInsertMultiRoundTrip() {
		String val = "Cys28_Lys29delinsTrp";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(28l).end(29l).wildType("C").secondWildType("K").varType("W")
		.variantType(VariantType.DELETION_INSERTION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	
	
	@Test
	public void testProteinDeletionInsertMultiOneLett() {
		String val = "C177_P188delins*";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,false);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(177l, hgvsDescription.getStart().longValue());
		assertEquals("C", hgvsDescription.getWildType());
		assertEquals("P", hgvsDescription.getSecondWildType());
		assertEquals(188l, hgvsDescription.getEnd().longValue());
		assertEquals("*", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION_INSERTION, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testProteinDeletionInsertMultiOneLettRoundTrip() {
		String val = "C177_P188delins*";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.stop("*").start(177l).end(188l).wildType("C").secondWildType("P")
		.variantType(VariantType.DELETION_INSERTION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(false));
	}	
	
	
	@Test
	public void testProteinInsertMultiOneLett() {
		
		String val = "A1009_R1010insAAF*Q*";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,false);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(1009l, hgvsDescription.getStart().longValue());
		assertEquals("A", hgvsDescription.getWildType());
		assertEquals("R", hgvsDescription.getSecondWildType());
		assertEquals(1010l, hgvsDescription.getEnd().longValue());
		assertEquals("AAF*Q*", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.INSERTION, hgvsDescription.getVariantType());

	}
	
	@Test
	public void testProteinInsertMultiOneLettRoundTrip() {
		
		String val = "A1009_R1010insAAF*Q*";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(1009l).end(1010l).wildType("A").secondWildType("R").varType("AAF*Q*")
		.variantType(VariantType.INSERTION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(false));
	}	
	

	@Test
	public void testProteinDeletionInsertMulti2() {
		String val = "(Glu125_Ala132delinsGlyLeuHisArgPheIleValLeu)";

		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(125l, hgvsDescription.getStart().longValue());
		assertEquals("E", hgvsDescription.getWildType());
		assertEquals("A", hgvsDescription.getSecondWildType());
		assertEquals(132l, hgvsDescription.getEnd().longValue());
		assertEquals("GLHRFIVL", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION_INSERTION, hgvsDescription.getVariantType());
	}
	
	
	@Test
	public void testProteinDeletionInsertMulti2RoundTrip() {
		String val = "(Glu125_Ala132delinsGlyLeuHisArgPheIleValLeu)";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(125l).end(132l).wildType("E").secondWildType("A").varType("GLHRFIVL")
		.variantType(VariantType.DELETION_INSERTION).predicted(true);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinRepeatSingle() {
		String val = "Gln18[23]";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(18l, hgvsDescription.getStart().longValue());
		assertEquals("Q", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		assertEquals(null, hgvsDescription.getEnd());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals(23, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.REPEAT, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testProteinRepeatSingleRoundTrip() {
		String val = "Gln18[23]";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		AbstractMap.SimpleEntry<String, Integer> r 
		= new AbstractMap.SimpleEntry<String, Integer>("", 23);
		builder.start(18l).wildType("Q").repeat(r)
		.variantType(VariantType.REPEAT).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinRepeatMulti() {
		String val = "Arg65_Ser67[12]";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(65l, hgvsDescription.getStart().longValue());
		assertEquals("R", hgvsDescription.getWildType());
		assertEquals("S", hgvsDescription.getSecondWildType());
		assertEquals(67, hgvsDescription.getEnd().longValue());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals(12, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.REPEAT, hgvsDescription.getVariantType());
	}
	
	@Test
	public void testProteinRepeatMultiRoundTrip() {
		String val = "Arg65_Ser67[12]";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		AbstractMap.SimpleEntry<String, Integer> r 
		= new AbstractMap.SimpleEntry<String, Integer>("", 12);
		builder.start(65l).end(67l).wildType("R").secondWildType("S").repeat(r)
		.variantType(VariantType.REPEAT).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testRepeatDescriptionNotParsed() {
		String val = "(Gln18)[(70_80)]";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);

		assertEquals(val, hgvsDescription.getValue());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(VariantType.REPEAT, hgvsDescription.getVariantType());
	}

	@Test
	public void testProteinFrameshift() {
		String val = "Arg97ProfsTer23";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(97l, hgvsDescription.getStart().longValue());
		assertEquals("R", hgvsDescription.getWildType());
		assertEquals("P", hgvsDescription.getVarType());
		assertEquals("Ter23", hgvsDescription.getStop());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.FRAMESHIFT, hgvsDescription.getVariantType());
		assertTrue(hgvsDescription.hasTer());
		assertTrue(hgvsDescription.hasFrameShift());
	}
	
	
	@Test
	public void testProteinFrameshiftRoundTrip() {
		String val = "Arg97ProfsTer23";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.stop("*23").start(97l).wildType("R").varType("P")
		.variantType(VariantType.FRAMESHIFT).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	

	@Test
	public void testProteinFrameshiftShort() {
		String val = "Arg97fs";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(97l, hgvsDescription.getStart().longValue());
		assertEquals("R", hgvsDescription.getWildType());
		assertEquals("", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.FRAMESHIFT, hgvsDescription.getVariantType());
		assertFalse(hgvsDescription.hasTer());
		assertTrue(hgvsDescription.hasFrameShift());

	}
	
	
	@Test
	public void testProteinFrameshiftShortRoundTrip() {
		String val = "Arg97fs";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.frameShift("fs").start(97l).wildType("R").varType("")
		.variantType(VariantType.FRAMESHIFT).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinFrameshift2() {
		String val = "Pro633Leufs";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(633l, hgvsDescription.getStart().longValue());
		assertEquals("P", hgvsDescription.getWildType());
		assertEquals("L", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.FRAMESHIFT, hgvsDescription.getVariantType());
		assertFalse(hgvsDescription.hasTer());
		assertTrue(hgvsDescription.hasFrameShift());

	}
	
	
	@Test
	public void testProteinFrameshift2RoundTrip() {
		String val = "Pro633Leufs";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.start(633l).wildType("P").varType("L")
		.variantType(VariantType.FRAMESHIFT).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testProteinTerFrameshift() {
		String val = "*194Mfs*15";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,false);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(194l, hgvsDescription.getStart().longValue());
		assertEquals("*", hgvsDescription.getWildType());
		assertEquals("M", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.FRAMESHIFT, hgvsDescription.getVariantType());
		assertTrue(hgvsDescription.hasTer());
		assertTrue(hgvsDescription.hasFrameShift());

	}
	
	@Test
	public void testProteinTerFrameshiftRoundTrip() {
		String val = "Ter194MetfsTer15";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.stop("*15").start(194l).wildType("*").varType("M")
		.variantType(VariantType.FRAMESHIFT).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	
	
	
	
	@Test
	public void testProteinFrameshiftPred() {
		String val = "(Gln151Thrfs*9)";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(151l, hgvsDescription.getStart().longValue());
		assertEquals("Q", hgvsDescription.getWildType());
		assertEquals("T", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.FRAMESHIFT, hgvsDescription.getVariantType());
		assertTrue(hgvsDescription.hasTer());
		assertTrue(hgvsDescription.hasFrameShift());
	}
	
	
	@Test
	public void testProteinFrameshiftPredRoundTrip() {
		String val = "(Gln151ThrfsTer9)";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		builder.stop("*9").start(151l).wildType("Q").varType("T")
		.variantType(VariantType.FRAMESHIFT).predicted(true);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testExtensionMet() {
		String val = "Met1ext-5";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(1l, hgvsDescription.getStart().longValue());
		assertEquals("M", hgvsDescription.getWildType());
		assertEquals("?", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(-5, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.EXTENSION, hgvsDescription.getVariantType());
		assertFalse(hgvsDescription.hasTer());
		assertFalse(hgvsDescription.hasFrameShift());

	}
	
	
	@Test
	public void testExtensionMetRoundTrip() {
		String val = "Met1ext-5";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		AbstractMap.SimpleEntry<String, Integer> r 
		= new AbstractMap.SimpleEntry<String, Integer>(null, -5);
		builder.start(1l).repeat(r).wildType("M")
		.variantType(VariantType.EXTENSION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testExtensionMet2() {
		String val = "Met1Valext-12";

		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(1l, hgvsDescription.getStart().longValue());
		assertEquals("M", hgvsDescription.getWildType());
		assertEquals("V", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(-12, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.EXTENSION, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testExtensionMet2RoundTrip() {
		String val = "Met1Valext-12";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		AbstractMap.SimpleEntry<String, Integer> r 
		= new AbstractMap.SimpleEntry<String, Integer>(null, -12);
		builder.start(1l).repeat(r).wildType("M").varType("V")
		.variantType(VariantType.EXTENSION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	
	

	@Test
	public void testExtensionTer() {
		String val = "Ter110Glnext*17";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(110l, hgvsDescription.getStart().longValue());
		assertEquals("*", hgvsDescription.getWildType());
		assertEquals("Q", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(17, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.EXTENSION, hgvsDescription.getVariantType());

	}
	
	
	@Test
	public void testExtensionTerRoundTrip() {
		String val = "Ter110GlnextTer17";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		AbstractMap.SimpleEntry<String, Integer> r 
		= new AbstractMap.SimpleEntry<String, Integer>("*", 17);
		builder.start(110l).repeat(r).wildType("*").varType("Q")
		.variantType(VariantType.EXTENSION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testExtensionTerWithStar() {
		String val = "*110Glnext*17";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(110l, hgvsDescription.getStart().longValue());
		assertEquals("-", hgvsDescription.getWildType());
		assertEquals("Q", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(17, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.EXTENSION, hgvsDescription.getVariantType());
		assertTrue(hgvsDescription.hasTer());
		assertFalse(hgvsDescription.hasFrameShift());

	}
	
	@Test
	public void testExtensionTerWithStarRoundTrip() {
		String val = "Ter110GlnextTer17";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		AbstractMap.SimpleEntry<String, Integer> r 
		= new AbstractMap.SimpleEntry<String, Integer>("*", 17);
		builder.start(110l).repeat(r).wildType("*").varType("Q")
		.variantType(VariantType.EXTENSION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	
	

	@Test
	public void testExtensionLettersTer() {
		String val = "(Ter315TyrextAsnLysGlyThrTer)";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(315l, hgvsDescription.getStart().longValue());
		assertEquals("*", hgvsDescription.getWildType());
		assertEquals("Y", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("NKGT", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(1, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.EXTENSION, hgvsDescription.getVariantType());
		assertTrue(hgvsDescription.hasTer());
		assertFalse(hgvsDescription.hasFrameShift());

	}
	
	@Test
	public void testExtensionLettersTerRoundTrip() {
		String val = "(Ter315TyrextAsnLysGlyThrTer)";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		AbstractMap.SimpleEntry<String, Integer> r 
		= new AbstractMap.SimpleEntry<String, Integer>("NKGT*", null);
		builder.start(315l).repeat(r).wildType("*").varType("Y")
		.variantType(VariantType.EXTENSION).predicted(true);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
	}	
	

	@Test
	public void testExtensionLettersTerQuestion() {
		String val = "Ter327Argext*?";
		HgvsProteinDescripton hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val,true);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(327, hgvsDescription.getStart().longValue());
		assertEquals("*", hgvsDescription.getWildType());
		assertEquals("R", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(-1, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.EXTENSION, hgvsDescription.getVariantType());
		assertTrue(hgvsDescription.hasTer());
		assertFalse(hgvsDescription.hasFrameShift());
		

	}
	
	@Test
	public void testExtensionLettersTerQuestionRoundTrip() {
		String val = "Ter327ArgextTer?";
		HgvsProteinDescriptionImpl.Builder builder = HgvsProteinDescriptionImpl.builder();
		AbstractMap.SimpleEntry<String, Integer> r 
		= new AbstractMap.SimpleEntry<String, Integer>("*?", null);
		builder.start(327l).repeat(r).wildType("*").varType("R")
		.variantType(VariantType.EXTENSION).predicted(false);
		HgvsProteinDescripton hgvsDescription = builder.build();
		assertEquals(val,hgvsDescription.getDisplayValue(true));
		
	}
}
