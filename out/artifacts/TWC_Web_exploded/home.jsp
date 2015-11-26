<%--
  Created by IntelliJ IDEA.
  User: Pankaj
  Date: 4/5/2015
  Time: 8:20 PM
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
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand">Temporal Tag Cloud</a>
    </div>
    <div>
      <ul class="nav navbar-nav">
        <li class="active"><a href="home">Home</a></li>
        <li><a>Processing</a></li>
        <li><a>Result</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container special">
  <h1>Wel-Come to Temporal Tag Cloud!</h1>
  <div class="jumbotron">
    <form action="${pageContext.request.contextPath}/home" method="post" enctype="multipart/form-data">

      <div class="form-group">
        <label for="text">Select File:</label>
        <div class="radio">
          <label><input type="radio" name="dFile" value="5M.txt" onclick="togleUplead()">File 1(5 MB)</label>
        </div>
        <div class="radio">
          <label><input type="radio" name="dFile" value="50M.txt" onclick="togleUplead()">File 2(50 MB)</label>
        </div>
        <div class="radio">
          <label><input type="radio" name="dFile" value="250M.txt" onclick="togleUplead()">File 3(250 MB)</label>
        </div>
        <input type="file" name="uploadFile" />
      </div>

      <div class="form-group">
        <label for="text">Time Interval:(in Hours)</label>
        <input type="number" onkeypress="return isNumberKey(event)" min= "1" class="form-control" name="Time" value="1" required="">
      </div>

      <div class="form-group">
        <label for="text">Minimum no of words :</label>
        <input type="number" onkeypress="return isNumberKey(event)" min="1" max="250" class="form-control" name="Min" value="5">
      </div>

      <%--<div class="form-group">
        <label for="text">Maximum no of words :</label>
        <input type="number" onkeypress="return isNumberKey(event)" min="1" max="150" class="form-control" name="Max" value="150">
      </div>--%>

      <div class="form-group">
        <label for="text">Word Orientation :</label><br/>
        <select name="orientation" class="btn btn-default dropdown-toggle">
          <option value="90">Horizontal and Vertical</option>
          <option value="0">Horizontal Only</option>
        </select>
      </div>

      <button type="submit"  value="upload" class="btn btn-primary">Submit</button>
    </form>
    <div class="column">
      <p>Please select a file from given list, if you have tweeter data-set file please upload using browse button</p><br/>
      <p>File size is restricted to 3MB</p><br/>
      <p>Select orientation horizontal if you want all words only in horizontal manner</p>
    </div>
  </div>
</div>

</body>
</html>
