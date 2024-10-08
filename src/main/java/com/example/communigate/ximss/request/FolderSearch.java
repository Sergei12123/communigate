package com.example.communigate.ximss.request;


import com.example.communigate.ximss.BaseXIMSSRequest;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "folderSearch")
public class FolderSearch extends BaseXIMSSRequest {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String folder = "INBOX";

    @JacksonXmlProperty(isAttribute = true)
    private Long limit;

    @JacksonXmlProperty(isAttribute = true)
    private Long timeout;

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String filter = "ALL";


}
