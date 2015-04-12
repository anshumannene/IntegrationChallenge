import grails.util.Environment
import integrationchallenge.AuthenticationService
import integrationchallenge.MockSubscriptionService
import integrationchallenge.SubscriptionService

beans = {

    switch(Environment.getCurrentEnvironment()) {
        case Environment.TEST:
            subscriptionService(MockSubscriptionService)
            break
        case Environment.DEVELOPMENT:
            subscriptionService(SubscriptionService) { authenticationService(AuthenticationService) }
            break
        case Environment.PRODUCTION:
            subscriptionService(SubscriptionService) { authenticationService(AuthenticationService) }
            break
    }
}
