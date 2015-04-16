package integrationchallenge

class LoginController {

    def index() { }

    def loggedin() {
        redirect(controller: "user", view: "list")
    }

    def error() {
        render "there was an error"
    }
}
