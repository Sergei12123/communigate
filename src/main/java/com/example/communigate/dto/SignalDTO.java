package com.example.communigate.dto;

import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.SignalStage;
import com.example.communigate.ximss.response_request.Rule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SignalDTO extends RuleDTO {

    private SignalStage stage;

    public SignalDTO(final Rule rule) {
        super(rule);
        this.stage = rule.getStage();
        super.selected = false;
    }
}
