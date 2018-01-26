package uk.ac.ebi.uniprot.variation.hgvs.protein;

import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;
import uk.ac.ebi.uniprot.variation.hgvs.protein.ProteinHgvs;
import uk.ac.ebi.uniprot.variation.hgvs.protein.ProteinHgvsType;
import uk.ac.ebi.uniprot.variation.hgvs.protein.ProteinHgvss;

import java.util.regex.Matcher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ProteinHgvsDuplicationTest {
    @Test
    public void testProteinDuplicationSingle() {
        String val = "Ala3dup";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_DUPLICATION_PATTERN.matcher(val);

        assertTrue(matcher.matches());
        for (int i = 1; i <= matcher.groupCount(); i++) {
            System.out.println(matcher.group(i));
        }
        assertEquals("Ala", matcher.group(2));
        assertEquals("3", matcher.group(3));
        assertEquals("dup", matcher.group(8));
    }

    @Test
    public void testProteinDuplicationSingle2() {
        String val = "(Ala3dup)";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_DUPLICATION_PATTERN.matcher(val);

        assertTrue(matcher.matches());
        for (int i = 1; i <= matcher.groupCount(); i++) {
            System.out.println(matcher.group(i));
        }
        assertEquals("Ala", matcher.group(2));
        assertEquals("3", matcher.group(3));
        assertEquals("dup", matcher.group(8));
    }

    @Test
    public void testProteinDuplicationMulti() {
        String val = "Ala3_Ser5dup";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_DUPLICATION_PATTERN.matcher(val);

        assertTrue(matcher.matches());
        for (int i = 1; i <= matcher.groupCount(); i++) {
            System.out.println(matcher.group(i));
        }
        assertEquals("Ala", matcher.group(2));
        assertEquals("3", matcher.group(3));
        assertEquals("Ser", matcher.group(6));
        assertEquals("5", matcher.group(7));
        assertEquals("dup", matcher.group(8));
    }
    @Test
    public void testCreateDuplication(){
        String val= "LRG_199p1:p.Ala3_Ser5dup";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.DUPLICATION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getSequenceId());
        assertFalse(hgvs.isPredicted());
        assertEquals("A", hgvs.getWildType());
        assertEquals(3, hgvs.getStart());
        assertEquals("*", hgvs.getVarType());
        assertEquals("S", hgvs.getSecondWildType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(5, hgvs.getSecondStart().intValue());
        assertTrue(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateDuplicationPredict(){
        String val= "LRG_199p1:p.(Ala3dup)";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.DUPLICATION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getSequenceId());
        assertTrue(hgvs.isPredicted());
        assertEquals("A", hgvs.getWildType());
        assertEquals(3, hgvs.getStart());
        assertEquals("*", hgvs.getVarType());
        assertNull( hgvs.getSecondWildType());
        assertNull(hgvs.getSecondStart());
        assertFalse(hgvs.hasSecond());      
    }
    
    @Test
    public void testConvert2Location(){
        String val= "LRG_199p1:p.Ala3_Ser5dup";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        VariationLocation location = hgvs.convert2Location();
        assertNotNull(location);
        assertEquals(HgvsType.PROTEIN, location.getLocationType());
        assertEquals("LRG_199p1", location.getSequenceId());
   
        assertEquals("A", location.getWildType());
        assertEquals(3, location.getStart().intValue());
        assertEquals(5, location.getEnd().intValue());
        assertEquals("*", location.getVarType());
    
    }
    
    @Test
    public void testConvert2Location2(){
        String val= "LRG_199p1:p.(Ala3dup)";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        
        VariationLocation location = hgvs.convert2Location();
        assertNotNull(location);
        assertEquals(HgvsType.PROTEIN, location.getLocationType());
        assertEquals("LRG_199p1", location.getSequenceId());
   
        assertEquals("A", location.getWildType());
        assertEquals(3, location.getStart().intValue());
        assertEquals(3, location.getEnd().intValue());
        assertEquals("*", location.getVarType());
       
    }
}
