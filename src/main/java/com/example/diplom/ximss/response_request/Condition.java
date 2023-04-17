package com.example.diplom.ximss.response_request;

import com.example.diplom.ximss.ximss_dictionary.ConditionOpCode;
import com.example.diplom.ximss.ximss_dictionary.ConditionOperator;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
    @JacksonXmlProperty(isAttribute = true)
    private ConditionOpCode opCode;
    @JacksonXmlProperty(isAttribute = true)
    private ConditionOperator operator;

    @JacksonXmlText
    private String value;

}
