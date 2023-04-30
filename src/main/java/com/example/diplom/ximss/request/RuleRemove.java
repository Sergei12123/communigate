package com.example.diplom.ximss.request;


import com.example.diplom.ximss.BaseXIMSS;
import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.RuleType;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "ruleRemove")
public class RuleRemove extends BaseXIMSS {

    @JacksonXmlProperty(isAttribute = true)
    private RuleType type;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

}
