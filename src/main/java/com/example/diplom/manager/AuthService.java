package com.example.diplom.manager;

import com.example.diplom.dto.UserDTO;
import com.example.diplom.service.UserCache;
import com.example.diplom.ximss.BaseXIMSSRequest;
import com.example.diplom.ximss.request.Login;
import com.example.diplom.ximss.request.Signup;
import com.example.diplom.ximss.response.Response;
import com.example.diplom.ximss.response.Session;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {

    private final XmlMapper xmlMapper;

    private final RestTemplate restTemplate;

    private final UserCache userCache;

    private final XimssService ximssService;

    static private final String DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS = "http://localhost:8100/ximsslogin/";

    static private final String DEFAULT_SESSION_SYNC_REQUEST_URL = "http://localhost:8100/Session/%s/sync";

    public static final String GET_MESSAGE_URL = "http://localhost:8100/Session/%s/MIME/INBOX/%d-P.txt";

    public static final String GET_REQUEST_URL = "http://localhost:8100/Session/%s/get?maxWait=%d";

    public Session makeBasicLogin(final Login loginEntity) {
        HttpHeaders headers = new HttpHeaders();
        String auth = loginEntity.getUserName() + ":" + loginEntity.getPassword();
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.setAccept(List.of(MediaType.ALL));
        headers.setConnection("keep-alive");
        headers.setCacheControl(CacheControl.noCache());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(
                DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS,
                HttpMethod.POST,
                entity,
                String.class);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().build();
        }
        if (response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return ximssService.getObjectFromXML(response.getBody(), Session.class);
        } else {
            return Session.builder().build();
        }
    }

    public Response makeSignup(final UserDTO userDTO) {
        return makeSignup(Signup.builder().userName(userDTO.getUserLogin()).password(userDTO.getPassword()).build());
    }

    public Response makeSignup(final Signup signup) {
        String res = ximssService.getXML(signup);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache());
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.ALL));
        headers.setConnection("keep-alive");
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(res, headers);
        final String response = restTemplate.postForObject(
            ximssService.getNecessaryUrl(signup),
            stringHttpEntity,
            String.class
        );
        List<Response> listFromXML = ximssService.getListFromXML(response, Response.class);
        return listFromXML.isEmpty() ? null : listFromXML.get(0);
    }

    private <T extends BaseXIMSSRequest> HttpEntity<String> getRequestWithBody(final T requestXimssEntity) {
        String res = ximssService.getXML(requestXimssEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache());
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(List.of(MediaType.ALL));
        headers.setConnection("keep-alive");
        return new HttpEntity<>(res, headers);
    }

}
