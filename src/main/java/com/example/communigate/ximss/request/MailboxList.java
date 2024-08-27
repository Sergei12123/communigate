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
@JacksonXmlRootElement(localName = "mailBoxList")
public class MailboxList extends BaseXIMSSRequest {

    @JacksonXmlProperty(isAttribute = true)
    private String filter;

    @JacksonXmlProperty(isAttribute = true)
    private String mailboxClass;

    @JacksonXmlProperty(isAttribute = true)
    private String pureFolder;

}
