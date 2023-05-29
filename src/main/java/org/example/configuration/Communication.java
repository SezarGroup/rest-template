package org.example.configuration;

import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class Communication {
    private final String URL = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    @Autowired
    public Communication(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.httpHeaders.set("Cookie",
                String.join(";", Objects.requireNonNull(restTemplate.headForHeaders(URL).get("Set-Cookie"))));
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
        System.out.println(responseEntity.getHeaders());
        return responseEntity.getBody();
    }

    public String addUser(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, entity, String.class);
        System.out.println("new user added = " + user);
        return responseEntity.getBody();
    }

    public String updateUser(long id, User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class, id);
        System.out.println("user with id  = " + id + " updated");
        return responseEntity.getBody();
    }

    public String deleteUser(long id) {
        HttpEntity<User> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/{id}", HttpMethod.DELETE, entity, String.class, id);
        System.out.println("user with id = " + id + " deleted");
        return responseEntity.getBody();
    }
}
