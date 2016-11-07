package com.microservice.training.gateway;

import com.microservice.training.model.Event;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventCompositeGateway {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private RestOperations restTemplate;

    public Event getEvent(String eventId) {
        return parseEvent(restTemplate.getForEntity(loadBalancer.choose("EVENT").getUri() + "/events/" + eventId, String.class));
    }

    public List getRecommendations(String eventId) {
        ResponseEntity<String> resultStr = null;
        try
        {
            resultStr = restTemplate.getForEntity(loadBalancer.choose("RECOMMENDATION").getUri() + "/recommendations?eventId=" + eventId, String.class);
        }
        catch (Exception e){}
        if (null != resultStr){
            return parseRecommends(resultStr);
        }
        return new ArrayList<>();
    }

    public List getReviews(String eventId) {
        ResponseEntity<String> resultStr = null;
        try
        {
            resultStr = restTemplate.getForEntity(loadBalancer.choose("REVIEW").getUri() + "/reviews?eventId=" + eventId, String.class);
        }
        catch (Exception e){}
        if (null != resultStr){
            return parseReviews(resultStr);
        }
        return new ArrayList<>();
    }

    private Event parseEvent(ResponseEntity<String> resp) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Event event = null;
        try {
            event = objectMapper.readerFor(Event.class).readValue(resp.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return event;
    }

    private List parseRecommends(ResponseEntity<String> resp) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode node = null;
        try {
            node = objectMapper.readerFor(JsonNode.class).readValue(resp.getBody());
            JsonNode jsonNode = node.get("_embedded").get("recommendations");
            return objectMapper.convertValue(jsonNode, List.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List parseReviews(ResponseEntity<String> resp) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode node = null;
        try {
            node = objectMapper.readerFor(JsonNode.class).readValue(resp.getBody());
            JsonNode jsonNode = node.get("_embedded").get("reviews");
            return objectMapper.convertValue(jsonNode, List.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
