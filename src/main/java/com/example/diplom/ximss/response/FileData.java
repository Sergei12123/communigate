package com.example.diplom.ximss.response;

import com.example.diplom.ximss.BaseXIMSS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "fileData")
public class FileData extends BaseXIMSS {

    @JacksonXmlProperty(isAttribute = true)
    private String fileName;

    @JacksonXmlProperty(isAttribute = true)
    private int position;

    @JacksonXmlProperty(isAttribute = true)
    private int slice;

    @JacksonXmlProperty(isAttribute = true)
    private int size;

    @JacksonXmlProperty(isAttribute = true)
    private String timeModified;

    @JacksonXmlText
    private String fileData;


}
