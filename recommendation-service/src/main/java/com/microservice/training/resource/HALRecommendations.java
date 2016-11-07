package com.microservice.training.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.ResourceSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HALRecommendations extends ResourceSupport {
    public HALRecommendations() {
        this.embedded = new HashMap<>();
    }

    @JsonProperty("_embedded")
    private Map<String, List<HALRecommendation>> embedded;

    public void add(String name, List<HALRecommendation> embedded) {

        this.embedded.put(name, embedded);
    }

    public Map<String, List<HALRecommendation>> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Map<String, List<HALRecommendation>> embedded) {
        this.embedded = embedded;
    }
}
