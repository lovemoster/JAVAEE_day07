<%@ page import="com.powernode.pojo.Emp" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    String id = request.getParameter("id");
    List<Emp> emps = (List<Emp>) session.getAttribute("list");
    Emp semp = null;
    for (Emp emp : emps) {
        if (emp.getId() == Integer.parseInt(id)) {
            semp = emp;
        }
    }
    pageContext.setAttribute("emp", semp);
%>
<html>
<head>
    <title>编辑员工</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <script type="text/javascript" src="../layui/layui.js"></script>
    <style>
        #login {
            width: 800px;
            margin: 150px auto;
            height: 500px;
            position: relative;
        }

        #btn {
            position: absolute;
            left: 50%;
            margin-left: -235px;
            margin-top: 25px;
            float: left;
        }

        .test {
            margin-left: 275px;
            margin-top: 25px;
        }

        .layui-form-item .layui-input-inline {
            width: 250px;
        }
    </style>
</head>
<body>
<div id="login">
    <div style="text-align: center">
        <h1 style="color: #009688">编辑员工</h1>
    </div>
    <form class="layui-form" action="../main.do?action=edit&id=${emp.id}" method="post">
        <div class="test">
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <input class="layui-input" value="${emp.name}" type="text" name="name" placeholder="请输入姓名" required
                           lay-verify="required" style="width: 250px">
                </div>
            </div>
            <div class="layui-form-item inner">
                <div class="layui-input-inline">
                    <input class="layui-input" value="${emp.age}" type="text" name="age" placeholder="请输入年龄" required
                           lay-verify="required" style="width: 250px">
                </div>
            </div>
            <div class="layui-form-item inner">
                <div class="layui-input-inline">
                    <input type="text" name="birthday" value="${emp.date}" class="layui-input inner" id="date"
                           placeholder="请输入生日" style="width: 250px">
                </div>
            </div>
            <div class="layui-form-item inner">
                <div class="layui-input-inline">
                    <select name="deptID" lay-verify="required" style="width: 250px">
                        <option value="">请选择部门</option>
                        <td>
                            <c:forEach items="${sessionScope.dept}" var="dept">
                                <option value="${dept.deptID}"
                                        <c:if test="${dept.deptID == emp.deptID}">
                                            selected
                                        </c:if>
                                >${dept.deptName}</option>
                            </c:forEach>
                        </td>
                    </select>
                </div>
            </div>
            <div class="layui-form-item inner">
                <label class="layui-form-label" style="width: 35px">性别</label>
                <div class="layui-input-inline">
                    <input type="radio" name="sex" value="男" title="男"
                           <c:if test="${emp.sex == 1}">checked </c:if>
                    >
                    <input type="radio" name="sex" value="女" title="女"
                           <c:if test="${emp.sex == 0}">checked </c:if>
                    >
                </div>
            </div>
            <div>
                <img id="img" src="" alt="">
                <button type="button" class="layui-btn" id="upload" style="width: 125px">
                    <i class="layui-icon">&#xe67c;</i>上传图片
                </button>
                <input type="hidden" id="filePath" name="filePath" value="">
                <input type="hidden" id="fileHash" name="fileHash" value="">
            </div>
            <div class="layui-form-item" id="btn">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="formDemo" style="margin-left: 20px;width: 125px">
                        修改
                    </button>
                    <button type="reset" class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo"
                            style="margin-left: 20px">重置
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
<script>

    let fileHashByDB = '';
    <c:choose>
    <c:when test="${empty emp.filePath}">
    </c:when>
    <c:otherwise>
    document.getElementById("img").setAttribute("src", "../upload/${emp.filePath}");
    document.getElementById("img").setAttribute("width", "100");
    document.getElementById("img").setAttribute("height", "100");
    document.getElementById("filePath").setAttribute("name", "filePath");
    document.getElementById("filePath").setAttribute("value", '${emp.filePath}');
    document.getElementById("fileHash").setAttribute("name", "fileHash");
    document.getElementById("fileHash").setAttribute("value", '${emp.fileHash}');
    fileHashByDB = '${emp.fileHash}';
    </c:otherwise>
    </c:choose>


    layui.use(['laydate', 'form', 'upload'], function () {
        let laydate = layui.laydate;
        let upload = layui.upload;


        //执行一个laydate实例
        laydate.render({
            elem: '#date' //指定元素
        });

        let uploadInst = upload.render({
            elem: '#upload' //绑定元素
            , url: '../main.do?action=upload' //上传接口
            , done: function (res) {
                if (res.message === 'success') {
                    if (fileHashByDB === '') {
                        document.getElementById("img").setAttribute("src", "../upload/" + res.filePath);
                        document.getElementById("img").setAttribute("width", "100");
                        document.getElementById("img").setAttribute("height", "100");
                        document.getElementById("filePath").setAttribute("name", "filePath");
                        document.getElementById("filePath").setAttribute("value", res.filePath);
                        document.getElementById("fileHash").setAttribute("name", "fileHash");
                        document.getElementById("fileHash").setAttribute("value", res.fileHash);
                    } else if (res.fileHash !== fileHashByDB) {
                        document.getElementById("img").setAttribute("src", "../upload/" + res.filePath);
                        document.getElementById("img").setAttribute("width", "100");
                        document.getElementById("img").setAttribute("height", "100");
                        document.getElementById("filePath").setAttribute("name", "filePath");
                        document.getElementById("filePath").setAttribute("value", res.filePath);
                        document.getElementById("fileHash").setAttribute("name", "fileHash");
                        document.getElementById("fileHash").setAttribute("value", res.fileHash);
                    } else if (res.fileHash === fileHashByDB) {
                        document.getElementById("filePath").setAttribute("name", "filePath");
                        document.getElementById("filePath").setAttribute("value", '${emp.filePath}');
                        document.getElementById("fileHash").setAttribute("name", "fileHash");
                        document.getElementById("fileHash").setAttribute("value", '${emp.fileHash}');
                    }
                }
            }
            , error: function () {
                //请求异常回调
            }
        });
    });
</script>
</html>
