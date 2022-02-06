package g52977.stib.dto;

import javafx.util.Pair;

/**
 * Dto for the transfer of stops data and the name of the stations according
 * to the stops.
 * 
 * @author Maximilien Faucon 52977
 */
public class StopDto extends Dto<Pair<Integer, Integer>> {

	private final Integer id_line;
	private final Integer id_station;
	private final Integer id_order;
	private final String name_station;
    
        /**
         * Constructor of StopDto.
         * 
         * @param id_line The line id of a station.
         * @param id_station The station id.
         * @param id_order The order id in the database.
         * @param name_station The station name.
         */
	public StopDto(Integer id_line, Integer id_station, Integer id_order,
		String name_station) {
		super(new Pair<>(id_line, id_station));
		this.id_line = id_line;
		this.id_station = id_station;
		this.id_order = id_order;
		this.name_station = name_station;
	}

        /**
         * Getter of id_line.
         * 
         * @return The line id of a station.
         */
	public Integer getLineId() {
		return this.id_line;
	}

        /**
         * Getter of id_station.
         * 
         * @return The station id.
         */
	public Integer getStationId() {
		return this.id_station;
	}

        /**
         * Getter of id_order.
         * 
         * @return The order id in the database.
         */
	public Integer getOrderId() {
		return this.id_order;
	}
	
        /**
         * Getter of name_station.
         * 
         * @return The station name. 
         */
	public String getStationName() {
		return this.name_station;
	}

}
