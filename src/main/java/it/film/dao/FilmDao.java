package it.film.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import it.film.entity.Film;

public class FilmDao implements IFilmDao {

	private EntityManager em = EntityManagerHelper.getEntityManager();
	public void aggiungiFilm(Film film) {
		em.getTransaction().begin();
		em.persist(film);
		em.getTransaction().commit();
	}
	
	public List<Film> trovaFilmByRegista(String regista) {
		Query query = em.createQuery("SELECT f FROM Film f WHERE f.regista = '" + regista + "'");
		List<Film> film = query.getResultList();
		return film;
	}


	public List<Film> trovaTutti() {
		Query query = em.createNamedQuery("trovatutti");
		List<Film> listaFilm = query.getResultList();
		return listaFilm;
	}


	public void eliminaFilm(int id) {
		em.getTransaction().begin();
		em.remove(em.find(Film.class, id));
		em.getTransaction().commit();
	}
}