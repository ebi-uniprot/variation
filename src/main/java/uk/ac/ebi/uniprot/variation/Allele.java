package uk.ac.ebi.uniprot.variation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.variation.hgvs.Hgvs;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;
import uk.ac.ebi.uniprot.variation.hgvs.parser.Hgvss;


public class Allele {
	private static final String DELIMITER = "/";
	private final String wildType;
	private final List<String> varType;

	public static Allele fromHgvs(String genomeHgvs) {
		if ((genomeHgvs != null) && !genomeHgvs.isEmpty()){
			Hgvs hgvs =Hgvss.from(genomeHgvs);
			if(hgvs.getDescription().getVariantType()  ==VariantType.SUBSTITUTION) {
				return new Allele(hgvs.getDescription().getWildType(), 
						hgvs.getDescription().getVarType());
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
