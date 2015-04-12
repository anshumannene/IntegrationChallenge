package integrationchallenge

class User {

    String firstName
    String lastName
    String uuid
    String openId

    static constraints = {
        firstName nullable: false
        lastName nullable: false
        uuid nullable: false, unique: true
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
