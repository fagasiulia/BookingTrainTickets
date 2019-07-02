package tables;

public class Statii {
	private final int id_ruta;
	private final int id_statie;
	private String statie;
	private int km;
	
	public Statii(int id_ruta, int id_statie, String statie, int km){
		this.id_ruta = id_ruta;
		this.id_statie = id_statie;
		this.statie = statie;
		this.km = km;
	}

	public String getStatie() {
		return statie;
	}

	public void setStatie(String statie) {
		this.statie = statie;
	}

	public int getKm() {
		return km;
	}

	public void setKm(int km) {
		this.km = km;
	}

	public int getId_ruta() {
		return id_ruta;
	}

	public int getId_statie() {
		return id_statie;
	}
	
	

}
