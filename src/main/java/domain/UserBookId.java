package domain;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class UserBookId implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long book;
    private Long user;
}
