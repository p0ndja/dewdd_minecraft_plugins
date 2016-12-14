package piston_ruin_the_world;

public class Block {
	private int id;
	private int data;
	
	private Material material ;
	
	public Material getType() {
		return this.material;
	}
	
	public void setType(Material material) {
		this.material = material;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	private Location location;
	
}
