package uk.ac.ebi.uniprot.variation.hgvs.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;

public class HgvsProteinDescriptionParserTest {
	@Test
	public void testSubstitutionDescriptionMissense() {
		String val = "Trp24Cys";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(24l, hgvsDescription.getStart().longValue());
		assertEquals("W", hgvsDescription.getWildType());
		assertEquals("C", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());
	}

	@Test
	public void testSubstitutionDescriptionPred() {
		String val = "(Trp24Cys)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(24l, hgvsDescription.getStart().longValue());
		assertEquals("W", hgvsDescription.getWildType());
		assertEquals("C", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());
	}

	@Test
	public void testSubstitutionDescriptionNonsense() {
		String val = "Trp24*";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(24l, hgvsDescription.getStart().longValue());
		assertEquals("W", hgvsDescription.getWildType());
		assertEquals("-", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());
	}

	@Test
	public void testSubstitutionDescriptionSilent() {
		String val = "Cys188=";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(188l, hgvsDescription.getStart().longValue());
		assertEquals("C", hgvsDescription.getWildType());
		assertEquals("-", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());
	}

	@Test
	public void testSubstitutionDescriptionUnknow() {
		String val = "Cys188?";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(188l, hgvsDescription.getStart().longValue());
		assertEquals("C", hgvsDescription.getWildType());
		assertEquals("-", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());

	}

	@Test
	public void testUnsupportedHgvsDescriptionWrong() {
		String val = "33038255C/A";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isParsed());
		assertEquals(val, hgvsDescription.getValue());
		assertEquals(VariantType.UNKNOWN, hgvsDescription.getVariantType());

	}

	@Test
	public void testProteinDeletionSingle() {
		String val = "Ala3del";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("A", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());

	}

	@Test
	public void testProteinDeletionSinglePred() {
		String val = "(Ala3del)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("A", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());
	}

	@Test
	public void testProteinDeletionMulti() {
		String val = "Ala3_Ser5del";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinDeletionMulti2() {
		String val = "Gly2_Met46del";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinDuplicationSingle() {
		String val = "Ala3dup";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinDuplicationSinglePred() {
		String val = "(Ala3dup)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinDuplicationMulti() {
		String val = "Ala3_Ser5dup";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinInsert() {
		String val = "His4_Gln5insAlaSer";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinInsert2() {
		String val = "(His4_Gln5insAla)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinInsertNumber() {
		String val = "Arg78_Gly79ins23";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinDeletionInsertSingle() {
		String val = "Cys28delinsTrpVal";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinDeletionInsertMulti() {
		String val = "Cys28_Lys29delinsTrp";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinDeletionInsertMulti2() {
		String val = "(Glu125_Ala132delinsGlyLeuHisArgPheIleValLeu)";

		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinRepeatSingle() {
		String val = "Gln18[23]";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testProteinRepeatMulti() {
		String val = "Arg65_Ser67[12]";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testRepeatDescriptionNotParsed() {
		String val = "(Gln18)[(70_80)]";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);

		assertEquals(val, hgvsDescription.getValue());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(VariantType.REPEAT, hgvsDescription.getVariantType());
	}

	@Test
	public void testProteinFrameshift() {
		String val = "Arg97ProfsTer23";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(97l, hgvsDescription.getStart().longValue());
		assertEquals("R", hgvsDescription.getWildType());
		assertEquals("P", hgvsDescription.getVarType());
		assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.FRAMESHIFT, hgvsDescription.getVariantType());
	}

	@Test
	public void testProteinFrameshiftShort() {
		String val = "Arg97fs";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(97l, hgvsDescription.getStart().longValue());
		assertEquals("R", hgvsDescription.getWildType());
		assertEquals("*", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.FRAMESHIFT, hgvsDescription.getVariantType());

	}

	@Test
	public void testProteinFrameshift2() {
		String val = "Pro633Leufs";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(633l, hgvsDescription.getStart().longValue());
		assertEquals("P", hgvsDescription.getWildType());
		assertEquals("L", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.FRAMESHIFT, hgvsDescription.getVariantType());

	}

	@Test
	public void testProteinFrameshiftPred() {
		String val = "(Gln151Thrfs*9)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(151l, hgvsDescription.getStart().longValue());
		assertEquals("Q", hgvsDescription.getWildType());
		assertEquals("T", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.FRAMESHIFT, hgvsDescription.getVariantType());
	}

	@Test
	public void testExtensionMet() {
		String val = "Met1ext-5";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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

	}

	@Test
	public void testExtensionMet2() {
		String val = "Met1Valext-12";

		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testExtensionTer() {
		String val = "Ter110Glnext*17";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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
	public void testExtensionTerWithStar() {
		String val = "*110Glnext*17";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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

	}

	@Test
	public void testExtensionLettersTer() {
		String val = "(Ter315TyrextAsnLysGlyThrTer)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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

	}

	@Test
	public void testExtensionLettersTerQuestion() {
		String val = "Ter327Argext*?";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseHgvsDescription(val);
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

	}
}
