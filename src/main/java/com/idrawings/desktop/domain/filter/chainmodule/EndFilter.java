package com.idrawings.desktop.domain.filter.chainmodule;


import com.idrawings.desktop.domain.FilterCriteria;

/**
 * Created by White Stream on 16.04.2017.
 */
public class EndFilter extends FilterChain {

    public EndFilter(FilterCriteria criteria) {
        super(null, criteria);
    }

    @Override
    public boolean filter(DocumentDTO documentDTO) {
        return true;
    }
}
