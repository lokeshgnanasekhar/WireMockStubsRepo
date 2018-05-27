package com.tech;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class MockRunner {

    public static void main(String args[]) {

        WireMockConfiguration configuration = new WireMockConfiguration()
                .usingFilesUnderDirectory("resources/api/responses/")
                .port(9997)
                .extensions(new ResponseTemplateTransformer(true));
        WireMockServer wireMockServer = new WireMockServer(configuration);
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        invokeStubs();

    }

    public static void invokeStubs() {
        try {
            Class wireMockStubs = WireMockStubs.class;
            Object object = wireMockStubs.newInstance();
            Method stubs[] = wireMockStubs.getDeclaredMethods();
            for (Method mockStub : stubs)
                mockStub.invoke(object , null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
