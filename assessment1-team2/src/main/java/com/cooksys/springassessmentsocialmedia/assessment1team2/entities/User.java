package com.cooksys.springassessmentsocialmedia.assessment1team2.entities;

import java.sql.Timestamp;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.BadRequestException;

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

	@CreationTimestamp
	@Column(nullable = false)
	private Timestamp joined;

	private boolean deleted = false;

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
