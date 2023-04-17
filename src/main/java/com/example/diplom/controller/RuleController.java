package com.example.diplom.controller;

import com.example.diplom.dto.RuleDTO;
import com.example.diplom.service.RuleService;
import com.example.diplom.ximss.response_request.Action;
import com.example.diplom.ximss.response_request.Condition;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class RuleController {

    private final RuleService ruleService;

    @GetMapping("/rules")
    private String rulesPage(Model model) {
        model.addAttribute("rules", ruleService.getAllRulesForCurrentUser());
        return "rules";
    }

    @GetMapping("/new-rule")
    private String newMessagePage(Model model) {
        if (model.getAttribute("rule") == null)
            model.addAttribute("rule", RuleDTO.builder().build());
        return "new-rule";
    }

    @PostMapping(value = "/addCondition")
    public String addCondition(@ModelAttribute("rule") RuleDTO dto) {
        dto.setRemoveConditionAllow(true);
        dto.getConditionList().add(Condition.builder().build());
        return "new-rule";
    }

    @PostMapping(value = "/removeCondition")
    public String removeCondition(@ModelAttribute("rule") RuleDTO dto) {
        dto.getConditionList().remove(dto.getConditionList().size() - 1);
        if (dto.getConditionList().size() == 1) {
            dto.setRemoveConditionAllow(false);
        }
        return "new-rule";
    }

    @PostMapping(value = "/addAction")
    public String addAction(@ModelAttribute("rule") RuleDTO dto) {
        dto.setRemoveActionAllow(true);
        dto.getActionList().add(Action.builder().build());
        return "new-rule";
    }

    @PostMapping(value = "/removeAction")
    public String removeAction(@ModelAttribute("rule") RuleDTO dto) {
        dto.getActionList().remove(dto.getActionList().size() - 1);
        if (dto.getActionList().size() == 1) {
            dto.setRemoveActionAllow(false);
        }
        return "new-rule";
    }

    @PostMapping(value = "/createRule")
    public String createRule(@ModelAttribute("rule") RuleDTO dto) {
        ruleService.createRule(dto);
        return "redirect:/rules";
    }

    @PostMapping(value = "/editRule")
    public String editRule(@RequestParam("ruleName") String ruleName, RedirectAttributes redirectAttributes) {
        final RuleDTO dto = ruleService.getRuleWitnName(ruleName);
        dto.setEdit(true);
        redirectAttributes.addFlashAttribute("rule", dto);
        return "redirect:/new-rule";
    }


}
