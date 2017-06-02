package com.idrawings.desktop.domain.filter.chainmodule;


import java.util.List;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Created by White Stream on 16.04.2017.
 */
public class SizeFilter extends FilterChain {
    public SizeFilter(FilterChain nextChain) {
        super(nextChain, nextChain.getCriteria());
    }

    @Override
    public boolean filter(DocumentDTO documentDTO) {
        return defaultString(criteria.getSize()).isEmpty() ? nextChain.filter(documentDTO) : isFiltered(documentDTO.getDocumentSizes(), criteria.getSize());
    }

    private boolean isFiltered(List<String> sizes, String pattern){
        return sizes.stream().anyMatch(s -> s.toLowerCase().contains(pattern.toLowerCase()));
    }
}
