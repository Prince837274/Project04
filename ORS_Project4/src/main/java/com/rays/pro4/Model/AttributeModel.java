package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.AttributeBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class AttributeModel {

	public Integer nextPk() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_attribute");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			pk = rs.getInt(1);
		}
		JDBCDataSource.closeConnection(conn);

		return pk + 1;

	}

	public long add(AttributeBean bean) throws Exception {

		AttributeBean existBean = findBydisplayName(bean.getDisplayName());

		if (existBean != null) {
			throw new DuplicateRecordException("designation already exist..!!");
		}
		int pk = nextPk();

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("insert into st_attribute values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

		pstmt.setLong(1, pk);
		pstmt.setString(2, bean.getDisplayName());
		pstmt.setString(3, bean.getDataType());
		pstmt.setString(4, bean.getIsActive());
		pstmt.setString(5, bean.getDescription());
		pstmt.setString(6, bean.getCreatedBy());
		pstmt.setString(7, bean.getModifiedBy());
		pstmt.setTimestamp(8, bean.getCreatedDatetime());
		pstmt.setTimestamp(9, bean.getModifiedDatetime());

		int i = pstmt.executeUpdate();

		JDBCDataSource.closeConnection(conn);

		System.out.println("data inserted => " + i);
		return pk;
	}

	public void update(AttributeBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(
				"update st_attribute set display_name = ?, data_type = ?, is_active = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
		pstmt.setString(1, bean.getDisplayName());
		pstmt.setString(2, bean.getDataType());
		pstmt.setString(3, bean.getIsActive());
		pstmt.setString(4, bean.getDescription());
		pstmt.setString(5, bean.getCreatedBy());
		pstmt.setString(6, bean.getModifiedBy());
		pstmt.setTimestamp(7, bean.getCreatedDatetime());
		pstmt.setTimestamp(8, bean.getModifiedDatetime());
		pstmt.setLong(9, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("Data updated" + i);

	}

	public void delete(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("delete from st_attribute where id =?");

		pstmt.setLong(1, id);

		int i = pstmt.executeUpdate();

		pstmt.executeUpdate();

		JDBCDataSource.closeConnection(conn);

		System.out.println("data deleted => " + i);
	}

	public AttributeBean findByPk(long id) throws Exception {

		Connection conn = null;
		AttributeBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_attribute where id=?");
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AttributeBean();
				bean.setId(rs.getLong(1));
				bean.setDisplayName(rs.getString(2));
				bean.setDataType(rs.getString(3));
				bean.setIsActive(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Attribute by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;

	}

	public AttributeBean findBydisplayName(String displayName) throws Exception {

		Connection conn = null;
		AttributeBean bean = null;

		conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt;

		pstmt = conn.prepareStatement("select * from st_attribute where display_name =?");

		pstmt.setString(1, displayName);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			bean = new AttributeBean();
			bean.setId(rs.getLong(1));
			bean.setDisplayName(rs.getString(2));
			bean.setDataType(rs.getString(3));
			bean.setIsActive(rs.getString(4));
			bean.setDescription(rs.getString(5));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreatedDatetime(rs.getTimestamp(8));
			bean.setModifiedDatetime(rs.getTimestamp(9));

		}

		JDBCDataSource.closeConnection(conn);
		return bean;

	}

	public List list() throws Exception {
		return search(null, 0, 0);
	}
	public List search(AttributeBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_attribute where 1=1");

		if (bean != null) {
			if (bean.getDisplayName() != null && bean.getDisplayName().length() > 0) {
				sql.append(" and display_name like '" + bean.getDisplayName() + "%'");
			}
			if (bean.getIsActive() != null && bean.getIsActive().length() > 0) {
				sql.append(" and is_active like '" + bean.getIsActive() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		System.out.println("sql ==>> " + sql.toString());

		Connection conn = null;
		List list = new ArrayList();

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AttributeBean();
				bean.setId(rs.getLong(1));
				bean.setDisplayName(rs.getString(2));
				bean.setDataType(rs.getString(3));
				bean.setIsActive(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search position " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
