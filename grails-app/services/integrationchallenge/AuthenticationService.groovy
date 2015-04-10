package integrationchallenge

import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb

import uk.co.desirableobjects.oauth.scribe.OauthResourceService
import uk.co.desirableobjects.oauth.scribe.OauthService

class AuthenticationService extends OauthService {

    OauthResourceService oauthResourceService
    
    def signAndSendRequest(url) {
        OAuthRequest request = new OAuthRequest(Verb.GET, url)
        Token token = new Token("", "")
        Response response = oauthResourceService.signAndSend(this, new Token("", ""), request)
        response.getBody()
    }
}
