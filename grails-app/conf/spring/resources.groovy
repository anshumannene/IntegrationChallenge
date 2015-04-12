import grails.util.Environment
import integrationchallenge.MockSubscriptionService

beans = {
    switch(Environment.getCurrentEnvironment()) {
        case Environment.TEST:
            subscriptionService(MockSubscriptionService)
            break
        case Environment.DEVELOPMENT:
            break
        case Environment.PRODUCTION:
            break
    }
}
