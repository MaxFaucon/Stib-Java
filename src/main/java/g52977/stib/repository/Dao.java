package g52977.stib.repository;

import g52977.stib.dto.Dto;
import g52977.stib.exception.RepositoryException;
import java.util.List;

/**
 * Data access object of a resource (file, database, web service).
 *
 * @author Maximilien Faucon 52977
 * @param <K> key of an item.
 * @param <T> item of the resource.
 */
public interface Dao<K, T extends Dto<K>> {
	
    /**
     * Returns all the elements of the resource. This method can be long.
     *
     * @return all the elements of the resource.
     * @throws RepositoryException if the resource can't be accessed.
     */
    List<T> selectAll() throws RepositoryException;
}
