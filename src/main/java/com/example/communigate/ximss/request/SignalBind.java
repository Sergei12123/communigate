package com.example.communigate.ximss.request;

import com.example.communigate.ximss.BaseXIMSSRequest;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "signalBind")
public class SignalBind extends BaseXIMSSRequest {

}
