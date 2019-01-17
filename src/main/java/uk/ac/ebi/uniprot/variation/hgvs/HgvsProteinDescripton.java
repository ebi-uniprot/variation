package uk.ac.ebi.uniprot.variation.hgvs;

public interface HgvsProteinDescripton extends HgvsDescription {

	boolean hasTer();
	boolean hasFrameShift();
	String getStop();
	
	
}
