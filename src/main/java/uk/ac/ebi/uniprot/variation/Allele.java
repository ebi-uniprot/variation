package uk.ac.ebi.uniprot.variation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.variation.hgvs.protein.ProteinHgvss;

public class Allele {
	private static final String DELIMITER = "/";
	private final String wildType;
	private final List<String> varType;

	public static Allele fromHgvs(String genomeHgvs) {
		if (genomeHgvs != null) {
			Matcher matcher = ProteinHgvss.HGVS_BASE_PATTERN.matcher(genomeHgvs);
			if (matcher.matches()) {
				String val = matcher.group(5);
				Matcher matcher2 = ProteinHgvss.HGVS_SUBSTITUTION_PATTERN.matcher(val);
				if (matcher2.matches()) {
					String wild = matcher2.group(2);
					String var = matcher2.group(4);
					return new Allele(wild, var);
				}
			}
		}
		return null;
	}

	public static Allele from(String allele) {
		if ((allele == null) || (allele.isEmpty())) {
			return null;
		}
		String[] val = allele.split(DELIMITER);
		List<String> varType = new ArrayList<>();
		String wildType = val[0];
		if (val.length < 2) {
			varType.add("-");
		} else {
			for (int i = 1; i < val.length; i++) {
				varType.add(val[i]);
			}
		}
		return new Allele(wildType, varType);
	}

	public Allele(String wildType, String varType) {
		this.wildType = wildType;
		this.varType = Arrays.asList(varType);
	}

	public Allele(String wildType, List<String> varType) {
		this.wildType = wildType;
		this.varType = varType;
	}

	public String getWildType() {
		return wildType;
	}

	public String getFirstVarType() {
		return varType.get(0);
	}

	public List<String> getVarType() {
		return varType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((varType == null) ? 0 : varType.hashCode());
		result = prime * result + ((wildType == null) ? 0 : wildType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Allele other = (Allele) obj;
		if (varType == null) {
			if (other.varType != null)
				return false;
		} else if (!varType.equals(other.varType))
			return false;
		if (wildType == null) {
			if (other.wildType != null)
				return false;
		} else if (!wildType.equals(other.wildType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(wildType).append(DELIMITER).append(varType.stream().collect(Collectors.joining(DELIMITER)));
		return sb.toString();
	}
}
