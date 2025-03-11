<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.AttributeCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=ORSView.ATTRIBUTE_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.rays.pro4.Bean.AttributeBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1>

				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<tr>
					<th><font size="5px"> Update Attribute</font></th>
				</tr>
				<%
					} else {
				%>
				<tr>
					<th><font size="5px"> Add Attribute</font></th>
				</tr>
				<%
					}
				%>
			</h1>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>

		</div>

		<div align="center">

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="left">Display Name<span style="color: red">*</span></th>
					<td><input type="text" name="displayName" style="width: 203px"
						placeholder="Enter Display Name" 
						value="<%=DataUtility.getStringData(bean.getDisplayName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("displayName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Data Type<span style="color: red">*</span></th>
					<td><input type="text" name="dataType" style="width: 203px"
						placeholder="Enter Data Type"
						value="<%=DataUtility.getStringData(bean.getDataType())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dataType", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Is Active<span style="color: red">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Yes", "Yes");
							map.put("No", "No");
						%> <%=HTMLUtility.getList("isActive", bean.getIsActive(), map)%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("isActive", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Description<span style="color: red">*</span></th>
					<td align="center"><textarea
							style="width: 203px; resize: none;" name="description" rows="3"
							placeholder="Enter Short description">
							<%
								if (bean != null && bean.getId() > 0) {
							%><%=DataUtility.getStringData(bean.getDescription())%>
							<%
								}
							%>
						</textarea></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				
                <tr ><th></th>
                <%
                if(bean.getId()>0){
                %>
                <td colspan="2" >
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=AttributeCtl.OP_UPDATE%>">
                      &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=AttributeCtl.OP_CANCEL%>"></td>
                
                <% }else{%>
                
                <td colspan="2" > 
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=AttributeCtl.OP_SAVE%>">
                    &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=AttributeCtl.OP_RESET%>"></td>
                
                <% } %>
                </tr>
			</table>
		</div>
	</form>
</body>
</html>