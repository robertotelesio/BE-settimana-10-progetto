package it.film.dto;

public class FilmDto {

	private String titolo;
	private int anno;
	private String regista;
	private String genere;
	private String incasso;

	public String getTitolo() {
		return titolo;
	}

	public int getAnno() {
		return anno;
	}

	public String getRegista() {
		return regista;
	}

	public String getGenere() {
		return genere;
	}

	public String getIncasso() {
		return incasso;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public void setRegista(String regista) {
		this.regista = regista;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public void setIncasso(String incasso) {
		this.incasso = incasso;
	}
}