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

    def status() {
        render (view: "status", model: [result: subscriptionService.status(params.url)], contentType: 'text/xml') as XML
    }

    def list() {
        render(view: "list", model: [users: User.list(), companies: Company.list(), subscriptions: Subscription.list()])
    }
}
