package integrationchallenge

import org.springframework.context.MessageSource

class SubscriptionService {

    AuthenticationService authenticationService
    UnmarshallingService unmarshallingService
    MessageSource messageSource

    def create(String url) {
        String responseXml = authenticationService.signAndSendRequest(url)
        def event = new XmlSlurper().parseText(responseXml)
        handleEvent(event)
    }

    def cancel(String url) {
        String responseXml = authenticationService.signAndSendRequest(url)
        def event = new XmlSlurper().parseText(responseXml)
        handleEvent(event)
    }

    def change(String url) {
        String responseXml = authenticationService.signAndSendRequest(url)
        def event = new XmlSlurper().parseText(responseXml)
        handleEvent(event)
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
                result = changeSubscription(event)
                break
            case 'SUBSCRIPTION_NOTICE':
                result = noticeSubscription(event)
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
                user.save(flush: true)

                def payload = event?.payload
                Company company = unmarshallingService.getCompany(payload?.company)
                Company.withTransaction {
                    company.addToEmployees(user)
                    company.save(flush: true)
                }

                if (Subscription.findByCompany(company)) {
                    result.errorCode = 'USER_ALREADY_EXISTS'
                } else {
                    def edition = payload?.order?.editionCode?.text()
                    Subscription subscription = Subscription.createInstance(company, edition)
                    Subscription.withTransaction { subscription.save(flush: true) }
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
        def accountIdentifier = event?.payload?.account?.accountIdentifier?.text()
        def result = [success: false]
        try {
            def query = [accountIdentifier: accountIdentifier]
            Subscription subscription = Subscription.findWhere(query)
            if (subscription) {
                subscription.status = 'CANCELLED'
                subscription.save(true)
                result.success = true
            } else {
                result.errorCode = 'ACCOUNT_NOT_FOUND'
            }
        } catch (Exception e) {
            result.errorCode = 'UNKNOWN_ERROR'
        }
        result
    }

    private changeSubscription(event) {
        def accountIdentifier = event?.payload?.account?.accountIdentifier?.text()
        def result = [success: false]
        try {
            def query = [accountIdentifier: accountIdentifier]
            Subscription subscription = Subscription.findWhere(query)
            if (subscription) {
                def edition = event?.payload?.order?.editionCode?.text()
                subscription.edition = edition
                subscription.save(true)
                result.success = true
            } else {
                result.errorCode = 'ACCOUNT_NOT_FOUND'
            }
        } catch (Exception e) {
            result.errorCode = 'UNKNOWN_ERROR'
        }
        result
    }
    
    private noticeSubscription(event) {
        def accountIdentifier = event?.payload?.account?.accountIdentifier?.text()
        def result = [success: false]
        
        try {
            def query = [accountIdentifier: accountIdentifier]
            Subscription subscription = Subscription.findWhere(query)
            if (subscription) {
                def noticeType = event?.payload?.notice?.type?.text(),
                    status = getStatusFromNotificationType(noticeType)
                if (status) {
                    subscription.status = status
                    subscription.save(true)
                }
                result.success = true
            } else {
                result.errorCode = 'ACCOUNT_NOT_FOUND'
            }
        } catch (Exception e) {
            result.errorCode = 'UNKNOWN_ERROR'
        }
        result
    }
    
    private getStatusFromNotificationType(String noticeType) {
        def status
        switch(noticeType) {
            case 'DEACTIVATED':
                status = 'SUSPENDED'
                break
            case 'REACTIVATED':
                status = 'ACTIVE'
                break
            case 'CLOSED':
                status = 'CANCELLED'
                break
            default:
                break 
        }
        status
    }
}
