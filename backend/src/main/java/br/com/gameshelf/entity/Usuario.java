package br.com.gameshelf.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="usuarios", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class Usuario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String nome;
 @Column(nullable=false,unique=true,length=160) private String email;
 @Column(nullable=false) private String senha;
 @Column(nullable=false) private LocalDateTime criadoEm=LocalDateTime.now();
 protected Usuario() {}
 public Usuario(String nome,String email,String senha){this.nome=nome;this.email=email;this.senha=senha;}
 public Long getId(){return id;} public String getNome(){return nome;} public String getEmail(){return email;} public String getSenha(){return senha;} public LocalDateTime getCriadoEm(){return criadoEm;}
}
