<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:useBean id="product" class="com.example.ProductBean" scope="request" />
    <html>

    <head>
        <title>Product Record</title>
    </head>

    <body>

        <h1>Product Record</h1>
        <div>Product Name:
            <jsp:getProperty name="product" property="name" />
        </div>
        <div>Product Price:
            <jsp:getProperty name="product" property="price" />
        </div>

    </body>

    </html>