package integrationchallenge

class LoginController {

    def index() { }

    def loggedin() {
        render "Logged in as: ${session.openidIdentifier} --> ${session}"
    }

    def error() {
        render "there was an error"
    }
}
