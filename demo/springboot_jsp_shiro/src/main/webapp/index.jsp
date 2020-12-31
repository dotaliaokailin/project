<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
    <h1>首页</h1>
    <a href="${pageContext.request.contextPath}/logout">退出登录</a><br/>
    <ul>
        <shiro:hasAnyRoles name="admin,user">
            <li>
                <a href="">用户管理</a>
                <ul>
                    <shiro:hasPermission name="user:create:*">
                        <li><a href="">添加</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:update:*">
                        <li><a href="">修改</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:delete:*">
                        <li><a href="">删除</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:select:*">
                        <li><a href="">查询</a></li>
                    </shiro:hasPermission>
                </ul>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasRole name="admin">
           <li><a href="">商品管理</a></li>
           <li><a href="">物流管理</a></li>
           <li><a href="">菜单管理</a></li>
           <li><a href="">权限管理</a></li>
        </shiro:hasRole>
    </ul>
</body>
</html>
