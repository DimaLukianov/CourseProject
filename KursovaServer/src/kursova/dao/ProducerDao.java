package kursova.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import kursova.model.Producer;

public class ProducerDao {
	
	private static final String INSERT_QUERY = "insert into producer "
			+ "(producer_name, country, city, street, email, web_site, telephone) values (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "update producer "
			+ "set producer_name = ?, country = ?, city = ?, street = ?, email = ?, web_site = ?, telephone = ? "
			+ "where producer_id = ?";
	private static final String DELETE_QUERY = "delete from producer where producer_id = ?";
	private static final String SELECT_QUERY = "select producer_id, producer_name, country, city, street, email, web_site, telephone "
			+ "from producer where producer_id = ?";
	private static final String SELECT_ALL_QUERY = "select  producer_id, producer_name, country, city, street, email, web_site, telephone from producer ";

	public int insertProducer(Producer producer) throws Exception {

		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
				Statement.RETURN_GENERATED_KEYS);

		try {
			statement.setString(1, producer.getName());
			statement.setString(2, producer.getCountry());
			statement.setString(3, producer.getCity());
			statement.setString(4, producer.getStreet());
			statement.setString(5, producer.getEmail());
			statement.setString(6, producer.getWebSite());
			statement.setString(7, producer.getTelephone());
			statement.executeUpdate();

			return DataAccessUtil.getNewRowKey(statement);
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void updateProducer(Producer producer) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

		try {
			statement.setString(1, producer.getName());
			statement.setString(2, producer.getCountry());
			statement.setString(3, producer.getCity());
			statement.setString(4, producer.getStreet());
			statement.setString(5, producer.getEmail());
			statement.setString(6, producer.getWebSite());
			statement.setString(7, producer.getTelephone());
			statement.setInt(8, producer.getProducerId());

			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void deleteProducer(int producerId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

		try {
			statement.setInt(1, producerId);
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public Producer findById(int producerId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, producerId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return getProducerFromRow(rs);
			}
			return null;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<Producer> findAll() throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection
				.prepareStatement(SELECT_ALL_QUERY);

		try {
			ResultSet rs = statement.executeQuery();
			List<Producer> result = new ArrayList<Producer>();
			while (rs.next()) {
				result.add(getProducerFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	private static Producer getProducerFromRow(ResultSet rs) throws Exception {
		Producer p = new Producer();
		p.setProducerId(rs.getInt(1));
		p.setName(rs.getString(2));
		p.setCountry(rs.getString(3));
		p.setCity(rs.getString(4));
		p.setStreet(rs.getString(5));
		p.setEmail(rs.getString(6));
		p.setWebSite(rs.getString(7));
		p.setTelephone(rs.getString(8));

		return p;
	}

}
