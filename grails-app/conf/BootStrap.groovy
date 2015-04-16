import integrationchallenge.Company
import integrationchallenge.Subscription
import integrationchallenge.User

class BootStrap {

    def init = { servletContext ->
        if (!User.findByUuid("6fbedef2-f983-42bc-b56b-adafe8f07965")) {
            User user = new User(uuid: "6fbedef2-f983-42bc-b56b-adafe8f07965", firstName: "Anshuman", lastName: "Nene",
                openId: "https://www.appdirect.com/openid/id/6fbedef2-f983-42bc-b56b-adafe8f07965")
            user.save(flush:true)
            
            Company company = new Company(uuid: "4acc9d59-9403-47a5-8149-2636a90a5d75", name: "Deem", email: "nene.anshuman@gmail.com", website: "www.deem.com")
            company.addToEmployees(user)
            company.save(flush: true)
            
            Subscription subscription = new Subscription(accountIdentifier: "a652448d-ddf0-4cd2-bd04-1ff0ff1ec8af", company: company, status: "FREE_TRIAL", edition: "FREE")
            subscription.save(flush: true)
        }
    }
    def destroy = {
    }
}
