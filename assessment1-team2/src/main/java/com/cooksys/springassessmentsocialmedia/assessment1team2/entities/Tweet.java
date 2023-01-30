package com.cooksys.springassessmentsocialmedia.assessment1team2.entities;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

	private Timestamp posted;

	private boolean deleted;

	private String content;

	@ManyToMany(mappedBy = "likedTweet")
	private List<User> likes;

	@ManyToMany(mappedBy = "mentions")
	private List<User> mentions;

	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies;

	@ManyToOne
	@JoinColumn(name = "reply_to_id")
	private Tweet inReplyTo;

	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts;

	@ManyToOne
	@JoinColumn(name = "repost_id")
	private Tweet repostOf;

}
