package g52977.stib.repository;

import g52977.stib.dto.StopDto;
import g52977.stib.exception.RepositoryException;
import g52977.stib.jdbc.StopsDao;
import java.util.List;
import javafx.util.Pair;

/**
 * Manages the access to the stops table from the database.
 * 
 * @author Maximilien Faucon 52977
 */
public class StopRepository implements Repository<Pair<Integer, Integer>, StopDto> {

	private final StopsDao dao;

        /**
         * Constructor of StopRepository.
         * 
         * @throws RepositoryException Exception thrown if the access to the 
         * stops table failed.
         */
	public StopRepository() throws RepositoryException {
		dao = StopsDao.getInstance();
	}

	StopRepository(StopsDao dao) {
		this.dao = dao;
	}

        /**
         * Select all the stops from the stops table.
         * 
         * @return A list containing all the stops from the table.
         * @throws RepositoryException Exception thrown if the access to the 
         * stops table failed.
         */
	@Override
	public List<StopDto> getAll() throws RepositoryException {
		return dao.selectAll();
	}

}
