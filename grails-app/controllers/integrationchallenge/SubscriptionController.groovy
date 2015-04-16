package integrationchallenge

import grails.converters.XML

class SubscriptionController {

    def subscriptionService

    def create() {
        render (view: "create", model: [result: subscriptionService.create(params.url)], contentType: 'text/xml') as XML
    }

    def change() {
        render (view: "change", model: [result: subscriptionService.change(params.url)], contentType: 'text/xml') as XML
    }

    def cancel() {
        render (view: "cancel", model: [result: subscriptionService.cancel(params.url)], contentType: 'text/xml') as XML
    }

    def notice() {
        render (view: "notify", model: [result: subscriptionService.notify(params.url)], contentType: 'text/xml') as XML
    }

    def assign() {
        render (view: "assign", model: [result: subscriptionService.assign(params.url)], contentType: 'text/xml') as XML
    }
    
    def unassign() {
        render (view: "unassign", model: [result: subscriptionService.unassign(params.url)], contentType: 'text/xml') as XML
    }

    def list() {
        render(view: "list", model: [users: User.list(), companies: Company.list(), subscriptions: Subscription.list()])
    }
}
