package uk.ac.ebi.uniprot.variation.hgvs.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.SequenceType;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.protein.ProteinHgvss;
import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;

public class HgvsImplTest {
	@Test
	public void testFromCDnaHgvs() {
		String val = "ENSMUST00000082421.1:c.115G>A";
		Hgvs hgvs = HgvsImpl.from(val);
		verify(hgvs, "ENSMUST00000082421.1", SequenceType.CDNA, "115G>A");
	}

	@Test(expected = InvalidHgvsException.class)
	public void wrongSequenceType() {
		String val = "ENSMUST00000082421.1:a.115G>A";
		Hgvs hgvs = HgvsImpl.from(val);
		verify(hgvs, "ENSMUST00000082421.1", SequenceType.CDNA, "115G>A");

	}

	@Test(expected = InvalidHgvsException.class)
	public void wrongHgvs() {
		String val = "ENSMUST00000082421.1:g115G>A";
		Hgvs hgvs = HgvsImpl.from(val);
		verify(hgvs, "ENSMUST00000082421.1", SequenceType.CDNA, "115G>A");
	}

	@Test
	public void testFromProteinHgvs() {
		String val = "NP_003997.1:p.(Trp24Cys)";
		Hgvs hgvs = HgvsImpl.from(val);
		verify(hgvs, "NP_003997.1", SequenceType.PROTEIN, "(Trp24Cys)");
	}

	@Test
	public void testFromRnaHgvs() {
		String val = "NM_004006.1:r.14a>c";
		Hgvs hgvs = HgvsImpl.from(val);
		verify(hgvs, "NM_004006.1", SequenceType.RNA, "14a>c");
	}

	private void verify(Hgvs hgvs, String seqId, SequenceType seqType, String description) {
		assertEquals(seqId, hgvs.getSequenceId());
		assertEquals(seqType, hgvs.getSequenceType());
		assertEquals(description, hgvs.getDescription());
	}
}
