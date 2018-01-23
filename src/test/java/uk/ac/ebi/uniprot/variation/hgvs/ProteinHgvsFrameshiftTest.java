package uk.ac.ebi.uniprot.variation.hgvs;

import uk.ac.ebi.uniprot.variation.VariationLocation;
import uk.ac.ebi.uniprot.variation.hgvs.ProteinHgvs;
import uk.ac.ebi.uniprot.variation.hgvs.ProteinHgvsType;
import uk.ac.ebi.uniprot.variation.hgvs.ProteinHgvss;

import java.util.regex.Matcher;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProteinHgvsFrameshiftTest { 
    
    @Test
    public void testBasePatern(){
        String val= "LRG_199p1:p.Pro633Leufs";
        
        Matcher matcher = ProteinHgvss.HGVS_BASE_PATTERN.matcher(val);
       
        assertTrue(matcher.matches());  
        assertEquals("LRG_199p1", matcher.group(1));
        assertEquals("p", matcher.group(3));
        assertEquals("Pro633Leufs", matcher.group(5));       
    }
    
    @Test
    public void testProteinFrameshift(){
        String val ="Arg97ProfsTer23";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_FRAMESHIFT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Arg", matcher.group(2));
        assertEquals("97", matcher.group(3));
        assertEquals("Pro", matcher.group(4));
        assertEquals("23", matcher.group(8));
      
    }
    
    @Test
    public void testProteinFrameshiftShort(){
        String val ="Arg97fs";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_FRAMESHIFT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Arg", matcher.group(2));
        assertEquals("97", matcher.group(3));
    //    assertEquals("Pro", matcher.group(4));
    //    assertEquals("23", matcher.group(8));
      
    }
    
    @Test
    public void testProteinFrameshift2(){
        String val ="Pro633Leufs";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_FRAMESHIFT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Pro", matcher.group(2));
        assertEquals("633", matcher.group(3));
        assertEquals("Leu", matcher.group(4));
    //    assertEquals("23", matcher.group(8));
      
    }
    
    @Test
    public void testCreateFrameshift(){
  
        String val= "LRG_199p1:p.Pro633Leufs";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.FRAMESHIFT, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertFalse(hgvs.isPredicted());
        assertEquals("P", hgvs.getWildType());
        assertEquals(633, hgvs.getStart());
        assertEquals("L", hgvs.getVarType());


        assertFalse(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateFrameshift2(){
  
        String val= "LRG_199p1:p.Arg97ProfsTer23";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.FRAMESHIFT, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertFalse(hgvs.isPredicted());
        assertEquals("R", hgvs.getWildType());
        assertEquals(97, hgvs.getStart());
        assertEquals("P", hgvs.getVarType());
       
        assertNotNull(hgvs.getSecondStart());
        assertEquals(23, hgvs.getSecondStart().intValue());
  
        assertFalse(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateFrameshift3(){
  
        String val= "LRG_199p1:p.Arg97fs";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.FRAMESHIFT, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertFalse(hgvs.isPredicted());
        assertEquals("R", hgvs.getWildType());
        assertEquals(97, hgvs.getStart());
        assertEquals("*", hgvs.getVarType());
        assertNull(hgvs.getSecondStart());
         
    }
    
    @Test(expected =UnsupportedOperationException.class)
    public void testConvert2Location(){
  
        String val= "LRG_199p1:p.Arg97fs";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        VariationLocation location = hgvs.convert2Location();
        assertNull(location);
         
    }
}

