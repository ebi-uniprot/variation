package uk.ac.ebi.uniprot.variation.parser;

import uk.ac.ebi.uniprot.variation.VariantCallFormat;
import uk.ac.ebi.uniprot.variation.impl.VariantCallFormatImpl;

import org.junit.Test;

import static org.junit.Assert.*;

public class VariantCallFormatImplTest {

    @Test
    public void testVariantCallFormatImpl() {
        VariantCallFormatImpl.VariantCallFormatBuilder builder= VariantCallFormatImpl.builder();
        builder.chromosome("12");
        VariantCallFormat variantCallFormat = builder.build();
        assertEquals("12", variantCallFormat.getChromosome());
    }

}
