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

    def notify(String url) {
        String responseXml = authenticationService.signAndSendRequest(url)
        def event = new XmlSlurper().parseText(responseXml)
        handleEvent(event)
    }
    
    def assign(String url) {
        String responseXml = authenticationService.signAndSendRequest(url)
        def event = new XmlSlurper().parseText(responseXml)
        handleEvent(event)
    }

    def unassign(String url) {
        String responseXml = authenticationService.signAndSendRequest(url)
        def event = new XmlSlurper().parseText(responseXml)
        handleEvent(event)
    }

    
    private handleEvent(event) {
        EventType eventType = EventType.fromValue(event?.type?.text())
        
        def result = [:]
        switch(eventType) {
            case EventType.SUBSCRIPTION_ORDER:
                result = createSubscription(event)
                break
            case EventType.SUBSCRIPTION_CANCEL:
                result = cancelSubscription(event)
                break
            case EventType.SUBSCRIPTION_CHANGE:
                result = changeSubscription(event)
                break
            case EventType.SUBSCRIPTION_NOTICE:
                result = noticeSubscription(event)
                break
            case EventType.USER_ASSIGNMENT:
                result = assignUser(event)
                break
            case EventType.USER_UNASSIGNMENT:
                result = unassignUser(event)
                break
            default:
                result.success = false
                result.errorCode = ErrorCode.INVALID_RESPONSE.toString()
                break
        }

        result
    }

    private createSubscription(event) {
        def result = [success: false]
        try {
            def creator = event?.creator
            if (User.findWhere([uuid: creator?.uuid?.text()])) {
                result.errorCode = ErrorCode.USER_ALREADY_EXISTS.toString()
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
                    result.errorCode = ErrorCode.USER_ALREADY_EXISTS.toString()
                } else {
                    def edition = payload?.order?.editionCode?.text()
                    Subscription subscription = Subscription.createInstance(company, edition)
                    Subscription.withTransaction { subscription.save(flush: true) }
                    result.success = true
                    result.accountIdentifier = subscription.accountIdentifier
                }
            }
        } catch (Exception e) {
            log.error(e)
            result.errorCode = ErrorCode.UNKNOWN_ERROR.toString()
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
                subscription.status = AccountStatus.CANCELLED.toString()
                subscription.save(true)
                result.success = true
            } else {
                result.errorCode = ErrorCode.ACCOUNT_NOT_FOUND.toString()
            }
        } catch (Exception e) {
            log.error(e)
            result.errorCode = ErrorCode.UNKNOWN_ERROR.toString()
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
                result.errorCode = ErrorCode.ACCOUNT_NOT_FOUND.toString()
            }
        } catch (Exception e) {
            log.error(e)
            result.errorCode = ErrorCode.UNKNOWN_ERROR.toString()
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
                result.errorCode = ErrorCode.ACCOUNT_NOT_FOUND.toString()
            }
        } catch (Exception e) {
            log.error(e)
            result.errorCode = ErrorCode.UNKNOWN_ERROR.toString()
        }
        result
    }
    
    private assignUser(event) {
        def payload = event?.payload,
            accountIdentifier = payload?.account?.accountIdentifier?.text(),
            result = [success: false]
        try {
            Subscription subscription = Subscription.findWhere([accountIdentifier: accountIdentifier])
            if (subscription) {
                if (subscription.users.size() >= subscription.maxUsers) {
                    result.errorCode = ErrorCode.MAX_USERS_REACHED.toString()
                } else {
                    User user = unmarshallingService.getUser(payload?.user)
                    user.save(true)
                    
                    subscription.users.add(user)
                    subscription.save(flush:true)
                }
            }
        } catch (Exception e) {
            log.error(e)
            result.errorCode = ErrorCode.UNKNOWN_ERROR.toString()
        }
        result
    }
    
    private unassignUser(event) {
        def payload = event?.payload,
        accountIdentifier = payload?.account?.accountIdentifier?.text(),
        result = [success: false]
        try {
            Subscription subscription = Subscription.findWhere([accountIdentifier: accountIdentifier])
            if (subscription) {
                User user = unmarshallingService.getUser(payload?.user)
                
                subscription.users.remove(user)
                subscription.save(flush:true)
                result.success = true
            }
        } catch (Exception e) {
            log.error(e)
            result.errorCode = ErrorCode.UNKNOWN_ERROR.toString()
        }
        result
    }
    
    private getStatusFromNotificationType(noticeType) {
        def status
        switch(NoticeType.fromValue(noticeType)) {
            case NoticeType.DEACTIVATED:
                status = AccountStatus.SUSPENDED.toString()
                break
            case NoticeType.REACTIVATED:
                status = AccountStatus.ACTIVE.toString()
                break
            case NoticeType.CLOSED:
                status = AccountStatus.CANCELLED.toString()
                break
            default:
                break 
        }
        status
    }

}
