package com.idrawings.desktop.domain.filter.chainmodule;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Created by White Stream on 16.04.2017.
 */
public class CodeFilter extends FilterChain {
    public CodeFilter(FilterChain nextChain) {
        super(nextChain, nextChain.getCriteria());
    }

    @Override
    public boolean filter(DocumentDTO documentDTO) {
        return defaultString(criteria.getCode()).isEmpty() ? nextChain.filter(documentDTO) : isFiltered(documentDTO.getStampDTO().getKsStDescription(), criteria.getCode()) && nextChain.filter(documentDTO);
    }
}
