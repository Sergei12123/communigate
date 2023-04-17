package com.example.diplom.ximss.response;

import com.example.diplom.ximss.BaseXIMSS;
import com.example.diplom.ximss.ximss_dictionary.SaslType;
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
public class Features extends BaseXIMSS {
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

