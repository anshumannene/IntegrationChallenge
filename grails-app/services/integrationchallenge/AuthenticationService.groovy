package integrationchallenge

import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.oauth.OAuthService

import uk.co.desirableobjects.oauth.scribe.OauthResourceService

class AuthenticationService {

    OauthResourceService oauthResourceService
    OAuthService service

    def signAndSendRequest(url) {
        OAuthRequest request = new OAuthRequest(Verb.GET, url)
        Token token = new Token("", "")
        Response response = oauthResourceService.signAndSend(service, new Token("", ""), request)
        response.getBody()
    }
}
