package uk.ac.ebi.uniprot.variation.hgvs.impl;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsType;
@Builder(builderClassName = "HgvsDescriptionBuilder")
@Data
public class HgvsDescriptionImpl implements HgvsDescription {
	private final boolean predicted;
	private final String wildType;
	private final String varType;
	private final Long start;
	private final Long startCross;
	private final Long end;
	private final Long endCross;
	private final String secondWildType;
//	private final Long secondStart;
	private final HgvsType type;
	private final String description;
	private final boolean parsed;
	private final String conversionSeqId;
	private @Singular final List<Map.Entry<String, Integer> > repeats;
	
}
