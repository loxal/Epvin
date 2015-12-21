<%--
  ~ Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
  --%>

<!DOCTYPE html>
<!--
~ Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
-->
<meta charset="utf-8">
<meta name="viewport"
      content="width=device-width, height=device-height, initial-scale=0.2, minimum-scale=0.1, maximum-scale=1.0, user-scalable=yes">
<link rel="apple-touch-icon" href="http://me.loxal.net/resource/loxal-logo.png"/>
<link rel="apple-touch-icon-precomposed" href="/favicon.ico"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
<link rel="apple-touch-startup-image" href="http://me.loxal.net/resource/loxal-logo.png"/>
<title>Epvin | Loxal</title>

<%--Try this in SuperDevMode again--%>
<script id="module"></script>

<%
    final String module;
    if (request.getParameter("module") == null) {
        module = "loxal.epvin.manager.Manager"; // default GWT module
    } else {
        module = request.getParameter("module");
    }
%>
<script src="<%= "/" + module + "/" + module + ".nocache.js" %>"></script>
<iframe src="javascript:''" id="__gwt_historyFrame" style="width:0; height:0; border:0;"></iframe>