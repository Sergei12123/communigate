package com.example.communigate.ximss.response;

import com.example.communigate.ximss.BaseXIMSSResponse;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.SaslType;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "features")
public class Features extends BaseXIMSSResponse {
    @JacksonXmlProperty(isAttribute = true)
    private String domain;

    @JacksonXmlProperty
    private String starttls;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SaslType> sasl;

    @JacksonXmlProperty
    private String nonce;

    @JacksonXmlProperty
    private String language;

    @JacksonXmlProperty
    private String signup;

    @JacksonXmlProperty
    private Connect connect;
}

