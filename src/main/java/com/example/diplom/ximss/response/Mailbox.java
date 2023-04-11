package com.example.diplom.ximss.response;

import com.example.diplom.ximss.BaseXIMSS;
import com.example.diplom.ximss.ximss_dictionary.SaslType;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "mailbox")
public class Mailbox extends BaseXIMSS {

    @JacksonXmlProperty(isAttribute = true)
    private String mailbox;

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
