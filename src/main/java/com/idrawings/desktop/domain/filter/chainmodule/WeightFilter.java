package com.idrawings.desktop.domain.filter.chainmodule;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Created by White Stream on 16.04.2017.
 */
public class WeightFilter extends FilterChain {
    public WeightFilter(FilterChain nextChain) {
        super(nextChain, nextChain.getCriteria());
    }

    @Override
    public boolean filter(DocumentDTO documentDTO) {
        return defaultString(criteria.getWeight()).isEmpty() ?
                nextChain.filter(documentDTO) :
                isFiltered(documentDTO.getStampDTO().getKsStMass(), criteria.getWeight()) && nextChain.filter(documentDTO);
    }
}
