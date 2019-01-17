package uk.ac.ebi.uniprot.variation.hgvs.impl;

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
		
		
		public Builder() {}
		

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
	
	
}
