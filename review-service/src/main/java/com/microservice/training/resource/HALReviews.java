package com.microservice.training.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HALReviews extends ResourceSupport {
    public HALReviews() {
        this.embedded = new HashMap<>();
    }

    @JsonProperty("_embedded")
    private Map<String, List<HALReview>> embedded;

    public void add(String name, List<HALReview> embedded) {
        this.embedded.put(name, embedded);
    }

    public Map<String, List<HALReview>> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Map<String, List<HALReview>> embedded) {
        this.embedded = embedded;
    }
}
