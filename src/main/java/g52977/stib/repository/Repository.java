package g52977.stib.repository;

import g52977.stib.dto.Dto;
import g52977.stib.exception.RepositoryException;
import java.util.List;

/**
 * Repository pattern to manage a resource of the application: a file, a
 * database, a web service.
 *
 * @author Maximilien Faucon 52977
 * @param <K> key of an element.
 * @param <T> an element.
 */
public interface Repository<K, T extends Dto<K>> {

    /**
     * Returns all the elements of the repository.
     *
     * @return all the elements of the repository.
     *
     * @throws RepositoryException if the repository can't access to the elements.
     */
    List<T> getAll() throws RepositoryException;

}
