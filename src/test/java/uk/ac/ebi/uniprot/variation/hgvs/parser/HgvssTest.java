package uk.ac.ebi.uniprot.variation.hgvs.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsProteinDescripton;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;

public class HgvssTest {
    @Test
    public void testFromCDnaHgvs() {
        String val = "ENSMUST00000082421.1:c.115G>A";
        Hgvs hgvs = Hgvss.from(val, false);
        verify(hgvs, "ENSMUST00000082421.1", HgvsType.CDNA, "115G>A", val);
    }

    @Test
    public void wrongSequenceType() {
        String val = "ENSMUST00000082421.1:a.115G>A";

        assertThrows(InvalidHgvsException.class, () -> {
            Hgvss.from(val, false);
        });
    }

    @Test
    public void wrongHgvs() {
        String val = "ENSMUST00000082421.1:g115G>A";
        assertThrows(InvalidHgvsException.class, () -> {
            Hgvss.from(val, false);
        });

    }

    @Test
    public void testDashAndVersionHgvs() {
        String val = "AAEL019493-PA.1:p.Lys196Arg";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "AAEL019493-PA.1", HgvsType.PROTEIN, "Lys196Arg", val);
    }

    @Test
    public void testDelExtTerHgvs1() {
        String val = "ENSGALP00000006654.5:p.LeuTer533delextTer?";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "ENSGALP00000006654.5", HgvsType.PROTEIN, "LeuTer533delextTer?", val);
    }

    @Test
    public void testDelExtTerHgvs2() {
        String val = "ENSCAFP00000001124.4:p.SerLeuTer1772delextTer71";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "ENSCAFP00000001124.4", HgvsType.PROTEIN, "SerLeuTer1772delextTer71", val);
    }

    @Test
    public void testDelExtTerHgvs3() {
        String val = "Solyc01g103530.3.1:p.AsnLeuValTer993delextTer43";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "Solyc01g103530.3.1", HgvsType.PROTEIN, "AsnLeuValTer993delextTer43", val);
    }

    @Test
    public void testDelExtTerHgvs4() {
        String val = "Solyc11g071330.2.1:p.LysGluTer213delextTer16";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "Solyc11g071330.2.1", HgvsType.PROTEIN, "LysGluTer213delextTer16", val);
    }

    @Test // (expected = InvalidHgvsException.class)
    public void testBadHgvs() {
        String val = "Solyc04g025035.1.1:p.Met1_?3";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "Solyc04g025035.1.1", HgvsType.PROTEIN, "Met1_?3", val);
    }

    @Test
    public void testFromProteinHgvs() {
        String val = "NP_003997.1:p.(Trp24Cys)";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "NP_003997.1", HgvsType.PROTEIN, "(Trp24Cys)", val);
    }

    @Test
    public void testFromPombeProteinHgvs() {
        String val = "SPAC1805.18.1:pep.1:p.Arg78Trp";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "SPAC1805.18.1:pep.1", HgvsType.PROTEIN, "Arg78Trp", val);
    }

    @Test
    public void testLRGHgvswithDash() {
        String val = "LRG_321t1-1:c.978A>T";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "LRG_321t1-1", HgvsType.CDNA, "978A>T", val);
    }

    @Test
    public void testFromRnaHgvs() {
        String val = "NM_004006.1:r.14a>c";
        Hgvs hgvs = Hgvss.from(val, false);
        verify(hgvs, "NM_004006.1", HgvsType.RNA, "14a>c", val);
    }
    
    @Test
    public void testFromRnaHgvsWithBracket() {
        String val = "NM_000277.1(PAH):c.1097C>A";
        Hgvs hgvs = Hgvss.from(val, false);
        verify(hgvs, "NM_000277.1", HgvsType.CDNA, "1097C>A", "NM_000277.1:c.1097C>A");
    }
    
    @Test
    public void testFromRnaHgvsWithBracket2() {
        String val = "NM_001754.4(RUNX1):c.*1683_*1684del";
        Hgvs hgvs = Hgvss.from(val, false);
        verify(hgvs, "NM_001754.4", HgvsType.CDNA, "*1683_*1684del", "NM_001754.4:c.*1683_*1684del");
    }
    
    
    @Test
    public void testFromRnaHgvs2() {
        String val = "p.Val589Ter";
        Hgvs hgvs = Hgvss.from(val, true);
        verify(hgvs, "", HgvsType.PROTEIN, "Val589Ter", val);
    }
    
    

    private void verify(Hgvs hgvs, String seqId, HgvsType seqType, String description, String value) {
        assertEquals(seqId, hgvs.getSequenceId());
        assertEquals(seqType, hgvs.getType());
        assertEquals(description, hgvs.getDescription().getValue());
        assertEquals(value, hgvs.getValue());
    }
    @Test
    public void testSubstitutionDescriptionSingle() {
        String val = "p.V600R"; 
        Hgvs hgvs = Hgvss.from(val, false);
        HgvsDescription descr = hgvs.getDescription();
        
       System.out.println(descr);
    }
    
    @Test
    public void testP() {
        String val = "p.S247Qfs*8"; 
        Hgvs hgvs = Hgvss.from(val, false);
        HgvsDescription descr = hgvs.getDescription();
        
       System.out.println(descr);
    }
}
