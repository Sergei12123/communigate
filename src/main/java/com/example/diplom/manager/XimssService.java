package com.example.diplom.manager;

import com.example.diplom.annotation.PreLoginRequest;
import com.example.diplom.service.RedisRepository;
import com.example.diplom.ximss.BaseXIMSS;
import com.example.diplom.ximss.request.Login;
import com.example.diplom.ximss.response.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class XimssService {

    private final XmlMapper xmlMapper;

    private final RestTemplate restTemplate;

    private final RedisRepository redisRepository;

    private final SessionService sessionService;

    static private final String DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS = "http://localhost:8100/ximsslogin/";

    static private final String DEFAULT_SESSION_SYNC_REQUEST_URL = "http://localhost:8100/Session/%s/sync";

    public static final String DUMB_LOGIN_URL = "http://localhost:8100/ximsslogin/?userName={userName}&password={password}";

    public <T extends BaseXIMSS, P> P sendRequestToGetObject(final T requestXimssEntity, final Class<P> responseClass) {
        final List<P> list = sendRequestToGetList(requestXimssEntity, responseClass);
        return list.isEmpty() ? null : list.get(0);
    }

    public <T extends BaseXIMSS> void sendRequestToGetNothing(final T requestXimssEntity) {
        restTemplate.postForObject(
                getNecessaryUrl(requestXimssEntity),
                getRequestWithBody(requestXimssEntity),
                String.class
        );
    }

    public <T extends BaseXIMSS, P> List<P> sendRequestToGetList(final T requestXimssEntity, final Class<P> responseClass) {
        final String response = restTemplate.postForObject(
                getNecessaryUrl(requestXimssEntity),
                getRequestWithBody(requestXimssEntity),
                String.class
        );

        return response == null ? null : getListFromXML(response, responseClass);

    }


    public Session makeDumbLogin(final Login loginEntity) {
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
            return getObjectFromXML(response.getBody(), Session.class);
        } else {
            return Session.builder().build();
        }
    }

    public <T> List<T> getListFromXML(final String xmlString, final Class<T> returnType) {
        try {
            final List<T> resultList = new ArrayList<>();
            String xmlStringTemp = String.valueOf(xmlString);
            final String localName = returnType.getAnnotation(JacksonXmlRootElement.class).localName();
            int xmlLastLength;
            do {
                xmlLastLength = xmlStringTemp.length();
                final JsonNode jsonNode = xmlMapper.readTree(xmlStringTemp).get(localName);
                if (jsonNode != null) {
                    try {
                        resultList.add(0, xmlMapper.treeToValue(jsonNode, returnType));
                    } catch (Exception e) {
                        try {
                            resultList.add(0, xmlMapper.readValue(unwrapXimss(xmlString), returnType));
                            return resultList;
                        } catch (Exception e1) {
                            System.out.println(e1);
                        }
                    }
                    xmlStringTemp = xmlStringTemp.replace(xmlMapper.writeValueAsString(resultList.get(0)), "");
                }
            } while (xmlLastLength > xmlStringTemp.length());
            return resultList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getObjectFromXML(final String xmlString, final Class<T> returnType) {
        List<T> listFromXML = getListFromXML(xmlString, returnType);
        return listFromXML.isEmpty() ? null : listFromXML.get(0);
    }


    private String wrapInXimssTag(final String xml) {
        return "<XIMSS>" + xml + "</XIMSS>";
    }

    private String unwrapXimss(final String xml) {
        return xml.replace("<XIMSS>", "").replace("</XIMSS>", "");
    }

    private String getXML(final Object object) {
        try {
            return wrapInXimssTag(xmlMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getXML(final List<Object> objects) {
        AtomicReference<String> xml = new AtomicReference<>("");
        objects.forEach(object -> {
            try {
                xml.set(xml.get() + xmlMapper.writeValueAsString(object));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return wrapInXimssTag(xml.get());
    }

    private <T extends BaseXIMSS> HttpEntity<String> getRequestWithBody(final T requestXimssEntity) {
        String res = getXML(requestXimssEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache());
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.ALL));
        headers.setConnection("keep-alive");
        return new HttpEntity<>(res, headers);
    }

    private <T extends BaseXIMSS> String getNecessaryUrl(final T requestXimssEntity) {
        final String url;
        if (requestXimssEntity.getClass().isAnnotationPresent(PreLoginRequest.class)) {
            url = DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS;
        } else if (sessionService.getCurrentUserName() != null) {
            url = String.format(DEFAULT_SESSION_SYNC_REQUEST_URL, redisRepository.getSessionIdForCurrentUser());
        } else {
            url = DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS;
        }
        return url;
    }
}
