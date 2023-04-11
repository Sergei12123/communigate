package com.example.diplom.ximss.request;


import com.example.diplom.annotation.PreLoginRequest;
import com.example.diplom.ximss.BaseXIMSS;
import com.example.diplom.ximss.ximss_dictionary.MessageFields;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.example.diplom.ximss.ximss_dictionary.MessageFields.*;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "folderRead")
public class FolderRead extends BaseXIMSS {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String folder="INBOX";

    @JacksonXmlProperty(isAttribute = true, localName = "UID")
    private Long uid;

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private int totalSizeLimit = 10000000;

}
