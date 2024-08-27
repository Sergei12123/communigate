package com.example.communigate.ximss.request;

import com.example.communigate.ximss.BaseXIMSSRequest;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;

@Builder
@JacksonXmlRootElement(localName = "bye")
public class Bye extends BaseXIMSSRequest {
}
