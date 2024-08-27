package com.example.communigate.ximss.response;

import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.MimeMessageSubtype;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.MimeMessageType;
import com.example.communigate.ximss.response_request.Email;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "MIME")
public class Mime {

    @JacksonXmlProperty(isAttribute = true)
    private String charset;

    @JacksonXmlProperty(isAttribute = true)
    private int estimatedSize;

    @JacksonXmlProperty(isAttribute = true)
    private MimeMessageSubtype subtype;

    @JacksonXmlProperty(isAttribute = true)
    private MimeMessageType type;

    @JacksonXmlProperty(isAttribute = true, localName = "partID")
    private String partId;

    @JacksonXmlProperty(isAttribute = true, localName = "Type-format")
    private String typeFormat;

    @JacksonXmlProperty(isAttribute = true)
    private String format;

    @JacksonXmlText
    private String messageText;

    @JacksonXmlProperty(localName = "EMail")
    private Email email;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MIME")
    private List<Mime> mimes;

    public void addText(final String text) {
        this.messageText += "\n" + text;
    }
}
