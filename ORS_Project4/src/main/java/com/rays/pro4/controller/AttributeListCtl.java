package com.rays.pro4.controller;

	import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.AttributeBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Model.AttributeModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

	@WebServlet(name ="AttributeListCtl", urlPatterns = {"/ctl/AttributeListCtl"})
	public class AttributeListCtl extends BaseCtl {
		
		
		@Override
		protected BaseBean populateBean(HttpServletRequest request) {
			AttributeBean bean = new AttributeBean();
			bean.setDisplayName(DataUtility.getString(request.getParameter("displayName")));
			bean.setIsActive(DataUtility.getString(request.getParameter("isActive")));
			return bean;
		}
		
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			List<AttributeBean> list = null;
			List<AttributeBean> next = null;

			int pageNo = 1;
			int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

			AttributeBean bean = (AttributeBean) populateBean(request);

			AttributeModel model = new AttributeModel();

			try {
				list = model.search(bean, pageNo, pageSize);
				next = model.search(bean, pageNo + 1, pageSize);
				request.setAttribute("nextListSize", (next != null) ? next.size() : 0);
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			

			List<AttributeBean> list = null;
			List<AttributeBean> next = null;
			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

			pageNo = (pageNo == 0) ? 1 : pageNo;
			pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

			AttributeBean bean = (AttributeBean) populateBean(request);
			String op = DataUtility.getString(request.getParameter("operation"));
			String[] ids = request.getParameterValues("ids");

			AttributeModel model = new AttributeModel();
			try {
			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;
			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ATTRIBUTE_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					for (String id : ids) {
						
							model.delete(DataUtility.getLong(id));
					}
					ServletUtility.setSuccessMessage("Data is deleted successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ATTRIBUTE_LIST_CTL, request, response);
				return;
			}

			ServletUtility.setBean(bean, request);

				list = model.search(bean, pageNo, pageSize);
				next = model.search(bean, pageNo + 1, pageSize);

				if (!OP_DELETE.equalsIgnoreCase(op) && (list == null || list.size() == 0)) {
					ServletUtility.setErrorMessage("No record found", request);
				}

				request.setAttribute("nextListSize", next.size());
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected String getView() {
			return ORSView.ATTRIBUTE_LIST_VIEW;
		}

	}



