<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Login</title>
        <openid:css />
    </head>
    <body>
        <h1>Login</h1>
        <openid:hasLoginError>
            <div class="errors">
                <ul>
                    <li><openid:renderLoginError /></li>
                </ul>
            </div>
        </openid:hasLoginError>
        
        <openid:form success="[controller:'login', action:'loggedin']" error="[controller:'login', action:'error']">
            <openid:input size="30" value="https://www.appdirect.com/openid/id" /> (e.g. http://username.myopenid.com)
            <br/>
            <g:submitButton name="login" value="Login" />
        </openid:form>
    </body>
</html>