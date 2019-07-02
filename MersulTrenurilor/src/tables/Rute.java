package tables;

public class Rute {
	private final int id;
	private String ruta;
	
	public Rute (int id, String ruta){
		this.id = id;
		this.ruta = ruta;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public int getId() {
		return id;
	}
	

}
