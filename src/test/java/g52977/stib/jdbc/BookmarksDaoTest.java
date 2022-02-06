package g52977.stib.jdbc;

import g52977.stib.config.ConfigManager;
import g52977.stib.dto.BookmarkDto;
import g52977.stib.exception.RepositoryException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Maximilien Faucon 52977
 */
public class BookmarksDaoTest {

	private final BookmarkDto bmTest1;
	private final BookmarkDto bmTest2;
	
	private final BookmarkDto bmTest3;
	private final BookmarkDto bmTest4;

	private static final int KEY = 1;

	private final List<BookmarkDto> all;
	private BookmarksDao instance;

	public BookmarksDaoTest() {
		System.out.println("BookmarksDaoTest Constructor");
		bmTest1 = new BookmarkDto(KEY, "test", "ROGIER", "DELTA");
		bmTest2 = new BookmarkDto(4, "test4", "DELTA", "ROGIER");
		bmTest3 = new BookmarkDto(2, "test2", "PARC", "ALMA");
		bmTest4 = new BookmarkDto(3, "test3", "MERODE", "DELTA");

		all = List.of(bmTest1, bmTest3, bmTest4);

		try {
			ConfigManager.getInstance().load();
			instance = BookmarksDao.getInstance();
		} catch (RepositoryException | IOException ex) {
			Assertions.fail("Connection to the test DataBase error");
		}
	}

	@Test
	public void testSelectAllExist() throws Exception {
		System.out.println("testSelectAllExist");								
		//Arrange
		List<BookmarkDto> expected = all;
		//Action
		List<BookmarkDto> result = instance.selectAll();
		//Assert
		assertEquals(expected, result);
	}
	
	@Test
	public void testSelectExist() throws Exception {
		System.out.println("testSelectExist");
		//Arrange
		BookmarkDto expected = bmTest1;
		//Action
		BookmarkDto result = instance.select(KEY);
		//Assert
		assertEquals(expected, result);
	}

	@Test
	public void testSelectNotExist() throws Exception {
		System.out.println("testSelectNotExist");
		assertNull(instance.select(bmTest2.getKey()));
	}
	
	@Test
	public void testSelectIncorrectParameters() throws Exception {
		System.out.println("testSelectIncorrectParameters");
		//Arrange
		Integer incorrect = null;
		//Assert
		assertThrows(RepositoryException.class, () -> {
			//Action
			instance.select(incorrect);
		});
	}
	
	@Test
	public void testInsertExist() throws Exception {
		System.out.println("testInsertExist");
		//Assert
		assertThrows(RepositoryException.class, () -> {
			//Action
			instance.insert(bmTest1);
		});
	}
	
	@Test
	public void testInsertNotExist() throws Exception {
		System.out.println("testInsertNotExist");
		//Arrange
		BookmarkDto bmTest5 = new BookmarkDto(4, "test4", 
			"DE BROUCKERE", "YSER");
		//Action
		Integer expected = instance.insert(bmTest5);
		//Assert
		assertEquals(expected, bmTest5.getKey());
		assertEquals(bmTest5, instance.select(bmTest5.getKey()));
	}
	
	@Test
	public void testInsertIncorrectParameters() throws Exception {
		System.out.println("testInsertIncorrectParameters");
		//Arrange
		BookmarkDto incorrect = null;
		//Assert
		assertThrows(RepositoryException.class, () -> {
			//Action
			instance.insert(incorrect);
		});
	}
	
	@Test
	public void testUpdateExist() throws Exception {
		System.out.println("testUpdateExist");		
		//Arrange		
		BookmarkDto expected = new BookmarkDto(bmTest3.getKey(), 
			"blabla", "DE BROUCKERE", "ROGIER");		
		//Action
		instance.update(expected);			
		//Assert
		assertEquals(expected, instance.select(bmTest3.getKey()));
	}
	
	@Test
	public void testUpdateNotExist() throws Exception {
		System.out.println("testUpdateNotExist");
		//Arrange
		BookmarkDto bmTest5 = new BookmarkDto(5, "test4", 
			"DE BROUCKERE", "YSER");
		//Action
		instance.update(bmTest5);
		//Assert
		assertNull(instance.select(bmTest5.getKey()));
	}
	
	@Test
	public void testUpdateIncorrectParameters() throws Exception {
		System.out.println("testUpdateIncorrectParameters");
		//Arrange
		BookmarkDto incorrect = null;
		//Assert
		assertThrows(RepositoryException.class, () -> {
			//Action
			instance.update(incorrect);
		});
	}
	
	@Test
	public void testDeleteExist() throws Exception {
		System.out.println("testDeleteExist");								
		//Action
		instance.delete(bmTest4.getKey());
		//Assert
		assertNull(instance.select(bmTest4.getKey()));
	}
	
	@Test
	public void testDeleteNotExist() throws Exception {
		System.out.println("testDeleteNotExist");		
		//Action
		instance.delete(10);
	}
	
	
	@Test
	public void testDeleteIncorrectParameters() throws Exception {
		System.out.println("testDeleteIncorrectParameters");
		//Arrange
		Integer incorrect = null;
		//Assert
		assertThrows(RepositoryException.class, () -> {
			//Action
			instance.delete(incorrect);
		});
	}	

}
