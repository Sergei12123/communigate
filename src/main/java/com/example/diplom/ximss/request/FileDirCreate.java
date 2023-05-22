package com.example.diplom.ximss.request;


import com.example.diplom.ximss.BaseXIMSSRequest;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "fileDirCreate")
public class FileDirCreate extends BaseXIMSSRequest {

    @JacksonXmlProperty(isAttribute = true)
    private String fileName;

}
