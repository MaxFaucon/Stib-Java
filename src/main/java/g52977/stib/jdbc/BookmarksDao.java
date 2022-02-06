package g52977.stib.jdbc;

import g52977.stib.dto.BookmarkDto;
import g52977.stib.exception.RepositoryException;
import g52977.stib.repository.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for the bookmarks.
 * 
 * @author Maximilien Faucon 52977
 */
public class BookmarksDao implements Dao<Integer, BookmarkDto> {
    
	private Connection connexion;

        /**
         * Private constructor of BookmarksDao. Makes the connection to the
         * database.
         * 
         * @throws RepositoryException Exception thrown if the connection failed.
         */
	private BookmarksDao() throws RepositoryException {
		connexion = DBManager.getInstance().getConnection();
	}

	public static BookmarksDao getInstance() throws RepositoryException {
		return StudentsDaoHolder.getInstance();
	}

        /**
         * Insert a bookmark in the bookmarks table.
         * 
         * @param item The bookmark to add in the table.
         * @return The id of the added bookmark.
         * @throws RepositoryException Exception thrown if the add failed.
         */
	public Integer insert(BookmarkDto item) throws RepositoryException {
		if (item == null) {
			throw new RepositoryException("Aucune élément donné en paramètre");
		}
		Integer id = 0;
		String sql = "INSERT INTO BOOKMARKS(name, source, arrival) values(?, ?, ?)";
		try ( PreparedStatement pstmt = connexion.prepareStatement(sql)) {
			pstmt.setString(1, item.getName());
			pstmt.setString(2, item.getSource());
			pstmt.setString(3, item.getArrival());
			pstmt.executeUpdate();

			ResultSet result = pstmt.getGeneratedKeys();
			while (result.next()) {
				id = result.getInt(1);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return id;
	}

        /**
         * Delete a bookmark in the bookmarks table.
         * 
         * @param key The id of the bookmark to delete.
         * @throws RepositoryException Exception thrown if the deletion failed.
         */
	public void delete(Integer key) throws RepositoryException {		
		if (key == null) {
			throw new RepositoryException("Aucune clé donnée en paramètre");
		}
		String sql = "DELETE FROM BOOKMARKS WHERE id = ?";
		try ( PreparedStatement pstmt = connexion.prepareStatement(sql)) {
			pstmt.setInt(1, key);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

        /**
         * Update a bookmark in the bookmarks table.
         * 
         * @param item The bookmark to update.
         * @throws RepositoryException Exception thrown if the update failed.
         */
	public void update(BookmarkDto item) throws RepositoryException {
		if (item == null) {
			throw new RepositoryException("Aucune élément donné en paramètre");
		}
		String sql = "UPDATE BOOKMARKS SET name=? ,source=?, arrival=? where id=? ";
		try ( PreparedStatement pstmt = connexion.prepareStatement(sql)) {
			pstmt.setString(1, item.getName());
			pstmt.setString(2, item.getSource());
			pstmt.setString(3, item.getArrival());
			pstmt.setInt(4, item.getKey());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	
        /**
         * Select a bookmark in the bookmarks table.
         * 
         * @param key The id of the bookmark to select.
         * @return A dto containing the bookmark information.
         * @throws RepositoryException Exception thrown if the select didn't work.
         */
	public BookmarkDto select(Integer key) throws RepositoryException {
		if (key == null) {
			throw new RepositoryException("Aucune clé donnée en paramètre");
		}
		String sql = "SELECT id,name,source,arrival FROM BOOKMARKS WHERE  id = ?";
		BookmarkDto dto = null;
		try ( PreparedStatement pstmt = connexion.prepareStatement(sql)) {
			pstmt.setInt(1, key);
			ResultSet rs = pstmt.executeQuery();							
			int count = 0;
			while (rs.next()) {								
				dto = new BookmarkDto(rs.getInt(1), 
					rs.getString(2), rs.getString(3), rs.getString(4));
				count++;
			}
			if (count > 1) {
				throw new RepositoryException("Record pas unique " + key);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}		
		return dto;
	}

        /**
         * Select all the bookmarks from the bookmarks table.
         * 
         * @return A dto list containing all the bookmarks.
         * @throws RepositoryException Exception thrown if the select failed.
         */
	@Override
	public List<BookmarkDto> selectAll() throws RepositoryException {
		String sql = "SELECT id,name,source, arrival FROM BOOKMARKS";
		List<BookmarkDto> dtos = new ArrayList<>();
		try ( Statement stmt = connexion.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				BookmarkDto dto = new BookmarkDto(rs.getInt(1), 
					rs.getString(2), rs.getString(3), rs.getString(4));
				dtos.add(dto);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return dtos;
	}

	private static class StudentsDaoHolder {

		private static BookmarksDao getInstance() throws RepositoryException {
			return new BookmarksDao();
		}
	}
}
