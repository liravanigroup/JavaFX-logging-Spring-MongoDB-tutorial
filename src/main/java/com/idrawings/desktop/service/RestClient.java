package com.idrawings.desktop.service;

import com.idrawings.desktop.domain.FileSearchCriteria;
import com.idrawings.dto.document.DocumentDTO;
import com.idrawings.dto.file.LocalFileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by White Stream on 13.05.2017.
 */
@Service
public class RestClient {

    @Autowired
    private RestTemplate restTemplate;

    private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

    private final static String FILE_SEARCH_SERVICE = "http://localhost:5001";
    private final static String KOMPAS_SERVICE = "http://localhost:5500";


    public BigDecimal getFilesCountInIndex() {
        return restTemplate.getForObject(FILE_SEARCH_SERVICE + "/files/count", BigDecimal.class);
    }

    public void createIndex() {
        restTemplate.put(FILE_SEARCH_SERVICE + "/files", String.class);
    }

    public void updateIndex() {
        cleanIndex();
        createIndex();
    }

    private void cleanIndex() {
        restTemplate.delete(FILE_SEARCH_SERVICE + "/files");
    }

    public List<LocalFileDTO> findFiles(FileSearchCriteria criteria) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        ParameterizedTypeReference<List<LocalFileDTO>> typeReference = new ParameterizedTypeReference<List<LocalFileDTO>>() {
        };

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FILE_SEARCH_SERVICE + "/files/search")
                .queryParam("discs", criteria.getDiscs().stream().collect(Collectors.joining(",")))
                .queryParam("formats", criteria.getFormats().stream().collect(Collectors.joining(",")))
                .queryParam("partOfFileName", criteria.getPartOfFileName())
                .queryParam("pathOfPFilePath", criteria.getPathOfPFilePath())
                .queryParam("creationDateFrom", criteria.getCreationDateFrom())
                .queryParam("creationDateUntil", criteria.getCreationDateUntil())
                .queryParam("lastModifiedDateFrom", criteria.getLastModifiedDateFrom())
                .queryParam("lastModifiedDateUntil", criteria.getLastModifiedDateUntil())
                .queryParam("lastAccessDateFrom", criteria.getLastAccessDateFrom())
                .queryParam("lastAccessDateUntil", criteria.getLastAccessDateUntil())
                .queryParam("fileSizeFrom", criteria.getFileSizeFrom())
                .queryParam("fileSizeUntil", criteria.getFileSizeUntil());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<List<LocalFileDTO>> rateResponse = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, typeReference);

        return rateResponse.getBody();
    }

    public List<DocumentDTO> getDataFromFile(List<LocalFileDTO> localFileDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<String> personList = localFileDTO.stream().map(LocalFileDTO::getPath).collect(Collectors.toList());

        HttpEntity<Object> requestEntity = new HttpEntity<>(personList, headers);
        ResponseEntity<List<DocumentDTO>> rateResponse = restTemplate.exchange(KOMPAS_SERVICE + "/drawings", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<DocumentDTO>>() {
        });

        return rateResponse.getBody();
    }
}