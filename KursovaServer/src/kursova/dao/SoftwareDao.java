package kursova.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import kursova.model.Software;

public class SoftwareDao {
	
	private static final String INSERT_QUERY = "insert into software "
			+ "(software_name, software_icon, version, os_windows, os_unix, os_mac, release_date, producer_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "update software "
			+ "set software_name = ?, software_icon = ?, version = ?, os_windows = ?, os_unix = ?, os_mac = ?, release_date = ?, producer_id = ? "
			+ "where software_id = ?";
	private static final String DELETE_QUERY = "delete from software where software_id = ?";
	private static final String SELECT_QUERY = "select software_id, software_name, software_icon, version, os_windows, os_unix, os_mac, release_date, producer_id "
			+ "from software where software_id = ?";
	private static final String SELECT_ALL_QUERY = "select software_id, software_name, software_icon, version, os_windows, os_unix, os_mac, release_date, producer_id from software ";
	private static final String SELECT_ALL_WHERE_PRODUCER_ID_QUERY = "select software_id, software_name, software_icon, version, os_windows, os_unix, os_mac, release_date, producer_id from software "
			+"WHERE producer_id=?";
	
	public int insertSoftware(Software software) throws Exception {
		
		System.out.println("INSERT software '"+software.getSoftwareId()+"'");
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
				Statement.RETURN_GENERATED_KEYS);

		try {
			statement.setString(1, software.getName());
			statement.setString(2, software.getIconPath());
			statement.setString(3, software.getVersion());
			statement.setBoolean(4, software.isOsWindows());
			statement.setBoolean(5, software.isOsUnix());
			statement.setBoolean(6, software.isOsMac());
			statement.setString(7, software.getReleaseDate());
			statement.setInt(8, software.getProducerId());
			statement.executeUpdate();

			return DataAccessUtil.getNewRowKey(statement);
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void updateSoftware(Software software) throws Exception {
		System.out.println("UPDATE software with id "+software.getSoftwareId());
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

		try {
			statement.setString(1, software.getName());
			statement.setString(2, software.getIconPath());
			statement.setString(3, software.getVersion());
			statement.setBoolean(4, software.isOsWindows());
			statement.setBoolean(5, software.isOsUnix());
			statement.setBoolean(6, software.isOsMac());
			statement.setString(7, software.getReleaseDate());
			statement.setInt(8, software.getProducerId());
			statement.setInt(9, software.getSoftwareId());

			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void deleteSoftware(int softwareId) throws Exception {
		System.out.println("DELETE software with id "+softwareId);
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

		try {
			statement.setInt(1, softwareId);
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public Software findById(int softwareId) throws Exception {
		System.out.println("Find software with id "+softwareId);
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, softwareId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return getSoftwareFromRow(rs);
			}
			return null;
		} finally {
			DataAccessUtil.close(connection);
		}
	}
	
	public List<Software> findAllByProducerId(Integer producer_id) throws Exception {
		System.out.println("SELECT all software WHERE producer_id="+producer_id);
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection
				.prepareStatement(SELECT_ALL_WHERE_PRODUCER_ID_QUERY);

		try {
			statement.setInt(1, producer_id);
			ResultSet rs = statement.executeQuery();
			List<Software> result = new ArrayList<Software>();
			while (rs.next()) {
				result.add(getSoftwareFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<Software> findAll() throws Exception {
		System.out.println("SELECT all software");
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection
				.prepareStatement(SELECT_ALL_QUERY);

		try {
			ResultSet rs = statement.executeQuery();
			List<Software> result = new ArrayList<Software>();
			while (rs.next()) {
				result.add(getSoftwareFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	private static Software getSoftwareFromRow(ResultSet rs) throws Exception {
		Software s = new Software();
		s.setSoftwareId(rs.getInt(1));
		s.setName(rs.getString(2));
		s.setIconPath(rs.getString(3));
		s.setVersion(rs.getString(4));
		s.setOsWindows(rs.getBoolean(5));
		s.setOsUnix(rs.getBoolean(6));
		s.setOsMac(rs.getBoolean(7));
		s.setReleaseDate(rs.getString(8));
		s.setProducerId(rs.getInt(9));

		return s;
	}

}
