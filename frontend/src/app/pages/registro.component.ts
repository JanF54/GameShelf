import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthService } from '../core/auth.service';

@Component({
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, NgIf],
  template: `
    <div class="auth-page">
      <div class="auth-brand">
        <span class="brand-mark">G</span>
        <span>game<span class="lime">shelf</span></span>
      </div>

      <div class="auth-card">
        <span class="eyebrow">CRIE SEU ESPAÇO</span>

        <h1>
          Sua jornada<br>
          <span class="lime">começa aqui.</span>
        </h1>

        <p class="muted">
          Uma biblioteca para cada fase da sua vida gamer.
        </p>

        <form [formGroup]="form" (ngSubmit)="cadastrar()">
          <label>Nome</label>
          <input
            formControlName="nome"
            placeholder="Como podemos te chamar?"
          >

          <label>E-mail</label>
          <input
            type="email"
            formControlName="email"
            placeholder="voce@email.com"
          >

          <label>Senha</label>
          <input
            type="password"
            formControlName="senha"
            placeholder="Mínimo de 8 caracteres"
          >

          <small
            class="field-error"
            *ngIf="form.controls.senha.touched && form.controls.senha.invalid"
          >
            A senha deve ter pelo menos 8 caracteres.
          </small>

          <p class="error" *ngIf="erro">{{ erro }}</p>

          <button
            class="primary full"
            [disabled]="form.invalid || carregando"
          >
            {{ carregando ? 'Criando...' : 'Criar minha conta →' }}
          </button>
        </form>

        <p class="auth-foot">
          Já tem uma conta?
          <a routerLink="/login">Entrar</a>
        </p>
      </div>
    </div>
  `
})
export class RegistroComponent {
  form;
  erro = '';
  carregando = false;

  constructor(
    fb: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {
    this.form = fb.nonNullable.group({
      nome: ['', [Validators.required, Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(72)
      ]]
    });
  }

  cadastrar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.carregando = true;
    this.erro = '';

    const { nome, email, senha } = this.form.getRawValue();

    this.auth.registro(nome, email, senha).subscribe({
      next: () => {
        this.carregando = false;
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.carregando = false;
        this.erro = err.error?.message || 'Não foi possível criar a conta.';
      }
    });
  }
}