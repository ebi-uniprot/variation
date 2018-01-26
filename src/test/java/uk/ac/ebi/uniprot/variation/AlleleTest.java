package uk.ac.ebi.uniprot.variation;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.Allele;

import static org.junit.Assert.*;

public class AlleleTest {
    @Test
    public void testEmpty() {
        String alleleStr = "";
        Allele allele = Allele.from(alleleStr);
        assertNull(allele);    
    }
    @Test
    public void testSimple() {
        String alleleStr = "A/C";
        Allele allele = Allele.from(alleleStr);
        assertEquals("A", allele.getWildType());
        assertEquals(1, allele.getVarType().size());
        assertEquals("C", allele.getVarType().get(0));
        assertEquals("C", allele.getFirstVarType());
        assertEquals(alleleStr, allele.toString());
    }
    @Test
    public void testSingle() {
        String alleleStr = "A";
        Allele allele = Allele.from(alleleStr);
        assertEquals("A", allele.getWildType());
        assertEquals(1, allele.getVarType().size());
        assertEquals("-", allele.getVarType().get(0));
        assertEquals("-", allele.getFirstVarType());
        assertEquals("A/-", allele.toString());
    }
    @Test
    public void testMulti() {
        String alleleStr = "A/C/D";
        Allele allele = Allele.from(alleleStr);
        assertEquals("A", allele.getWildType());
        assertEquals(2, allele.getVarType().size());
        assertEquals("C", allele.getVarType().get(0));
        assertEquals("C", allele.getFirstVarType());
        assertEquals("D", allele.getVarType().get(1));
        assertEquals(alleleStr, allele.toString());
    }
    
    @Test
    public void testFromHgvs() {
    		String hgvs ="ENSMUST00000082421.1:c.115G>A";
    		Allele allele = Allele.fromHgvs(hgvs);
    		 assertEquals("G", allele.getWildType());
    	        assertEquals(1, allele.getVarType().size());
    	        assertEquals("A", allele.getVarType().get(0));
    	        assertEquals("A", allele.getFirstVarType());
    	        assertEquals("G/A", allele.toString());
    }
    
    
    @Test
    public void testAlleleFromHgvsEmpty() {
        String genomeHgvs = "";
        Allele allele = Allele.fromHgvs(genomeHgvs);
        assertNull(allele);
        genomeHgvs = null;
        allele = Allele.fromHgvs(genomeHgvs);
        assertNull(allele);
    }

    @Test
    public void testAlleleFromHgvs() {
        String genomeHgvs = "NC_000081.6:g.82616099C>A";
        Allele allele = Allele.fromHgvs(genomeHgvs);
        assertNotNull(allele);
        assertEquals("C", allele.getWildType());
        assertEquals("A", allele.getFirstVarType());
    }
    

    @Test
    public void testAlleleFromHgvsWrong() {
        String genomeHgvs = "NC_000081.6.82616099C>A";
        Allele allele = Allele.fromHgvs(genomeHgvs);
        assertNull(allele);
    }

    @Test
    public void testAlleleFromHgvsWrong2() {
        String genomeHgvs = "NC_000081.6:g.82616099C";
        Allele allele = Allele.fromHgvs(genomeHgvs);
        assertNull(allele);
    }
}
