package integrationchallenge

class LoginController {

    def index() { }
	
	def loggedin() {
		render "Logged in" 
	}
	
	def error() {
		render "there was an error"
	}
}
