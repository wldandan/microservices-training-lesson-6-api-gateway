package com.microservice.training.controller;

import com.microservice.training.resource.HALReviews;
import com.microservice.training.resource.HALReview;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class ReviewController {
    private static final Logger LOG = LoggerFactory.getLogger(ReviewController.class);

    @GetMapping(path = "/", produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
    public HttpEntity<ResourceSupport> root() {
        ResourceSupport halResponse = new ResourceSupport();
        halResponse.add(
                new Link(
                        new UriTemplate(linkTo(ReviewController.class, "").slash("/reviews").toString() + "{?eventId}"), "reviews"));
        halResponse.add(
                new Link(
                        new UriTemplate(linkTo(ReviewController.class, "").slash("reviews").toString() + "/{reviewId}"), "review"));

        return new ResponseEntity<>(halResponse, HttpStatus.OK);
    }

    @RequestMapping("/reviews/{id}")
    public HttpEntity<HALReview> review(@PathVariable String id) {
        HALReview review = generateReview(id);
        review.add(linkTo(methodOn(ReviewController.class).review(id)).withRel("self"));
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @RequestMapping("/reviews")
    public HttpEntity<HALReviews> reviews(@RequestParam(value = "eventId") String eventId) {
        HALReviews halReviews = new HALReviews();
        halReviews.add(linkTo(methodOn(ReviewController.class).reviews(eventId)).withRel("self"));
        halReviews.add("reviews", generateReviews(eventId));
        return new ResponseEntity<>(halReviews, HttpStatus.OK);
    }

    private HALReview generateReview(String id){
        return new HALReview(id, id, "author " + id, "subject " + id, "content " + id);
    }

    private List<HALReview> generateReviews(String eventId){
        List<HALReview> reviews = new ArrayList<HALReview>();
        for (int i = 1; i < 4; i++) {
            HALReview review = new HALReview(eventId, String.valueOf(i), "author " + i, "subject " + i, "content " + i);
            review.add(linkTo(methodOn(ReviewController.class).review(String.valueOf(i))).withRel("self"));
            reviews.add(review);
        }
        return reviews;
    }
}