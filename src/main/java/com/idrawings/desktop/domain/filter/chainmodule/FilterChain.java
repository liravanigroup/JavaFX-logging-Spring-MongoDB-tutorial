package com.idrawings.desktop.domain.filter.chainmodule;


import com.idrawings.desktop.domain.FilterCriteria;

/**
 * Created by Admin on 25.02.2017.
 */
public abstract class FilterChain {
    protected FilterChain nextChain;
    protected FilterCriteria criteria;

    public FilterChain(FilterChain nextChain, FilterCriteria criteria) {
        this.nextChain = nextChain;
        this.criteria = criteria;
    }

    boolean isFiltered(String authorDrawing, String authorFilter){
        return authorDrawing.toLowerCase().contains(authorFilter.toLowerCase());
    }

    public abstract boolean filter(DocumentDTO documentDTO);

    public FilterCriteria getCriteria() {
        return criteria;
    }
}
