import { Injectable,signal } from '@angular/core';import { HttpClient } from '@angular/common/http';import { tap } from 'rxjs';import { AuthResponse,Usuario } from './models';
const API='http://localhost:8080/api';
@Injectable({providedIn:'root'}) export class AuthService {
 private readonly tokenKey='gameshelf_token';private readonly userKey='gameshelf_user';usuario=signal<Usuario|null>(this.readUser());
 constructor(private http:HttpClient){}
 private readUser():Usuario|null{try{const v=localStorage.getItem(this.userKey);return v?JSON.parse(v) as Usuario:null}catch{return null}}
 token():string|null{return localStorage.getItem(this.tokenKey)}
 autenticado():boolean{return !!this.token()}
 login(email:string,senha:string){return this.http.post<AuthResponse>(`${API}/auth/login`,{email,senha}).pipe(tap(r=>this.save(r)))}
 registro(nome:string,email:string,senha:string){return this.http.post<AuthResponse>(`${API}/auth/registro`,{nome,email,senha}).pipe(tap(r=>this.save(r)))}
 private save(r:AuthResponse){localStorage.setItem(this.tokenKey,r.token);localStorage.setItem(this.userKey,JSON.stringify(r.usuario));this.usuario.set(r.usuario)}
 sair(){localStorage.removeItem(this.tokenKey);localStorage.removeItem(this.userKey);this.usuario.set(null)}
}
