package uk.ac.ebi.uniprot.variation.hgvs.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import uk.ac.ebi.uniprot.variation.SequenceType;
import uk.ac.ebi.uniprot.variation.exception.InvalidHgvsException;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.Hgvss;

@Data
public class HgvsImpl implements Hgvs {
	private final String sequenceId;
	private final SequenceType sequenceType;
	private final String description;
	

	public static Hgvs from(String hgvsString) {
		Matcher matcher = Hgvss.HGVS_PATTERN.matcher(hgvsString);
		if (matcher.matches()) {
			return new HgvsImpl(matcher.group(1), SequenceType.getType(matcher.group(3)), matcher.group(5));
		} else {
			throw new InvalidHgvsException(hgvsString);
		}
	}

}
