package com.example.diplom.ximss.parts_of_ximss;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class XimssAddress {

    @JacksonXmlProperty(isAttribute = true)
    private String realName;

    @JacksonXmlText
    private String text;

    public XimssAddress(final String text) {
        this.text = text;
    }

}
