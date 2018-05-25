package com.tech;

import com.github.tomakehurst.wiremock.http.Fault;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;

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

    //get request with query parameter
    public void getQueryParam() {
        stubFor(get(urlPathEqualTo("/getQueryParam"))
                .withQueryParam("searchFor" ,equalTo("WireMock"))
                .willReturn(aResponse()
                        .withBody("Able to get query Parameter")));
    }

    // get request with body matching
    public void requestBodyMatching()
    {
        stubFor(post(urlPathEqualTo("/requestBodyMatching"))
                .withRequestBody(containing("WireMock"))
                .withRequestBody(containing("Mock Service"))
                .willReturn(aResponse()
                        .withBody("Request Body Matching with given specifications")));
    }

    //get request with headers matching
    public void requestHeaderMatching()
    {
        stubFor(post(urlPathEqualTo("/requestHeaderMatching"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("WireMock"))
                .withRequestBody(containing("Mock Service"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Request Header Matching with given specifications")));
    }

    //request with basic authentication
    public void basicAuthentication()
    {
        stubFor(post(urlPathEqualTo("/basicAuthentication"))
                .withBasicAuth("Admin","wiremock@123")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Authenticated !!")));
    }

    //any unmapped request
    public void anyRequest()
    {
        stubFor(any(anyUrl())
                .willReturn(aResponse()
                        .withBody("<html><B><font color=\"red\">End Point Not Found</font></B></html>")));
    }

    //regex with urlPathMatching
    public void regexRequestUsingURLPathMatching()
    {
        stubFor(get(urlPathMatching("/regex/([a-z]*)"))
                .atPriority(2)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\" : 200 ,  \"Message\":\"URL Matched With Regex\" }")));
    }

    //request with stub priority
    public void requestForStubWithPriority(){

        stubFor(get(urlPathMatching("/regex/stubPriority"))
                .atPriority(1)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\" : 200 ,  \"Message\":\"Stub Priority\" }")));
    }

    //Stateful Scenario

    public void shoppingcart() {

        stubFor(get(urlEqualTo("/shop/items")).inScenario("shopping")
                .whenScenarioStateIs(STARTED)
                .willReturn(aResponse()
                        .withBody("<html><h1>Welcome to Shopping Cart<h1></html>"))
                        .willSetStateTo("Start Shopping"));

        stubFor(get(urlEqualTo("/shop/items")).inScenario("shopping")
                .whenScenarioStateIs("Start Shopping")
                .willReturn(aResponse()
                        .withBody("<html><h1>Welcome to Shopping Cart<h1><hr> <li>Milk</li></html>"))
                        .willSetStateTo("First Item"));

        stubFor(get(urlEqualTo("/shop/items")).inScenario("shopping")
                .whenScenarioStateIs("First Item")
                .willReturn(aResponse()
                        .withBody("<html><h1>Welcome to Shopping Cart<h1><hr> <li>Milk</li><li>Eggs</li></html>"))
                        .willSetStateTo("Second Item"));

        stubFor(get(urlEqualTo("/shop/items")).inScenario("shopping")
                .whenScenarioStateIs("Second Item")
                .willReturn(aResponse()
                        .withBody("<html><h1>Welcome to Shopping Cart<h1><hr> <li>Milk</li><li>Eggs</li><hr><i>Thanks For Shopping</i></html>")));


    }

    public void responseFormats() {

        //respond back with status Ok - 200 status cod
        stubFor(delete("/ok")
                .willReturn(ok()));

        //respond back status ok with message
        stubFor(get("/withbody")
                .willReturn(ok("some body content")));

        //respond with json message , we can send string , byte and number formats
        stubFor(get("/json")
                .willReturn(okJson("{ \"message\": \"This is json\" }")));

        //responds with redirected url
        stubFor(post("/redirect")
                .willReturn(temporaryRedirect("/json")));

        //responds with unauthorized status
        stubFor(post("/unauthorized")
                .willReturn(unauthorized()));

        //responds with status code 500 - here server error
        stubFor(put("/status")
                .willReturn(status(500)));

        //responds with status Message
        stubFor(get("/statusMSG")
                .willReturn(aResponse()
                        .withStatusMessage("Define your Status Message")));
    }

    public void faultHandling() {

        //Send an OK status header, then garbage, then close the connection.
        stubFor(get(urlEqualTo("/fault1"))
                .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

        //Return a completely empty response
        stubFor(get(urlEqualTo("/fault2"))
                .willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));

        //Send garbage then close the connection.
        stubFor(get(urlEqualTo("/fault3"))
                .willReturn(aResponse().withFault(Fault.RANDOM_DATA_THEN_CLOSE)));
    }

    public void delayedReponses(){

        //delayed response
        stubFor(get(urlEqualTo("/delayed")).willReturn(
                aResponse()
                        .withStatus(200)
                        .withBody("This Message is displaying after 2000 milli sec")
                        .withFixedDelay(2000)));

        //send response as chunks
        stubFor(get("/chunked/delayed").willReturn(
                aResponse()
                        .withBody("Chunked response ")
                        .withChunkedDribbleDelay(5, 5000)));


    }

    public void proxying(){

        //Proxying
        stubFor(get(urlMatching("/proxy"))
                .willReturn(aResponse().proxiedFrom("http://www.wiremock.com")));

        // Inject user agent to trigger rendering of mobile version of website
        stubFor(get(urlMatching("/test/proxy/.*"))
                .willReturn(aResponse()
                        .proxiedFrom("http://papajohns.com")
                        .withAdditionalRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; U; CPU iPhone)")));

    }


}
