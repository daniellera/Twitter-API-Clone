package com.cooksys.springassessmentsocialmedia.assessment1team2.entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hashtag {

	@Id
	@GeneratedValue
	private Long id;

	private String label;

	@CreationTimestamp
	private Timestamp firstUsed;

	@UpdateTimestamp
	private Timestamp lastUsed;
	
}
