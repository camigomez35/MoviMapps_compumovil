package co.edu.udea.moviemapps.model;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "page",
        "results",
        "total_pages",
        "total_results"
})

public class ServiceResult {

    @JsonProperty("page")
    private Integer page;
    @JsonProperty("results")
    private List<Movie> results = new ArrayList<>();
    @JsonProperty("resultsClassification")
    private List<Classification> resultsClassification = new ArrayList<>();
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("total_results")
    private Integer totalResults;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    @JsonProperty("results")
    public List<Movie> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @JsonProperty("resultsClassification")
    public void setResultsClassfication(List<Classification> results) {
        this.resultsClassification = results;
    }

    @JsonProperty("resultsClassification")
    public List<Classification> getResultsClassfication() {
        return resultsClassification;
    }

    @JsonProperty("total_pages")
    public Integer getTotalPages() {
        return totalPages;
    }

    @JsonProperty("total_pages")
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @JsonProperty("total_results")
    public Integer getTotalResults() {
        return totalResults;
    }

    @JsonProperty("total_results")
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
