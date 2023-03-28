package com.example.diplom.ximss.request;

import com.example.diplom.annotation.PreLoginRequest;
import com.example.diplom.ximss.BaseXIMSS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;

@Builder
@JacksonXmlRootElement(localName = "signup")
@PreLoginRequest
public class Signup extends BaseXIMSS {
    @JacksonXmlProperty(isAttribute = true)
    private String userName;

    @JacksonXmlProperty(isAttribute = true)
    private String password;
}
