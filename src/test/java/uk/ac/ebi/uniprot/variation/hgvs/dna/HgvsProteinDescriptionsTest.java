package uk.ac.ebi.uniprot.variation.hgvs.dna;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.exception.InvalidadHgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;

public class HgvsProteinDescriptionsTest {
	@Test
	public void testSubstitutionDescriptionMissense() {
		String val = "Trp24Cys";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseSubstitutionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(24l, hgvsDescription.getStart().longValue());
		assertEquals("Trp", hgvsDescription.getWildType());
		assertEquals("Cys", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.SUBSTITUTION, hgvsDescription.getType());
	}

	@Test
	public void testSubstitutionDescriptionPred() {
		String val = "(Trp24Cys)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseSubstitutionDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(24l, hgvsDescription.getStart().longValue());
		assertEquals("Trp", hgvsDescription.getWildType());
		assertEquals("Cys", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.SUBSTITUTION, hgvsDescription.getType());
	}

	@Test
	public void testSubstitutionDescriptionNonsense() {
		String val = "Trp24*";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseSubstitutionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(24l, hgvsDescription.getStart().longValue());
		assertEquals("Trp", hgvsDescription.getWildType());
		assertEquals("*", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.SUBSTITUTION, hgvsDescription.getType());
	}

	@Test
	public void testSubstitutionDescriptionSilent() {
		String val = "Cys188=";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseSubstitutionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(188l, hgvsDescription.getStart().longValue());
		assertEquals("Cys", hgvsDescription.getWildType());
		assertEquals("=", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.SUBSTITUTION, hgvsDescription.getType());
	}

	@Test
	public void testSubstitutionDescriptionUnknow() {
		String val = "Cys188?";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseSubstitutionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(188l, hgvsDescription.getStart().longValue());
		assertEquals("Cys", hgvsDescription.getWildType());
		assertEquals("?", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.SUBSTITUTION, hgvsDescription.getType());

	}

	@Test(expected = InvalidadHgvsDescription.class)
	public void testSubstitutionWrong() {
		String val = "33038255C/A";
		HgvsProteinDescriptions.parseSubstitutionDescription(val);

	}

	@Test
	public void testProteinDeletionSingle() {
		String val = "Ala3del";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDeletionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("Ala", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION, hgvsDescription.getType());

	}

	@Test
	public void testProteinDeletionSinglePred() {
		String val = "(Ala3del)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDeletionDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("Ala", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION, hgvsDescription.getType());
	}

	@Test
	public void testProteinDeletionMulti() {
		String val = "Ala3_Ser5del";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDeletionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("Ala", hgvsDescription.getWildType());
		assertEquals("Ser", hgvsDescription.getSecondWildType());
		assertEquals(5l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION, hgvsDescription.getType());
	}

	@Test
	public void testProteinDeletionMulti2() {
		String val = "Gly2_Met46del";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDeletionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(2l, hgvsDescription.getStart().longValue());
		assertEquals("Gly", hgvsDescription.getWildType());
		assertEquals("Met", hgvsDescription.getSecondWildType());
		assertEquals(46l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION, hgvsDescription.getType());
	}

	@Test
	public void testProteinDuplicationSingle() {
		String val = "Ala3dup";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDuplicationDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("Ala", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		// assertEquals(46l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DUPLICATION, hgvsDescription.getType());
	}

	@Test
	public void testProteinDuplicationSinglePred() {
		String val = "(Ala3dup)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDuplicationDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("Ala", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		// assertEquals(46l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DUPLICATION, hgvsDescription.getType());
	}

	@Test
	public void testProteinDuplicationMulti() {
		String val = "Ala3_Ser5dup";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDuplicationDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(3l, hgvsDescription.getStart().longValue());
		assertEquals("Ala", hgvsDescription.getWildType());
		assertEquals("Ser", hgvsDescription.getSecondWildType());
		assertEquals(5l, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DUPLICATION, hgvsDescription.getType());

	}

	@Test
	public void testProteinInsert() {
		String val = "His4_Gln5insAlaSer";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseInsertionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(4l, hgvsDescription.getStart().longValue());
		assertEquals("His", hgvsDescription.getWildType());
		assertEquals("Gln", hgvsDescription.getSecondWildType());
		assertEquals(5l, hgvsDescription.getEnd().longValue());
		assertEquals("AlaSer", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.INSERTION, hgvsDescription.getType());

	}

	@Test
	public void testProteinInsert2() {
		String val = "(His4_Gln5insAla)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseInsertionDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(4l, hgvsDescription.getStart().longValue());
		assertEquals("His", hgvsDescription.getWildType());
		assertEquals("Gln", hgvsDescription.getSecondWildType());
		assertEquals(5l, hgvsDescription.getEnd().longValue());
		assertEquals("Ala", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.INSERTION, hgvsDescription.getType());

	}

	@Test
	public void testProteinInsertNumber() {
		String val = "Arg78_Gly79ins23";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseInsertionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(78l, hgvsDescription.getStart().longValue());
		assertEquals("Arg", hgvsDescription.getWildType());
		assertEquals("Gly", hgvsDescription.getSecondWildType());
		assertEquals(79l, hgvsDescription.getEnd().longValue());
		assertEquals(null, hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals(23, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.INSERTION, hgvsDescription.getType());

	}

	@Test
	public void testProteinDeletionInsertSingle() {
		String val = "Cys28delinsTrpVal";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDeletionInsertionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(28l, hgvsDescription.getStart().longValue());
		assertEquals("Cys", hgvsDescription.getWildType());
		// assertEquals("Gly", hgvsDescription.getSecondWildType());
		// assertEquals(79l, hgvsDescription.getEnd().longValue());
		assertEquals("TrpVal", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION_INSERTION, hgvsDescription.getType());

	}

	@Test
	public void testProteinDeletionInsertMulti() {
		String val = "Cys28_Lys29delinsTrp";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDeletionInsertionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(28l, hgvsDescription.getStart().longValue());
		assertEquals("Cys", hgvsDescription.getWildType());
		assertEquals("Lys", hgvsDescription.getSecondWildType());
		assertEquals(29l, hgvsDescription.getEnd().longValue());
		assertEquals("Trp", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION_INSERTION, hgvsDescription.getType());

	}

	@Test
	public void testProteinDeletionInsertMulti2() {
		String val = "(Glu125_Ala132delinsGlyLeuHisArgPheIleValLeu)";

		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseDeletionInsertionDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(125l, hgvsDescription.getStart().longValue());
		assertEquals("Glu", hgvsDescription.getWildType());
		assertEquals("Ala", hgvsDescription.getSecondWildType());
		assertEquals(132l, hgvsDescription.getEnd().longValue());
		assertEquals("GlyLeuHisArgPheIleValLeu", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION_INSERTION, hgvsDescription.getType());
	}

	@Test
	public void testProteinRepeatSingle() {
		String val = "Gln18[23]";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseRepeatDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(18l, hgvsDescription.getStart().longValue());
		assertEquals("Gln", hgvsDescription.getWildType());
		assertEquals(null, hgvsDescription.getSecondWildType());
		assertEquals(null, hgvsDescription.getEnd());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals(23, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.REPEAT, hgvsDescription.getType());

	}

	@Test
	public void testProteinRepeatMulti() {
		String val = "Arg65_Ser67[12]";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseRepeatDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(65l, hgvsDescription.getStart().longValue());
		assertEquals("Arg", hgvsDescription.getWildType());
		assertEquals("Ser", hgvsDescription.getSecondWildType());
		assertEquals(67, hgvsDescription.getEnd().longValue());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals(12, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.REPEAT, hgvsDescription.getType());
	}

	@Test
	public void testRepeatDescriptionNotParsed() {
		String val = "(Gln18)[(70_80)]";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseRepeatDescription(val);

		assertEquals(val, hgvsDescription.getDescription());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(HgvsType.REPEAT, hgvsDescription.getType());
	}

	@Test
	public void testProteinFrameshift() {
		String val = "Arg97ProfsTer23";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseFrameshiftDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(97l, hgvsDescription.getStart().longValue());
		assertEquals("Arg", hgvsDescription.getWildType());
		assertEquals("Pro", hgvsDescription.getVarType());
		assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.FRAMESHIFT, hgvsDescription.getType());
	}

	@Test
	public void testProteinFrameshiftShort() {
		String val = "Arg97fs";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseFrameshiftDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(97l, hgvsDescription.getStart().longValue());
		assertEquals("Arg", hgvsDescription.getWildType());
		assertEquals("*", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.FRAMESHIFT, hgvsDescription.getType());

	}

	@Test
	public void testProteinFrameshift2() {
		String val = "Pro633Leufs";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseFrameshiftDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(633l, hgvsDescription.getStart().longValue());
		assertEquals("Pro", hgvsDescription.getWildType());
		assertEquals("Leu", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.FRAMESHIFT, hgvsDescription.getType());

	}

	@Test
	public void testProteinFrameshiftPred() {
		String val = "(Gln151Thrfs*9)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseFrameshiftDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(151l, hgvsDescription.getStart().longValue());
		assertEquals("Gln", hgvsDescription.getWildType());
		assertEquals("Thr", hgvsDescription.getVarType());
		// assertEquals(23, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.FRAMESHIFT, hgvsDescription.getType());
	}

	@Test
	public void testExtensionMet() {
		String val = "Met1ext-5";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseExtensionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(1l, hgvsDescription.getStart().longValue());
		assertEquals("Met", hgvsDescription.getWildType());
		assertEquals("?", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(-5, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.EXTENSION, hgvsDescription.getType());

	}

	@Test
	public void testExtensionMet2() {
		String val = "Met1Valext-12";

		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseExtensionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(1l, hgvsDescription.getStart().longValue());
		assertEquals("Met", hgvsDescription.getWildType());
		assertEquals("Val", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(-12, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.EXTENSION, hgvsDescription.getType());

	}

	@Test
	public void testExtensionTer() {
		String val = "Ter110Glnext*17";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseExtensionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(110l, hgvsDescription.getStart().longValue());
		assertEquals("Ter", hgvsDescription.getWildType());
		assertEquals("Gln", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(17, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.EXTENSION, hgvsDescription.getType());

	}

	@Test
	public void testExtensionTerWithStar() {
		String val = "*110Glnext*17";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseExtensionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(110l, hgvsDescription.getStart().longValue());
		assertEquals("*", hgvsDescription.getWildType());
		assertEquals("Gln", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(17, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.EXTENSION, hgvsDescription.getType());

	}

	@Test
	public void testExtensionLettersTer() {
		String val = "(Ter315TyrextAsnLysGlyThrTer)";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseExtensionDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(315l, hgvsDescription.getStart().longValue());
		assertEquals("Ter", hgvsDescription.getWildType());
		assertEquals("Tyr", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("AsnLysGlyThr", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(1, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.EXTENSION, hgvsDescription.getType());

	}

	@Test
	public void testExtensionLettersTerQuestion() {
		String val = "Ter327Argext*?";
		HgvsDescription hgvsDescription = HgvsProteinDescriptions.parseExtensionDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(327, hgvsDescription.getStart().longValue());
		assertEquals("Ter", hgvsDescription.getWildType());
		assertEquals("Arg", hgvsDescription.getVarType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("*", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(-1, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.EXTENSION, hgvsDescription.getType());

	}

}
