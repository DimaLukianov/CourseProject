package kursova.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import kursova.model.Licence;

public class LicenceDao {
	
	private static final String INSERT_QUERY = "insert into licence "
			+ "(licence_name, licence_type, licence_period, licence_price) values (?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "update licence "
			+ "set licence_name = ?, licence_type = ?, licence_period = ?, licence_price = ? "
			+ "where licence_id = ?";
	private static final String DELETE_QUERY = "delete from licence where licence_id = ?";
	private static final String SELECT_QUERY = "select licence_id, licence_name, licence_type, licence_period, licence_price "
			+ "from licence where licence_id = ?";
	private static final String SELECT_ALL_QUERY = "select licence_id, licence_name, licence_type, licence_period, licence_price  from licence ";

	public int insertLicence(Licence licence) throws Exception {

		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
				Statement.RETURN_GENERATED_KEYS);

		try {
			statement.setString(1, licence.getName());
			statement.setString(2, licence.getType());
			statement.setInt(3, licence.getPeriod());
			statement.setDouble(4, licence.getPrice());
			statement.executeUpdate();

			return DataAccessUtil.getNewRowKey(statement);
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void updateLicence(Licence licence) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

		try {
			statement.setString(1, licence.getName());
			statement.setString(2, licence.getType());
			statement.setInt(3, licence.getPeriod());
			statement.setDouble(4, licence.getPrice());
			statement.setInt(5, licence.getLicenceId());

			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void deleteLicence(int licenceId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

		try {
			statement.setInt(1, licenceId);
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public Licence findById(int licenceId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, licenceId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return getLicenceFromRow(rs);
			}
			return null;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<Licence> findAll() throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection
				.prepareStatement(SELECT_ALL_QUERY);

		try {
			ResultSet rs = statement.executeQuery();
			List<Licence> result = new ArrayList<Licence>();
			while (rs.next()) {
				result.add(getLicenceFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	private static Licence getLicenceFromRow(ResultSet rs) throws Exception {
		Licence l = new Licence();
		l.setLicenceId(rs.getInt(1));
		l.setName(rs.getString(2));
		l.setType(rs.getString(3));
		l.setPeriod(rs.getInt(4));
		l.setPrice(rs.getDouble(5));

		return l;
	}

}
