<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
</head>
<body>
    <h1>登录页面</h1>
    <form method="post" action="${pageContext.request.contextPath}/login">
        登录名：<input type="text" name="username"/><br/>
        密码  ：<input type="password" name="password"/><br/>
        <input type="submit" value="登录"/><a href="${pageContext.request.contextPath}/register.jsp">注册</a>
    </form>
</body>
</html>
