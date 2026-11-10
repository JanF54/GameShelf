package br.com.gameshelf.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="jogos")
public class Jogo {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=140) private String titulo;
 @Column(length=120) private String desenvolvedora;
 @Column(length=80) private String plataforma;
 @Column(length=80) private String genero;
 private Integer anoLancamento;
 @Column(length=1000) private String capaUrl;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private StatusJogo status=StatusJogo.QUERO_JOGAR;
 private Double avaliacao;
 @Column(length=3000) private String resenha;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="usuario_id",nullable=false) private Usuario usuario;
 @Column(nullable=false) private LocalDateTime criadoEm=LocalDateTime.now();
 @Column(nullable=false) private LocalDateTime atualizadoEm=LocalDateTime.now();
 protected Jogo() {}
 public Jogo(String titulo,String desenvolvedora,String plataforma,String genero,Integer anoLancamento,String capaUrl,StatusJogo status,Double avaliacao,String resenha,Usuario usuario){this.titulo=titulo;this.desenvolvedora=desenvolvedora;this.plataforma=plataforma;this.genero=genero;this.anoLancamento=anoLancamento;this.capaUrl=capaUrl;this.status=status;this.avaliacao=avaliacao;this.resenha=resenha;this.usuario=usuario;}
 public void atualizar(String titulo,String desenvolvedora,String plataforma,String genero,Integer anoLancamento,String capaUrl,StatusJogo status,Double avaliacao,String resenha){this.titulo=titulo;this.desenvolvedora=desenvolvedora;this.plataforma=plataforma;this.genero=genero;this.anoLancamento=anoLancamento;this.capaUrl=capaUrl;this.status=status;this.avaliacao=avaliacao;this.resenha=resenha;this.atualizadoEm=LocalDateTime.now();}
 public Long getId(){return id;} public String getTitulo(){return titulo;} public String getDesenvolvedora(){return desenvolvedora;} public String getPlataforma(){return plataforma;} public String getGenero(){return genero;} public Integer getAnoLancamento(){return anoLancamento;} public String getCapaUrl(){return capaUrl;} public StatusJogo getStatus(){return status;} public Double getAvaliacao(){return avaliacao;} public String getResenha(){return resenha;} public Long getUsuarioId(){return usuario.getId();} public LocalDateTime getCriadoEm(){return criadoEm;} public LocalDateTime getAtualizadoEm(){return atualizadoEm;}
}
