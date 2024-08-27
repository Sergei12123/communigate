package com.example.communigate.manager;

import com.example.communigate.dto.UserDTO;
import com.example.communigate.ximss.request.Login;
import com.example.communigate.ximss.request.Signup;
import com.example.communigate.ximss.response.Response;
import com.example.communigate.ximss.response.Session;
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


    public static final String KEEP_ALIVE = "keep-alive";

    private final RestTemplate restTemplate;


    private final XimssService ximssService;

    private static final String DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS = "http://localhost:8100/ximsslogin/";

    public Session makeBasicLogin(final Login loginEntity) {
        HttpHeaders headers = new HttpHeaders();
        String auth = loginEntity.getUserName() + ":" + loginEntity.getPassword();
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.setAccept(List.of(MediaType.ALL));
        headers.setConnection(KEEP_ALIVE);
        headers.setCacheControl(CacheControl.noCache());

        HttpEntity<String> entity = new HttpEntity<>(headers);

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
        headers.setConnection(KEEP_ALIVE);
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(res, headers);
        final String response = restTemplate.postForObject(
                ximssService.getNecessaryUrl(signup),
                stringHttpEntity,
                String.class
        );
        List<Response> listFromXML = ximssService.getListFromXML(response, Response.class);
        return listFromXML.isEmpty() ? null : listFromXML.get(0);
    }

}
