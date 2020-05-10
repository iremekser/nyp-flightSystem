import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Capital {
	private int id;
	private String name;
	private float lat;
	private float lng;

	public Capital(int id, String name, float lat, float lng) {
		this.id = id;
		this.name = name;
		this.lat = lat;
		this.lng = lng;
	}

	public Capital() {
		// TODO Auto-generated constructor stub
	}

	public void appendText() throws IOException {
		Files.write(Paths.get("src/capitals.txt"), txtRow().getBytes(), StandardOpenOption.APPEND);
	}

	public String txtRow() {
		return this.getId() + "," + this.getName() + "," + this.getLat() + "," + this.getLng() + "\n";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

}
