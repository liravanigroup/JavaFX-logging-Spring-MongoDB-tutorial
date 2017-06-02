package com.idrawings.desktop.domain.filter.chainmodule;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Created by White Stream on 16.04.2017.
 */
public class AuthorFilter extends FilterChain {

    public AuthorFilter(FilterChain nextChain) {
        super(nextChain, nextChain.getCriteria());
    }

    @Override
    public boolean filter(DocumentDTO documentDTO) {
        return defaultString(criteria.getAuthor()).isEmpty() ?
                nextChain.filter(documentDTO) :
                isFiltered(documentDTO.getStampDTO().getKsStAuthor(), criteria.getAuthor()) && nextChain.filter(documentDTO);
    }
}
