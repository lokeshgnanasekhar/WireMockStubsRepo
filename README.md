# WireMockStubsRepo
This Java Project demo's all the functionalities of WireMock by creating stubs

* WireMockRunner class  - Holds Responsibility for running stubs
* WireMockStubs class - Will have all different functionality defined as a stubs / Mock

#### To Build
`.\gradle build'

#### To Run
`.\gradle Run`


If you need to add any new stub go to `WireMockStubs.java` and add you own stub. calling stub is automatically taken care in the code.

By default this code will run WireMock Server in `9997` port .So, When you test, you need to use `http://localhost:9997/mockapiendpoint`.

##### Note: You can change to any other port if you require. 
##### Any new stub if you add you need to stop the server / Program and should Re-run to make stub work.
