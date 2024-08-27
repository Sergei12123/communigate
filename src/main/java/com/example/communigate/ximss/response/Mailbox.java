package com.example.communigate.ximss.response;

import com.example.communigate.ximss.BaseXIMSSResponse;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "mailbox")
public class Mailbox extends BaseXIMSSResponse {

    @JacksonXmlProperty(isAttribute = true, localName = "mailbox")
    private String mailboxName;

    @JacksonXmlProperty(isAttribute = true, localName = "BoxSeq")
    private String boxSeq;

    @JacksonXmlProperty(isAttribute = true, localName = "Messages")
    private String messages;

    @JacksonXmlProperty(isAttribute = true, localName = "Size")
    private String size;

    @JacksonXmlProperty(isAttribute = true, localName = "UIDNext")
    private String uidNext;

    @JacksonXmlProperty(isAttribute = true, localName = "UIDValidity")
    private String uidValidity;

    @JacksonXmlProperty(isAttribute = true, localName = "Unseen")
    private String unseen;
}
