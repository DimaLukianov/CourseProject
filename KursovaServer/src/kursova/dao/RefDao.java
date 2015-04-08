package kursova.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import kursova.model.Ref;

public class RefDao {

	private static final String INSERT_QUERY = "insert into ref "
			+ "(software_id, licence_id) values (?, ?)";
	private static final String UPDATE_QUERY = "update ref "
			+ "set software_id = ?, licence_id = ? "
			+ "where ref_id = ?";
	private static final String DELETE_QUERY = "delete from ref where ref_id = ?";
	private static final String SELECT_QUERY = "select ref_id, software_id, licence_id "
			+ "from ref where ref_id = ?";
	private static final String SELECT_ALL_QUERY = "select ref_id, software_id, licence_id from ref ";

	public int insertRef(Ref ref) throws Exception {

		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
				Statement.RETURN_GENERATED_KEYS);

		try {
			statement.setInt(1, ref.getSoftwareId());
			statement.setInt(2, ref.getLicenceId());
			statement.executeUpdate();

			return DataAccessUtil.getNewRowKey(statement);
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void updateRef(Ref ref) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

		try {
			statement.setInt(1, ref.getSoftwareId());
			statement.setInt(2, ref.getLicenceId());
			
			statement.setInt(3, ref.getRefId());

			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void deleteRef(int refId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

		try {
			statement.setInt(1, refId);
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public Ref findById(int refId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, refId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return getRefFromRow(rs);
			}
			return null;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<Ref> findAll() throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection
				.prepareStatement(SELECT_ALL_QUERY);

		try {
			ResultSet rs = statement.executeQuery();
			List<Ref> result = new ArrayList<Ref>();
			while (rs.next()) {
				result.add(getRefFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	private static Ref getRefFromRow(ResultSet rs) throws Exception {
		Ref r = new Ref();
		r.setRefId(rs.getInt(1));
		r.setSoftwareId(rs.getInt(2));
		r.setLicenceId(rs.getInt(3));
		
		return r;
	}
	
}
