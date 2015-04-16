<html>
<head>
  <title>Subscriptions</title>
  <link type="text/css" href="${resource(dir: 'css', file: 'main.css')}" />
</head>
<body>
<g:if test="${subscriptions}">
    <div style="display:table; width: 500px; border-bottom: 1px solid; margin-bottom: 10px;">
      <div style="display:table-row;">
        <span style="display:table-cell; width: 40%;">Account #</span>
        <span style="display:table-cell; width:20%;">Company</span>
        <span style="display:table-cell; width:20%">Edition</span>
        <span style="display:table-cell; wdith:20%">Status</span>
      </div>
    </div>
    <g:each var="subscription" in="${subscriptions}">
      <div style="display:table; width: 500px; margin-bottom: 10px;">
        <div style="display:table-row;">
          <span style="display:table-cell; width:40%;">${subscription.accountIdentifier}</span>
          <span style="display:table-cell; width:20%;">${subscription.company.name}</span>
          <span style="display:table-cell; width:20%;">${subscription.edition}</span>
          <span style="display:table-cell; width:20%;">${subscription.status}</span>
        </div>
      </div>
    </g:each>
</g:if>
<g:else>
  <div>You have no subscriptions currently.</div>
</g:else>
</body>
</html>