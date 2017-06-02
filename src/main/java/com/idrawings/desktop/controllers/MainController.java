package com.idrawings.desktop.controllers;

import com.idrawings.dto.file.LocalFileDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Admin on 27.05.2017.
 */
@Component
public class MainController {

    private ObservableList<LocalFileDTO> localFileObservableList = FXCollections.observableArrayList();

    @Autowired
    private DocumentSearchController documentSearchController;

    @FXML
    private Label totalFilesCount;
    @FXML
    private Label totalFilesSize;
    @FXML
    private Label selectedFilesCount;


    public void setTotalFilesCount(String totalFilesCount) {
        this.totalFilesCount.setText(totalFilesCount);
    }

    public void setTotalFilesSize(String totalFilesSize) {
        this.totalFilesSize.setText(totalFilesSize);
    }

    public void setSelectedFilesCount(String selectedFilesCount) {
        this.selectedFilesCount.setText(selectedFilesCount);
    }

    public ObservableList<LocalFileDTO> getLocalFileObservableList() {
        return localFileObservableList;
    }

}
