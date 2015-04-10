package integrationchallenge

class SubscriptionController {

	def subscriptionService
	
    def create() {
		render "create"
	}
	
	def change() {
		render "change"
	}
	
	def cancel() {
		render "cancel"
	}
	
	def status() {
		render "status"
	}
}
