package com.example.diplom.dto;

import com.example.diplom.ximss.response_request.Action;
import com.example.diplom.ximss.response_request.Condition;
import com.example.diplom.ximss.response_request.Rule;
import com.example.diplom.ximss.ximss_dictionary.RulePriority;
import com.example.diplom.ximss.ximss_dictionary.RuleType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDTO {

    private RuleType type;

    private String name;

    @Builder.Default
    private RulePriority priority = RulePriority.FIVE;

    @Builder.Default
    private List<Condition> conditionList = new ArrayList<>(List.of(Condition.builder().build()));

    @Builder.Default
    private List<Action> actionList = new ArrayList<>(List.of(Action.builder().build()));

    private String comment;

    private boolean selected;

    private boolean removeConditionAllow;

    private boolean removeActionAllow;

    private boolean edit;

    public RuleDTO(final Rule rule) {
        this.type = rule.getType();
        this.name = rule.getName();
        this.priority = rule.getPriority();
        this.conditionList = rule.getConditionList();
        this.actionList = rule.getActionList();
        this.comment = rule.getComment();
        this.selected = false;
    }
}
