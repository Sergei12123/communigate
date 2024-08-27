package com.example.communigate.ximss;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseXIMSSRequest {
    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private Long id = System.currentTimeMillis();

}