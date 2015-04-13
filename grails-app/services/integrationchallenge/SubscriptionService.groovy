package integrationchallenge



class SubscriptionService {

    AuthenticationService authenticationService
    UnmarshallingService unmarshallingService

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
                result = createSubscription(event)
                break
            case 'SUBSCRIPTION_CANCEL':
                result = cancelSubscription(event)
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

    private createSubscription(event) {
        def result = [success: false]
        try {
            def creator = event?.creator
            if (User.findWhere([uuid: creator?.uuid?.text()])) {
                result.errorCode = 'USER_ALREADY_EXISTS'
            } else {
                User user = unmarshallingService.getUser(creator)
                user.save(true)

                def payload = event?.payload
                Company company = unmarshallingService.getCompany(payload?.company)
                Company.withTransaction {
                    company.addToEmployees(user)
                    company.save(true)
                }

                if (Subscription.findByCompany(company)) {
                    result.errorCode = 'USER_ALREADY_EXISTS'
                } else {
                    Subscription subscription = Subscription.createInstance(company)
                    Subscription.withTransaction { subscription.save(true) }
                    result.success = true
                    result.accountIdentifier = subscription.accountIdentifier
                }
            }
        } catch (Exception e) {
            result.errorCode = 'UNKNOWN_ERROR'
        }
        result
    }

    private cancelSubscription(event) {
        def accountIdentifier = payload?.account?.accountIdentifier?.text()
        result = [success: false]
        try {
            def query = [accountIdentifier: accountIdentifier]
            User user = User.findWhere(query)
            if (user) {
                user.delete(flush: true)
                result.success = true
            } else {
                result.errorCode = 'USER_NOT_FOUND'
            }
        } catch (Exception e) {
            result.errorCode = 'UNKNOWN_ERROR'
        }
    }
}
