package integrationchallenge

class SubscriptionController {

	def subscriptionService
	
    def create() {
		render params.url
	}
	
	def change() {
		render params.url
	}
	
	def cancel() {
		render params.url
	}
	
	def status() {
		render params.url
	}
}
