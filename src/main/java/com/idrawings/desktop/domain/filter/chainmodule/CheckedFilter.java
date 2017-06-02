package com.idrawings.desktop.domain.filter.chainmodule;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Created by White Stream on 16.04.2017.
 */
public class CheckedFilter extends FilterChain {
    public CheckedFilter(FilterChain nextChain) {
        super(nextChain, nextChain.getCriteria());
    }

    @Override
    public boolean filter(DocumentDTO documentDTO) {
        return defaultString(criteria.getChecked()).isEmpty() ? nextChain.filter(documentDTO) : isFiltered(documentDTO.getStampDTO().getKsStCheckedBy(), criteria.getChecked()) && nextChain.filter(documentDTO);
    }
}
