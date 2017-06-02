package com.idrawings.desktop.domain.filter;


import com.idrawings.desktop.domain.FilterCriteria;
import com.idrawings.desktop.domain.filter.chainmodule.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by White Stream on 16.04.2017.
 */
public class DrawingFilter {
    public ObservableList<DocumentDTO> filter(ObservableList<DocumentDTO> drawingsList, FilterCriteria filterCriteria) {

        FilterChain filterChain = new AuthorFilter(new CheckedFilter(new CodeFilter(new WeightFilter(new MaterialFilter(new NameFilter(new OrganizationFilter(new SizeFilter(new TableFilter(new TextFilter(new EndFilter(filterCriteria)))))))))));

        ObservableList<DocumentDTO> result = FXCollections.observableArrayList();

        for (DocumentDTO documentDTO : drawingsList){
            if (filterChain.filter(documentDTO)) {
                result.add(documentDTO);
            }
        }

        return result;
    }
}
