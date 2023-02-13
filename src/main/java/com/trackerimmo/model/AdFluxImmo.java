package com.trackerimmo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AdFluxImmo extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1234567L;

	@JsonProperty("ads_type")
	private AdsType adsType;

	private double price;

	@JsonProperty("original_price")
	private double originalPrice;

	private double area;

	private String city;

	@JsonProperty("land_surface")
	private double landSurface;

	private int rooms;

	private int bedrooms;

	@Column(length = 50)
	private String title;

	private String description;

	@JsonProperty("postal_code")
	private String postalCode;

	private double longitude;

	private double latitude;

	private boolean agency;

	@Column(length = 50)
	@JsonProperty("agency_name")
	private String agencyName;

	@Type(JsonType.class)
	@JsonProperty("agency_details")
	@Column(columnDefinition = "jsonb")
	private Map<String, Object> agencyDetails = new HashMap<>();

	private boolean available;

	@JsonProperty("last_checked_at")
	private Date lastCheckedAt;

	@JsonProperty("coloc_friendly")
	private boolean colocFriendly;

	private boolean elevator;

	private int floor;

	private boolean furnished;

	private boolean balcony;

	private boolean terrace;

	@JsonProperty("is_renovated")
	private boolean isRenovated;

	@JsonProperty("insee_code")
	private String inseeCode;

	@JsonProperty("mandat_exclusif")
	private boolean mandatExclusif;

	@JsonProperty("city_longitude")
	private double cityLongitude;

	@JsonProperty("city_latitude")
	private double cityLatitude;

	@JsonProperty("is_to_renovate")
	private boolean isToRenovate;

	private boolean pool;

	private boolean parking;

	private boolean jardin;

	@Type(ListArrayType.class)
	@JsonProperty("images_url")
	@Column(name = "images_url", columnDefinition = "text[]")
	private List<String> imagesUrl;

	@JsonProperty("is_new")
	private boolean isNew;

	private String website;

	@JsonProperty("property_type")
	private String propertyType;

	@JsonProperty("site_id")
	private String siteId;

	@JsonProperty("published_at")
	private Date publishedAt;

	private Date createdAt;

	@Type(JsonType.class)
	@Column(columnDefinition = "jsonb")
	private Map<String, Object> others = new HashMap<>();

	@Type(JsonType.class)
	@Column(columnDefinition = "jsonb")
	private Map<String, Object> building = new HashMap<>();

	@JsonProperty("sous_loc")
	private boolean sousLoc;

	@JsonProperty("short_term")
	private boolean shortTerm;

	@JsonProperty("anomaly_detected")
	private boolean anomalyDetected;

	private int duplicate;

	@JsonProperty("unique_id")
	private long uniqueId;

	private String url;

	private String dpe;

	private String ges;

	@Type(JsonType.class)
	@Column(name = "lots", columnDefinition = "jsonb")
	private List<Map<String, Object>> lots;

	public AdsType getAdsType() {
		return adsType;
	}

	public void setAdsType(AdsType adsType) {
		this.adsType = adsType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLandSurface() {
		return landSurface;
	}

	public void setLandSurface(double landSurface) {
		this.landSurface = landSurface;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public int getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public boolean isAgency() {
		return agency;
	}

	public void setAgency(boolean agency) {
		this.agency = agency;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Map<String, Object> getAgencyDetails() {
		return agencyDetails;
	}

	public void setAgencyDetails(Map<String, Object> agencyDetails) {
		this.agencyDetails = agencyDetails;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Date getLastCheckedAt() {
		return lastCheckedAt;
	}

	public void setLastCheckedAt(Date lastCheckedAt) {
		this.lastCheckedAt = lastCheckedAt;
	}

	public boolean isColocFriendly() {
		return colocFriendly;
	}

	public void setColocFriendly(boolean colocFriendly) {
		this.colocFriendly = colocFriendly;
	}

	public boolean isElevator() {
		return elevator;
	}

	public void setElevator(boolean elevator) {
		this.elevator = elevator;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public boolean isFurnished() {
		return furnished;
	}

	public void setFurnished(boolean furnished) {
		this.furnished = furnished;
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public boolean isTerrace() {
		return terrace;
	}

	public void setTerrace(boolean terrace) {
		this.terrace = terrace;
	}

	public boolean isRenovated() {
		return isRenovated;
	}

	public void setRenovated(boolean isRenovated) {
		this.isRenovated = isRenovated;
	}

	public String getInseeCode() {
		return inseeCode;
	}

	public void setInseeCode(String inseeCode) {
		this.inseeCode = inseeCode;
	}

	public boolean isMandatExclusif() {
		return mandatExclusif;
	}

	public void setMandatExclusif(boolean mandatExclusif) {
		this.mandatExclusif = mandatExclusif;
	}

	public double getCityLongitude() {
		return cityLongitude;
	}

	public void setCityLongitude(double cityLongitude) {
		this.cityLongitude = cityLongitude;
	}

	public double getCityLatitude() {
		return cityLatitude;
	}

	public void setCityLatitude(double cityLatitude) {
		this.cityLatitude = cityLatitude;
	}

	public boolean isToRenovate() {
		return isToRenovate;
	}

	public void setToRenovate(boolean isToRenovate) {
		this.isToRenovate = isToRenovate;
	}

	public boolean isPool() {
		return pool;
	}

	public void setPool(boolean pool) {
		this.pool = pool;
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public boolean isJardin() {
		return jardin;
	}

	public void setJardin(boolean jardin) {
		this.jardin = jardin;
	}

	public List<String> getImagesUrl() {
		return imagesUrl;
	}

	public void setImagesUrl(List<String> imagesUrl) {
		this.imagesUrl = imagesUrl;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Map<String, Object> getOthers() {
		return others;
	}

	public void setOthers(Map<String, Object> others) {
		this.others = others;
	}

	public Map<String, Object> getBuilding() {
		return building;
	}

	public void setBuilding(Map<String, Object> building) {
		this.building = building;
	}

	public boolean isSousLoc() {
		return sousLoc;
	}

	public void setSousLoc(boolean sousLoc) {
		this.sousLoc = sousLoc;
	}

	public boolean isShortTerm() {
		return shortTerm;
	}

	public void setShortTerm(boolean shortTerm) {
		this.shortTerm = shortTerm;
	}

	public boolean isAnomalyDetected() {
		return anomalyDetected;
	}

	public void setAnomalyDetected(boolean anomalyDetected) {
		this.anomalyDetected = anomalyDetected;
	}

	public int getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(int duplicate) {
		this.duplicate = duplicate;
	}

	public long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(long uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDpe() {
		return dpe;
	}

	public void setDpe(String dpe) {
		this.dpe = dpe;
	}

	public String getGes() {
		return ges;
	}

	public void setGes(String ges) {
		this.ges = ges;
	}

	public List<Map<String, Object>> getLots() {
		return lots;
	}

	public void setLots(List<Map<String, Object>> lots) {
		this.lots = lots;
	}

}
