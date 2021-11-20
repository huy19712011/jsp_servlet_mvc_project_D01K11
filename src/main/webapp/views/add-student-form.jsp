<%-- 
    Document   : add-student-form
    Created on : Nov 20, 2021, 8:09:55 PM
    Author     : huynq
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="container">
            <h3>Add Student</h3>
            
            <form action="../StudentControllerServlet" method="GET">
                
                <input type="hidden" name="command" value="ADD"/>
                
                <table>
                    <tbody>
                        <tr>
                            <td><label>First name:</label></td>
                            <td><input type="text" name="firstName"/></td>
                        </tr>
                        <tr>
                            <td><label>Last name:</label></td>
                            <td><input type="text" name="lastName"/></td>
                        </tr>
                        <tr>
                            <td><label>Email:</label></td>
                            <td><input type="text" name="email"/></td>
                        </tr>
                        
                        <tr>
                            <td><label></label></td>
                            <td><input type="submit" value="Save" class="save"/></td>
                        </tr>
                    </tbody>
                </table>
                
            </form>
            
        </div>
    </body>
</html>
