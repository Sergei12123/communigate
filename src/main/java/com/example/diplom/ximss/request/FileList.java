package com.example.diplom.ximss.request;

import com.example.diplom.ximss.BaseXIMSS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "fileList")
public class FileList extends BaseXIMSS {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String directory = "private/IM";

    @JacksonXmlProperty(isAttribute = true)
    private String fileName;

    @JacksonXmlProperty(isAttribute = true)
    private int position;

    @JacksonXmlProperty(isAttribute = true)
    private int limit;

}
