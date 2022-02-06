package g52977.stib.repository;

import g52977.stib.config.ConfigManager;
import g52977.stib.dto.BookmarkDto;
import g52977.stib.exception.RepositoryException;
import g52977.stib.jdbc.BookmarksDao;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Maximilien Faucon 52977
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class BookmarkRepositoryTest {

	@Mock
	private BookmarksDao mock;

	private final BookmarkDto bmTest1;
	private final BookmarkDto bmTest2;

	private final BookmarkDto bmTest3;
	private final BookmarkDto bmTest4;

	private static final int KEY = 1;

	private final List<BookmarkDto> all;
	private BookmarksDao instance;

	public BookmarkRepositoryTest() {
		System.out.println("BookmarkRepositoryTest Constructor");
		bmTest1 = new BookmarkDto(KEY, "test", "ROGIER", "DELTA");
		bmTest2 = new BookmarkDto(4, "test4", "DELTA", "ROGIER");
		bmTest3 = new BookmarkDto(2, "test2", "PARC", "ALMA");
		bmTest4 = new BookmarkDto(3, "test3", "MERODE", "DELTA");

		all = List.of(bmTest1, bmTest3, bmTest4);
	}

	@BeforeEach
	void init() throws RepositoryException {
		Mockito.lenient().when(
			mock.select(bmTest1.getKey())).thenReturn(bmTest1);
		Mockito.lenient().when(
			mock.select(bmTest2.getKey())).thenReturn(null);
		Mockito.lenient().when(
			mock.selectAll()).thenReturn(all);
		Mockito.lenient().when(
			mock.select(null)).thenThrow(RepositoryException.class);
	}

	@Test
	public void testAddWhenExisting() throws Exception {
		System.out.println("testAddWhenExisting");
		//Arrange		
		BookmarkRepository repository = new BookmarkRepository(mock);
		//Action
		repository.add(bmTest1);
		//Assert
		Mockito.verify(mock, times(1)).select(KEY);
		Mockito.verify(mock, times(1)).update(bmTest1);
		Mockito.verify(mock, times(0)).insert(bmTest1);
	}

	@Test
	public void testAddWhenNotExisting() throws Exception {
		System.out.println("testAddWhenNotExisting");
		//Arrange		
		BookmarkRepository repository = new BookmarkRepository(mock);
		//Action
		repository.add(bmTest2);
		//Assert
		Mockito.verify(mock, times(1)).select(bmTest2.getKey());
		Mockito.verify(mock, times(0)).update(bmTest2);
		Mockito.verify(mock, times(1)).insert(bmTest2);
	}

	@Test
	public void testAddIncorrectParameters() throws Exception {
		System.out.println("testAddIncorrectParameters");
		//Arrange		
		BookmarkRepository repository = new BookmarkRepository(mock);
		//Assert
		assertThrows(NullPointerException.class, () -> {
			//Action
			repository.add(null);
		});
		Mockito.verify(mock, times(0)).select(null);
		Mockito.verify(mock, times(0)).update(null);
		Mockito.verify(mock, times(0)).insert(null);
	}

	@Test
	public void testRemoveWhenExisting() throws Exception {
		System.out.println("testRemoveWhenExisting");
		//Arrange		
		BookmarkRepository repository = new BookmarkRepository(mock);
		//Action
		repository.remove(KEY);
		//Assert
		Mockito.verify(mock, times(1)).delete(KEY);
	}

	@Test
	public void testRemoveWhenNotExisting() throws Exception {
		System.out.println("testRemoveWhenNotExisting");
		//Arrange		
		BookmarkRepository repository = new BookmarkRepository(mock);
		//Action
		repository.remove(10);
		//Assert
		Mockito.verify(mock, times(1)).delete(10);
	}

	@Test
	public void testRemoveIncorrectParameters() throws Exception {
		System.out.println("testRemoveIncorrectParameters");
		//Arrange		
		BookmarkRepository repository = new BookmarkRepository(mock);
		//Action
		repository.remove(null);
		//Assert
		Mockito.verify(mock, times(1)).delete(null);
	}
	
	@Test
	public void testGetAllExist() throws Exception {
		System.out.println("testGetAllExist");
		//Arrange
		List<BookmarkDto> expected = all;
		BookmarkRepository repository = new BookmarkRepository(mock);
		//Action
		List<BookmarkDto> result = repository.getAll();
		//Assert
		assertEquals(expected, result);
		Mockito.verify(mock, times(1)).selectAll();
	}
}
