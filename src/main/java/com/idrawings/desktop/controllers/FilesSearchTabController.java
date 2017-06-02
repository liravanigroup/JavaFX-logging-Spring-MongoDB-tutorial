package com.idrawings.desktop.controllers;

import com.idrawings.desktop.domain.FileSearchCriteria;
import com.idrawings.desktop.domain.TableItem;
import com.idrawings.desktop.service.RestClient;
import com.idrawings.dto.enums.FileFormat;
import com.idrawings.dto.file.LocalFileDTO;
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
import javafx.util.Callback;
import jfxtras.labs.scene.control.BigDecimalField;
import org.controlsfx.control.CheckComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.io.File.listRoots;
import static java.util.stream.Stream.of;

@Component
public class FilesSearchTabController {

    private static final int FIRST_ELEMENT = 0;

    @Value("${rest.date.template}")
    private String dateTemplate;

    @Autowired
    private RestClient restClient;
    @Autowired
    private MainController mainController;
    @Autowired
    private DocumentSearchController documentSearchController;

    private Map<LocalFileDTO, ObservableValue<Boolean>> map = new LinkedHashMap<>();

    @FXML
    private ListView<LocalFileDTO> fileList;
    @FXML
    private TableView<TableItem> fileInfoTable;
    @FXML
    private TableColumn<TableItem, String> key, value;
    @FXML
    private CheckComboBox<String> discMultiCheckBox;
    @FXML
    private CheckComboBox<String> formatMultiCheckBox;
    @FXML
    private TextField partOfFileName, partOfFilePath;
    @FXML
    private DatePicker creationDateFrom, creationDateUntil;
    @FXML
    private DatePicker lastModifiedDateFrom, lastModifiedDateUntil;
    @FXML
    private DatePicker lastAccessDateFrom, lastAccessDateUntil;
    @FXML
    private BigDecimalField fileSizeFrom, fileSizeUntil;
    @FXML
    private Button index;

    @FXML
    private void readData() {
        List<LocalFileDTO> files = new LinkedList<>();
        for (LocalFileDTO key : map.keySet()) {
            if (map.get(key).getValue()) {
                files.add(key);
            }
        }

        restClient.getDataFromFile(files).forEach(f -> documentSearchController.addDrawingToList(f));
    }

    @FXML
    private void initialize() {
        key.setCellValueFactory(new PropertyValueFactory<>("key"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        fileList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        formatMultiCheckBox.getItems().addAll(FileFormat.getAll());
        formatMultiCheckBox.getCheckModel().check(FIRST_ELEMENT);
        discMultiCheckBox.getItems().addAll(of(listRoots()).map(file -> file.getAbsolutePath().substring(0, 1)).sorted().toArray(String[]::new));
        discMultiCheckBox.getCheckModel().check(FIRST_ELEMENT);
        fileSizeUntil.setNumber(new BigDecimal(0));
        fileSizeFrom.setNumber(new BigDecimal(0));
        fileSizeFrom.setMinValue(new BigDecimal(0));
        fileSizeUntil.setMinValue(new BigDecimal(0));
        initIndexButton();
        setListeners();
    }

    private void setListeners() {
        ObservableList<LocalFileDTO> checkedList = fileList.getSelectionModel().getSelectedItems();
        checkedList.addListener((ListChangeListener<LocalFileDTO>) c -> {
            fileInfoTable.setItems(getInitialTableData(fileList.getSelectionModel().getSelectedItem()));
        });
    }

    private ObservableList<TableItem> getInitialTableData(LocalFileDTO selectedFile) {
        if (selectedFile == null) {
            return FXCollections.emptyObservableList();
        }
        List<TableItem> list = new ArrayList<>();

        list.add(new TableItem(new SimpleStringProperty("ID"), new SimpleStringProperty(selectedFile.getId().toString())));
        list.add(new TableItem(new SimpleStringProperty("Название файла"), new SimpleStringProperty(selectedFile.getName())));
        list.add(new TableItem(new SimpleStringProperty("Формат"), new SimpleStringProperty(selectedFile.getExtension())));
        list.add(new TableItem(new SimpleStringProperty("Путь к файлу"), new SimpleStringProperty(selectedFile.getPath())));
        list.add(new TableItem(new SimpleStringProperty("Размер"), new SimpleStringProperty("151 КБ (154 760 байт)")));
        list.add(new TableItem(new SimpleStringProperty("Владелец"), new SimpleStringProperty(selectedFile.getSize().toString())));
        list.add(new TableItem(new SimpleStringProperty("Дата создания"), new SimpleStringProperty(selectedFile.getCreationDate().toString())));
        list.add(new TableItem(new SimpleStringProperty("Дата изменения"), new SimpleStringProperty(selectedFile.getModifiedDate().toString())));
        list.add(new TableItem(new SimpleStringProperty("Дата открытия"), new SimpleStringProperty(selectedFile.getAccessDate().toString())));
        list.add(new TableItem(new SimpleStringProperty("Программа"), new SimpleStringProperty("Kompac")));
        list.add(new TableItem(new SimpleStringProperty("Тип файла"), new SimpleStringProperty("KОМПАС-Чертеж")));
        list.add(new TableItem(new SimpleStringProperty("Атрибуты"), new SimpleStringProperty("А")));

        return FXCollections.observableList(list);
    }

    @FXML
    private void findFiles() {
        Collection<LocalFileDTO> resultList = restClient.findFiles(getCriteria());

        mainController.setTotalFilesCount(resultList.size() + "");
        mainController.setTotalFilesSize(resultList.stream().mapToLong(LocalFileDTO::getSize).sum() + "");

        resultList.forEach(file -> map.put(file, new SimpleBooleanProperty(false)));
        fileList.setCellFactory(CheckBoxListCell.forListView(map::get));
        fileList.getItems().addAll(resultList);
    }

    private FileSearchCriteria getCriteria() {
        FileSearchCriteria criteria = new FileSearchCriteria();
        criteria.setDiscs(discMultiCheckBox.getCheckModel().getCheckedItems());
        criteria.setFormats(formatMultiCheckBox.getCheckModel().getCheckedItems());
        criteria.setPartOfFileName(partOfFileName.getText());
        criteria.setPathOfPFilePath(partOfFilePath.getText());
        criteria.setCreationDateFrom(formatDate(creationDateFrom.getValue()));
        criteria.setCreationDateUntil(formatDate(creationDateUntil.getValue()));
        criteria.setLastModifiedDateFrom(formatDate(lastModifiedDateFrom.getValue()));
        criteria.setLastModifiedDateUntil(formatDate(lastModifiedDateUntil.getValue()));
        criteria.setLastAccessDateFrom(formatDate(lastAccessDateFrom.getValue()));
        criteria.setLastAccessDateUntil(formatDate(lastAccessDateUntil.getValue()));
        criteria.setFileSizeFrom(fileSizeFrom.getNumber());
        criteria.setFileSizeUntil(fileSizeUntil.getNumber());
        return criteria;
    }

    private String formatDate(LocalDate date) {
        return date == null ? null : date.format(DateTimeFormatter.ofPattern(dateTemplate));
    }


    @FXML
    private void processIndex() {
        if (index.getText().equals("Обновить индекс")) {
            restClient.updateIndex();
            index.setText("");
        } else {
            restClient.createIndex();
            index.setText("Обновить индекс");
        }
    }

    private void initIndexButton() {
        if (!restClient.getFilesCountInIndex().equals(BigDecimal.ZERO)) {
            index.setText("Обновить индекс");
        } else {
            index.setText("Создать индекс");
        }
    }

    @FXML
    private void resetParameters() {
        discMultiCheckBox.getCheckModel().clearChecks();
        discMultiCheckBox.getCheckModel().check(FIRST_ELEMENT);
        formatMultiCheckBox.getCheckModel().clearChecks();
        formatMultiCheckBox.getCheckModel().check(FIRST_ELEMENT);
        partOfFileName.clear();
        partOfFilePath.clear();
        creationDateFrom.setValue(null);
        creationDateUntil.setValue(null);
        lastAccessDateFrom.setValue(null);
        lastAccessDateUntil.setValue(null);
        lastModifiedDateFrom.setValue(null);
        lastModifiedDateUntil.setValue(null);
        fileSizeUntil.setNumber(new BigDecimal(0));
        fileSizeFrom.setNumber(new BigDecimal(0));
    }

    @FXML
    private void checkAll() {
        fileList.getItems().forEach(e -> map.put(e, new SimpleBooleanProperty(true)));
        fileList.getItems().clear();
        fileList.getItems().addAll(map.keySet());
        Callback<LocalFileDTO, ObservableValue<Boolean>> itemToBoolean = map::get;
        fileList.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        mainController.setSelectedFilesCount(fileList.getItems().size() + "");
    }

    @FXML
    private void uncheckedAll() {
        fileList.getItems().forEach(e -> map.put(e, new SimpleBooleanProperty(false)));
        fileList.getItems().clear();
        fileList.getItems().addAll(map.keySet());
        Callback<LocalFileDTO, ObservableValue<Boolean>> itemToBoolean = map::get;
        fileList.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        mainController.setSelectedFilesCount(0 + "");
    }

    @FXML
    private void cleanTable() {
        fileList.getItems().clear();
    }
}