package com.example.diplom.ximss.request;

import com.example.diplom.annotation.PreLoginRequest;
import com.example.diplom.ximss.BaseXIMSSRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@PreLoginRequest
public class Login extends BaseXIMSSRequest {

    private String userName;

    private String password;

    private String authData;

    private String nonce;

    private String errorAsXML;

    private String version;
}
