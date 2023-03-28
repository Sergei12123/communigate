package com.example.diplom.manager;

import com.example.diplom.annotation.PreLoginRequest;
import com.example.diplom.ximss.BaseXIMSS;
import com.example.diplom.ximss.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@AllArgsConstructor
public class XimssService {

    private final XmlMapper xmlMapper;

    static private final String DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS = "http://localhost:8100/ximsslogin/";

    static private final String DEFAULT_URL_FOR_LOGIN_OPERATIONS = "http://localhost:8100/ximsslogin/";

    public <T extends BaseXIMSS> Response sendRequest(final T requestXimssEntity) {
        try {
            String res = wrapInXimssTag(xmlMapper.writeValueAsString(requestXimssEntity));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> request = new HttpEntity<>(res, headers);

            final ResponseEntity<String> response = new RestTemplate().postForEntity(
                    requestXimssEntity.getClass().isAnnotationPresent(PreLoginRequest.class) ?
                            DEFAULT_URL_FOR_PRE_LOGIN_OPERATIONS
                            : DEFAULT_URL_FOR_LOGIN_OPERATIONS,
                    request,
                    String.class
            );

            return xmlMapper.readValue(unwrapXimss(Objects.requireNonNull(response.getBody())), Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private String wrapInXimssTag(final String xml) {
        return "<XIMSS>" + xml + "</XIMSS>";
    }

    private String unwrapXimss(final String xml) {
        return xml.replace("<XIMSS>","").replace("</XIMSS>","");
    }
}
