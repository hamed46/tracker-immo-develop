package com.trackerimmo.model;

public class Commune {

	private Long id;
	private String code_commune_INSEE;
	private String nom_commune_postal;
	private String code_postal;
	private double latitude;
	private double longitude;
	private String code_commune;
	private String nom_commune;
	private String nom_commune_complet;
	private String code_departement;
	private String nom_departement;
	private String code_region;
	private String nom_region;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode_commune_INSEE() {
		return code_commune_INSEE;
	}

	public void setCode_commune_INSEE(String code_commune_INSEE) {
		this.code_commune_INSEE = code_commune_INSEE;
	}

	public String getNom_commune_postal() {
		return nom_commune_postal;
	}

	public void setNom_commune_postal(String nom_commune_postal) {
		this.nom_commune_postal = nom_commune_postal;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCode_commune() {
		return code_commune;
	}

	public void setCode_commune(String code_commune) {
		this.code_commune = code_commune;
	}

	public String getNom_commune() {
		return nom_commune;
	}

	public void setNom_commune(String nom_commune) {
		this.nom_commune = nom_commune;
	}

	public String getNom_commune_complet() {
		return nom_commune_complet;
	}

	public void setNom_commune_complet(String nom_commune_complet) {
		this.nom_commune_complet = nom_commune_complet;
	}

	public String getCode_departement() {
		return code_departement;
	}

	public void setCode_departement(String code_departement) {
		this.code_departement = code_departement;
	}

	public String getNom_departement() {
		return nom_departement;
	}

	public void setNom_departement(String nom_departement) {
		this.nom_departement = nom_departement;
	}

	public String getCode_region() {
		return code_region;
	}

	public void setCode_region(String code_region) {
		this.code_region = code_region;
	}

	public String getNom_region() {
		return nom_region;
	}

	public void setNom_region(String nom_region) {
		this.nom_region = nom_region;
	}

}
