package g52977.stib.jdbc;

import g52977.stib.dto.StopDto;
import g52977.stib.exception.RepositoryException;
import g52977.stib.repository.Dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 * Data access object for the stops.
 * 
 * @author Maximilien Faucon 52977
 */
public class StopsDao implements Dao<Pair<Integer, Integer>, StopDto> {

	private final Connection connexion;

        /**
         * Private constructor of StopsDao.
         * 
         * @throws RepositoryException Exception thrown if the connection to the
         * database failed.
         */
	private StopsDao() throws RepositoryException {		
		connexion = DBManager.getInstance().getConnection();		
	}

	public static StopsDao getInstance() throws RepositoryException {
		return StopsDaoDaoHolder.getInstance();
	}

        /**
         * Select all the stops from the table.
         * 
         * @return A list of dto containing the information of all the stops.
         * @throws RepositoryException Exception thrown if the select failed.
         */
	@Override
	public List<StopDto> selectAll() throws RepositoryException {
		String sql = "SELECT id_line,id_station,id_order,name "
			+ "FROM STOPS JOIN STATIONS ON id_station=id";
		List<StopDto> dtos = new ArrayList<>();
		try ( Statement stmt = connexion.createStatement();  
			ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				StopDto dto = new StopDto(rs.getInt(1), 
					rs.getInt(2), rs.getInt(3), rs.getString(4));
				dtos.add(dto);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return dtos;
	}

	private static class StopsDaoDaoHolder {

		private static StopsDao getInstance() throws RepositoryException {
			return new StopsDao();
		}
	}
	
}
