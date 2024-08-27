package com.example.communigate.ximss.response_request;

import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.ActionOpCode;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    @JacksonXmlProperty(isAttribute = true)
    private ActionOpCode opCode;

    @JacksonXmlText
    private String value;

}
