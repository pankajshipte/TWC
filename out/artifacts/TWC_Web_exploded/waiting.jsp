<%--
  Created by IntelliJ IDEA.
  User: Pankaj
  Date: 4/5/2015
  Time: 10:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Temporal Tag Cloud</title>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="css/normalize.css" />
  <link rel="stylesheet" href="css/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="css/animation.css"/>
  <link rel="stylesheet" type="text/css" href="css/demo.css" />
  <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/animation.js"></script>
  <script>
    function sendData() {
      $.ajax({
        type: "POST",
        url : "/waiting",
        data: {},
        success: function(message) {
          window.location.href = "/result";//Math.random()*Math.random()
        },
        error : function(){
          alert("Something went wrong.. Please try again !!");
        }
      });
    }
  </script>
</head>

<body onload="sendData();">
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand">Temporal Tag Cloud</a>
    </div>
    <div>
      <ul class="nav navbar-nav">
        <li><a href="home">Home</a></li>
        <li class="active"><a href="waiting">Processing</a></li>
        <li><a href="result">Result</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container special">
  <h1>Please Wait</h1>
  <div id="loader-wrapper">
    <div id="loader"></div>

    <div class="loader-section section-left"></div>
    <div class="loader-section section-right"></div>

  </div>
  <div class="jumbotron">
    <div id="content">
      <p>Sit back and relax, We are processing your document</p>
      <p>Createing Word cloud is lengthy process. It may take several minutes.</p><br/><br/><br/><br/><br/><br/>
    </div>
  </div>
</div>

</body>
</html>
