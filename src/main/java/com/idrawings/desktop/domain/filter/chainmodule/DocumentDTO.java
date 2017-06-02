package com.idrawings.desktop.domain.filter.chainmodule;

import java.util.List;

/**
 * Created by White Stream on 30.05.2017.
 */
public class DocumentDTO {
    private List<String> documentSizes;
    private List<String> documentTables;
    private StampDTO stampDTO;
    private List<String> documentTexts;

    public List<String> getDocumentSizes() {
        return documentSizes;
    }

    public List<String> getDocumentTables() {
        return documentTables;
    }

    public StampDTO getStampDTO() {
        return stampDTO;
    }

    public List<String> getDocumentTexts() {
        return documentTexts;
    }
}
