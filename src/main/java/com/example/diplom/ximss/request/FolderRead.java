package com.example.diplom.ximss.request;


import com.example.diplom.ximss.BaseXIMSSRequest;
import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.FolderReadMode;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "folderRead")
public class FolderRead extends BaseXIMSSRequest {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String folder = "INBOX";

    @JacksonXmlProperty(isAttribute = true, localName = "UID")
    private Long uid;

    @JacksonXmlProperty(isAttribute = true)
    private FolderReadMode mode;

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private int totalSizeLimit = 10000000;

}
