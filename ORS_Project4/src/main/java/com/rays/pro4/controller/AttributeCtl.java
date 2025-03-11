package com.rays.pro4.controller;

	import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.AttributeBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.AttributeModel;
import com.rays.pro4.Model.UserModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

	@WebServlet(name = "AttributeCtl", urlPatterns = { "/ctl/AttributeCtl" })
	public class AttributeCtl extends BaseCtl {

		@Override
		protected boolean validate(HttpServletRequest request) {

			boolean pass = true;

			if (DataValidator.isNull(request.getParameter("displayName"))) {
				request.setAttribute("displayName", PropertyReader.getValue("error.require", "Display Name"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("dataType"))) {
				request.setAttribute("dataType", PropertyReader.getValue("error.require", "Data Type"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("isActive"))) {
				request.setAttribute("isActive", PropertyReader.getValue("error.require", "Select Option"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("description"))) {
				request.setAttribute("description", PropertyReader.getValue("error.require", "Short Description"));
				pass = false;
			}
			return pass;
		}

		@Override
		protected BaseBean populateBean(HttpServletRequest request) {

			AttributeBean bean = new AttributeBean();
			bean.setId(DataUtility.getLong(request.getParameter("id")));
			bean.setDisplayName(DataUtility.getString(request.getParameter("displayName")));
			bean.setDataType(DataUtility.getString(request.getParameter("dataType")));
			bean.setIsActive(DataUtility.getString(request.getParameter("isActive")));
			bean.setDescription(DataUtility.getString(request.getParameter("description")));
			populateDTO(bean, request);
			return bean;
		}

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String op = DataUtility.getString(request.getParameter("operation"));
			Long id = DataUtility.getLong(request.getParameter("id"));

			if (id > 0) {

				AttributeModel model = new AttributeModel();

				try {
					AttributeBean bean = model.findByPk(id);
					ServletUtility.setBean(bean, request);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ServletUtility.forward(getView(), request, response);
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			System.out.println("uctl Do Post");

			String op = DataUtility.getString(request.getParameter("operation"));
			long id = DataUtility.getLong(request.getParameter("id"));

			System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		AttributeModel model = new AttributeModel();
			if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
				AttributeBean bean = (AttributeBean) populateBean(request);
				System.out.println(" U ctl DoPost 11111111");

				try {
					if (id > 0) {

						// System.out.println("hi i am in dopost update");
						model.update(bean);
						ServletUtility.setBean(bean, request);
						System.out.println(" U ctl DoPost 222222");
						ServletUtility.setSuccessMessage("Attribute is successfully Updated", request);

					} else {
						System.out.println(" U ctl DoPost 33333");
						long pk = model.add(bean);
						 bean.setId(pk);
//						 ServletUtility.setBean(bean, request);

						ServletUtility.setSuccessMessage("Attribute is successfully Added", request);
						//ServletUtility.forward(getView(), request, response);
						bean.setId(pk);
					}
					/*
					 * ServletUtility.setBean(bean, request);
					 * ServletUtility.setSuccessMessage("User is successfully saved", request);
					 */

				} catch (ApplicationException e) {
				
					ServletUtility.handleException(e, request, response);
					return;
				} catch (DuplicateRecordException e) {
//					System.out.println(" U ctl D post 4444444");
//					ServletUtility.setBean(bean, request);
//					ServletUtility.setErrorMessage("Login id already exists", request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}  else if (OP_CANCEL.equalsIgnoreCase(op)) {
				System.out.println(" U  ctl Do post 77777");

				ServletUtility.redirect(ORSView.ATTRIBUTE_LIST_CTL, request, response);
				return;
			}
			
			ServletUtility.forward(getView(), request, response);


		}
		@Override
		protected String getView() {
			return ORSView.ATTRIBUTE_VIEW;
		}

	}



