package integrationchallenge


class MockSubscriptionService extends SubscriptionService {

    @Override
    public Object create(String url) {
        def PATH = "/mock/createResponse.xml"
        def event = loadXml(PATH)
        handleEvent(event)
    }

    @Override
    public Object cancel(String url) {
        def PATH = "/mock/cancelResponse.xml"
        loadXml(PATH)
    }

    @Override
    public Object change(String url) {
        def PATH = "/mock/changeResponse.xml"
        loadXml(PATH)
    }

    @Override
    public Object status(String url) {
        def PATH = "/mock/status.xml"
        loadXml(PATH)
    }

    private loadXml(String path) {
        InputStream is = this.class.getResourceAsStream(path)
        def event = new XmlSlurper().parse(is)
    }
}
