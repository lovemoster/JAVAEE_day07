<%@ page import="com.powernode.pojo.Emp" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <title>用户管理</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <script src="../layui/layui.js" type="text/javascript"></script>
    <style>
        #box {
            width: 850px;
            margin: 100px auto;
        }

        .btn-class {
            width: 45px;
        }

        #pages-plugin {
            text-align: right;
        }

        form {
            text-align: right;
        }
    </style>
</head>
<body>
<div id="box">
    <div>
        <span style="margin-left: 50px"><strong>欢迎：</strong>${sessionScope.username}</span>
        <a href="add.jsp">
            <button type="button" class="layui-btn layui-btn-primary layui-btn-sm layui-btn-radius"
                    style="margin-left: 600px; display: inline">添加用户信息
            </button>
        </a>
    </div>
    <div style="margin-top: 10px; padding-left: 50px; padding-right: 20px">
        <form action="../main.do" id="getData" method="get">
            <input type="hidden" name="action" value="getData">
            <input type="hidden" id="current_page" name="current_page" value="">
            <input type="hidden" id="limit_page" name="limit_page" value="">
            <label>
                姓名：<input type="text" class="layui-input" style="width: 150px;display: inline" name="name" value=""
                          id="name">
            </label>
            <label>
                年龄：<input type="text" class="layui-input" style="width: 150px;display: inline" name="age" value=""
                          id="age">
            </label>
            <button type="button" class="layui-btn  layui-btn-sm layui-btn-radius" style=" display: inline; width: 95px"
                    onclick="getData();">查询
            </button>
        </form>

    </div>


    <table class="layui-table">
        <colgroup>
            <col width="120">
            <col width="120">
            <col width="120">
            <col width="150">
            <col width="120">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>生日</th>
            <th>性别</th>
            <th>所属部门</th>
            <th>操作</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="emp" items="${sessionScope.list}">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.name}</td>
                <td>${emp.age}</td>
                <td>${emp.date}</td>
                <td>
                    <c:choose>
                        <c:when test="${emp.sex == 1}">
                            男
                        </c:when>
                        <c:otherwise>
                            女
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:forEach var="deptins" items="${sessionScope.dept}">
                        <c:if test="${deptins.deptID == emp.deptID}">
                            ${deptins.deptName}
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <a href="edit.jsp?id=${emp.id}">
                        <button type="button"
                                class="layui-btn layui-btn-sm layui-btn-radius layui-btn-normal btn-class">修改
                        </button>
                    </a>
                    <a href="../main.do?action=remove&id=${emp.id}">
                        <button type="button"
                                class="layui-btn layui-btn-sm layui-btn-radius layui-btn-danger btn-class">删除
                        </button>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div id="pages-plugin">

    </div>

</div>
</body>
<script>
    let current_page = null;
    let limit_page = null;

    layui.use('laypage', function () {
        let laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'pages-plugin',//注意，这里的 test1 是 ID，不用加 # 号
            count: <c:choose>
                <c:when test="${empty sessionScope.total}">1</c:when>
            <c:otherwise>${sessionScope.total}</c:otherwise>
            </c:choose>, //数据总数，从服务端得到
            limit: <c:choose>
                <c:when test="${empty sessionScope.limit}">10</c:when>
            <c:otherwise>${sessionScope.limit}</c:otherwise>
            </c:choose>,
            limits: [5, 10, 15, 20],
            prev: '上一页',
            next: '下一页',
            first: '首页',
            last: '尾页',
            layout: ['page', 'limit', 'next'],
            curr: <c:choose>
                <c:when test="${empty sessionScope.current}">1</c:when>
            <c:otherwise>${sessionScope.current}</c:otherwise>
            </c:choose>,
            jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                current_page = obj.curr; //得到当前页，以便向服务端请求对应页的数据。
                limit_page = obj.limit; //得到每页显示的条数
                if (!first) {
                    getData();
                }
            }
        });
    });

    function getData() {
        document.getElementById("current_page").setAttribute("value", current_page)
        document.getElementById("limit_page").setAttribute("value", limit_page)
        document.getElementById("getData").submit();
    }


    <c:choose>
    <c:when test="${empty sessionScope.name}">
    document.getElementById("name").setAttribute("value", "")
    </c:when>
    <c:otherwise>
    document.getElementById("name").setAttribute("value", "${sessionScope.name}")
    </c:otherwise>
    </c:choose>

    <c:choose>
    <c:when test="${empty sessionScope.eage}">
    document.getElementById("age").setAttribute("value", "")
    </c:when>
    <c:otherwise>
    document.getElementById("age").setAttribute("value", "${sessionScope.eage}")
    </c:otherwise>
    </c:choose>
</script>
</html>
