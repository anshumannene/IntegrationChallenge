package integrationchallenge

class Subscription {

    String accountIdentifier
    Company company
    String status
    String edition

    static constraints = {
        accountIdentifier nullable: false, unique: true
    }

    def static Subscription createInstance(Company company, String edition) {
        Subscription instance = new Subscription()
        instance.accountIdentifier = UUID.randomUUID().toString()
        instance.company = company
        instance.status = "FREE_TRIAL"
        instance.edition = edition
        return instance
    }
    @Override
    public String toString() {
        [accountIdentifier, company].join(" : ")
    }
}
