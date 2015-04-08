package kursova.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kursova.model.Record;
import kursova.model.Software;

public class RecordDao {
	
	private static final SoftwareDao softwareDao = new SoftwareDao();
	
	private static final ProducerDao producerDao = new ProducerDao();
	
	private static final LicenceDao licenceDao = new LicenceDao();
	
	private static final RefDao refDao = new RefDao();
		
	private static final String SELECT_ALL_QUERY = "SELECT `ref`.`ref_id`, `ref`.`software_id`, `ref`.`licence_id` "
			+"FROM soft.ref ";
	private static final String SELECT_QUERY = "SELECT `ref`.`ref_id`, `ref`.`software_id`, `ref`.`licence_id` "
			+"FROM soft.ref "
			+"WHERE `ref`.`ref_id` = ?;";

		

		public Record findById(int softwareId) throws Exception {
			Connection connection = DataAccessUtil.createConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

			try {
				statement.setInt(1, softwareId);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					return getAllFromRow(rs);
				}
				return null;
			} finally {
				DataAccessUtil.close(connection);
			}
		}

		public List<Record> findAll() throws Exception {
			Connection connection = DataAccessUtil.createConnection();
			PreparedStatement statement = connection
					.prepareStatement(SELECT_ALL_QUERY);

			try {
				ResultSet rs = statement.executeQuery();
				List<Record> result = new ArrayList<Record>();
				while (rs.next()) {
					result.add(getAllFromRow(rs));
				}
				return result;
			} finally {
				DataAccessUtil.close(connection);
			}
		}

		private static Record getAllFromRow(ResultSet rs) throws Exception {
			Record r = new Record();
			r.setRef(refDao.findById(rs.getInt(1)));
			Software s = softwareDao.findById(rs.getInt(2));
			r.setSoftware(s);
			r.setLicence(licenceDao.findById(rs.getInt(3)));
			r.setProducer(producerDao.findById(s.getProducerId()));

			return r;
		}

}
