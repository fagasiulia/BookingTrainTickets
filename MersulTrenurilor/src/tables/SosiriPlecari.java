package tables;

public class SosiriPlecari {
	private final int id_statie;
	private final int id_tren;
	private String sosire;
	private String plecare;
	
	public SosiriPlecari(int id_statie, int id_tren, String sosire, String plecare){
		this.id_statie = id_statie;
		this.id_tren = id_tren;
		this.sosire = sosire;
		this.plecare = plecare;
	}

	public String getSosire() {
		return sosire;
	}

	public void setSosire(String sosire) {
		this.sosire = sosire;
	}

	public String getPlecare() {
		return plecare;
	}

	public void setPlecare(String plecare) {
		this.plecare = plecare;
	}

	public int getId_statie() {
		return id_statie;
	}

	public int getId_tren() {
		return id_tren;
	}
	

}
