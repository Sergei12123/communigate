package com.example.diplom.ximss.response;

import com.example.diplom.ximss.BaseXIMSSResponse;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "response")
public class Response extends BaseXIMSSResponse {
    @JacksonXmlProperty(isAttribute = true)
    private String errorText;

    @JacksonXmlProperty(isAttribute = true)
    private Long errorNum;

    public String toString() {
        return "Response(id=" + super.getId() + ", errorText=" + this.getErrorText() + ", errorNum=" + this.getErrorNum() + ")";
    }
}
