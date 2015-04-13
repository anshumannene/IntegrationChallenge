package integrationchallenge

class User {

    String firstName
    String lastName
    String uuid
    String openId

    static belongsTo = [company: Company]

    static constraints = {
        uuid nullable: false, unique: true
        firstName nullable: false
        lastName nullable: false
        openId nullable: false
    }

    @Override
    public String toString() {
        [
            firstName,
            lastName,
            uuid,
            openId
        ].join(" : ")
    }
}
