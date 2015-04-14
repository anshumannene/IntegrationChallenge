package integrationchallenge

class Company {

    String uuid
    String email
    String name
    String website

    static hasMany = [employees: User]

    static constraints = {
        uuid nullable: false, unique: true
        name nullable: false
        email nullable: true
        website nullable: true
    }

    @Override
    public String toString() {
        [
            uuid,
            name,
            email,
            website,
            employees.toList()
        ].join(" : ")
    }
}
