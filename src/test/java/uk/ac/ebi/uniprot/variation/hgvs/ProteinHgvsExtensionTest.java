package uk.ac.ebi.uniprot.variation.hgvs;

import uk.ac.ebi.uniprot.variation.VariantLocation;
import uk.ac.ebi.uniprot.variation.hgvs.ProteinHgvs;
import uk.ac.ebi.uniprot.variation.hgvs.ProteinHgvsType;
import uk.ac.ebi.uniprot.variation.hgvs.ProteinHgvss;

import java.util.regex.Matcher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ProteinHgvsExtensionTest {
    @Test
    public void testBasePatern(){
        String val= "LRG_199p1:p.Ter110Glnext*17";
        
        Matcher matcher = ProteinHgvss.HGVS_BASE_PATTERN.matcher(val);
       
        assertTrue(matcher.matches());  
        assertEquals("LRG_199p1", matcher.group(1));
        assertEquals("p", matcher.group(3));
        assertEquals("Ter110Glnext*17", matcher.group(5));       
    }
    
    @Test
    public void testExtensionMet(){
        String val ="Met1ext-5";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_EXTENSION_MET_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Met", matcher.group(2));
        assertEquals("1", matcher.group(3));
        assertEquals(null, matcher.group(4));
        assertEquals("5", matcher.group(7));  
    }
    @Test
    public void testExtensionMet2(){
        String val ="Met1Valext-12";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_EXTENSION_MET_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Met", matcher.group(2));
        assertEquals("1", matcher.group(3));
        assertEquals("Val", matcher.group(4));
        assertEquals("12", matcher.group(7));  
    }
    @Test
    public void testExtensionTer(){
        String val ="Ter110Glnext*17";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_EXTENSION_TER_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(i +"\t"+ matcher.group(i));
        }
        assertEquals("Ter", matcher.group(2));
        assertEquals("110", matcher.group(3));
        assertEquals("Gln", matcher.group(4));
        assertEquals(null, matcher.group(6));
        assertEquals("17", matcher.group(8));  
    }
    @Test
    public void testExtensionTerWithStar(){
        String val ="*110Glnext*17";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_EXTENSION_TER_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(i +"\t"+ matcher.group(i));
        }
        assertEquals("*", matcher.group(2));
        assertEquals("110", matcher.group(3));
        assertEquals("Gln", matcher.group(4));
        assertEquals(null, matcher.group(6));
        assertEquals("17", matcher.group(8));  
    }
    
    @Test
    public void testExtensionLettersTer(){
        String val ="(Ter315TyrextAsnLysGlyThrTer)";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_EXTENSION_TER_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(i +"\t"+ matcher.group(i));
        }
        assertEquals("Ter", matcher.group(2));
        assertEquals("315", matcher.group(3));
        assertEquals("Tyr", matcher.group(4));
        assertEquals("AsnLysGlyThr", matcher.group(6));
        assertEquals(null, matcher.group(8));  
    }
    @Test
    public void testExtensionLettersTerQuestion(){
        String val ="Ter327Argext*?";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_EXTENSION_TER_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(i +"\t"+ matcher.group(i));
        }
        assertEquals("Ter", matcher.group(2));
        assertEquals("327", matcher.group(3));
        assertEquals("Arg", matcher.group(4));
        assertEquals(null, matcher.group(6));
        assertEquals("?", matcher.group(8));  
    }
    

    
    
    @Test
    public void testCreateExtensionTer(){
        String val= "LRG_199p1:p.Ter110Glnext*17";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.EXTENSION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertFalse(hgvs.isPredicted());
        assertEquals("*", hgvs.getWildType());
        assertEquals(110, hgvs.getStart());
        assertEquals("Q", hgvs.getVarType());
        assertNotNull( hgvs.getSecondWildType());
        assertEquals("*", hgvs.getSecondWildType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(17, hgvs.getSecondStart().intValue());
        assertTrue(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateExtensionTer2(){
  
        String val= "LRG_199p1:p.*110Glnext*17";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.EXTENSION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertFalse(hgvs.isPredicted());
        assertEquals("*", hgvs.getWildType());
        assertEquals(110, hgvs.getStart());
        assertEquals("Q", hgvs.getVarType());
        assertNotNull( hgvs.getSecondWildType());
        assertEquals("*", hgvs.getSecondWildType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(17, hgvs.getSecondStart().intValue());
        assertTrue(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateExtensionTerPredict(){
  
        String val= "LRG_199p1:p.(Ter315TyrextAsnLysGlyThrTer)";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.EXTENSION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertTrue(hgvs.isPredicted());
        assertEquals("*", hgvs.getWildType());
        assertEquals(315, hgvs.getStart());
        assertEquals("Y", hgvs.getVarType());
        assertNotNull( hgvs.getSecondWildType());
        assertEquals("NKGT", hgvs.getSecondWildType());
        assertNull(hgvs.getSecondStart());
        assertTrue(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateExtensionMet(){
        String val= "LRG_199p1:p.Met1ext-5";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.EXTENSION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertFalse(hgvs.isPredicted());
        assertEquals("M", hgvs.getWildType());
        assertEquals(1, hgvs.getStart());
        assertEquals("?", hgvs.getVarType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(5, hgvs.getSecondStart().intValue());
        assertFalse(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateExtensionMet2(){
        String val= "LRG_199p1:p.Met1Valext-12";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.EXTENSION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertFalse(hgvs.isPredicted());
        assertEquals("M", hgvs.getWildType());
        assertEquals(1, hgvs.getStart());
        assertEquals("V", hgvs.getVarType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(12, hgvs.getSecondStart().intValue());
        assertFalse(hgvs.hasSecond());      
    }
    
    @Test
    public void testCConvert2Location(){
        String val= "LRG_199p1:p.Met1Valext-12";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        VariantLocation location = hgvs.convert2Location();
        assertNull(location);
    }
}
