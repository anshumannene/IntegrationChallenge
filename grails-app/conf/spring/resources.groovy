import grails.util.Environment
import integrationchallenge.MockSubscriptionService
import integrationchallenge.UnmarshallingService

beans = {
    switch(Environment.getCurrentEnvironment()) {
        case Environment.TEST:
            unmarshallingService(UnmarshallingService)
            subscriptionService(MockSubscriptionService) { unmarshallingService = unmarshallingService }
            break
        case Environment.DEVELOPMENT:
            break
        case Environment.PRODUCTION:
            break
    }
}
