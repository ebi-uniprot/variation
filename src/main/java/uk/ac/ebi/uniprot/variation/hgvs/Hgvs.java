package uk.ac.ebi.uniprot.variation.hgvs;

import uk.ac.ebi.uniprot.variation.SequenceType;
//http://varnomen.hgvs.org
public interface Hgvs {
	String getSequenceId();
	SequenceType getSequenceType();
	String getDescription();
	
}
