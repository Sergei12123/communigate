package com.example.diplom.ximss.request;


import com.example.diplom.annotation.PreLoginRequest;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "mailBoxList")
public class MailboxList {

    @JacksonXmlProperty(isAttribute = true)
    private String filter;

    @JacksonXmlProperty(isAttribute = true)
    private String mailboxClass;

    @JacksonXmlProperty(isAttribute = true)
    private String pureFolder;

}
