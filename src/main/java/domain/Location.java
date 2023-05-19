package domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Location(Book book, int placecode1, int placecode2, String name) {
		this.book = book;
		this.placecode1 = placecode1;
		this.placecode2 = placecode2;
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Book book;
	
	@Column
	private int placecode1;

	@Column
	private int placecode2;
	
	@Column
	private String name;
}
