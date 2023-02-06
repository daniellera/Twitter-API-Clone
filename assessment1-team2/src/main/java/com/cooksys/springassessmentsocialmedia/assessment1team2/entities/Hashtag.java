package com.cooksys.springassessmentsocialmedia.assessment1team2.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

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

	@Column(nullable = false, unique = true)
	private String label;

	@CreationTimestamp
	private Timestamp firstUsed;

	@UpdateTimestamp
	private Timestamp lastUsed;

	@JsonIgnore
	@ManyToMany(mappedBy = "hashtags", cascade = CascadeType.ALL)
	private List<Tweet> tweets = new ArrayList<>();
	
}
