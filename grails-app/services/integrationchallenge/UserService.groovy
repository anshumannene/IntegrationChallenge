package integrationchallenge

class UserService {
    
    def fetchUserSubscriptions(openId) {
        def subscriptions
        User user = User.findByOpenId(openId)
        if (user) {
            Company company = Company.findByUuid("4acc9d59-9403-47a5-8149-2636a90a5d75")
            subscriptions = Subscription.findAllByCompany(company)
        }
        
        subscriptions
    }
}
