package integrationchallenge

class LoginController {

    def index() { }
	
	def loggedin() {
		render "Logged in as: ${session.openidIdentifier}   ${session.openidParams.sreg}    ${session.openidParams.ax}"
	}
	
	def error() {
		render "there was an error"
	}
}
