package uk.ac.ebi.uniprot.variation.hgvs;

import java.util.regex.Pattern;

public final class Hgvss {
	  public final static String HGVS =
	            "([\\w.]+)(\\:)([cgmnpr])(\\.)(.+)"; // ENSMUST00000082421.1:c.115G>A;	    
	  public final static Pattern HGVS_PATTERN = Pattern.compile(HGVS);
	  
	
	  
}
