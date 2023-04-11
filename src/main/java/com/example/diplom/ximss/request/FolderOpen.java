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
@JacksonXmlRootElement(localName = "folderOpen")
public class FolderOpen extends BaseXIMSS {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String folder="INBOX";

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String maibox="INBOX";

    @Builder.Default
    @JacksonXmlProperty(localName = "field")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<MessageFields> fields=List.of(FLAGS, FROM);

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private MessageFields sortField = INTERNALDATE;

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String sortOrder="asc";

}
