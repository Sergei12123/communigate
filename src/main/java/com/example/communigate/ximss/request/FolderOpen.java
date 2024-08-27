package com.example.communigate.ximss.request;


import com.example.communigate.ximss.BaseXIMSSRequest;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.MessageField;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.MessageField.*;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "folderOpen")
public class FolderOpen extends BaseXIMSSRequest {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String folder = "INBOX";

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String mailbox = "INBOX";

    @Builder.Default
    @JacksonXmlProperty(localName = "field")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<MessageField> fields = List.of(FLAGS, FROM);

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private MessageField sortField = INTERNALDATE;

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String sortOrder = "asc";

}
