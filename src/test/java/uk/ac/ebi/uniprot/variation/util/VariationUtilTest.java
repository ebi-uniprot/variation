package uk.ac.ebi.uniprot.variation.util;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsProteinDescripton;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;
import uk.ac.ebi.uniprot.variation.hgvs.impl.HgvsProteinDescriptionImpl;


import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.Map;


public class VariationUtilTest {

    @Test
    public void testConvertAminoAcid(){
        String aa= "ACE";
        String converted = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(aa);
        assertEquals("AlaCysGlu", converted);
    }
    @Test
    public void testConvertAminoAcidDash(){
        String aa= "-";
        String converted = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(aa);
        assertEquals("del", converted);
    }
    @Test
    public void testConvert3letter2oneLetterAminoAcid(){
        String data ="Lys";
        String converted =VariationUtil.convertThreeLetterAminoAcid2OneLetter(data);
        assertEquals("K", converted);
    }
    
    @Test
    public void testConvert3letter2oneLetterAminoAcidDel(){
        String data ="Del";
        String converted =VariationUtil.convertThreeLetterAminoAcid2OneLetter(data);
        assertEquals("del", converted);
    }
    @Test
    public void testConvertMulti3letter2oneLetterAminoAcid(){
        String data ="GlyLeuHisArgPheIleValLeu";
        String converted =VariationUtil.convertThreeLetterAminoAcid2OneLetter(data);
        assertEquals("GLHRFIVL", converted);
    }
    @Test//(expected =InvalidHgvsException.class)
    public void testConvert3letter2oneLetterWrong(){
        String data ="LysAc";
        
        String converted =VariationUtil.convertThreeLetterAminoAcid2OneLetter(data);
        assertEquals("K", converted);
    }
    
 // test HGVS = (Gln151Thrfs*9)
    private HgvsProteinDescripton makeFrameShift() {
    	HgvsProteinDescriptionImpl.Builder builder = new HgvsProteinDescriptionImpl.Builder();
		builder.frameShift("fs").wildType("Q").start(151l).varType("T").variantType(VariantType.FRAMESHIFT);
		builder.stop("*9");
		return builder.build();
    }
  //"Arg65_Ser67[12]"
    private HgvsProteinDescripton makeRepeat() {
    	HgvsProteinDescriptionImpl.Builder builder = new HgvsProteinDescriptionImpl.Builder();
    	builder = new HgvsProteinDescriptionImpl.Builder();
		builder.wildType("R").start(65l).secondWildType("S").varType("T").variantType(VariantType.REPEAT);
		builder.end(67l);
		Map.Entry<String, Integer> repeat = new AbstractMap.SimpleEntry<>("",12);
		builder.repeat(repeat);
		return builder.build();
    }	
    
    
    @Test
    public void testFormatAAWildType() {
    	
    	HgvsProteinDescripton hgvsDescription = makeFrameShift();
		String wildTypeOneLett = VariationUtil.formatAAWildType(hgvsDescription, false);
		assertEquals("Q",wildTypeOneLett);
		String wildTypeThreeLett = VariationUtil.formatAAWildType(hgvsDescription, true);
		assertEquals("Gln",wildTypeThreeLett);
		
		//"Arg65_Ser67[12]"
		
		hgvsDescription = makeRepeat();
		wildTypeOneLett = VariationUtil.formatAAWildType(hgvsDescription, false);
		assertEquals("R_S",wildTypeOneLett);
		wildTypeThreeLett = VariationUtil.formatAAWildType(hgvsDescription, true);
		assertEquals("Arg_Ser",wildTypeThreeLett);
		
		
    }
    
    
    @Test
    public void testFormatAAVarType() {
		
    	HgvsProteinDescripton hgvsDescription = makeFrameShift();
		String varTypeOneLett = VariationUtil.formatAAVarType(hgvsDescription, false);
		assertEquals("Tfs*9",varTypeOneLett);
		String varTypeThreeLett = VariationUtil.formatAAVarType(hgvsDescription, true);
		assertEquals("Thrfs*9",varTypeThreeLett);
		hgvsDescription = makeRepeat();
		varTypeOneLett = VariationUtil.formatAAVarType(hgvsDescription, false);
		assertEquals("[12]",varTypeOneLett);
		varTypeThreeLett = VariationUtil.formatAAVarType(hgvsDescription, true);
		assertEquals("[12]",varTypeThreeLett);
    }

    // "ENSP00000284981:p.Asp131ValfsTer4"
    private HgvsProteinDescripton makeFrameShift2() {
    	HgvsProteinDescriptionImpl.Builder builder = new HgvsProteinDescriptionImpl.Builder();
		builder.frameShift("fs").wildType("D").start(131l).varType("V").variantType(VariantType.FRAMESHIFT);
		builder.stop("*4");
		return builder.build();
    }
    
    @Test
    public void testFormatAAVarType2() {
		
    	HgvsProteinDescripton hgvsDescription = makeFrameShift2();
		String varTypeOneLett = VariationUtil.formatAAVarType(hgvsDescription, false);
		assertEquals("Vfs*4",varTypeOneLett);
		String varTypeThreeLett = VariationUtil.formatAAVarType(hgvsDescription, true);
		assertEquals("Valfs*4",varTypeThreeLett);
		
    }
    
 // "sometranscriptID:p.Ter194MetfsTer15"
    private HgvsProteinDescripton makeFrameShift3() {
    	HgvsProteinDescriptionImpl.Builder builder = new HgvsProteinDescriptionImpl.Builder();
		builder.frameShift("fs").wildType("*").start(194l).varType("M").variantType(VariantType.FRAMESHIFT);
		builder.stop("*15");
		return builder.build();
    }
    
    @Test
    public void testFormatAAVarType3() {
		
    	HgvsProteinDescripton hgvsDescription = makeFrameShift3();
    	assertEquals("*",hgvsDescription.getWildType());
    	assertEquals(new Long(194l),hgvsDescription.getStart());
		String varTypeOneLett = VariationUtil.formatAAVarType(hgvsDescription, false);
		assertEquals("Mfs*15",varTypeOneLett);
		String varTypeThreeLett = VariationUtil.formatAAVarType(hgvsDescription, true);
		assertEquals("Metfs*15",varTypeThreeLett);
		
    }
}
