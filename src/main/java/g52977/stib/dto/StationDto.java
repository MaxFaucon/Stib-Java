package g52977.stib.dto;

/**
 * Dto for the transfer of stations data.
 * 
 * @author Maximilien Faucon 52977
 */
public class StationDto extends Dto<Integer> {

    private String name;    

    /**
     * Constructor of StationDto.
     * 
     * @param key The id of the station.
     */
    public StationDto(Integer key) {
        super(key);
    }
    
    /**
     * Constructor of StationDto.
     * 
     * @param key The id of the station.
     * @param name The name of the station.
     */
    public StationDto(Integer key, String name) {
	    super(key);
	    this.name = name;
    }
    
    /**
     * Getter of name.
     * 
     * @return The name of the station.
     */
    public String getName() {
        return name;
    }

}
