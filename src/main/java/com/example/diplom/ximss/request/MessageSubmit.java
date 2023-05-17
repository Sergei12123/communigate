package com.example.diplom.ximss.request;


import com.example.diplom.ximss.BaseXIMSSRequest;
import com.example.diplom.ximss.response_request.Email;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "messageSubmit")
public class MessageSubmit extends BaseXIMSSRequest {

    @JacksonXmlProperty(isAttribute = true, localName = "EMail")
    private Email email;

}
