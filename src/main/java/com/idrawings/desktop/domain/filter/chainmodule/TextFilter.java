package com.idrawings.desktop.domain.filter.chainmodule;

import java.util.List;

/**
 * Created by White Stream on 16.04.2017.
 */
public class TextFilter extends FilterChain {
    public TextFilter(FilterChain nextChain) {
        super(nextChain, nextChain.getCriteria());
    }

    @Override
    public boolean filter(DocumentDTO documentDTO) {
        return criteria.getText().isEmpty() ? nextChain.filter(documentDTO) : isFiltered(documentDTO.getDocumentTexts(), criteria.getText());
    }

    private boolean isFiltered(List<String> texts, String pattern){
        return texts.stream().anyMatch(s -> s.toLowerCase().contains(pattern.toLowerCase()));
    }
}
