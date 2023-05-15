package domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	
	@Id
	@ManyToOne
	private User user;
	
	@Id
	@ManyToOne
	private Book book;
	
	@Column
	private boolean isFavorite;
	
}
