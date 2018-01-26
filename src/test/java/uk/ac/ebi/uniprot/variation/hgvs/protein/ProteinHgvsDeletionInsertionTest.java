package uk.ac.ebi.uniprot.variation.hgvs.protein;

import uk.ac.ebi.uniprot.variation.SequenceType;
import uk.ac.ebi.uniprot.variation.VariationLocation;
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

public class ProteinHgvsDeletionInsertionTest {
    @Test
    public void testProteinDeletionInsertSingle(){
        String val ="Cys28delinsTrpVal";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_DELETION_INSERT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Cys", matcher.group(2));
        assertEquals("28", matcher.group(3));
     //   assertEquals("Gln", matcher.group(5));
     //   assertEquals("5", matcher.group(6));
        assertEquals("TrpVal", matcher.group(9));  
    }
    
    @Test
    public void testProteinDeletionInsertMulti(){
        String val ="Cys28_Lys29delinsTrp";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_DELETION_INSERT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Cys", matcher.group(2));
        assertEquals("28", matcher.group(3));
        assertEquals("Lys", matcher.group(6));
        assertEquals("29", matcher.group(7));
        assertEquals("Trp", matcher.group(9));  
    }
    
    @Test
    public void testProteinDeletionInsertMulti2(){
        String val ="(Glu125_Ala132delinsGlyLeuHisArgPheIleValLeu)";
        Matcher matcher = ProteinHgvss.HGVS_PROTEIN_DELETION_INSERT_PATTERN.matcher(val);
        
        assertTrue(matcher.matches());  
        for(int i=1; i<= matcher.groupCount(); i++){
            System.out.println(matcher.group(i));
        }
        assertEquals("Glu", matcher.group(2));
        assertEquals("125", matcher.group(3));
        assertEquals("Ala", matcher.group(6));
        assertEquals("132", matcher.group(7));
        assertEquals("GlyLeuHisArgPheIleValLeu", matcher.group(9));  
    }
    
    @Test
    public void testCreateDeletionInsertion(){
        String val= "LRG_199p1:p.Cys28delinsTrpVal";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.DELETION_INSERTION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getSequenceId());
        assertFalse(hgvs.isPredicted());
        assertEquals("C", hgvs.getWildType());
        assertEquals(28, hgvs.getStart());
        assertEquals("WV", hgvs.getVarType());
        assertNull( hgvs.getSecondWildType());
        assertNull(hgvs.getSecondStart());
        assertFalse(hgvs.hasSecond());      
    }
    
    @Test
    public void testCreateDeletionInsertionPredict(){
        String val= "LRG_199p1:p.(Glu125_Ala132delinsGlyLeuHisArgPheIleValLeu)";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.DELETION_INSERTION, hgvs.getType());
        assertEquals("LRG_199p1", hgvs.getSequenceId());
        assertTrue(hgvs.isPredicted());
        assertEquals("E", hgvs.getWildType());
        assertEquals(125, hgvs.getStart());
        assertEquals("GLHRFIVL", hgvs.getVarType());
        assertEquals("A", hgvs.getSecondWildType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(132, hgvs.getSecondStart().intValue());
        assertTrue(hgvs.hasSecond());       
    }
    
    
    
    @Test
    public void testConvert2Location(){
        String val= "LRG_199p1:p.Cys28delinsTrpVal";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        VariationLocation location = hgvs.convert2Location();
        assertNotNull(location);
        assertEquals(SequenceType.PROTEIN, location.getLocationType());
        assertEquals("LRG_199p1", location.getSequenceId());
   
        assertEquals("C", location.getWildType());
        assertEquals(28, location.getStart().intValue());
        assertEquals(28, location.getEnd().intValue());
        assertEquals("WV", location.getVarType());

    }
    
    @Test
    public void testConvert2Location2(){
        String val= "LRG_199p1:p.(Glu125_Ala132delinsGlyLeuHisArgPheIleValLeu)";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        
        VariationLocation location = hgvs.convert2Location();
        assertNotNull(location);
        assertEquals(SequenceType.PROTEIN, location.getLocationType());
        assertEquals("LRG_199p1", location.getSequenceId());
   
        assertEquals("E_A", location.getWildType());
        assertEquals(125, location.getStart().intValue());
        assertEquals(132, location.getEnd().intValue());
        assertEquals("GLHRFIVL", location.getVarType());
      
    }
    
    @Test
    public void testCreateDeletionInsertion2(){
        String val= "NP_075259.4:p.Ser252_Pro253delinsPheSer";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        assertEquals(ProteinHgvsType.DELETION_INSERTION, hgvs.getType());
        assertEquals("NP_075259", hgvs.getSequenceId());
        assertFalse(hgvs.isPredicted());
        assertEquals("S", hgvs.getWildType());
        assertEquals(252, hgvs.getStart());
        assertEquals("FS", hgvs.getVarType());
        assertEquals("P", hgvs.getSecondWildType());
        assertNotNull(hgvs.getSecondStart());
        assertEquals(253, hgvs.getSecondStart().intValue());
        assertTrue(hgvs.hasSecond());       
    }
    
    @Test
    public void testConvert2Location3(){
        String val=  "NP_075259.4:p.Ser252_Pro253delinsPheSer";
        ProteinHgvs hgvs = ProteinHgvss.create(val);
        assertNotNull(hgvs);
        
        VariationLocation location = hgvs.convert2Location();
        assertNotNull(location);
        assertEquals(SequenceType.PROTEIN, location.getLocationType());
        assertEquals("NP_075259", location.getSequenceId());
   
        assertEquals("SP", location.getWildType());
        assertEquals(252, location.getStart().intValue());
        assertEquals(253, location.getEnd().intValue());
        assertEquals("FS", location.getVarType());
      
    }
    
}
