package integrationchallenge

class UnmarshallingService {

    def getUser(node) {
        def query = [firstName: node?.firstName?.text(), lastName: node?.lastName?.text(), uuid: node?.uuid?.text() ?: UUID.randomUUID().toString(), openId: node?.openId?.text()]

        new User(query)
    }

    def getCompany(node) {
        Company company = Company.findWhere([uuid: node?.uuid?.text()])
        if (null == company) {
            company = new Company([uuid: node?.uuid?.text() ?: UUID.randomUUID().toString(), name: node?.name?.text(), email: node?.email?.text(), website: node?.website?.text()])
        }
        company
    }
}
