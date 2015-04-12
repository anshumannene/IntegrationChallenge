package integrationchallenge

import grails.converters.XML

class SubscriptionController {

    def subscriptionService

    def create() {
        render (view: "create", model: [result: subscriptionService.create(params.url)], contentType: 'text/xml') as XML
    }

    def change() {
        render subscriptionService.change(params.url)
    }

    def cancel() {
        render subscriptionService.cancel(params.url)
    }

    def status() {
        render subscriptionService.status(params.url)
    }
}
