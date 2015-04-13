<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<result>
  <success>${result.success}</success>
  <g:if test="${!result.success}">
    <errorCode>${result.errorCode}</errorCode>
  </g:if>
  
  <g:if test="${result.message}">
    <message>${result.message}</message>
  </g:if>
</result>