package com.example.communigate.ximss.response;

import com.example.communigate.ximss.BaseXIMSSResponse;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.FolderReadMode;
import com.example.communigate.ximss.response_request.Email;
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
@JacksonXmlRootElement(localName = "folderMessage")
public class FolderMessage extends BaseXIMSSResponse {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String folder = "INBOX";

    @JacksonXmlProperty(isAttribute = true)
    private FolderReadMode mode;

    @JacksonXmlProperty(isAttribute = true, localName = "UID")
    private Long uid;

    @JacksonXmlProperty(localName = "EMail")
    private Email email;

}
