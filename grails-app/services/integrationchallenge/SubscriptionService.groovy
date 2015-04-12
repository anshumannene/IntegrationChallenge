package integrationchallenge


class SubscriptionService {

    AuthenticationService authenticationService

    def create(String url) {
        String responseXml = authenticationService.signAndSendRequest(url)
        def event = new XmlSlurper().parseText(responseXml)
        handleEvent(event)
    }

    def cancel(String url) {
        String createResponse = authenticationService.signAndSendRequest(url)
    }

    def change(String url) {
        String createResponse = authenticationService.signAndSendRequest(url)
    }

    def status(String url) {
        String createResponse = authenticationService.signAndSendRequest(url)
    }

    private handleEvent(event) {
        def eventType = event?.type?.text()

        def result = [:]
        switch(eventType) {
            case 'SUBSCRIPTION_ORDER':
                result = createSubscription(event.creator)
                break
            case 'SUBSCRIPTION_CANCEL':
                result = cancelSubscription(event.creator)
                break
            case 'SUBSCRIPTION_CHANGE':
                break
            case 'SUBSCRIPTION_STATUS':
                break
            default:
                result.success = false
                result.errorCode = 'INVALID_RESPONSE'
                break
        }

        result
    }

    private createSubscription(creator) {
        def result = [:]
        try {
            def query = [firstName: creator?.firstName.text(), lastName: creator?.lastName.text(), uuid: creator?.uuid.text(), openId: creator?.openId.text()]
            User user = User.findWhere(query)
            if (user) {
                result.success = false
                result.errorCode = 'USER_ALREADY_EXISTS'
            } else {
                user = new User(query).save(flush: true)
                result.success = true
                result.accountIdentifier = user.uuid
            }
        } catch (Exception e) {
            result.success = false
            result.errorCode = 'UNKNOWN_ERROR'
        }
        result
    }

    private cancelSubscription(creator) {
    }
}
