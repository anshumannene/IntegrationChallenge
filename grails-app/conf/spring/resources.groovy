import grails.util.Environment
import integrationchallenge.AuthenticationService
import integrationchallenge.MockOpenidService
import integrationchallenge.MockSubscriptionService
import integrationchallenge.SubscriptionService

beans = {
    switch(Environment.current) {
        case Environment.TEST:
            openidService(MockOpenidService)
            subscriptionService(MockSubscriptionService)
            break
        case Environment.DEVELOPMENT:
            subscriptionService(SubscriptionService) {  authenticationService(AuthenticationService)  }
            break
        case Environment.PRODUCTION:
            subscriptionService(SubscriptionService) {  authenticationService(AuthenticationService)  }
            break
    }
}
