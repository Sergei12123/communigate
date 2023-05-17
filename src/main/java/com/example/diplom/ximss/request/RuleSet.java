package com.example.diplom.ximss.request;

import com.example.diplom.dto.RuleDTO;
import com.example.diplom.dto.SignalDTO;
import com.example.diplom.ximss.BaseXIMSSRequest;
import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.RulePriority;
import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.RuleType;
import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.SignalStage;
import com.example.diplom.ximss.response_request.Action;
import com.example.diplom.ximss.response_request.Condition;
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
@JacksonXmlRootElement(localName = "ruleSet")
public class RuleSet extends BaseXIMSSRequest {

    @JacksonXmlProperty(isAttribute = true)
    private RuleType type;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

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

    public RuleSet(final RuleDTO dto) {
        this.type = RuleType.MAIL_IN;
        this.name = dto.getName();
        this.priority = dto.getPriority();
        this.conditionList = dto.getConditionList();
        this.actionList = dto.getActionList();
        this.comment = dto.getComment();
    }

    public RuleSet(final SignalDTO dto) {
        this.type = RuleType.SIGNAL_IN;
        this.name = dto.getName();
        this.priority = dto.getPriority();
        this.conditionList = dto.getConditionList();
        this.actionList = dto.getActionList();
        this.comment = dto.getComment();
        this.stage = dto.getStage();
    }

}

