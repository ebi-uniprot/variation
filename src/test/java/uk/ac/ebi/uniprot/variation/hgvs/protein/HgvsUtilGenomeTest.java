package uk.ac.ebi.uniprot.variation.hgvs.protein;

import java.util.regex.Matcher;
import org.junit.Test;

import uk.ac.ebi.uniprot.variation.hgvs.protein.ProteinHgvss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HgvsUtilGenomeTest {
    @Test
    public void testBasePatern(){
        String val= "ENSMUST00000082421.1:c.115G>A";
        
        Matcher matcher = ProteinHgvss.HGVS_BASE_PATTERN.matcher(val);
       
        assertTrue(matcher.matches());  
        assertEquals("ENSMUST00000082421.1", matcher.group(1));
        assertEquals("c", matcher.group(3));
        assertEquals("115G>A", matcher.group(5));       
    }
    @Test
    public void testSubstitution(){
        String val = "115G>A";
        
        Matcher matcher = ProteinHgvss.HGVS_SUBSTITUTION_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("115", matcher.group(1));
        assertEquals("G", matcher.group(2));
        assertEquals("A", matcher.group(4));  
    }
    @Test
    public void testSubstitutionLowerCase(){
        String val = "115g>a";
        
        Matcher matcher = ProteinHgvss.HGVS_SUBSTITUTION_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("115", matcher.group(1));
        assertEquals("g", matcher.group(2));
        assertEquals("a", matcher.group(4));  
    }
}
