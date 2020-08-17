package uk.ac.ebi.uniprot.variation.hgvs.impl;


import java.util.Map.Entry;

/**
 * This class extends the generic HgvsDescriptionImpl class so
 * protein specific descriptions can be handled.  Two additional
 * variables are defined frameshift for declaring if the HGVS is
 * describing a frameshift (in DNA ORF) variant and ter for when the 
 * HGVS is describing a protein variant that results in a termination
 * or stop.
 * 
 */


import uk.ac.ebi.uniprot.variation.hgvs.HgvsProteinDescripton;
import uk.ac.ebi.uniprot.variation.hgvs.VariantType;
import uk.ac.ebi.uniprot.variation.util.VariationUtil;



public class HgvsProteinDescriptionImpl extends HgvsDescriptionImpl implements HgvsProteinDescripton {

	private boolean ter;
	private boolean frameShift;
	private String stop = "";
	
	public static Builder builder() {
		return new Builder();
	}
	
	protected HgvsProteinDescriptionImpl(Builder builder) {
		super(builder);
		this.ter = builder.ter;
		this.frameShift = builder.frameShift;
		this.stop = builder.stop;
	}
	
	public static class Builder extends HgvsDescriptionImpl.Builder<Builder> {
	
		private boolean ter = false;
		private boolean frameShift = false;
		private String stop = "";
		
		
		public Builder() {
			super();
		}
		
		@Override
		public HgvsProteinDescripton build() {
			return new HgvsProteinDescriptionImpl(this);
		}
		
		public Builder stop(String ter) {
			if(null != ter && (ter.contains("Ter") || ter.contains("*"))) {
				this.stop = ter;
				this.ter = true;
			}
			return this;
		}
		
		public Builder frameShift(String fsIcon) {
			if(null != fsIcon && "fs".equals(fsIcon)) {
				this.frameShift = true;
			}
			
			return this;
		}
	}		
	
	
	
	@Override
	public boolean hasTer() {
		return ter;
	}

	@Override
	public boolean hasFrameShift() {
		return frameShift;
	}

	@Override
	public String getStop() {
		return stop;
	}
	
	
	
	@Override
	public String getDisplayValue(boolean threeLett) {
		if(null != this.getValue()) {
			return this.getValue();
		}
		
			StringBuilder sb = new StringBuilder();
			if(this.isPredicted()) {
				sb.append("(");
			}
			if(threeLett) {
				sb.append(VariationUtil.convertOneLetterAminoAcid2ThreeLetters(this.getWildType()));
			} else {
				sb.append(this.getWildType());
			}
			
			sb.append(this.getStart());
			if(null != this.getSecondWildType()) {
				sb.append("_");
				if(threeLett) {
					sb.append(VariationUtil.convertOneLetterAminoAcid2ThreeLetters(this.getSecondWildType()));
				} else {
					sb.append(this.getSecondWildType());
				}
				
				sb.append(this.getEnd());
			}
			if(null == this.getVarType()) {
				sb.append(noVarHgvsEnd(threeLett));
			
			} else { 
				
				String disVarType = null;
				if(threeLett) {
					disVarType = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(this.getVarType());
				} else {
					disVarType = this.getVarType();
				}
				
				if(this.getVariantType().equals(VariantType.EXTENSION) ) {
					sb.append(addExtension(threeLett, disVarType));
				
				} else {
					if(this.getVariantType().equals(VariantType.INSERTION)) {
						sb.append("ins");
						sb.append(disVarType);
					}
					else if(this.getVariantType().equals(VariantType.DELETION_INSERTION)) {
						sb.append("delins").append(disVarType);
						
					}
					else if(this.getVariantType().equals(VariantType.FRAMESHIFT)) {
						sb.append(this.getFrameShift(disVarType));
					} 
					 else {
						sb.append(disVarType);
					}
					if(this.ter) {
						sb.append(addTer(threeLett));
					}
					if(null != this.getEndCross())
						sb.append(this.getEndCross());
				}
			}
			if(isPredicted()) {
				sb.append(")");
			}
			
			return sb.toString();
			
			
		
	}

	
	
	
	private String getFrameShift(String disVarType) {
		
		StringBuilder sb = new StringBuilder();
		if(!"del".equals(disVarType) ) {
			sb.append(disVarType);
		}
		sb.append("fs");

		
		
		
		return sb.toString();
	}

	private String addExtension(boolean threeLett, String disVarType) {
		
		if(this.getWildType().equals("M") && null == disVarType) {
			return "ext".concat(getRepeats().get(0).getValue().toString());
		}
		else if(null != this.getVarType()) {
			StringBuilder sb = new StringBuilder();
			sb.append(disVarType).append("ext");
			for (Entry<String, Integer> entry : this.getRepeats()) {
				
				String ext = entry.getKey();
				if(null != ext) {
					if(threeLett) {
						ext = VariationUtil.convertOneLetterAminoAcid2ThreeLetters(ext);
					}
					sb.append(ext);
				}
				if(null != entry.getValue()) {
					sb.append(entry.getValue());
				}
				
			}
			return sb.toString();
		}
		
		return "ext";
	}

	private String addTer(boolean threeLett) {
		if(threeLett && stop.contains("*"))
			return stop.replace("*", "Ter");
		
		return stop;
	}

	private String noVarHgvsEnd(boolean threeLett) {
		StringBuilder sb = new StringBuilder();
		if(this.getVariantType().equals(VariantType.DUPLICATION)) {
			return "dup";
			
		} 
		else if(this.getVariantType().equals(VariantType.DELETION)) {
			return "del";
			
		} else if(this.getVariantType().equals(VariantType.INSERTION)) {
			sb.append("ins");
			sb.append(this.getRepeats().get(0).getValue());
			
			
		}
		
		else if(this.getVariantType().equals(VariantType.REPEAT)) {
			
			for (Entry<String, Integer> entry : this.getRepeats()) {
				sb.append(entry.getKey()).append("[").append(entry.getValue()).append("]");
			}
			return sb.toString();
		} else if(this.getVariantType().equals(VariantType.EXTENSION)) {
			
			sb.append(addExtension(threeLett, null));
			return sb.toString();
		} else if(this.getVariantType().equals(VariantType.DELETION_INSERTION)) {
			sb.append("delins");
		} 
		
		if(this.ter) {
			sb.append(addTer(threeLett));
		}
		return sb.toString();
	}
	
}
