package com.example.diplom.ximss.response;

import com.example.diplom.ximss.BaseXIMSS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "folderReport")
public class FolderReport extends BaseXIMSS {

    @JacksonXmlProperty(isAttribute = true)
    private String folder;

    @JacksonXmlProperty(isAttribute = true)
    private String mode;

    @JacksonXmlProperty(isAttribute = true)
    private Long index;

    @JacksonXmlProperty(isAttribute = true, localName = "UID")
    private Long uid;

    @JacksonXmlProperty(isAttribute = true)
    private String messages;

    @JacksonXmlProperty(isAttribute = true, localName = "UIDNext")
    private String uidNext;

    @JacksonXmlProperty(isAttribute = true, localName = "UIDValidity")
    private String uidValidity;

    @JacksonXmlProperty(isAttribute = true)
    private String unseen;

    @JacksonXmlProperty(isAttribute = true)
    private String rights;

    @JacksonXmlProperty(localName = "FLAGS")
    private String flag;

    @JacksonXmlProperty(localName = "FROM")
    private String from;

}
