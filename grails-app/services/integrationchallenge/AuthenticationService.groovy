package integrationchallenge

import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.oauth.OAuthService

import uk.co.desirableobjects.oauth.scribe.OauthResourceService
import uk.co.desirableobjects.oauth.scribe.OauthService

class AuthenticationService extends OauthService {

    OauthResourceService oauthResourceService

    String signAndSendRequest(url) {
        log.info(url)
        //verify incoming request
        OAuthRequest request = new OAuthRequest(Verb.GET, url)
        log.info(request.getOauthParameters())
        Token token = new Token("", "")
        OAuthService service = findService("appdirect")
        Response response = oauthResourceService.signAndSend(service, token, request)
        response.getBody()
    }
}
