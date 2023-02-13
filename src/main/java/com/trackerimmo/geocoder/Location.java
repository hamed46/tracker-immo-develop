package com.trackerimmo.geocoder;

public class Location {

	public Location(Address formattedAddress, double distance) {
		this.address = formattedAddress;
		this.distance = distance;
	}
	
	public Location() {
		this.address = new Address();
	}

	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	private float latitude;

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	private float longitude;

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	private double distance;

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	
	
	public String getPostcode() {
		return address.getPostcode();
	}

	public void setPostcode(String postcode) {
		this.address.setPostcode(postcode);
	}

	public String getCountry() {
		return address.getCountry();
	}

	public void setCountry(String country) {
		this.address.setCountry(country);
	}

	public String getState() {
		return address.getState();
	}

	public void setState(String state) {
		this.address.setState(state);
	}

	public String getDistrict() {
		return address.getDistrict();
	}

	public void setDistrict(String district) {
		this.address.setDistrict(district);
	}

	public String getSettlement() {
		return address.getSettlement();
	}

	public void setSettlement(String settlement) {
		this.address.setSettlement(settlement);
	}

	public String getSuburb() {
		return address.getSuburb();
	}

	public void setSuburb(String suburb) {
		this.address.setSuburb(suburb);
	}

	public String getStreet() {
		return address.getStreet();
	}

	public void setStreet(String street) {
		this.address.setStreet(street);
	}

	public String getHouse() {
		return address.getHouse();
	}

	public void setHouse(String house) {
		this.address.setHouse(house);
	}

	private String formattedAddress;

	public String getFormattedAddress() {
//    	AddressFormatter formatter = Context.getBean(AddressFormatter.class);
//    	return formatter.format(this);
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
}
