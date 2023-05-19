package domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_book")
@IdClass(UserBookId.class)
public class UserBook implements Serializable{
	private static final long serialVersionUID = 1L;

	public UserBook(User user, Book book, boolean isFavorite) {
		this.user = user;
		this.book = book;
		this.isFavorite = isFavorite;
	}
	
	@JsonIgnore
	@Id
	@ManyToOne
	private User user;
	
	@JsonIgnore
	@Id
	@ManyToOne
	private Book book;
	
	@Column
	@Setter
	private boolean isFavorite;
	
}
