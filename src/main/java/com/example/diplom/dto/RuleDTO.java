package com.example.diplom.dto;

import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.RulePriority;
import com.example.diplom.ximss.parts_of_ximss.ximss_dictionary.RuleType;
import com.example.diplom.ximss.response_request.Action;
import com.example.diplom.ximss.response_request.Condition;
import com.example.diplom.ximss.response_request.Rule;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDTO {

    protected RuleType type;

    protected String oldName;

    protected String name;

    @Builder.Default
    protected RulePriority priority = RulePriority.FIVE;

    @Builder.Default
    protected List<Condition> conditionList = new ArrayList<>();

    @Builder.Default
    protected List<Action> actionList = new ArrayList<>();

    protected String comment;

    protected boolean selected;

    private boolean removeConditionAllow;

    private boolean removeActionAllow;

    private boolean edit;

    public RuleDTO(final Rule rule) {
        this.type = rule.getType();
        this.oldName = rule.getName();
        this.name = rule.getName();
        this.priority = rule.getPriority();
        this.conditionList = rule.getConditionList();
        this.actionList = rule.getActionList();
        this.comment = rule.getComment();
        this.selected = false;
    }
}
