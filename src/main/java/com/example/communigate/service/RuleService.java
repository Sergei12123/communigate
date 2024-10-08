package com.example.communigate.service;

import com.example.communigate.dto.RuleDTO;
import com.example.communigate.manager.XimssService;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.RuleType;
import com.example.communigate.ximss.request.*;
import com.example.communigate.ximss.response_request.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final XimssService ximssService;

    public List<RuleDTO> getAllRulesForCurrentUser() {
        final List<Rule> ruleList = ximssService.sendRequestToGetList(RuleList.builder().type(RuleType.MAIL_IN).build(), Rule.class);
        final List<Rule> correctRuleList = ruleList.stream()
                .map(Rule::getName)
                .map(name -> ximssService.sendRequestToGetObject(RuleRead.builder().type(RuleType.MAIL_IN).name(name).build(), Rule.class))
                .toList();
        return correctRuleList.stream()
                .map(RuleDTO::new).toList();

    }

    public RuleDTO getRuleWitnName(final String ruleName) {
        final Rule rule = ximssService.sendRequestToGetObject(RuleRead.builder().type(RuleType.MAIL_IN).name(ruleName).build(), Rule.class);
        return rule != null ? new RuleDTO(rule) : null;
    }

    public void createRule(final RuleDTO ruleDTO) {
        ximssService.sendRequestToGetNothing(new RuleSet(ruleDTO));
    }

    public void renameRule(final RuleDTO ruleDTO) {
        ximssService.sendRequestToGetNothing(RuleRename.builder().type(RuleType.MAIL_IN).name(ruleDTO.getOldName()).newName(ruleDTO.getName()).build());
    }

    public void deleteRules(List<String> selectedRules) {
        selectedRules.forEach(ruleName -> ximssService.sendRequestToGetNothing(RuleRemove.builder().type(RuleType.MAIL_IN).name(ruleName).build()));
    }
}
