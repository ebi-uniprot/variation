package uk.ac.ebi.uniprot.variation.hgvs;

//http://varnomen.hgvs.org
public interface Hgvs {
	HgvsType getType();
	String getSequenceId();
	HgvsDescription getDescription();
	
}
