<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome Page</title>
    </head>
    <body>
        <% if (request.getAttribute("student") !=null) { %>
            <h1>Welcome ${student.givenNames} ${student.lastName}</h1>
            <div>You are now logged in! </div>
        <% } else { %>
                <h1>No student record found.</h1>
        <% } %>
    </body>
</html>
