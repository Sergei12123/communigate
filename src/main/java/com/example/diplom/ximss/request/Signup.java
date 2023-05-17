package com.example.diplom.ximss.request;

import com.example.diplom.annotation.PreLoginRequest;
import com.example.diplom.ximss.BaseXIMSSRequest;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@PreLoginRequest
@JacksonXmlRootElement(localName = "signup")
public class Signup extends BaseXIMSSRequest {

    @JacksonXmlProperty(isAttribute = true)
    private String domain;

    @NonNull
    @JacksonXmlProperty(isAttribute = true)
    private String userName;

    @NonNull
    @JacksonXmlProperty(isAttribute = true)
    private String password;

    @JacksonXmlProperty(isAttribute = true)
    private String realName;

    @JacksonXmlProperty(isAttribute = true)
    private String recoverPassword;
}
