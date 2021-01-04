<%@page contentType="text/html;charset=utf-8" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>系统登录</title>
    <link rel="stylesheet" href="layui/css/layui.css">
    <script type="text/javascript" src="layui/layui.js"></script>
    <style>
        #login {
            width: 800px;
            margin: 150px auto;
            height: 500px;
            position: relative;
        }

        #username {
            position: absolute;
            left: 50%;
            margin-left: -125px;
            margin-top: 30px;
            float: left;
        }

        #password {
            position: absolute;
            left: 50%;
            margin-left: -125px;
            margin-top: 90px;
            float: left;
        }

        #btn {
            position: absolute;
            left: 50%;
            margin-left: -235px;
            margin-top: 150px;
            float: left;
        }

        #submit {
            width: 250px;
        }
    </style>
</head>
<body>
<div id="login">
    <div style="text-align: center">
        <h1 style="color: #009688">用户登录</h1>
    </div>
    <form class="layui-form" action="main.do?action=login" method="post">
        <div class="layui-form-item" id="username">
            <div class="layui-input-inline">
                <input class="layui-input" type="text" name="username" placeholder="请输入用户名" required
                       lay-verify="required" style="width: 250px">
            </div>
        </div>
        <div class="layui-form-item inner" id="password">
            <div class="layui-input-inline">
                <input class="layui-input" type="password" name="password" placeholder="请输入密码" required
                       lay-verify="required" style="width: 250px">
            </div>
        </div>
        <div class="layui-form-item" id="btn">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo" id="submit">登录</button>
            </div>
        </div>
    </form>
</div>
</body>
<script>
    layui.use(['form'], function () {
        let from = layui.form;
    });
</script>
</html>
