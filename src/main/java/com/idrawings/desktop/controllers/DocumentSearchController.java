package com.idrawings.desktop.controllers;

import com.idrawings.desktop.domain.FilterCriteria;
import com.idrawings.desktop.domain.TableItem;

import com.idrawings.dto.document.DocumentDTO;
import com.idrawings.dto.drawing.StampDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by White Stream on 30.05.2017.
 */
@Component
public class DocumentSearchController {

    private Map<DocumentDTO, ObservableValue<Boolean>> map = new LinkedHashMap<>();

    @FXML
    private ListView<DocumentDTO> drawingsList;
    @FXML
    private TableView<TableItem> drawingInfoTable;
    @FXML
    private TableColumn<TableItem, String> key, value;

    @FXML
    private TextField author;
    @FXML
    private TextField checked;
    @FXML
    private TextField code;
    @FXML
    private TextField name;
    @FXML
    private TextField material;
    @FXML
    private TextField organization;
    @FXML
    private TextField weight;
    @FXML
    private TextField text;
    @FXML
    private TextField size;
    @FXML
    private TextField table;

    @FXML
    private void reset() {
        author.setText("");
        checked.setText("");
        code.setText("");
        name.setText("");
        material.setText("");
        organization.setText("");
        weight.setText("");
        text.setText("");
        size.setText("");
        table.setText("");
    }

    @FXML
    private void initialize() {
        key.setCellValueFactory(new PropertyValueFactory<>("key"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        drawingsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setListeners();
    }

    private FilterCriteria getFilterCriteria() {
        FilterCriteria filterCriteria = new FilterCriteria();

        filterCriteria.setAuthor(author.getText());
        filterCriteria.setChecked(checked.getText());
        filterCriteria.setCode(code.getText());
        filterCriteria.setName(name.getText());
        filterCriteria.setMaterial(material.getText());
        filterCriteria.setOrganization(organization.getText());
        filterCriteria.setWeight(weight.getText());
        filterCriteria.setText(text.getText());
        filterCriteria.setSize(size.getText());
        filterCriteria.setTable(table.getText());

        return filterCriteria;
    }

    public void addDrawingToList(DocumentDTO file) {
        map.put(file, new SimpleBooleanProperty(false));
        drawingsList.setCellFactory(CheckBoxListCell.forListView(map::get));
        drawingsList.getItems().add(file);
    }

    private void setListeners() {
        drawingsList.getSelectionModel().getSelectedItems().addListener((ListChangeListener<DocumentDTO>) c -> {
            drawingInfoTable.setItems(getInitialTableData(drawingsList.getSelectionModel().getSelectedItem()));
        });
    }

    private ObservableList<TableItem> getInitialTableData(DocumentDTO selectedFile) {
        if (selectedFile == null) {
            return FXCollections.emptyObservableList();
        }

        StampDTO stamp = selectedFile.getStampDTO();

        List<TableItem> list = new ArrayList<>();
        list.add(new TableItem(new SimpleStringProperty("Наименование изделия"), new SimpleStringProperty(stamp.getKsStPartNumber())));
        list.add(new TableItem(new SimpleStringProperty("Обозначение документа"), new SimpleStringProperty(stamp.getKsStDescription())));
        list.add(new TableItem(new SimpleStringProperty("Обозначение материала"), new SimpleStringProperty(stamp.getKsStMaterial())));
        list.add(new TableItem(new SimpleStringProperty("Масса изделия"), new SimpleStringProperty(stamp.getKsStMass())));
        list.add(new TableItem(new SimpleStringProperty("Масштаб"), new SimpleStringProperty(stamp.getKsStScale())));
        list.add(new TableItem(new SimpleStringProperty("Номер листа"), new SimpleStringProperty(stamp.getKsStSheetNumber())));
        list.add(new TableItem(new SimpleStringProperty("Количество листов"), new SimpleStringProperty(stamp.getKsStNumberOfSheets())));
        list.add(new TableItem(new SimpleStringProperty("Индекс предприятия"), new SimpleStringProperty(stamp.getKsStCompany())));
        list.add(new TableItem(new SimpleStringProperty("Характер работы"), new SimpleStringProperty(stamp.getKsStTypeOfWork())));
        list.add(new TableItem(new SimpleStringProperty("Литера документа (графа 1)"), new SimpleStringProperty(stamp.getKsStDocumentLetter1())));
        list.add(new TableItem(new SimpleStringProperty("Литера документа (графа 2)"), new SimpleStringProperty(stamp.getKsStDocumentLetter2())));
        list.add(new TableItem(new SimpleStringProperty("Литера документа (графа 3)"), new SimpleStringProperty(stamp.getKsStDocumentLetter3())));
        list.add(new TableItem(new SimpleStringProperty("Имя файла (полное)"), new SimpleStringProperty(stamp.getKsStFullFileName())));
        list.add(new TableItem(new SimpleStringProperty("Имя файла (короткое)"), new SimpleStringProperty(stamp.getKsStShortFileName())));
        list.add(new TableItem(new SimpleStringProperty("Строка обозначения и дефис"), new SimpleStringProperty(stamp.getKsStMarkingLine())));
        list.add(new TableItem(new SimpleStringProperty("Наименование документа"), new SimpleStringProperty(stamp.getKsStDocumentName())));
        list.add(new TableItem(new SimpleStringProperty("Код документа"), new SimpleStringProperty(stamp.getKsStDocumentCode())));
        list.add(new TableItem(new SimpleStringProperty("Код ОКП"), new SimpleStringProperty(stamp.getKsStOKPCode())));
        list.add(new TableItem(new SimpleStringProperty("Фамилия разработавшего"), new SimpleStringProperty(stamp.getKsStAuthor())));
        list.add(new TableItem(new SimpleStringProperty("Фамилия проверившего"), new SimpleStringProperty(stamp.getKsStCheckedBy())));
        list.add(new TableItem(new SimpleStringProperty("Фамилия тех.контр"), new SimpleStringProperty(stamp.getKsStMfgApprovedBy())));
        list.add(new TableItem(new SimpleStringProperty("Фамилия вып.работу"), new SimpleStringProperty(stamp.getKsStDesigner())));
        list.add(new TableItem(new SimpleStringProperty("Фамилия норм.контр"), new SimpleStringProperty(stamp.getKsStRateOfInspection())));
        list.add(new TableItem(new SimpleStringProperty("Фамилия утверждающего"), new SimpleStringProperty(stamp.getKsStApprovedBy())));
        list.add(new TableItem(new SimpleStringProperty("Дата окончания разработки"), new SimpleStringProperty(stamp.getKsStEndDesignDate())));
        list.add(new TableItem(new SimpleStringProperty("Дата проверки"), new SimpleStringProperty(stamp.getKsStCheckedDate())));
        list.add(new TableItem(new SimpleStringProperty("Дата тех.контр"), new SimpleStringProperty(stamp.getKsStMfgApprovedDate())));
        list.add(new TableItem(new SimpleStringProperty("Дата выполнения"), new SimpleStringProperty(stamp.getKsStExecutionDate())));
        list.add(new TableItem(new SimpleStringProperty("Дата норм.контр"), new SimpleStringProperty(stamp.getKsStRateOfInspectionDate())));
        list.add(new TableItem(new SimpleStringProperty("Дата утверждения"), new SimpleStringProperty(stamp.getKsStApprovedDate())));

        return FXCollections.observableList(list);
    }
}
