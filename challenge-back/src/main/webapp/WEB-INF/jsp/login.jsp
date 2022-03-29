<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <body>
        <script src="/js/jQuery-3.5.1.js"></script>
        <link rel="stylesheet" href="/css/bootstrap.min.css">
        <link rel="stylesheet" href="/css/login.css">

        <div class="login">
            <h1>Login</h1>
            <form action="${login_path}" method='POST'>
            <input type="text" name="username" placeholder="Username" id="user" />
            <input type="password" name="password" placeholder="Password" id="password" />
            <input name="submit" type="submit" value="submit" />
            <button name="button" type="button" onclick="location.href='/register'" >Register</button>
            </form>
            <p style="color:white" id="err_msg" hidden>Error occured! Try again.</p>

        </div>
        <script>
            var url_string =  window.location;
            var url = new URL(url_string);
            var err = url.searchParams.get("err");
            if (err!=null || err!= undefined){
                err_msg_el=$("#err_msg")
                err_msg_el.attr("hidden",false)
            }
        </script>
    </body>
</html>