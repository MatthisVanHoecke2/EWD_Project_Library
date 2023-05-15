package domain;

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
@Table(name = "book_author")
@IdClass(BookAuthorId.class)
public class BookAuthor {
	
	public BookAuthor(Book book, Author author) {
		this.book = book;
		this.author = author;
	}
	
	@Id
	@ManyToOne
	private Book book;
	
	@Id
	@ManyToOne
	private Author author;
}
