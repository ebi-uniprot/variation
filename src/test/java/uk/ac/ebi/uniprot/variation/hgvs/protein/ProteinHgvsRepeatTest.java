package uk.ac.ebi.uniprot.variation.hgvs.protein;

import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.hgvs.protein.ProteinHgvs;
import uk.ac.ebi.uniprot.variation.hgvs.protein.ProteinHgvsType;
import uk.ac.ebi.uniprot.variation.hgvs.protein.ProteinHgvss;

import java.util.regex.Matcher;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProteinHgvsRepeatTest {
    @Test
    public void testBasePatern(){
        String val= "LRG_199p1:p.Gln18[23]";
        
        Matcher matcher = ProteinHgvss.HGVS_BASE_PATTERN.matcher(val);
       
        assertTrue(matcher.matches());  
        assertEquals("LRG_199p1", matcher.group(1));
        assertEquals("p", matcher.group(3));
        assertEquals("Gln18[23]", matcher.group(5));       
    }
    
    @Test
    public void testRepeat(){
        String val ="Gln18[23]";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_REPEAT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
      
        assertEquals("Gln", matcher.group(2));
        assertEquals("18", matcher.group(3));   
        assertNull( matcher.group(6));
        assertNull( matcher.group(7));   
        assertEquals("23", matcher.group(9));   
    }
    
    @Test
    public void testRepeat2(){
        String val ="Arg65_Ser67[12]";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_REPEAT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
      
        assertEquals("Arg", matcher.group(2));
        assertEquals("65", matcher.group(3));   
        assertEquals("Ser", matcher.group(6));
        assertEquals("67", matcher.group(7));   
        assertEquals("12", matcher.group(9));  
    }
    
    @Test
    public void testCreateRepeat(){
  
        String val= "LRG_199p1:p.Gln18[23]";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.REPEAT, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getSequenceId());
        assertFalse(hgvs.isPredicted());
        assertEquals("Q", hgvs.getWildType());
        assertEquals(18, hgvs.getStart());
        assertEquals(23, hgvs.getNumberOfOccurrences().intValue());

        assertFalse(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateRepeat2(){
  
        String val= "LRG_199p1:p.Arg65_Ser67[12]";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.REPEAT, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getSequenceId());
        assertFalse(hgvs.isPredicted());
        assertEquals("R", hgvs.getWildType());
        assertEquals(65, hgvs.getStart());
        assertNotNull( hgvs.getSecondWildType());
        assertEquals("S", hgvs.getSecondWildType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(67, hgvs.getSecondStart().intValue());
        assertEquals(12, hgvs.getNumberOfOccurrences().intValue());
        assertTrue(hgvs.hasSecond());      
    }
    
    @Test(expected =UnsupportedOperationException.class)
    public void testConvert2Location(){
  
        String val= "LRG_199p1:p.Arg65_Ser67[12]";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        VariationLocation location = hgvs.convert2Location();
        assertNull(location);
     
    } 
}
