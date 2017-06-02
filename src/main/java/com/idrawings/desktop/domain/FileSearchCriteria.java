package com.idrawings.desktop.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by White Stream on 30.05.2017.
 */
@JsonSerialize
public class FileSearchCriteria {
    private List<String> discs;
    private List<String> formats;
    private String partOfFileName;
    private String pathOfPFilePath;
    private String creationDateFrom, creationDateUntil;
    private String lastModifiedDateFrom, lastModifiedDateUntil;
    private String lastAccessDateFrom, lastAccessDateUntil;
    private BigDecimal fileSizeFrom, fileSizeUntil;

    public List<String> getDiscs() {
        return discs;
    }

    public void setDiscs(List<String> discs) {
        this.discs = discs;
    }

    public List<String> getFormats() {
        return formats;
    }

    public void setFormats(List<String> formats) {
        this.formats = formats;
    }

    public String getPartOfFileName() {
        return partOfFileName;
    }

    public void setPartOfFileName(String partOfFileName) {
        this.partOfFileName = partOfFileName;
    }

    public String getPathOfPFilePath() {
        return pathOfPFilePath;
    }

    public void setPathOfPFilePath(String pathOfPFilePath) {
        this.pathOfPFilePath = pathOfPFilePath;
    }

    public String getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(String creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public String getCreationDateUntil() {
        return creationDateUntil;
    }

    public void setCreationDateUntil(String creationDateUntil) {
        this.creationDateUntil = creationDateUntil;
    }

    public String getLastModifiedDateFrom() {
        return lastModifiedDateFrom;
    }

    public void setLastModifiedDateFrom(String lastModifiedDateFrom) {
        this.lastModifiedDateFrom = lastModifiedDateFrom;
    }

    public String getLastModifiedDateUntil() {
        return lastModifiedDateUntil;
    }

    public void setLastModifiedDateUntil(String lastModifiedDateUntil) {
        this.lastModifiedDateUntil = lastModifiedDateUntil;
    }

    public String getLastAccessDateFrom() {
        return lastAccessDateFrom;
    }

    public void setLastAccessDateFrom(String lastAccessDateFrom) {
        this.lastAccessDateFrom = lastAccessDateFrom;
    }

    public String getLastAccessDateUntil() {
        return lastAccessDateUntil;
    }

    public void setLastAccessDateUntil(String lastAccessDateUntil) {
        this.lastAccessDateUntil = lastAccessDateUntil;
    }

    public BigDecimal getFileSizeFrom() {
        return fileSizeFrom;
    }

    public void setFileSizeFrom(BigDecimal fileSizeFrom) {
        this.fileSizeFrom = fileSizeFrom;
    }

    public BigDecimal getFileSizeUntil() {
        return fileSizeUntil;
    }

    public void setFileSizeUntil(BigDecimal fileSizeUntil) {
        this.fileSizeUntil = fileSizeUntil;
    }

    @Override
    public String toString() {
        return "FileSearchCriteria{" +
                "discs=" + discs +
                ", formats=" + formats +
                ", partOfFileName='" + partOfFileName + '\'' +
                ", pathOfPFilePath='" + pathOfPFilePath + '\'' +
                ", creationDateFrom=" + creationDateFrom +
                ", creationDateUntil=" + creationDateUntil +
                ", lastModifiedDateFrom=" + lastModifiedDateFrom +
                ", lastModifiedDateUntil=" + lastModifiedDateUntil +
                ", lastAccessDateFrom=" + lastAccessDateFrom +
                ", lastAccessDateUntil=" + lastAccessDateUntil +
                ", fileSizeFrom=" + fileSizeFrom +
                ", fileSizeUntil=" + fileSizeUntil +
                '}';
    }
}
