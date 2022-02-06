package g52977.stib.dto;

/**
 * Dto for the transfer of the bookmarks data.
 * 
 * @author Maximilien Faucon 52977
 */
public class BookmarkDto extends Dto<Integer> {

	private String name;
	private String source;
	private String arrival;

        /**
         * Constructor of BookmarkDto.
         * 
         * @param key The id of the bookmark.
         */
	public BookmarkDto(Integer key) {
		super(key);
	}

        /**
         * Constructor of BookmarkDto.
         * 
         * @param key The id of the bookmark.
         * @param name The name of the bookmark.
         * @param source The source station of the path.
         * @param arrival The arrival station of the path.
         */
	public BookmarkDto(Integer key, String name, String source, 
		String arrival) {
		super(key);
		this.name = name;
		this.source = source;
		this.arrival = arrival;
	}

        /**
         * Getter of name.
         * 
         * @return The name of the bookmark.
         */
	public String getName() {
		return name;
	}
	
        /**
         * Getter of source.
         * 
         * @return The source station of the path.
         */
	public String getSource() {
		return source;
	}
	
        /**
         * Getter of arrival.
         * 
         * @return The arrival station of the path.
         */
	public String getArrival() {
		return arrival;
	}
}
