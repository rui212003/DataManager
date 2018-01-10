<%--
  User: rui
  Date:2018/1/9
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="htmlframe/headerFrame.jsp" />
<link href="/DataManager/css/main.css" rel="stylesheet" />
<html class="bg-black"><head _wxhkphogpkobbkjccgfifhfjlahnoocnan_="shake_1.0">
    <meta charset="UTF-8">
    <title>DataManagement</title>
    <link rel="shortcut icon" href="/DataManager/img/valdac32.ico" type="image/vnd.microsoft.icon">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<style type="text/css">
    .footer {
        background-color: #ebd3e8;
    }
</style>
<body class="bg-black">

<div class="form-box" id="login-box">
    <%--<c:if test="${message != null}">--%>
        <%--<div class="row">--%>
            <div class="row">
                <div id="errorMessage" style="color:#ff0000; text-align: center;font-size: 15pt;">${message}</div><br>
            </div>
        <%--</div>--%>
    <%--</c:if>--%>


            <div class="header">在庫管理システム(Ver1.0)</div>
            <form id="userForm" name="userForm"  action="/DataManager/login" onclick="return check()" method="post">
                <div class="body bg-gray">
                    <div class="form-group">
                        <input type="text" name="userName" class="form-control" placeholder="user Name">
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" class="form-control" placeholder="Password">
                    </div>
                </div>
                <div class="footer">
                    <button type="submit" class="btn bg-aqua btn-block">ログイン(Login)</button>
                </div>
            </form>
</div>


<!-- jQuery 2.0.2 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="/DataManager/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
    function check(){LoginController
        var flag=0;
        var msg=document.getElementById("errorMessage");
        msg.innerHTML="   ";
        //必須項目設定
        if(document.userForm.userName.value==""){flag=1;}
        if(document.userForm.password.value==""){flag=1;}
        if(flag){
            msg.innerHTML="ユーザ名とパスワードを両方入力ください";
            return false;
        }else{
            return true;
        }
    }

</script>

</body></html>
