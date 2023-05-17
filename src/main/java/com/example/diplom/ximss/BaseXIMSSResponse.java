package com.example.diplom.ximss;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseXIMSSResponse {

    @JacksonXmlProperty(isAttribute = true)
    private Long id;

}