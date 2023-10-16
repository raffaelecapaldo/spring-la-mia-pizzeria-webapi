package org.java.app.business.api;

public class PizzaDTO {
	
	private int id;
	
	private String name;
	private String description;
	private String imageUrl;
	private float price;
	
	public PizzaDTO() { }
	public PizzaDTO(String name, String description, String imageUrl, float price) {
		setName(name);
		setDescription(description);
		setImageUrl(imageUrl);
		setPrice(price);
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public float getApiPrice() {
		return getPrice()/100;
	}
	
	@Override
	public String toString() {
		return "[ID]: " + getId() + "\n"
				+ "[Name]: " + getName() + "\n"
				+ "[Description]: " + getDescription() + "\n"
				+ "[Image url]: " + getImageUrl() + "\n"
				+ "[Price]: " + getPrice()/100;
	}
}
