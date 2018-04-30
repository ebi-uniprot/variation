package uk.ac.ebi.uniprot.variation.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.variation.VariantCallFormat;

//@Builder(builderClassName = "VariantCallFormatBuilder")
//@Data
public class VariantCallFormatImpl implements VariantCallFormat {
	private static final String STOP = ".";
	private static final String TAB = "\t";
	private final String chromosome;
	private final int position;
	private final String id;
	private final String referenceBase;
	private final String alternativeAlleles;
	private final Double qualityScore;
	private final String filter;
	private final Map<String, String> info;
	private final String sampleFormat;
	private final List<String> samples;

	public static Builder builder() {
		return new Builder();
	}

	private VariantCallFormatImpl(Builder builder) {

		this.chromosome = builder.chromosome;
		this.position = builder.position;
		this.id = builder.id;
		this.referenceBase = builder.referenceBase;
		this.alternativeAlleles = builder.alternativeAlleles;
		this.qualityScore = builder.qualityScore;
		this.filter = builder.filter;
		if (builder.info == null) {
			this.info = Collections.emptyMap();
		} else
			this.info = Collections.unmodifiableMap(builder.info);
		this.sampleFormat = builder.sampleFormat;
		if (builder.samples == null) {
			this.samples = Collections.emptyList();
		} else
			this.samples = Collections.unmodifiableList(builder.samples);
	}

	public static class Builder {
		private String chromosome;
		private int position;
		private String id;
		private String referenceBase;
		private String alternativeAlleles;
		private Double qualityScore;
		private String filter;
		private Map<String, String> info;
		private String sampleFormat;
		private List<String> samples;

		public VariantCallFormat build() {
			return new VariantCallFormatImpl(this);
		}

		public Builder chromosome(String chromosome) {
			this.chromosome = chromosome;
			return this;
		}

		public Builder position(int position) {
			this.position = position;
			return this;
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder referenceBase(String referenceBase) {
			this.referenceBase = referenceBase;
			return this;
		}

		public Builder alternativeAlleles(String alternativeAlleles) {
			this.alternativeAlleles = alternativeAlleles;
			return this;
		}

		public Builder qualityScore(Double qualityScore) {
			this.qualityScore = qualityScore;
			return this;
		}

		public Builder filter(String filter) {
			this.filter = filter;
			return this;
		}

		public Builder info(Map<String, String> info) {
			this.info = info;
			return this;
		}

		public Builder sampleFormat(String sampleFormat) {
			this.sampleFormat = sampleFormat;
			return this;
		}

		public Builder samples(List<String> samples) {
			this.samples = samples;
			return this;
		}
	}

	public String getChromosome() {
		return chromosome;
	}

	public int getPosition() {
		return position;
	}

	public String getId() {
		return id;
	}

	public String getReferenceBase() {
		return referenceBase;
	}

	public String getAlternativeAlleles() {
		return alternativeAlleles;
	}

	public Double getQualityScore() {
		return qualityScore;
	}

	public String getFilter() {
		return filter;
	}

	public Map<String, String> getInfo() {
		return info;
	}

	@Override
	public String getSampleFormat() {
		return this.sampleFormat;
	}

	@Override
	public List<String> getSamples() {
		return this.samples;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getChromosome()).append(TAB).append(this.getPosition()).append(TAB).append(this.getId())
				.append(TAB).append(this.getReferenceBase()).append(TAB).append(this.getAlternativeAlleles());
		if (this.getQualityScore() != null)
			sb.append(TAB).append(this.getQualityScore());
		if (this.getFilter() != null)
			sb.append(TAB).append(this.getFilter());
		if (!this.getInfo().isEmpty()) {

		}
		;
		return sb.toString();
	}

	private String transform(String val) {
		if ((val == null) || (val.isEmpty()))
			return STOP;
		else
			return val;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alternativeAlleles == null) ? 0 : alternativeAlleles.hashCode());
		result = prime * result + ((chromosome == null) ? 0 : chromosome.hashCode());
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + (int) (position ^ (position >>> 32));
		result = prime * result + ((qualityScore == null) ? 0 : qualityScore.hashCode());
		result = prime * result + ((referenceBase == null) ? 0 : referenceBase.hashCode());
		result = prime * result + ((sampleFormat == null) ? 0 : sampleFormat.hashCode());
		result = prime * result + ((samples == null) ? 0 : samples.hashCode());
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
		VariantCallFormatImpl other = (VariantCallFormatImpl) obj;
		if (alternativeAlleles == null) {
			if (other.alternativeAlleles != null)
				return false;
		} else if (!alternativeAlleles.equals(other.alternativeAlleles))
			return false;
		if (chromosome == null) {
			if (other.chromosome != null)
				return false;
		} else if (!chromosome.equals(other.chromosome))
			return false;
		if (filter == null) {
			if (other.filter != null)
				return false;
		} else if (!filter.equals(other.filter))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (position != other.position)
			return false;
		if (qualityScore == null) {
			if (other.qualityScore != null)
				return false;
		} else if (!qualityScore.equals(other.qualityScore))
			return false;
		if (referenceBase == null) {
			if (other.referenceBase != null)
				return false;
		} else if (!referenceBase.equals(other.referenceBase))
			return false;
		if (sampleFormat == null) {
			if (other.sampleFormat != null)
				return false;
		} else if (!sampleFormat.equals(other.sampleFormat))
			return false;
		if (samples == null) {
			if (other.samples != null)
				return false;
		} else if (!samples.equals(other.samples))
			return false;
		return true;
	}

}
