package g52977.stib.repository;

import g52977.stib.dto.BookmarkDto;
import g52977.stib.exception.RepositoryException;
import g52977.stib.jdbc.BookmarksDao;
import java.util.List;

/**
 * Manages the access to the bookmarks table from database.
 *
 * @author Maximilien Faucon 52977
 */
public class BookmarkRepository implements Repository<Integer, BookmarkDto> {

	private final BookmarksDao dao;

        /**
         * Constructor of BookmarkRepository.
         * 
         * @throws RepositoryException Exception thrown if the access to the 
         * bookmarks table failed.
         */
	public BookmarkRepository() throws RepositoryException {
		dao = BookmarksDao.getInstance();
	}

	BookmarkRepository(BookmarksDao dao) {
		this.dao = dao;
	}

        /**
         * Adds a bookmark to the bookmarks table.
         * 
         * @param item The bookmark to add to the table.
         * @return The id of the added bookmark.
         * @throws RepositoryException Exception thrown if the access to the 
         * bookmarks table failed.
         */
	public Integer add(BookmarkDto item) throws RepositoryException {
		Integer key = item.getKey();
		if (contains(item.getKey())) {		
			dao.update(item);
		} else {				
			key = dao.insert(item);
		}
		return key;
	}

        /**
         * Removes a bookmark from the bookmarks table.
         * 
         * @param key The id of the bookmark to delete.
         * @throws RepositoryException Exception thrown if the access to the 
         * bookmarks table failed.
         */
	public void remove(Integer key) throws RepositoryException {
		dao.delete(key);
	}

        /**
         * Select all the bookmarks from the bookmarks table.
         * 
         * @return A list of all the bookmarks.
         * @throws RepositoryException Exception thrown if the access to the 
         * bookmarks table failed.
         */
	@Override
	public List<BookmarkDto> getAll() throws RepositoryException {
		return dao.selectAll();
	}

        /**
         * Search if a the bookmarks table contains a certain bookmark.
         * 
         * @param key The id of the bookmark to search.
         * @return True if the bookmark exists, false otherwise.
         * @throws RepositoryException Exception thrown if the access to the 
         * bookmarks table failed.
         */
	public boolean contains(Integer key) throws RepositoryException {		
		BookmarkDto refreshItem = dao.select(key);
		return refreshItem != null;
	}

}
