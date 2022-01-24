package uk.ac.ebi.uniprot.variation;

import java.util.List;

public interface VCFMeta {
    String getVCFVersion();

    List<VCFMetaInfo> getInfos();

    List<VCFMetaFilter> getFilters();

    List<VCFMetaFormat> getFormats();
}
