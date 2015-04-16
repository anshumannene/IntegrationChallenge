package integrationchallenge



class UserController {
    UserService userService
    
    def list() { 
        def openId = "https://www.appdirect.com/openid/id/6fbedef2-f983-42bc-b56b-adafe8f07965" //session.openidIdentifier
        def subscriptions = userService.fetchUserSubscriptions(openId)
        render(view: 'list', model: [subscriptions: subscriptions])
    }
}
