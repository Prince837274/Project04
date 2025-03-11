<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.controller.AttributeListCtl"%>
<%@page import="com.rays.pro4.Bean.AttributeBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.ORSView"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Attribute List</title>
</head>
<body>
       <%@ include file="Header.jsp" %>
    <div align="center">
        <form action="<%= ORSView.ATTRIBUTE_LIST_CTL%>" method="post">

            <jsp:useBean id="bean" class="com.rays.pro4.Bean.AttributeBean" scope="request"></jsp:useBean>

            <div align="center">
                <h1><font color="navy">Attribute List </font></h1>
            </div>

            <div align="center" style="height: 15px; margin-bottom: 12px">
                <h3><font color="red"><%= ServletUtility.getErrorMessage(request) %></font></h3>
                <h3><font color="green"><%= ServletUtility.getSuccessMessage(request) %></font></h3>
            </div>
           

            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                
                int nextPageSize = request.getAttribute("nextListSize") != null 
                    ? DataUtility.getInt(request.getAttribute("nextListSize").toString()) 
                    : 0;
                
                List list = ServletUtility.getList(request);
                if (list != null && !list.isEmpty()) {
                    Iterator it = list.iterator();
            %>

            <input type="hidden" name="pageNo" value="<%= pageNo %>">
            <input type="hidden" name="pageSize" value="<%= pageSize %>">

            <table style="width: 100%">
                <tr>
                    <td align="center">
                        <label><b>Display Name:</b></label>
                        <input type="text" name="displayName" value="<%= ServletUtility.getParameter("displayName", request) %>">&nbsp;
                        <label><b>Is Active:</b></label>
                        <%
							HashMap map1 = new HashMap();
							map1.put("Yes", "Yes");
							map1.put("No", "No");
						%> <%=HTMLUtility.getList("isActive",String.valueOf(bean.getIsActive()), map1)%>
				
                        <input type="submit" name="operation" value="<%= AttributeListCtl.OP_SEARCH %>"> &nbsp;
                        <input type="submit" name="operation" value="<%= AttributeListCtl.OP_RESET %>">
                    </td>
                </tr>
            </table>

            <br>

            <table border="1" style="width: 100%">
                <tr style="background-color: lavender; color: black;">
                    <th><input type="checkbox" id="selectall">
					</th>
                    <th>S.No.</th>
                    <th>Display Name</th>
                    <th>Data Type</th>
                    <th>Is Active</th>
                    <th>Description</th>
                    <th>Edit</th>
                </tr>
                <%
                    while (it.hasNext()) {
                        AttributeBean attributeBean = (AttributeBean) it.next();
                %>
                <tr align="center">
                    <td><input type="checkbox" class="case" name="ids" value="<%=attributeBean.getId() %>"></td>
                    <td><%= index++ %></td>
                    <td><%= attributeBean.getDisplayName() %></td>
                    <td><%= attributeBean.getDataType() %></td>
                    <td><%= attributeBean.getIsActive() %></td>
                    <td><%= attributeBean.getDescription() %></td>
                    <td><a href="<%= ORSView.ATTRIBUTE_CTL %>?id=<%= attributeBean.getId() %>">edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>

            <br>

            <table style="width: 100%">
                <tr>
                    <td style="width: 30%">
                        <input type="submit" name="operation" value="<%= AttributeListCtl.OP_PREVIOUS %>" 
                               <%= (pageNo == 1) ? "disabled" : "" %>>
                    </td>
                    <td style="width: 30%">
                        <input type="submit" name="operation" value="<%= AttributeListCtl.OP_NEW %>">
                    </td>
                    <td style="width: 25%">
                        <input type="submit" name="operation" value="<%= AttributeListCtl.OP_DELETE %>">
                    </td>
                    <td style="text-align: right;">
                        <input type="submit" name="operation" value="<%= AttributeListCtl.OP_NEXT %>" 
                               <%= (nextPageSize != 0) ? "" : "disabled" %>>
                    </td>
                </tr>
            </table>
            <%
                } else { 
            %>
            <br>
            <table>
                <tr>
                    <td align="right"><input type="submit" name="operation" value="<%= AttributeListCtl.OP_BACK %>"></td>
                </tr>
            </table>
            <% } %>
        </form>
    </div>
    <%@ include file="Footer.jsp" %>

</body>
</html>
