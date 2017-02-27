package br.com.mkacunha.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.arquivo.Linha;
import br.com.mkacunha.gerador.random.GeradorTexto;
import br.com.mkacunha.gerador.random.Random;
import br.com.mkacunha.gerador.random.RandomList;

@Entity
@Table(name = "filme")
public class Filme {

	@Id
	@Column(name = "id_filme")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ds_titulo", length = 100, nullable = false)
	private String titulo;

	@Column(name = "ano_lancamento", length = 4, nullable = false)
	private String anoLancamento;

	@Column(name = "qt_duracao_aluguel", nullable = false)
	private Integer duracaoAluguel;

	@Column(name = "vl_aluguel", nullable = false, precision = 14, scale = 2)
	private BigDecimal valorAluguel;

	@Column(name = "qt_duracao", nullable = false, precision = 5, scale = 2)
	private BigDecimal duracao;

	@Column(name = "vl_reposicao", nullable = false, precision = 14, scale = 2)
	private BigDecimal valorReposicao;

	@Column(name = "qt_classificacao", nullable = false)
	private Integer classificacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	@Column(name = "ds_caracteristicas_especiais", length = 255, nullable = false)
	private String caracteristicasEspeciais;

	@Lob
	@Column(name = "ds_sinopse", nullable = false)
	private String sinopse;

	@OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FilmeIdioma> idiomas;

	@OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FilmeAtor> atores;

	@OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FilmeCategoria> categorias;

	public static Filme of(String titulo, String anoLancamento, Integer duracaoAluguel, BigDecimal valorAluguel,
			BigDecimal duracao, BigDecimal valorReposicao, Integer classificacao, Date dataUltimaAlteracao,
			String caracteristicasEspeciais, String sinopse, List<FilmeIdioma> idiomas, List<FilmeAtor> atores,
			List<FilmeCategoria> categorias) {
		Filme filme = new Filme();
		filme.setTitulo(titulo);
		filme.setAnoLancamento(anoLancamento);
		filme.setDuracaoAluguel(duracaoAluguel);
		filme.setValorAluguel(valorAluguel);
		filme.setDuracao(duracao);
		filme.setValorReposicao(valorReposicao);
		filme.setClassificacao(classificacao);
		filme.setDataUltimaAlteracao(dataUltimaAlteracao);
		filme.setCaracteristicasEspeciais(caracteristicasEspeciais);
		filme.setSinopse(sinopse);
		filme.setidiomas(idiomas);
		filme.setAtores(atores);
		filme.setCategorias(categorias);
		return filme;
	}

	public static List<Filme> list(int quantidade, List<Idioma> idiomas, List<Ator> atores,
			List<Categoria> categorias) {
		List<Filme> filmes = new ArrayList<>();

		Random random = new Random();

		GeradorTexto geradorTexto = new GeradorTexto();

		List<Linha> titulos = new Arquivo(Arquivo.FILMES).randon(quantidade);

		for (int i = 0; i < quantidade; i++) {
			Filme filme = Filme.of(titulos.get(i).get(), String.valueOf(random.nextInt(1990, 2016)),
					random.nextInt(1, 5), random.nextMonetaryValue(5, 15), random.nextMonetaryValue(1, 3),
					random.nextMonetaryValue(10, 50), random.nextInt(4, 18), null, geradorTexto.get(15, 255),
					geradorTexto.get(300, 1000), null, null, null);

			List<Idioma> listIdiomas = new RandomList<Idioma>().list(idiomas, 3, 5);
			List<Ator> listAtores = new RandomList<Ator>().list(atores, 5, 15);
			List<Categoria> listCategorias = new RandomList<Categoria>().list(categorias, 2, 6);

			listIdiomas.forEach(idioma -> filme.add(idioma));
			listAtores.forEach(ator -> filme.add(ator));
			listCategorias.forEach(categoria -> filme.add(categoria));

			filmes.add(filme);
		}

		return filmes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(String anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public Integer getDuracaoAluguel() {
		return duracaoAluguel;
	}

	public void setDuracaoAluguel(Integer duracaoAluguel) {
		this.duracaoAluguel = duracaoAluguel;
	}

	public BigDecimal getValorAluguel() {
		return valorAluguel;
	}

	public void setValorAluguel(BigDecimal valorAluguel) {
		this.valorAluguel = valorAluguel;
	}

	public BigDecimal getDuracao() {
		return duracao;
	}

	public void setDuracao(BigDecimal duracao) {
		this.duracao = duracao;
	}

	public BigDecimal getValorReposicao() {
		return valorReposicao;
	}

	public void setValorReposicao(BigDecimal valorReposicao) {
		this.valorReposicao = valorReposicao;
	}

	public Integer getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Integer classificacao) {
		this.classificacao = classificacao;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public String getCaracteristicasEspeciais() {
		return caracteristicasEspeciais;
	}

	public void setCaracteristicasEspeciais(String caracteristicasEspeciais) {
		this.caracteristicasEspeciais = caracteristicasEspeciais;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public List<FilmeIdioma> getidiomas() {
		return idiomas;
	}

	public void setidiomas(List<FilmeIdioma> idiomas) {
		this.idiomas = idiomas;
	}

	public List<FilmeAtor> getAtores() {
		return atores;
	}

	public void setAtores(List<FilmeAtor> atores) {
		this.atores = atores;
	}

	public List<FilmeCategoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<FilmeCategoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public String toString() {
		return this.id + " - " + this.titulo + " - " + this.idiomas + " - " + this.atores + " - " + this.categorias;
	}

	public void add(Ator ator) {
		if (atores == null)
			atores = new ArrayList<>();
		atores.add(FilmeAtor.of(this, ator));
	}

	public void add(Idioma idioma) {
		if (idiomas == null)
			idiomas = new ArrayList<>();
		idiomas.add(FilmeIdioma.of(this, idioma));
	}

	public void add(Categoria categoria) {
		if (categorias == null)
			categorias = new ArrayList<>();
		categorias.add(FilmeCategoria.of(this, categoria));
	}
}
