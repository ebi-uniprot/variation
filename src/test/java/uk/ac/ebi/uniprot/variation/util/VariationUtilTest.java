package uk.ac.ebi.uniprot.variation.util;

import org.junit.Test;

import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;

import static org.junit.Assert.*;

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
        assertEquals("-", converted);
    }
    @Test
    public void testConvertMulti3letter2oneLetterAminoAcid(){
        String data ="GlyLeuHisArgPheIleValLeu";
        String converted =VariationUtil.convertThreeLetterAminoAcid2OneLetter(data);
        assertEquals("GLHRFIVL", converted);
    }
    @Test(expected =InvalidHgvsException.class)
    public void testConvert3letter2oneLetterWrong(){
        String data ="LysAc";
        String converted =VariationUtil.convertThreeLetterAminoAcid2OneLetter(data);
        assertEquals("K", converted);
    }

}
