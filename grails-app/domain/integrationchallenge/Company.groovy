package integrationchallenge

class Company {

    String uuid
    String name
    String email
    String website

    static hasMany = [employees: User]

    static constraints = {
        uuid nullable: false, unique: true
        name nullable: false
        email nullable: false
        website nullable: true
    }

    @Override
    public String toString() {
        [
            uuid,
            name,
            email,
            website,
            employees.asList()
        ].join(" : ")
    }
}
