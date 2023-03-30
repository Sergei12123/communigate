package com.example.diplom.ximss.request;


import com.example.diplom.annotation.PreLoginRequest;
import com.example.diplom.ximss.BaseXIMSS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@PreLoginRequest
@JacksonXmlRootElement(localName = "listFeatures")
public class ListFeatures extends BaseXIMSS {

    @JacksonXmlProperty(isAttribute = true)
    private String domain;

}
