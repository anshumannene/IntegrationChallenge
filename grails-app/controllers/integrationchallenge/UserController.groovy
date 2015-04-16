package integrationchallenge



class UserController {
    UserService userService
    
    def list() { 
        def openId = session.openidIdentifier
        def subscriptions = userService.fetchUserSubscriptions(openId)
        render(view: 'list', model: [subscriptions: subscriptions])
    }
}
