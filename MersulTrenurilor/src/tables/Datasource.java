package tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Datasource {

	private Connection conn;
	private PreparedStatement queryDepartureRute;
	private PreparedStatement queryArrivalRute;
	private PreparedStatement querySosiri;
	private PreparedStatement queryPlecari;
	private PreparedStatement querySeat;
	private PreparedStatement addRezervation;
	private PreparedStatement addStationDetails;
	private PreparedStatement queryRuta;
	
	// Open connection
	public boolean open() {
		try {
			conn = DriverManager.getConnection(Constante.CONNECTION_STRING);
			queryDepartureRute = conn.prepareStatement(Constante.QUERY_ROUTE);
			queryArrivalRute = conn.prepareStatement(Constante.QUERY_ROUTE);
			querySosiri = conn.prepareStatement(Constante.QUERY_SOSIRI_PLECARI);
			queryPlecari = conn.prepareStatement(Constante.QUERY_SOSIRI_PLECARI);
			querySeat = conn.prepareStatement(Constante.QUERY_REZERVARI);
			addRezervation = conn.prepareStatement(Constante.ADD_REZERVATION);
			addStationDetails = conn.prepareStatement(Constante.ADD_STATION_DETAILS);
			queryRuta = conn.prepareStatement(Constante.SEARCH_ROUTE);

			return true;

		} catch (SQLException e) {
			System.out.println("Unable to open connection " + e.getMessage());
			return false;
		}
	}

	// Close connection
	public void close() {
		try {
			if (queryRuta != null) {
				queryRuta.close();
			}
			if (addStationDetails != null) {
				addStationDetails.close();
			}
			if (addRezervation != null) {
				addRezervation.close();
			}
			if (querySeat != null) {
				querySeat.close();
			}
			if (queryPlecari != null) {
				queryPlecari.close();
			}
			if (querySosiri != null) {
				querySosiri.close();
			}
			if (queryArrivalRute != null) {
				queryArrivalRute.close();
			}
			if (queryDepartureRute != null) {
				queryDepartureRute.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Unable to close connection " + e.getMessage());
		}
	}

	// Search for a route
	private Map<Statii, Statii> queryRoute(String departure, String destination) throws SQLException {
		
		queryDepartureRute.setString(1, departure);
		queryArrivalRute.setString(1, destination);
		
		try (Statement statement = conn.createStatement();
				ResultSet results1 = queryDepartureRute.executeQuery();
				ResultSet results2 = queryArrivalRute.executeQuery()) {
			// Search for the departure information
			ArrayList<Statii> plecari = statiiList(results1);
			// Search for the arrival information
			List<Statii> sosiri = statiiList(results2);

			// Search the departure and the arrive lists for matches
			Map<Statii, Statii> findedMatch = new HashMap<>();
			for (Statii p : plecari) {
				for (Statii s : sosiri) {
					// If the Statii match on ID_RUTA that means is the same
					// route so we add both Statii to the findedMatch list for further use
					if (p.getId_ruta() == s.getId_ruta()) {
						findedMatch.put(p, s);
					}
				}
			}
			return findedMatch;

		} catch (SQLException e) {
			System.out.println("Unable to search for a route! " + e.getMessage());
			return null;
		}
	}

	// Construct a list of Statii
	private ArrayList<Statii> statiiList(ResultSet results) throws SQLException {
		ArrayList<Statii> statii = new ArrayList<>();
		while (results.next()) {
			Statii statie = new Statii(
					results.getInt(Constante.COLUMN_STATII_ID_RUTA),
					results.getInt(Constante.COLUMN_STATII_ID_STATIE),
					results.getString(Constante.COLUMN_STATII_STATIE),
					results.getDouble(Constante.COLUMN_STATII_KILOMETRU));
			statii.add(statie);
		}
		return statii;
	}

	// Search in SosiriPlecari for the ID_STATIE that matches departure and
	// arrival station
	private Map<SosiriPlecari, SosiriPlecari> sosiriPlecari(int departure_Id, int arrival_Id) throws SQLException {
		
		Map<SosiriPlecari, SosiriPlecari> findings = new HashMap<>();
		querySosiri.setInt(1, departure_Id);
		queryPlecari.setInt(1, arrival_Id);
		
		try (Statement statement = conn.createStatement();
				ResultSet resultSosiri = querySosiri.executeQuery();
				ResultSet resultPlecari = queryPlecari.executeQuery()){
			while(resultSosiri.next() && resultPlecari.next()){
				SosiriPlecari sosire = new SosiriPlecari(resultSosiri.getInt(Constante.COLUMN_SOSIRI_PLECARI_ID_TREN),
						resultSosiri.getInt(Constante.COLUMN_SOSIRI_PLECARI_ID_STATIE),
						resultSosiri.getString(Constante.COLUMN_SOSIRI_PLECARI_SOSIRE),
						resultSosiri.getString(Constante.COLUMN_SOSIRI_PLECARI_PLECARE));
				SosiriPlecari plecare = new SosiriPlecari(resultPlecari.getInt(Constante.COLUMN_SOSIRI_PLECARI_ID_TREN),
						resultPlecari.getInt(Constante.COLUMN_SOSIRI_PLECARI_ID_STATIE),
						resultPlecari.getString(Constante.COLUMN_SOSIRI_PLECARI_SOSIRE),
						resultPlecari.getString(Constante.COLUMN_SOSIRI_PLECARI_PLECARE));
				findings.put(sosire, plecare);
			}
			return findings;
		}catch(SQLException e){
			System.out.println("Unable to search for departure/ arrival times" + e.getMessage());
			return null;
		}
	}
	
	// Search in Rezervari to check if there are seats available for the specified train
	private int searchSeatAvailability(int id_tren) throws SQLException{
		int id_loc = 0;
		querySeat.setInt(1, id_tren);
		try(Statement statement = conn.createStatement();
				ResultSet result = querySeat.executeQuery()){
			id_loc = result.getInt("COLUMN_ID_REZERVARE");
			
			return id_loc;
		}catch(SQLException e){
			System.out.println("Unable to search for a seat! " + e.getMessage());
			return id_loc;
		}
	}
	
	// Add a rezervation
	private void addRezervation(String data, String orasPlecare, String orasDestinatie, int id_tren) throws SQLException{
		addRezervation.setString(1, data);
		addRezervation.setString(2, orasPlecare);
		addRezervation.setString(3, orasDestinatie);
		addRezervation.setInt(4, id_tren);
		
		try(Statement statement = conn.createStatement();
				ResultSet resutl = addRezervation.executeQuery()){
		}catch(SQLException e){
			System.out.println("Unable to add a rezervation! " + e.getMessage());
		}
		
	}

	// Calculate ticket price
	private double calculateTicketPrice(double kmDeparture, double kmArrival) {
		return ((kmArrival - kmDeparture) * Constante.PRICE_PER_KM);
	}
	
	// Booking method *******************/
	private void booking(String departure, String destination, String date){
		try {
			Map<Statii, Statii> route = queryRoute(departure, destination);
			if(route.isEmpty()){
				System.out.println("Unable to find the requested route!");
				//**** Try again function ****/
			}
			// If route is not empty that means we can proceed with searching for leaving/arrival hours
			else{
				Map<SosiriPlecari, SosiriPlecari> sosiriPlecariMap = new HashMap<>();
				// Parse the route map to get all the pairs departure/destination id_station 
				for(Entry<Statii, Statii> s : route.entrySet()){
					// Save the results in a temp Map and copy the findings to the original Map
					Map<SosiriPlecari, SosiriPlecari> temp = sosiriPlecari(s.getKey().getId_statie(), s.getValue().getId_statie());
					for(Entry<SosiriPlecari, SosiriPlecari> t: temp.entrySet()){
						sosiriPlecariMap.put(t.getKey(), t.getValue());
					}
				}
				// Check if the sosiriPlecariMap is empty. If it is that means something went wrong as 
				// no matched has been found for the desired route
				if(sosiriPlecariMap.isEmpty()){
					System.out.println("Oops, something went wrong! Check your database!");
				}
				// If the Map is not empty search for seat availability and save all the options in a List
				else{
					ArrayList<Integer> id_loc = new ArrayList<>();
					for(SosiriPlecari sp: sosiriPlecariMap.values()){
						int id_tren = searchSeatAvailability(sp.getId_tren());
						id_loc.add(id_tren);
					}
					// If train_id ArrayList is empty that means no seat available for the requested route
					if(id_loc.isEmpty()){
						System.out.println("No seat available!");
						//***** Try again function ********//
					}
					else{
						
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Unable to perfom the operation! " + e.getMessage());
		}
	}
	
	// Add Departure/ Arrival station details to RezervareDetalii
	private void addStationsDetails(Map<Statii, Statii> statii, String data){
		if(statii.isEmpty()){
			System.out.println("No route was found! ");
		}
		else{
			
			for(Entry<Statii, Statii> s : statii.entrySet()){
				String orasPlecare = s.getKey().getStatie();
				double kmPlecare = s.getKey().getKm();
				String ruta = getRoute(s.getKey().getId_ruta());
				String orasSosire = s.getValue().getStatie();
				double kmSosire = s.getValue().getKm();
				double price = calculateTicketPrice(kmPlecare, kmSosire);
				
				try {
					addStationDetails.setString(1, orasPlecare);
					addStationDetails.setDouble(2, kmPlecare);
					addStationDetails.setString(3, orasSosire);
					addStationDetails.setDouble(4, kmSosire);
					addStationDetails.setString(5, ruta);
					addStationDetails.setDouble(6, price);
					
				} catch (SQLException e) {
					System.out.println("Unable to addStationDetails " + e.getMessage());
				}
				
				try(Statement statement = conn.createStatement();
						ResultSet result = addStationDetails.executeQuery()){
					
				}catch(SQLException e){
					System.out.println("Unable to execute addStationDetails query! " + e.getMessage());
				}
			}
		}
	}
	
	// Get route based on id
	private String getRoute(int id){
		String ruta = "";
		try {
			queryRuta.setInt(1, id);
		} catch (SQLException e) {
			System.out.println("Unable to set id_route for searching the route! " + e.getMessage());
			return null;
		}
		try(Statement statement = conn.createStatement();
				ResultSet result = queryRuta.executeQuery()){
			ruta = result.getString(Constante.COLUMN_REZERVARE_DETALII_RUTA);
			
			return ruta;
			
		}catch(SQLException e){
			System.out.println("Unable to execute query for searching the route! " + e.getMessage());
			return null;
		}
	}
}
