package com.example.communigate.ximss.request;

import com.example.communigate.annotation.PreLoginRequest;
import com.example.communigate.ximss.BaseXIMSSRequest;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@PreLoginRequest
@JacksonXmlRootElement(localName = "noop")
public class Noop extends BaseXIMSSRequest {
}
