package uk.ac.ebi.uniprot.variation.hgvs;

import java.util.List;
import java.util.Map;

public interface HgvsDescription {
	
	final String PLUS = "+";
	final String UNDERSCORE = "_";
	final String INSERTION = "ins";
	final String DELETION = "del";
	final String GREATER_THAN = ">";
	final String DUPLICATION = "dup";
	final String DELETION_INSERTION = "delins";
	final String INVERSION = "inv";
	final String STAR = "*";
	final String TERMINATOR = "Ter";
	final String EXTENSION = "ext";
	final String FRAMESHIFT = "fs";
	final String PREDICTION_OPEN = "(";
	final String PREDICTION_CLOSE = ")";
	final String REPEAT_OPEN = "[";
	final String REPEAT_CLOSE = "]";
	final String STARTING_MET = "M";
	
	boolean isPredicted();
	String getWildType();
	String getVarType();
	Long getStart();
	Long getStartCross();
	Long getEnd();
	Long getEndCross();
	String getConversionSeqId();
	String getSecondWildType();
	VariantType getVariantType();
	String getValue();
	boolean isParsed();
	List<Map.Entry<String, Integer> > getRepeats();
//	String getDisplayValue();
	
}
