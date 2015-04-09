import grails.util.Environment
import integrationchallenge.MockOpenidService

beans = {
	switch(Environment.current) {
		case Environment.TEST:
			openidService(MockOpenidService)
			break
	}
}
