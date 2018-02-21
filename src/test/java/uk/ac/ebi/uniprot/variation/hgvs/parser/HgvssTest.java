package uk.ac.ebi.uniprot.variation.hgvs.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;

public class HgvssTest {
	@Test
	public void testFromCDnaHgvs() {
		String val = "ENSMUST00000082421.1:c.115G>A";
		Hgvs hgvs = Hgvss.from(val);
		verify(hgvs, "ENSMUST00000082421.1", HgvsType.CDNA, "115G>A", val);
	}

	@Test(expected = InvalidHgvsException.class)
	public void wrongSequenceType() {
		String val = "ENSMUST00000082421.1:a.115G>A";
		Hgvs hgvs = Hgvss.from(val);
		verify(hgvs, "ENSMUST00000082421.1", HgvsType.CDNA, "115G>A", val);

	}

	@Test(expected = InvalidHgvsException.class)
	public void wrongHgvs() {
		String val = "ENSMUST00000082421.1:g115G>A";
		Hgvs hgvs = Hgvss.from(val);
		verify(hgvs, "ENSMUST00000082421.1", HgvsType.CDNA, "115G>A", val);
	}

	@Test
	public void testFromProteinHgvs() {
		String val = "NP_003997.1:p.(Trp24Cys)";
		Hgvs hgvs = Hgvss.from(val);
		verify(hgvs, "NP_003997.1", HgvsType.PROTEIN, "(Trp24Cys)", val);
	}

	@Test
	public void testFromRnaHgvs() {
		String val = "NM_004006.1:r.14a>c";
		Hgvs hgvs = Hgvss.from(val);
		verify(hgvs, "NM_004006.1", HgvsType.RNA, "14a>c", val);
	}

	private void verify(Hgvs hgvs, String seqId, HgvsType seqType, String description, String value) {
		assertEquals(seqId, hgvs.getSequenceId());
		assertEquals(seqType, hgvs.getType());
		assertEquals(description, hgvs.getDescription().getValue());
		assertEquals(value, hgvs.getValue());
	}
}
