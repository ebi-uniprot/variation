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
import static org.junit.Assert.assertTrue;

public class ProteinHgvsSubstitutionTest {
      
    @Test
    public void testBasePaternProteinSubstitution(){
        String val= "LRG_199p1:p.Trp24Cys";
        
        Matcher matcher = ProteinHgvss.HGVS_BASE_PATTERN.matcher(val);
       
        assertTrue(matcher.matches());  
        assertEquals("LRG_199p1", matcher.group(1));
        assertEquals("p", matcher.group(3));
        assertEquals("Trp24Cys", matcher.group(5));       
    }
    
    @Test
    public void testBasePaternProtein2(){
        String val= "LRG_199p1:p.(Trp24Cys)";
        
        Matcher matcher = ProteinHgvss.HGVS_BASE_PATTERN.matcher(val);
       
        assertTrue(matcher.matches());  
        assertEquals("LRG_199p1", matcher.group(1));
        assertEquals("p", matcher.group(3));
        assertEquals("(Trp24Cys)", matcher.group(5));       
    }
    
    @Test
    public void testProteinMissense(){
        String val ="Asn1186Thr";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_SUBSTITUTION_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Asn", matcher.group(2));
        assertEquals("1186", matcher.group(3));
        assertEquals("Thr", matcher.group(4));  
    }
    
    @Test
    public void testProteinMissense2(){
        String val ="(Asn1186Thr)";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_SUBSTITUTION_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Asn", matcher.group(2));
        assertEquals("1186", matcher.group(3));
        assertEquals("Thr", matcher.group(4));  
    }
    
    @Test
    public void testProteinNonsense(){
        String val ="Trp24Ter";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_SUBSTITUTION_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Trp", matcher.group(2));
        assertEquals("24", matcher.group(3));
        assertEquals("Ter", matcher.group(4));  
    }
    
    @Test
    public void testProteinNonsense2(){
        String val ="Trp24*";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_SUBSTITUTION_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Trp", matcher.group(2));
        assertEquals("24", matcher.group(3));
        assertEquals("*", matcher.group(4));  
    }
    
    @Test
    public void testProteinSilent(){
        String val ="Cys188=";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_SUBSTITUTION_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Cys", matcher.group(2));
        assertEquals("188", matcher.group(3));
        assertEquals("=", matcher.group(4));  
    }
    
    @Test
    public void testCreateSubstitutionHgvs() {
        String val= "LRG_199p1:p.Trp24Cys";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.SUBSTITUTION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertFalse(hgvs.isPredicted());
        assertEquals("W", hgvs.getWildType());
        assertEquals(24, hgvs.getStart());
        assertEquals("C", hgvs.getVarType());
        assertFalse(hgvs.hasSecond());        
    }
    @Test
    public void testCreateSubstitutionHgvsPredict() {
        String val= "LRG_199p1:p.(Trp24Cys)";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.SUBSTITUTION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getPrimaryId());
        assertTrue(hgvs.isPredicted());
        assertEquals("W", hgvs.getWildType());
        assertEquals(24, hgvs.getStart());
        assertEquals("C", hgvs.getVarType());
        assertFalse(hgvs.hasSecond());        
    }
    
    @Test
    public void testCreateLocation() {
        String val= "LRG_199p1:p.Trp24Cys";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        VariantLocation location = hgvs.convert2Location();
        assertNotNull(location);
        assertEquals(LocationType.PROTEIN, location.getLocationType());
        assertEquals("LRG_199p1", location.getSequenceId());
   
        assertEquals("W", location.getWildType());
        assertEquals(24, location.getStart().intValue());
        assertEquals(24, location.getEnd().intValue());
        assertEquals("C", location.getVarType());
      
    }
}
