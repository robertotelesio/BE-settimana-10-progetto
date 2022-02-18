package it.film.rest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.film.dao.FilmDao;
import it.film.dto.FilmDto;
import it.film.entity.Film;

@RequestMapping("/film")
@RestController
@Api(value = "FilmRest", tags = "Cerca, elimina ed aggiungi film!")
public class FilmRest {

	Logger log = LoggerFactory.getLogger(getClass());

	FilmDao filmdao;

	public FilmDao getFilmDao() {
		if (filmdao == null) {
			filmdao = new FilmDao();
		}
		return filmdao;
	}

	@GetMapping
	@ApiOperation(value = "Mostra la lista dei film presenti", produces = "application/json", response = Film.class, responseContainer = "List")
	public ResponseEntity<List<Film>> showAllFilms() {
		try {
			log.info("La lista dei film e' pronta!");
			return new ResponseEntity<List<Film>>(getFilmDao().trovaTutti(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("c'� stato un errore nel recupero dati!");
			e.printStackTrace();
			return new ResponseEntity<List<Film>>((List<Film>) null, HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Elimina un Film dal db")
	public ResponseEntity<String> deleteFilm(@PathVariable int id) {
		try {
			getFilmDao().eliminaFilm(id);
			log.info("Eliminato!");
			return new ResponseEntity<String>("Eliminazione avvenuta con successo!", HttpStatus.OK);
		} catch (Exception e) {
			log.error("NON eliminato!");
			e.printStackTrace();
			return new ResponseEntity<String>("Eliminazione non avvenuta!", HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@PostMapping
	@ApiOperation(value = "Arricchisci il nostro database dei film con i tuoi titoli!", consumes = "application/json")
	public ResponseEntity<String> aggiungiFilm(@RequestBody FilmDto filmdto) {
		Film film = new Film();
		String importoCriptato = BCrypt.hashpw(filmdto.getIncasso(), BCrypt.gensalt());

		film.setTitolo(filmdto.getTitolo());
		film.setAnno(filmdto.getAnno());
		film.setGenere(filmdto.getGenere());
		film.setIncasso(importoCriptato);
		film.setRegista(filmdto.getRegista());

		try {
			getFilmDao().aggiungiFilm(film);
			log.info("Hai inserito un film! I dati sensibili sono stati crittografati");
			return new ResponseEntity<String>("Inserimento completato! I dati sensibili sono stati crittografati", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Errore!");
			e.printStackTrace();
			return new ResponseEntity<String>("Impossibile inserire!", HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@GetMapping("/byregista")
	@ApiOperation(value = "Cerca tutti i film prodotti da un regista!", produces = "application/json", response = Film.class, responseContainer = "List")
	public ResponseEntity<List<Film>> cercaByRegista(@RequestParam String regista) {
		try {
			log.info("Visualizzazione dei film del regista!");
			return new ResponseEntity<List<Film>>(getFilmDao().trovaFilmByRegista(regista), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Errore!");
			e.printStackTrace();
			return new ResponseEntity<List<Film>>(getFilmDao().trovaFilmByRegista(regista),
					HttpStatus.METHOD_NOT_ALLOWED);
		}
	}
}