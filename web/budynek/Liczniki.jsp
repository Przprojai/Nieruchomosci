<%-- 
    Document   : newjsp
    Created on : Apr 3, 2017, 11:46:45 AM
    Author     : Waldek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%= request.getAttribute("Name").toString() %>
       <%= JCP.BudynekController.setCsvFileLicznik(request.getAttribute("Name").toString()) %>
        
     
<jsp:forward page="/budynek/List_1.xhtml?faces-redirect=true"></jsp:forward>
    </body>
</html>
