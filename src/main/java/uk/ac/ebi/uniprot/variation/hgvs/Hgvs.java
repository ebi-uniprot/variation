package uk.ac.ebi.uniprot.variation.hgvs;

//http://varnomen.hgvs.org
public interface Hgvs {
	String getSequenceId();
	HgvsType getHgvsType();
	HgvsDescription getDescription();
	
}
