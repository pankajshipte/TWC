<%--
  Created by IntelliJ IDEA.
  User: Pankaj
  Date: 4/6/2015
  Time: 12:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Temporal Tag Cloud</title>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv='cache-control' content='no-cache'/>
  <meta http-equiv='expires' content='0'/>
  <meta http-equiv='pragma' content='no-cache'>
  <link rel="stylesheet" type="text/css" href="css/normalize.css" />
  <link rel="stylesheet" href="css/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="css/animation.css"/>
  <link rel="stylesheet" type="text/css" href="css/demo.css" />
  <script src="js/myscripts.js"></script>
  <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/d3.v2.min.js"></script>
  <script type="text/javascript" src="js/d3.layout.cloud.js"></script>
</head>
<body onload="setPageSVG(${sessionScope.Intervals}, '${sessionScope.FileName}', '${sessionScope.Orient}');hideButtons();getSVG();">

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand">Temporal Tag Cloud</a>
    </div>
    <div>
      <ul class="nav navbar-nav">
        <li><a href="home">Home</a></li>
        <li><a>Processing</a></li>
        <li class="active"><a href="result">Result</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container special">
  <div id="time"></div>
  <div class="jumbotron">
    <div id="image"></div>
    <div id="buttons">
      <BUTTON type="button" class="btn btn-default" id="prev" onclick="getPrevBtn()">Prev</BUTTON>
      <BUTTON type="button" class="btn btn-primary" id="next" onclick="getNextBtn()">Next</BUTTON>
      <BUTTON type="button" class="btn btn-primary" id="stop" onclick="stopAnimation()">Pause</BUTTON>
      <BUTTON type="button" class="btn btn-default" id="resume" onclick="resumeAnimation();hideButtons()">Resume</BUTTON>
    </div>
  </div>
</div>

</body>
</html>
