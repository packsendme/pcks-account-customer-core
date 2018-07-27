package com.packsendme.microservice.dto;

import org.springframework.data.annotation.Id;

public class SubmissionsHistoryDto {
	
	private @Id String id;
	
	private String nameDestination;

	private String collectedFrom;
	
	private String deliveredTo;
	
	private String transportType;

	private String cost;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNameDestination() {
		return nameDestination;
	}

	public void setNameDestination(String nameDestination) {
		this.nameDestination = nameDestination;
	}

	public String getCollectedFrom() {
		return collectedFrom;
	}

	public void setCollectedFrom(String collectedFrom) {
		this.collectedFrom = collectedFrom;
	}

	public String getDeliveredTo() {
		return deliveredTo;
	}

	public void setDeliveredTo(String deliveredTo) {
		this.deliveredTo = deliveredTo;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public SubmissionsHistoryDto(String id, String nameDestination, String collectedFrom, String deliveredTo,
			String transportType, String cost) {
		super();
		this.id = id;
		this.nameDestination = nameDestination;
		this.collectedFrom = collectedFrom;
		this.deliveredTo = deliveredTo;
		this.transportType = transportType;
		this.cost = cost;
	}
	
	
	
}
