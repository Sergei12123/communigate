package com.example.communigate.ximss.request;


import com.example.communigate.ximss.BaseXIMSSRequest;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.RuleType;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "ruleList")
public class RuleList extends BaseXIMSSRequest {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private RuleType type = RuleType.MAIL_IN;

}
