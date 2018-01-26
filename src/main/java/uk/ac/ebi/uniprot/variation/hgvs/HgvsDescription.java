package uk.ac.ebi.uniprot.variation.hgvs;

import java.util.List;
import java.util.Map;

public interface HgvsDescription {
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
}
