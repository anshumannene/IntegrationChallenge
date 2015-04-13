package integrationchallenge

class Subscription {

    String accountIdentifier
    Company company

    static constraints = {
        accountIdentifier nullable: false, unique: true
    }

    def static Subscription createInstance(Company company) {
        Subscription instance = new Subscription()
        instance.accountIdentifier = UUID.randomUUID().toString()
        instance.company = company
        return instance
    }
    @Override
    public String toString() {
        [accountIdentifier, company].join(" : ")
    }
}
