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
@JacksonXmlRootElement(localName = "folderSearch")
public class FolderSearch extends BaseXIMSS {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String folder="INBOX";

    @JacksonXmlProperty(isAttribute = true)
    private Long limit;

    @JacksonXmlProperty(isAttribute = true)
    private Long timeout;

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String filter="ALL";


}
