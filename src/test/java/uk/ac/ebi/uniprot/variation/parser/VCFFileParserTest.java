package uk.ac.ebi.uniprot.variation.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.variation.VariantCallFormat;

public class VCFFileParserTest {

    @Test
    public void testParseVCFLine1() {
        String data = "2\t4370\trs6057\tG\tA\t29\t.\tNS=2;DP=13;AF=0.5;DB;H2\tGT:GQ:DP:HQ 0|0:48:1:52,51 1|0:48:8:51,51\t1/1:43:5:.,.";
        // data ="20 2 . TC T . PASS DP=100.";
        // data="aa\tbb";
        String[] tokens = data.split("\t");

        VariantCallFormat vcf = VCFFileParser.parseVCFLine(data);
        assertNotNull(vcf);
        System.out.println(vcf.toString());
    }

}
