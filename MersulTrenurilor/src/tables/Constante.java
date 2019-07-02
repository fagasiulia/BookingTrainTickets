package tables;

public class Constante {

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
	public static final String COLUMN_REZERVARI_ID_TREN = "ID_TREN";
	public static final String COLUMN_REZERVARI_NUMAR_LOC = "NUMAR_LOC";
	public static final String COLUMN_REZERVARI_DATA = "DATA";
	public static final String COLUMN_REZERVARI_ORAS_PLECARE = "ORAS_PLECARE";
	public static final String COLUMN_REZERVARI_ORAS_SOSIRE = "ORAS_SOSIRE";
}
