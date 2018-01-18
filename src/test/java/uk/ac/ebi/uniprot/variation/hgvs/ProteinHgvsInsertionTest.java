package uk.ac.ebi.uniprot.variation.hgvs;

import uk.ac.ebi.uniprot.variation.LocationType;
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

public class ProteinHgvsInsertionTest {
    @Test
    public void testBasePatern(){
        String val= "LRG_199p1:p.His4_Gln5insAlaSer";
        
        Matcher matcher = ProteinHgvss.HGVS_BASE_PATTERN.matcher(val);
       
        assertTrue(matcher.matches());  
        assertEquals("LRG_199p1", matcher.group(1));
        assertEquals("p", matcher.group(3));
        assertEquals("His4_Gln5insAlaSer", matcher.group(5));       
    }

    @Test
    public void testProteinInsert(){
        String val ="His4_Gln5insAlaSer";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_INSERT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("His", matcher.group(2));
        assertEquals("4", matcher.group(3));
        assertEquals("Gln", matcher.group(5));
        assertEquals("5", matcher.group(6));
        assertEquals("AlaSer", matcher.group(8));  
    }
    @Test
    public void testProteinInsert2(){
        String val ="(His4_Gln5insAla)";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_INSERT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("His", matcher.group(2));
        assertEquals("4", matcher.group(3));
        assertEquals("Gln", matcher.group(5));
        assertEquals("5", matcher.group(6));
        assertEquals("Ala", matcher.group(8));  
    }
    @Test
    public void testProteinInsertNumber(){
        String val ="Arg78_Gly79ins23";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_INSERT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Arg", matcher.group(2));
        assertEquals("78", matcher.group(3));
        assertEquals("Gly", matcher.group(5));
        assertEquals("79", matcher.group(6));
        assertNull( matcher.group(8));  
        assertEquals("23", matcher.group(9));  
    }
    
    
    @Test
    public void testCreateInsertion(){
        String val= "LRG_199p1:p.His4_Gln5insAlaSer";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.INSERTION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertFalse(hgvs.isPredicted());
        assertEquals("H", hgvs.getWildType());
        assertEquals(4, hgvs.getStart());
        assertEquals("AS", hgvs.getVarType());
        assertEquals("Q", hgvs.getSecondWildType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(5, hgvs.getSecondStart().intValue());
        assertTrue(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateInsertionPredict(){
        String val= "LRG_199p1:p.(His4_Gln5insAla)";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.INSERTION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertTrue(hgvs.isPredicted());
        assertEquals("H", hgvs.getWildType());
        assertEquals(4, hgvs.getStart());
        assertEquals("A", hgvs.getVarType());
        assertEquals("Q", hgvs.getSecondWildType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(5, hgvs.getSecondStart().intValue());
        assertTrue(hgvs.hasSecond());       
    }
    
    
    
    @Test
    public void testConvert2Location(){
        String val= "LRG_199p1:p.His4_Gln5insAlaSer";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        
        VariantLocation location = hgvs.convert2Location();
        assertNotNull(location);
        assertEquals(LocationType.PROTEIN, location.getLocationType());
        assertEquals("LRG_199p1", location.getSequenceId());
   
        assertEquals("H", location.getWildType());
        assertEquals(4, location.getStart().intValue());
        assertEquals(5, location.getEnd().intValue());
        assertEquals("AS", location.getVarType());
   
    }
    
    @Test
    public void testConvert2Location2(){
        String val= "LRG_199p1:p.(His4_Gln5insAla)";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        
        VariantLocation location = hgvs.convert2Location();
        assertNotNull(location);
        assertEquals(LocationType.PROTEIN, location.getLocationType());
        assertEquals("LRG_199p1", location.getSequenceId());
   
        assertEquals("H", location.getWildType());
        assertEquals(4, location.getStart().intValue());
        assertEquals(5, location.getEnd().intValue());
        assertEquals("A", location.getVarType());
     
    }
    
    
}
