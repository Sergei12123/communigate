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
@JacksonXmlRootElement(localName = "folderBrowse")
public class FolderBrowse extends BaseXIMSS {
    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String folder = "INBOX";

    @Builder.Default
    @JacksonXmlProperty(localName = "UID")
    private Uid uid = Uid.builder().build();
}

