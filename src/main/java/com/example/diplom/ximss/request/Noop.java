package com.example.diplom.ximss.request;

import com.example.diplom.annotation.PreLoginRequest;
import com.example.diplom.ximss.BaseXIMSS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;

@Builder
@JacksonXmlRootElement(localName = "noop")
@PreLoginRequest
public class Noop extends BaseXIMSS {
}
