package integrationchallenge

import grails.converters.XML

class SubscriptionController {

    def subscriptionService

    def create() {
        render (view: "create", model: [result: subscriptionService.create(params.url)], contentType: 'text/xml') as XML
    }

    def change() {
        render (view: "cancel", model: [result: subscriptionService.cancel(params.url)], contentType: 'text/xml') as XML
    }

    def cancel() {
        render subscriptionService.cancel(params.url)
    }

    def status() {
        render subscriptionService.status(params.url)
    }

    def list() {
        render(view: "list", model: [users: User.list(), companies: Company.list(), subscriptions: Subscription.list()])
    }
}
