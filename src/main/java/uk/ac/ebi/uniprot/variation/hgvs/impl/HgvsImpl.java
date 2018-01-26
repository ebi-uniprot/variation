package uk.ac.ebi.uniprot.variation.hgvs.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import uk.ac.ebi.uniprot.variation.SequenceType;
import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.HgvsDescription;
import uk.ac.ebi.uniprot.variation.hgvs.parser.Hgvss;

@Data
public class HgvsImpl implements Hgvs {
	private final String sequenceId;
	private final SequenceType sequenceType;
	private final HgvsDescription description;
	

}
