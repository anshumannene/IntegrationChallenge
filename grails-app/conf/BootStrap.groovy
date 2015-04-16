import grails.util.Environment
import integrationchallenge.Company
import integrationchallenge.Subscription
import integrationchallenge.User

class BootStrap {

    def init = { servletContext ->
        if (Environment.getCurrentEnvironment().equals(Environment.TEST)) {
            User user = new User(uuid: UUID.randomUUID().toString(), firstName: "Tester", lastName: "Chester",
                openId: "https://www.appdirect.com/openid/id/6fbedef2-f983-42bc-b56b-adafe8f07965")
            
            Company company = new Company(uuid: UUID.randomUUID().toString(), name: "Deem", email: "tester.chester@deem.com", website: "www.deem.com")
            company.addToEmployees(user)
            company.save(flush: true)

            User endUser = new User(uuid: UUID.randomUUID().toString(), firstName: "end", lastName: "user",
                openId: "https://www.appdirect.com/openid/id/6fbedef2-f983-42bc-b56b-adafe8f07965")

            Subscription subscription = new Subscription(accountIdentifier: UUID.randomUUID().toString(), company: company, status: "FREE_TRIAL", edition: "FREE")
            //subscription.save(flush:true)
            subscription.users.add(endUser)
            subscription.save(true)
            
            def list = Subscription.list()
            println list.size()
        }
    }
    def destroy = {
    }
}
