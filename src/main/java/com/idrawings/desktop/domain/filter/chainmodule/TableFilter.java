package com.idrawings.desktop.domain.filter.chainmodule;

import java.util.List;

/**
 * Created by White Stream on 16.04.2017.
 */
public class TableFilter extends FilterChain {
    public TableFilter(FilterChain nextChain) {
        super(nextChain, nextChain.getCriteria());
    }

    @Override
    public boolean filter(DocumentDTO documentDTO) {
        return criteria.getTable().isEmpty() ? nextChain.filter(documentDTO) : isFiltered(documentDTO.getDocumentTables(), criteria.getTable());
    }

    private boolean isFiltered(List<String> tablesEntries, String pattern){
        return tablesEntries.stream().anyMatch(s -> s.toLowerCase().contains(pattern.toLowerCase()));
    }
}
