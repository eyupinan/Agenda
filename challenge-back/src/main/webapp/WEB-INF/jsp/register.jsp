
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <body>
        <script src="/js/jQuery-3.5.1.js"></script>
        <link rel="stylesheet" href="/css/bootstrap.min.css">
        <link rel="stylesheet" href="/css/login.css">

        <div class="login">
            <h1>Register</h1>
            <form action="${user_create_path}" method='POST'>
            <input type="text" name="username" placeholder="Username" id="username" />
            <input type="text" name="phone" placeholder="Phone" id="phone" />
            <textarea name="address" placeholder="address" id="address" style="height:80px"></textarea>
            <input type="email" name="email" placeholder="Email" id="text" />
            <input type="password" name="password" placeholder="Password" id="password" />
            <input name="submit" type="submit" value="submit" /></form>
            <p style="color:white" id="err_msg" hidden></p>
        </div>
        <script>
            var url_string =  window.location;
            var url = new URL(url_string);
            var err = url.searchParams.get("err");
            if (err!=null || err!= undefined){
                err_msg_el=$("#err_msg")
                err_msg_el.attr("hidden",false)
                err_msg_el.html("Error occured! Try again or change username")
            }
        </script>
    </body>
</html>
