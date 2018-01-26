package uk.ac.ebi.uniprot.variation.hgvs.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;

public class HgvsRnaDescriptionsParserTest {
	@Test
	public void testParseSubstitutionDescription() {
		String val = "76a>c";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(76, hgvsDescription.getStart().longValue());
		assertEquals("a", hgvsDescription.getWildType());
		assertEquals("c", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.SUBSTITUTION, hgvsDescription.getVariantType());

	}

	@Test
	public void testParseDeletionDescriptionSingle() {
		String val = "7del";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(7, hgvsDescription.getStart().longValue());
		assertNull(hgvsDescription.getEnd());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());

	}

	@Test
	public void testParseDeletionDescriptionMulti() {
		String val = "6_8del";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(6, hgvsDescription.getStart().longValue());
		assertEquals(8, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());

	}

	@Test
	public void testParseDeletionDescriptionMultiPred() {
		String val = "(4072_5145)del";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(4072, hgvsDescription.getStart().longValue());
		assertEquals(5145, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION, hgvsDescription.getVariantType());

	}

	@Test
	public void testDeplicationDescriptionSingle() {
		String val = "7dup";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(7, hgvsDescription.getStart().longValue());
		assertNull(hgvsDescription.getEnd());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DUPLICATION, hgvsDescription.getVariantType());
	}

	@Test
	public void testDeplicationDescriptionMulti() {
		String val = "6_8dup";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(6, hgvsDescription.getStart().longValue());
		assertEquals(8, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DUPLICATION, hgvsDescription.getVariantType());
	}

	@Test
	public void testInsertDescription() {
		String val = "756_757insuacu";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(756, hgvsDescription.getStart().longValue());
		assertEquals(757, hgvsDescription.getEnd().longValue());
		assertEquals("uacu", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.INSERTION, hgvsDescription.getVariantType());
	
	}
	
	@Test
	public void testInsertDescriptionPred() {
		String val = "(222_226)insg";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(222, hgvsDescription.getStart().longValue());
		assertEquals(226, hgvsDescription.getEnd().longValue());
		assertEquals("g", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.INSERTION, hgvsDescription.getVariantType());	
	}
	@Test
	public void testInversionDescription() {
		String val ="177_180inv";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(177, hgvsDescription.getStart().longValue());
		assertEquals(180, hgvsDescription.getEnd().longValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.INVERSION, hgvsDescription.getVariantType());	
		
		
	}

	@Test
	public void testConversionDescription() {
		String val="415_1655conAC096506.5:409_1649";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(415, hgvsDescription.getStart().longValue());
		assertEquals(1655, hgvsDescription.getStartCross().longValue());
		assertEquals(409, hgvsDescription.getEnd().longValue());
		assertEquals(1649, hgvsDescription.getEndCross().longValue());
		assertEquals("AC096506.5", hgvsDescription.getConversionSeqId());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.CONVERSION, hgvsDescription.getVariantType());	
	}
	@Test
	public void testDeletionInsertionDescriptionSingle() {
		String val ="775delinsga";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(775, hgvsDescription.getStart().longValue());
		
		assertEquals(775, hgvsDescription.getEnd().longValue());
		assertEquals("ga", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION_INSERTION, hgvsDescription.getVariantType());	

	}
	@Test
	public void testDeletionInsertionDescriptionMulti() {
		String val ="902_909delinsuuu";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(902, hgvsDescription.getStart().longValue());
		
		assertEquals(909, hgvsDescription.getEnd().longValue());
		assertEquals("uuu", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.DELETION_INSERTION, hgvsDescription.getVariantType());	
		
	
	}
	@Test
	public void testRepeatDescription() {
		String val ="-124_-123[14]";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(-124, hgvsDescription.getStart().longValue());
		
		assertEquals(-123, hgvsDescription.getEnd().longValue());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(14, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.REPEAT, hgvsDescription.getVariantType());	
	
	}
	
	@Test
	public void testRepeatDescriptionSeqSingle() {
		String val ="53AGC[19]";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(53, hgvsDescription.getStart().longValue());
		
		assertEquals(null, hgvsDescription.getEnd());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("AGC", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(19, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.REPEAT, hgvsDescription.getVariantType());
		
	
	}
	@Test
	public void testRepeatDescriptionSeqMulti() {
		String val ="53_55[31]";
		HgvsDescription hgvsDescription = HgvsRnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(53, hgvsDescription.getStart().longValue());
		
		assertEquals(55, hgvsDescription.getEnd().longValue());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(31, hgvsDescription.getRepeats().get(0).getValue().intValue());
		assertEquals(val, hgvsDescription.getValue());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(VariantType.REPEAT, hgvsDescription.getVariantType());

	}

}
