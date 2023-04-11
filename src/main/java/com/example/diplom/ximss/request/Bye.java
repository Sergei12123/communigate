package com.example.diplom.ximss.request;

import com.example.diplom.ximss.BaseXIMSS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;

@Builder
@JacksonXmlRootElement(localName = "bye")
public class Bye extends BaseXIMSS {
}
