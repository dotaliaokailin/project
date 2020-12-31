<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
</head>
<body>
    <h1>注册页面</h1>
    <form method="post" action="${pageContext.request.contextPath}/register">
        登录名：<input type="text" name="username"/><br/>
        密码  ：<input type="password" name="password"/><br/>
        <input type="submit" value="立即注册"/>
    </form>
</body>
</html>
