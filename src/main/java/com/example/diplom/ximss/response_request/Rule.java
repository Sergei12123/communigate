package com.example.diplom.ximss.response_request;

import com.example.diplom.ximss.BaseXIMSS;
import com.example.diplom.ximss.ximss_dictionary.RulePriority;
import com.example.diplom.ximss.ximss_dictionary.RuleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "rule")
public class Rule extends BaseXIMSS {

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private RuleType type = RuleType.MAIL_IN;

    @JacksonXmlProperty(isAttribute = true)
    private RulePriority priority;

    @JacksonXmlProperty(localName = "condition")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Condition> conditionList;

    @JacksonXmlProperty(localName = "action")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Action> actionList;
    private String comment;

}

