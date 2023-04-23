package com.example.diplom.service;

import com.example.diplom.dto.RuleDTO;
import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.*;
import com.example.diplom.ximss.response_request.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final XimssService ximssService;

    public List<RuleDTO> getAllRulesForCurrentUser() {
        final List<Rule> ruleList = ximssService.sendRequestToGetList(RuleList.builder().build(), Rule.class);
        final List<Rule> correctRuleList = ruleList.stream()
                .map(Rule::getName)
                .map(name -> ximssService.sendRequestToGetObject(RuleRead.builder().name(name).build(), Rule.class))
                .toList();
        return correctRuleList.stream()
                .map(RuleDTO::new).toList();

    }

    public RuleDTO getRuleWitnName(final String ruleName) {
        final Rule rule = ximssService.sendRequestToGetObject(RuleRead.builder().name(ruleName).build(), Rule.class);
        return rule != null ? new RuleDTO(rule) : null;
    }

    public void createRule(final RuleDTO ruleDTO) {
        ximssService.sendRequestToGetNothing(new RuleSet(ruleDTO));
    }

    public void renameRule(final RuleDTO ruleDTO) {
        ximssService.sendRequestToGetNothing(RuleRename.builder().name(ruleDTO.getOldName()).newName(ruleDTO.getName()).build());
    }

    public void deleteRules(List<String> selectedRules) {
        selectedRules.forEach(ruleName -> ximssService.sendRequestToGetNothing(RuleRemove.builder().name(ruleName).build()));
    }
}
