package com.example.diplom.ximss.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Connect {
    @JacksonXmlProperty(isAttribute = true)
    private String protocol;

}
