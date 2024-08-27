package com.example.communigate.ximss.response;

import com.example.communigate.ximss.BaseXIMSSResponse;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "session")
public class Session extends BaseXIMSSResponse {
    @JacksonXmlProperty(isAttribute = true)
    private String urlID;

    @JacksonXmlProperty(isAttribute = true)
    private String userName;

    @JacksonXmlProperty(isAttribute = true)
    private String realName;

    @JacksonXmlProperty(isAttribute = true)
    private String version;

    @JacksonXmlProperty(isAttribute = true)
    private String random;
}
