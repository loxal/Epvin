<!DOCTYPE html>
<!--
~ Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
-->
<meta http-equiv="X-UA-Compatible" content="chrome=1">
<meta charset="utf-8">
<meta name="viewport"
      content="width=device-width, height=device-height, initial-scale=1.2, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes">
<link rel="apple-touch-startup-image" href="http://module.loxal.net/resource/logo.png"/>
<title>Epvin | Loxal</title>

<%--Try this in SuperDevMode again--%>
<script type="application/dart" src="gwt_bootstrapper.dart"></script>
<script src="http://dart.googlecode.com/svn/branches/bleeding_edge/dart/client/dart.js"></script>
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