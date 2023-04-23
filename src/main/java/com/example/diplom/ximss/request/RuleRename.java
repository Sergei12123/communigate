package com.example.diplom.ximss.request;


import com.example.diplom.ximss.BaseXIMSS;
import com.example.diplom.ximss.ximss_dictionary.RuleType;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "ruleRename")
public class RuleRename extends BaseXIMSS {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private RuleType type = RuleType.MAIL_IN;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private String newName;

}
