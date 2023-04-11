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

import static com.example.diplom.ximss.ximss_dictionary.MessageFields.UID;

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
