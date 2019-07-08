package tables;

public class Constante {
	
	private Constante(){}
	public static final double PRICE_PER_KM = 3.5;

	public static final String DB_NAME = "MersulTrenurilor.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
	
	public static final String TABLE_RUTE = "Rute";
	public static final String COLUMN_RUTE_ID = "ID";
	public static final String COLUMN_RUTE_RUTA = "RUTA";
	
	public static final String TABLE_STATII = "Statii";
	public static final String COLUMN_STATII_ID_RUTA = "ID_RUTA";
	public static final String COLUMN_STATII_ID_STATIE = "ID_STATIE";
	public static final String COLUMN_STATII_STATIE = "STATIE";
	public static final String COLUMN_STATII_KILOMETRU = "KILOMETRU";
	
	public static final String TABLE_TRENURI = "Trenuri";
	public static final String COLUMN_TRENURI_ID_TREN = "ID_TREN";
	
	public static final String TABLE_SOSIRI_PLECARI = "SosiriPlecari";
	public static final String COLUMN_SOSIRI_PLECARI_ID_TREN = "ID_TREN";
	public static final String COLUMN_SOSIRI_PLECARI_ID_STATIE = "ID_STATIE";
	public static final String COLUMN_SOSIRI_PLECARI_SOSIRE = "SOSIRE";
	public static final String COLUMN_SOSIRI_PLECARI_PLECARE = "PLECARE";
	
	public static final String TABLE_REZERVARI = "Rezervari";
	public static final String COLUMN_ID_REZERVARE = "ID_REZERVARE";
	public static final String COLUMN_REZERVARI_ID_TREN = "ID_TREN";
	public static final String COLUMN_REZERVARI_NUMAR_LOC = "NUMAR_LOC";
	public static final String COLUMN_REZERVARI_DATA = "DATA";
	public static final String COLUMN_REZERVARI_ORAS_PLECARE = "ORAS_PLECARE";
	public static final String COLUMN_REZERVARI_ORAS_SOSIRE = "ORAS_SOSIRE";
	
	
	public static final String QUERY_ROUTE = "SELECT * FROM " +  TABLE_STATII + " WHERE " + COLUMN_STATII_STATIE + "= ?";
	
	public static final String QUERY_SOSIRI_PLECARI = "SELECT * FROM " + TABLE_SOSIRI_PLECARI + " WHERE " + 
	        COLUMN_SOSIRI_PLECARI_ID_STATIE + " = ?";
	
	public static final String QUERY_REZERVARI = "SELECT MIN(" + COLUMN_ID_REZERVARE +") FROM " + TABLE_REZERVARI + " WHERE " + 
			COLUMN_REZERVARI_ID_TREN + " = ? AND " +  COLUMN_REZERVARI_DATA + " IS NULL";
	
	public static final String ADD_REZERVATION = "UPDATE " + TABLE_REZERVARI + " SET " + COLUMN_REZERVARI_DATA +"= ?, " +
			COLUMN_REZERVARI_ORAS_PLECARE + "= ?, "  +  COLUMN_REZERVARI_ORAS_SOSIRE + "= ? WHERE " +  COLUMN_ID_REZERVARE +
			"=?";
	
	public static final String QUERY_SEAT_AND_TRAIN_NUMBER = "SELECT " + COLUMN_REZERVARI_ID_TREN + ", " + COLUMN_REZERVARI_NUMAR_LOC +
			" FROM " + TABLE_REZERVARI + " WHERE " + COLUMN_ID_REZERVARE + "=?";
	
	
	// Check this one//
	public static final String SEARCH_ROUTE = "SELECT " + COLUMN_RUTE_RUTA + " FROM " + TABLE_RUTE + " WHERE " +
			COLUMN_RUTE_ID + " = ?";
	
	
}
