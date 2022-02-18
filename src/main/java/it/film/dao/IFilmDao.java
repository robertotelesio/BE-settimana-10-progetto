package it.film.dao;

import java.util.List;

import it.film.entity.Film;

public interface IFilmDao {
	
	public void aggiungiFilm(Film film);

	public List<Film> trovaFilmByRegista(String regista);

	public List<Film> trovaTutti();

	public void eliminaFilm(int id);
}