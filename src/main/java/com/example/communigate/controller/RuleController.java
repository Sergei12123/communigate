package com.example.communigate.controller;

import com.example.communigate.dto.RuleDTO;
import com.example.communigate.service.RuleService;
import com.example.communigate.ximss.response_request.Action;
import com.example.communigate.ximss.response_request.Condition;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/rule")
public class RuleController {

    private final RuleService ruleService;

    private static final String RULE = "rule";

    private static final String NEW_RULE = "new-rule";

    private static final String RULES = "rules";

    private static final String REDIRECT_RULE_ALL = "redirect:/rule/all";

    @GetMapping("/all")
    public String rulesPage(Model model) {
        model.addAttribute(RULES, ruleService.getAllRulesForCurrentUser());
        return RULES;
    }

    @GetMapping("/new")
    public String newRule(Model model) {
        if (model.getAttribute(RULE) == null) {
            final RuleDTO dto = RuleDTO.builder().build();
            dto.getConditionList().add(Condition.builder().build());
            dto.setRemoveConditionAllow(true);
            dto.setRemoveActionAllow(true);
            dto.getActionList().add(Action.builder().build());
            model.addAttribute(RULE, dto);
        }
        return NEW_RULE;
    }

    @PostMapping(value = "/addCondition")
    public String addCondition(@ModelAttribute(RULE) RuleDTO dto) {
        dto.setRemoveConditionAllow(true);
        dto.getConditionList().add(Condition.builder().build());
        return NEW_RULE;
    }

    @PostMapping(value = "/removeCondition")
    public String removeCondition(@ModelAttribute(RULE) RuleDTO dto) {
        dto.getConditionList().remove(dto.getConditionList().size() - 1);
        if (dto.getConditionList().isEmpty()) {
            dto.setRemoveConditionAllow(false);
        }
        return NEW_RULE;
    }

    @PostMapping(value = "/addAction")
    public String addAction(@ModelAttribute(RULE) RuleDTO dto) {
        dto.setRemoveActionAllow(true);
        dto.getActionList().add(Action.builder().build());
        return NEW_RULE;
    }

    @PostMapping(value = "/removeAction")
    public String removeAction(@ModelAttribute(RULE) RuleDTO dto) {
        dto.getActionList().remove(dto.getActionList().size() - 1);
        if (dto.getActionList().isEmpty()) {
            dto.setRemoveActionAllow(false);
        }
        return NEW_RULE;
    }

    @PostMapping(value = "/create")
    public String createRule(@ModelAttribute(RULE) RuleDTO dto) {
        if (dto.isEdit() && !dto.getOldName().equals(dto.getName()))
            ruleService.renameRule(dto);
        ruleService.createRule(dto);
        return REDIRECT_RULE_ALL;
    }

    @PostMapping(value = "/edit/{name}")
    public String editRule(@PathVariable("name") String ruleName, Model model) {
        final RuleDTO dto = ruleService.getRuleWitnName(ruleName);
        dto.setEdit(true);
        dto.setRemoveConditionAllow(!dto.getConditionList().isEmpty());
        dto.setRemoveActionAllow(!dto.getActionList().isEmpty());
        model.addAttribute(RULE, dto);
        return NEW_RULE;
    }

    @PostMapping("/delete")
    public String deleteRules(@RequestParam(value = "selectedRules", required = false) List<String> selectedRules) {
        if (selectedRules == null)
            return REDIRECT_RULE_ALL;
        ruleService.deleteRules(selectedRules);
        return REDIRECT_RULE_ALL + (selectedRules.size() == 1 ? "?ruleDeleted" : "?rulesDeleted");
    }


}
