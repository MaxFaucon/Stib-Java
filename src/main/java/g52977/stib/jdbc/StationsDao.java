package g52977.stib.jdbc;

import g52977.stib.dto.StationDto;
import g52977.stib.exception.RepositoryException;
import g52977.stib.repository.Dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for the stations.
 * 
 * @author 52977
 */
public class StationsDao implements Dao<Integer, StationDto> {

	private final Connection connexion;

        /**
         * Private constructor of StationsDao.
         * 
         * @throws RepositoryException Exception thrown if the connection to the
         * database failed.
         */
	private StationsDao() throws RepositoryException {		
		connexion = DBManager.getInstance().getConnection();		
	}

	public static StationsDao getInstance() throws RepositoryException {
		return StationsDaoHolder.getInstance();
	}

        /**
         * Select all the stations from the stations table.
         * 
         * @return A dto list containing all the stations data.
         * @throws RepositoryException Exception thrown if the select failed.
         */
	@Override
	public List<StationDto> selectAll() throws RepositoryException {
		String sql = "SELECT id,name FROM STATIONS";
		List<StationDto> dtos = new ArrayList<>();
		try ( Statement stmt = connexion.createStatement();  
			ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				StationDto dto = new StationDto(rs.getInt(1), rs.getString(2));
				dtos.add(dto);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return dtos;
	}

	private static class StationsDaoHolder {

		private static StationsDao getInstance() throws RepositoryException {
			return new StationsDao();
		}
	}
}
