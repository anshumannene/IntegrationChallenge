<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<result>
  <success>${result.success}</success>
  <g:if test="${result.success}">
    <accountIdentifier>${result.accountIdentifier}</accountIdentifier>
  </g:if>
  <g:else>
    <errorCode>${result.errorCode}</errorCode>
  </g:else>
</result>