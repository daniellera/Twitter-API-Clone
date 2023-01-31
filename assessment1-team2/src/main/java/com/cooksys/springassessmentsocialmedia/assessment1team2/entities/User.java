package com.cooksys.springassessmentsocialmedia.assessment1team2.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
@Data
@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@CreationTimestamp
	private Timestamp joined;

	private boolean deleted;

	private String firstName;

	private String lastName;

	private String email;

	private String phoneNumber;

	@ManyToMany
	@JoinTable(name = "user_likes", joinColumns = { @JoinColumn(name = "tweet_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	private List<Tweet> likedTweet;

	@ManyToMany
	@JoinTable(name = "user_mentions", joinColumns = { @JoinColumn(name = "tweet_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	private List<Tweet> mentions;

	@ManyToMany
	@JoinTable(name = "follower_following", joinColumns = { @JoinColumn(name = "follower_id") }, inverseJoinColumns = {
			@JoinColumn(name = "following_id") })
	private List<User> followers;

	@ManyToMany(mappedBy = "followers")
	private List<User> following;

	@Embedded
	private Credentials credentials;

	@Embedded
	private Profile profile;

}
