<!-- webapps/ROOT/index.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <title>Embedded Tomcat JSP</title>
    </head>

    <body>
        <h1>Hello from JSP!</h1>
        <p>Current time: <%= new java.util.Date() %>
        </p>

        <form action="/product" method="post">
            Name: <input type="text" name="name" />
            Price: <input type="text" name="price" />
            <input type="submit" value="Submit" />
        </form>
    </body>

    </html>