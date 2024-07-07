package com.src.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movie {

	private Integer movieId;
	private String movieName;
	private String director;
	private String genre;
	private String hero;
	private String year;
	private Boolean nationalAward;
}
