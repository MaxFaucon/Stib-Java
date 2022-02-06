package g52977.stib.repository;

import g52977.stib.dto.StationDto;
import g52977.stib.exception.RepositoryException;
import g52977.stib.jdbc.StationsDao;
import java.util.List;

/**
 * Manages the access to the stations table from the database.
 * 
 * @author Maximilien Faucon 52977
 */
public class StationRepository implements Repository<Integer, StationDto> {

    private final StationsDao dao;

    /**
     * Constructor of StationRepository.
     * 
     * @throws RepositoryException Exception thrown if the access to the 
     * stations table failed.
     */
    public StationRepository() throws RepositoryException {
        dao = StationsDao.getInstance();
    }

    StationRepository(StationsDao dao) {
        this.dao = dao;
    }

    /**
     * Select all the stations from the stations table.
     * 
     * @return A list containing all the stations from the table.
     * @throws RepositoryException Exception thrown if the access to the 
     * stations table failed.
     */
    @Override
    public List<StationDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

}
