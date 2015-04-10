package integrationchallenge

class SubscriptionService {

    AuthenticationService authenticationService
    
    def create(String url) {
        authenticationService.signAndSendRequest(url)
    }
}
