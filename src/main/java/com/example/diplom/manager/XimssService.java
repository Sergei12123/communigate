package com.example.diplom.manager;

import com.example.diplom.annotation.PreLoginRequest;
import com.example.diplom.ximss.BaseXIMSS;
import com.example.diplom.ximss.request.Login;
import com.example.diplom.ximss.response.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Service
@AllArgsConstructor
public class XimssService {

    private final XmlMapper xmlMapper;

    private final RestTemplate restTemplate;

    static private final String DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS = "http://localhost:8100/ximsslogin/";

    static private final String DEFAULT_URL_FOR_LOGIN_OPERATIONS = "http://localhost:8100/ximsslogin/";

    public static final String DUMB_LOGIN_URL = "http://localhost:8100/ximsslogin/?userName={userName}&password={password}";

    public static final String LOGIN_URL = "http://localhost:8100/ximsslogin/?userName={userName}&password={password}";


    public <T, P extends BaseXIMSS> P sendRequest(final T requestXimssEntity, final Class<P> responseClass) {
        try {
            String res = wrapInXimssTag(xmlMapper.writeValueAsString(requestXimssEntity));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> request = new HttpEntity<>(res, headers);

            final ResponseEntity<String> response = restTemplate.postForEntity(
                    requestXimssEntity.getClass().isAnnotationPresent(PreLoginRequest.class) ?
                            DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS
                            : DEFAULT_URL_FOR_LOGIN_OPERATIONS,
                    request,
                    String.class
            );

            return xmlMapper.readValue(unwrapXimss(Objects.requireNonNull(response.getBody())), responseClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public Session makeDumbLogin(final Login loginEntity) {
        try {
            HttpHeaders headers = new HttpHeaders();
            String auth = loginEntity.getUserName() + ":" + loginEntity.getPassword();
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.set("Authorization", authHeader);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response;
            try {
                response = restTemplate.exchange(
                        DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS,
                        HttpMethod.GET,
                        entity,
                        String.class);
            } catch (Exception e) {
                response = ResponseEntity.badRequest().build();
            }
            if (response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
                return xmlMapper.readValue(unwrapXimss(Objects.requireNonNull(response.getBody())), Session.class);
            } else {
                return Session.builder().build();
            }


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    private String wrapInXimssTag(final String xml) {
        return "<XIMSS>" + xml + "</XIMSS>";
    }

    private String unwrapXimss(final String xml) {
        return xml.replace("<XIMSS>", "").replace("</XIMSS>", "");
    }
}
