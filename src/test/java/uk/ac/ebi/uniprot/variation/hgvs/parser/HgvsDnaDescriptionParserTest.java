package uk.ac.ebi.uniprot.variation.hgvs.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.exception.InvalidadHgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;
import uk.ac.ebi.uniprot.variation.hgvs.parser.HgvsDnaDescriptions;

public class HgvsDnaDescriptionParserTest {
	@Test
	public void testParseSubstitutionDescription() {
		String val = "33038255C>A";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertFalse(hgvsDescription.isPredicted());
		assertEquals(33038255l, hgvsDescription.getStart().longValue());
		assertEquals("C", hgvsDescription.getWildType());
		assertEquals("A", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.SUBSTITUTION, hgvsDescription.getType());

	}

	@Test
	public void testSubstitutionPred() {
		String val = "(33038255C>A)";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertTrue(hgvsDescription.isPredicted());
		assertEquals(33038255l, hgvsDescription.getStart().longValue());
		assertEquals("C", hgvsDescription.getWildType());
		assertEquals("A", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.SUBSTITUTION, hgvsDescription.getType());
	}

	@Test
	public void testSubstitutionWrong() {
		String val = "33038255C/A";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(val, hgvsDescription.getDescription());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(HgvsType.UNKNOWN, hgvsDescription.getType());

	}

	@Test
	public void testParseDeletionDescriptionSingle() {
		String val = "19del";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(19l, hgvsDescription.getStart().longValue());
		assertNull(hgvsDescription.getStartCross());
		assertNull(hgvsDescription.getEnd());
		assertNull(hgvsDescription.getEndCross());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION, hgvsDescription.getType());

	}

	@Test
	public void testParseDeletionDescriptionMulti() {
		String val = "19_21del";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(19l, hgvsDescription.getStart().longValue());
		assertNull(hgvsDescription.getStartCross());
		assertEquals(21, hgvsDescription.getEnd().longValue());
		assertNull(hgvsDescription.getEndCross());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION, hgvsDescription.getType());

	}

	@Test
	public void testParseDeletionDescriptionMultiEnd() {
		String val = "183_186+48del";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(183l, hgvsDescription.getStart().longValue());
		assertNull(hgvsDescription.getStartCross());
		assertEquals(186l, hgvsDescription.getEnd().longValue());
		assertEquals(48l, hgvsDescription.getEndCross().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION, hgvsDescription.getType());
	}

	@Test
	public void testParseDeletionDescriptionMultiBoth() {
		String val = "4072+1234_5155+246del";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(4072l, hgvsDescription.getStart().longValue());
		assertEquals(1234, hgvsDescription.getStartCross().longValue());
		assertEquals(5155l, hgvsDescription.getEnd().longValue());
		assertEquals(246l, hgvsDescription.getEndCross().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION, hgvsDescription.getType());
	}

	@Test
	public void testParseDeletionDescriptionNotParsed() {
		String val = "(4071+1_4072-1)_(5154+1_5155-1)del";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(val, hgvsDescription.getDescription());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION, hgvsDescription.getType());
	}

	@Test
	public void testParseDuplicationDescriptionSimple() {
		String val = "20dup";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(20l, hgvsDescription.getStart().longValue());
		assertNull(hgvsDescription.getStartCross());
		assertNull(hgvsDescription.getEnd());
		assertNull(hgvsDescription.getEndCross());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DUPLICATION, hgvsDescription.getType());

	}

	@Test
	public void testParseDuplicationDescriptionMulti() {
		String val = "20_23dup";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(20l, hgvsDescription.getStart().longValue());
		assertNull(hgvsDescription.getStartCross());
		assertEquals(23, hgvsDescription.getEnd().longValue());
		assertNull(hgvsDescription.getEndCross());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DUPLICATION, hgvsDescription.getType());
	}

	@Test
	public void testParseDuplicationDescriptionMultiEnd() {
		String val = "260_264+48dup";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(260l, hgvsDescription.getStart().longValue());
		assertNull(hgvsDescription.getStartCross());
		assertEquals(264, hgvsDescription.getEnd().longValue());
		assertEquals(48, hgvsDescription.getEndCross().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DUPLICATION, hgvsDescription.getType());
	}

	@Test
	public void testParseDuplicationDescriptionMultiBoth() {
		String val = "4072-1234_5155-246dup";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(4072l, hgvsDescription.getStart().longValue());
		assertEquals(1234, hgvsDescription.getStartCross().longValue());
		assertEquals(5155, hgvsDescription.getEnd().longValue());
		assertEquals(246, hgvsDescription.getEndCross().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DUPLICATION, hgvsDescription.getType());
	}

	@Test
	public void testParseDuplicationDescriptionNotParsed() {
		String val = "(4071+1_4072-1)_(5154+1_5155-1)dup";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(val, hgvsDescription.getDescription());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(HgvsType.DUPLICATION, hgvsDescription.getType());
	}

	@Test
	public void testParseInsertionDescription() {
		String val = "32862923_32862924insCCT";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(32862923l, hgvsDescription.getStart().longValue());

		assertNull(hgvsDescription.getStartCross());
		assertEquals(32862924l, hgvsDescription.getEnd().longValue());
		assertNull(hgvsDescription.getEndCross());
		assertNull(hgvsDescription.getWildType());
		assertEquals("CCT", hgvsDescription.getVarType());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.INSERTION, hgvsDescription.getType());

	}

	@Test
	public void testParseInsertionDescriptionNotParsed() {
		String val = "419_420ins[T;401_419]";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(val, hgvsDescription.getDescription());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(HgvsType.INSERTION, hgvsDescription.getType());
	}

	@Test
	public void testParseInversionDescription() {
		String val = "1077_1080inv";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(1077l, hgvsDescription.getStart().longValue());

		assertNull(hgvsDescription.getStartCross());
		assertEquals(1080l, hgvsDescription.getEnd().longValue());
		assertNull(hgvsDescription.getEndCross());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.INVERSION, hgvsDescription.getType());

	}

	@Test
	public void testParseInversionDescriptionNotParsed() {
		String val = "122_123ins213_234invinsAins123_211inv";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(val, hgvsDescription.getDescription());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(HgvsType.INSERTION_INVERSION, hgvsDescription.getType());
	}

	@Test
	public void testParseInsertionInversionDescription() {
		String val = "122_123ins123_234inv";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(122l, hgvsDescription.getStart().longValue());

		assertEquals(123, hgvsDescription.getStartCross().longValue());
		assertEquals(123l, hgvsDescription.getEnd().longValue());
		assertEquals(234l, hgvsDescription.getEndCross().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.INSERTION_INVERSION, hgvsDescription.getType());

	}

	@Test
	public void testParseInsertionInversionDescriptionNotParsed() {
		String val = "122_123ins213_234invinsAins123_211inv";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(val, hgvsDescription.getDescription());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(HgvsType.INSERTION_INVERSION, hgvsDescription.getType());
	}

	@Test
	public void testParseConversionDescription() {
		String val = "42522624_42522669con42536337_42536382";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(42522624, hgvsDescription.getStart().longValue());

		assertEquals(42522669, hgvsDescription.getStartCross().longValue());
		assertEquals(42536337, hgvsDescription.getEnd().longValue());
		assertEquals(42536382, hgvsDescription.getEndCross().longValue());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.CONVERSION, hgvsDescription.getType());

	}

	@Test
	public void testParseConversionDescriptionWithId() {
		String val = "6128892_6128954conNC_000022.10:17179029_17179091";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(6128892, hgvsDescription.getStart().longValue());

		assertEquals(6128954, hgvsDescription.getStartCross().longValue());
		assertEquals(17179029, hgvsDescription.getEnd().longValue());
		assertEquals(17179091, hgvsDescription.getEndCross().longValue());
		assertEquals("NC_000022.10", hgvsDescription.getConversionSeqId());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.CONVERSION, hgvsDescription.getType());
	}

	@Test
	public void testParseDeletionInsertionDescriptionSingle() {
		String val = "6775delinsGA";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(6775, hgvsDescription.getStart().longValue());

		assertNull(hgvsDescription.getStartCross());
		assertEquals(6775, hgvsDescription.getEnd().longValue());
		assertNull(hgvsDescription.getEndCross());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION_INSERTION, hgvsDescription.getType());
		assertEquals("GA", hgvsDescription.getVarType());
	}

	@Test
	public void testParseDeletionInsertionDescriptionMulti() {
		String val = "142_144delinsTGG";

		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(142, hgvsDescription.getStart().longValue());

		assertNull(hgvsDescription.getStartCross());
		assertEquals(144, hgvsDescription.getEnd().longValue());
		assertNull(hgvsDescription.getEndCross());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.DELETION_INSERTION, hgvsDescription.getType());
		assertEquals("TGG", hgvsDescription.getVarType());

	}

	@Test
	public void testParseRepeatDescriptionSingle() {
		String val = "101179660TG[14]";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(101179660l, hgvsDescription.getStart().longValue());

		assertNull(hgvsDescription.getStartCross());
		assertNull(hgvsDescription.getEnd());
		assertNull(hgvsDescription.getEndCross());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.REPEAT, hgvsDescription.getType());
		assertEquals(1, hgvsDescription.getRepeats().size());
		assertEquals("TG", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(14,  hgvsDescription.getRepeats().get(0).getValue().intValue());

	}

	@Test
	public void testParseRepeatDescriptionMulti() {
		String val = "112036755_112036823CTG[9]TTG[1]CTG[13]";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(112036755, hgvsDescription.getStart().longValue());

		assertNull(hgvsDescription.getStartCross());
		assertEquals(112036823, hgvsDescription.getEnd().longValue());
		assertNull(hgvsDescription.getEndCross());
		assertEquals(val, hgvsDescription.getDescription());
		assertTrue(hgvsDescription.isParsed());
		assertEquals(HgvsType.REPEAT, hgvsDescription.getType());
		assertEquals(3, hgvsDescription.getRepeats().size());
		assertEquals("CTG", hgvsDescription.getRepeats().get(0).getKey());
		assertEquals(9,  hgvsDescription.getRepeats().get(0).getValue().intValue());
		
		assertEquals("TTG", hgvsDescription.getRepeats().get(1).getKey());
		assertEquals(1,  hgvsDescription.getRepeats().get(1).getValue().intValue());
		
		assertEquals("CTG", hgvsDescription.getRepeats().get(2).getKey());
		assertEquals(13,  hgvsDescription.getRepeats().get(2).getValue().intValue());
		
//		Matcher matcher = HgvsDnaDescriptions.HGVS_REPEAT_PATTERN.matcher(val);
//		if (matcher.matches()) {
//			for (int i = 0; i <= matcher.groupCount(); i++) {
//				System.out.println(i + "\t" + matcher.group(i));
//			}
//			String repeats=matcher.group(9);
//			while(repeats !=null) {
//				Matcher matcher2 =HgvsDnaDescriptions.REPEAT_PATTERN.matcher(repeats );
//				if(matcher2.matches()) {
//					for (int i = 0; i <= matcher2.groupCount(); i++) {
//						System.out.println("Matcher2= " +i + "\t" + matcher2.group(i));
//					}
//					repeats = matcher2.group(5);
//				}else {
//					System.out.println("matcher2 not matchered");
//					//break;
//				}
//				
//				
//			}
//		} else {
//			System.out.println("Failed");
//		}
	}
	@Test
	public void testParseRepeatDescriptionNotParsed() {
		String val ="NM_002024.5:c.-128(GGC)[(600_800)]";
		HgvsDescription hgvsDescription = HgvsDnaDescriptions.parseHgvsDescription(val);
		assertEquals(val, hgvsDescription.getDescription());
		assertFalse(hgvsDescription.isParsed());
		assertEquals(HgvsType.REPEAT, hgvsDescription.getType());
	}
}
