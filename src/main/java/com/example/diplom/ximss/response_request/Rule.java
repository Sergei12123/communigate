package com.example.diplom.ximss.response_request;

import com.example.diplom.ximss.BaseXIMSSRequest;
import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.RulePriority;
import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.RuleType;
import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.SignalStage;
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
public class Rule extends BaseXIMSSRequest {

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private RuleType type;

    @JacksonXmlProperty(isAttribute = true)
    private RulePriority priority;

    @JacksonXmlProperty(isAttribute = true)
    private SignalStage stage;

    @JacksonXmlProperty(localName = "condition")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Condition> conditionList;

    @JacksonXmlProperty(localName = "action")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Action> actionList;


    private String comment;

}

