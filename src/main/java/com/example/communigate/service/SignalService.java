package com.example.communigate.service;

import com.example.communigate.dto.SignalDTO;
import com.example.communigate.manager.XimssService;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.RuleType;
import com.example.communigate.ximss.request.*;
import com.example.communigate.ximss.response_request.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignalService {

    private final XimssService ximssService;

    public List<SignalDTO> getAllRulesForCurrentUser() {
        final List<Rule> ruleList = ximssService.sendRequestToGetList(RuleList.builder().type(RuleType.SIGNAL_IN).build(), Rule.class);
        final List<Rule> correctRuleList = ruleList.stream()
                .map(Rule::getName)
                .map(name -> ximssService.sendRequestToGetObject(RuleRead.builder().type(RuleType.SIGNAL_IN).name(name).build(), Rule.class))
                .toList();
        return correctRuleList.stream()
                .map(SignalDTO::new).toList();

    }

    public SignalDTO getRuleWitnName(final String ruleName) {
        final Rule rule = ximssService.sendRequestToGetObject(RuleRead.builder().type(RuleType.SIGNAL_IN).name(ruleName).build(), Rule.class);
        return rule != null ? new SignalDTO(rule) : null;
    }

    public void createRule(final SignalDTO signalDTO) {
        ximssService.sendRequestToGetNothing(new RuleSet(signalDTO));
    }

    public void renameRule(final SignalDTO signalDTO) {
        ximssService.sendRequestToGetNothing(RuleRename.builder().type(RuleType.SIGNAL_IN).name(signalDTO.getOldName()).newName(signalDTO.getName()).build());
    }

    public void deleteRules(List<String> selectedRules) {
        selectedRules.forEach(ruleName -> ximssService.sendRequestToGetNothing(RuleRemove.builder().type(RuleType.SIGNAL_IN).name(ruleName).build()));
    }
}
