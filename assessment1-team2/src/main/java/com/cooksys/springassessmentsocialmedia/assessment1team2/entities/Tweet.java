package com.cooksys.springassessmentsocialmedia.assessment1team2.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Tweet {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;

	@CreationTimestamp
	private Timestamp posted;

	private boolean deleted = false;

	private String content;

	@ManyToMany(mappedBy = "likedTweet")
	private List<User> likes = new ArrayList<>();

	@ManyToMany(mappedBy = "mentions")
	private List<User> mentions = new ArrayList<>();

	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "reply_to_id")
	private Tweet inReplyTo;

	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "repost_id")
	private Tweet repostOf;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tweet_hashtags",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
	private List<Hashtag> hashtags = new ArrayList<>();

}
