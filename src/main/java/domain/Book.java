package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Book(String name, String isbn, double price) {
		this.name = name;
		this.isbn = isbn;
		this.purchasePrice = price;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonIgnore
	@Setter
	@OneToMany(mappedBy = "book")
	private List<Location> locations = new ArrayList<>();
	
	@JsonIgnore
	@Setter
	@OneToMany(mappedBy = "book")
	private List<BookAuthor> bookAuthors = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<UserBook> userBooks = new ArrayList<>();
	
	@Column
	private String name;
	
	@Column(unique = true)
	private String isbn;
	
	@Column
	private double purchasePrice;
}
