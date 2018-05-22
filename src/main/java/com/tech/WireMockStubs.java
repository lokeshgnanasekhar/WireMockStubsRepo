package com.tech;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class WireMockStubs {

    //Simple get Mock stub
    public void simpleMock(){
        stubFor(get(urlEqualTo("/simpleMock"))
                .willReturn(aResponse()
                        .withBody("Welcome to Mock World !")));
    }

    //Sending json file as response
    public void jsonResponseMock(){
        stubFor(get(urlEqualTo("/jsonResponseMock"))
                .willReturn(aResponse()
                        .withBodyFile("mockresponse.json")));
    }
}
