package com.example.communigate.controller;

import com.example.communigate.dto.SignalDTO;
import com.example.communigate.service.SignalService;
import com.example.communigate.ximss.response_request.Action;
import com.example.communigate.ximss.response_request.Condition;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/signal")
public class SignalController {

    private final SignalService signalService;

    private static final String SIGNAL = "signal";

    private static final String NEW_SIGNAL = "new-signal";

    private static final String SIGNALS = "signals";

    private static final String REDIRECT_SIGNAL_ALL = "redirect:/signal/all";


    @GetMapping("/all")
    public String signalsPage(Model model) {
        model.addAttribute(SIGNALS, signalService.getAllRulesForCurrentUser());
        return SIGNALS;
    }

    @GetMapping("/new")
    public String newSignal(Model model) {
        if (model.getAttribute(SIGNAL) == null) {
            final SignalDTO dto = SignalDTO.builder().build();
            dto.getConditionList().add(Condition.builder().build());
            dto.setRemoveConditionAllow(true);
            dto.setRemoveActionAllow(true);
            dto.getActionList().add(Action.builder().build());
            model.addAttribute(SIGNAL, dto);
        }
        return NEW_SIGNAL;
    }

    @PostMapping(value = "/addCondition")
    public String addCondition(@ModelAttribute(SIGNAL) SignalDTO dto) {
        dto.setRemoveConditionAllow(true);
        dto.getConditionList().add(Condition.builder().build());
        return NEW_SIGNAL;
    }

    @PostMapping(value = "/removeCondition")
    public String removeCondition(@ModelAttribute(SIGNAL) SignalDTO dto) {
        dto.getConditionList().remove(dto.getConditionList().size() - 1);
        if (dto.getConditionList().isEmpty()) {
            dto.setRemoveConditionAllow(false);
        }
        return NEW_SIGNAL;
    }

    @PostMapping(value = "/addAction")
    public String addAction(@ModelAttribute(SIGNAL) SignalDTO dto) {
        dto.setRemoveActionAllow(true);
        dto.getActionList().add(Action.builder().build());
        return NEW_SIGNAL;
    }

    @PostMapping(value = "/removeAction")
    public String removeAction(@ModelAttribute(SIGNAL) SignalDTO dto) {
        dto.getActionList().remove(dto.getActionList().size() - 1);
        if (dto.getActionList().isEmpty()) {
            dto.setRemoveActionAllow(false);
        }
        return NEW_SIGNAL;
    }

    @PostMapping(value = "/create")
    public String createSignal(@ModelAttribute(SIGNAL) SignalDTO dto) {
        if (dto.isEdit() && !dto.getOldName().equals(dto.getName()))
            signalService.renameRule(dto);
        signalService.createRule(dto);
        return REDIRECT_SIGNAL_ALL;
    }

    @PostMapping(value = "/edit/{name}")
    public String editSignal(@PathVariable("name") String signalName, Model model) {
        final SignalDTO dto = signalService.getRuleWitnName(signalName);
        dto.setEdit(true);
        dto.setRemoveConditionAllow(!dto.getConditionList().isEmpty());
        dto.setRemoveActionAllow(!dto.getActionList().isEmpty());

        model.addAttribute(SIGNAL, dto);
        return NEW_SIGNAL;
    }

    @PostMapping("/delete")
    public String deleteSignals(@RequestParam(value = "selectedSignals", required = false) List<String> selectedSignals) {
        if (selectedSignals == null)
            return REDIRECT_SIGNAL_ALL;
        signalService.deleteRules(selectedSignals);
        return REDIRECT_SIGNAL_ALL + (selectedSignals.size() == 1 ? "?signalDeleted" : "?signalsDeleted");
    }


}
