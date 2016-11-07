package com.microservice.training.controller;

import com.microservice.training.resource.HALRecommendation;
import com.microservice.training.resource.HALRecommendations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RecommendationController {

    @GetMapping(path = "/", produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
    public HttpEntity<ResourceSupport> root() {

        ResourceSupport halResponse = new ResourceSupport();
        halResponse.add(
                new Link(new UriTemplate(linkTo(RecommendationController.class, "").slash("/recommendations").toString() + "{?eventId}"), "recommendations"));
        halResponse.add(
                new Link(
                        new UriTemplate(linkTo(RecommendationController.class, "").slash("recommendations").toString() + "/{recommendationId}"), "recommendation"));

        return new ResponseEntity<>(halResponse, HttpStatus.OK);
    }

    @RequestMapping("/recommendations/{id}")
    public HttpEntity<HALRecommendation> recommendation(@PathVariable String id) {
        HALRecommendation recommendation = generateHalRecommendation(id);
        recommendation.add(linkTo(methodOn(RecommendationController.class).recommendation(id)).withRel("self"));
        return new ResponseEntity<>(recommendation, HttpStatus.OK);
    }


    @RequestMapping("/recommendations")
    public HttpEntity<HALRecommendations> recommendations(@RequestParam(value = "eventId") String eventId) {
        HALRecommendations halRecommendations = new HALRecommendations();
        halRecommendations.add(linkTo(methodOn(RecommendationController.class).recommendations(eventId)).withRel("self"));
        halRecommendations.add("recommendations", generateHALRecommendations());
        return new ResponseEntity<>(halRecommendations, HttpStatus.OK);
    }

    private HALRecommendation generateHalRecommendation(String id) {
        return new HALRecommendation(id, id, "content " + id);
    }

    private List<HALRecommendation> generateHALRecommendations() {
        List<HALRecommendation> recommends = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            String recommendId = String.valueOf(i);
            HALRecommendation recommendation = generateHalRecommendation(recommendId);
            recommendation.add(linkTo(methodOn(RecommendationController.class).recommendation(recommendId)).withRel("self"));
            recommends.add(recommendation);
        }
        return recommends;
    }
}