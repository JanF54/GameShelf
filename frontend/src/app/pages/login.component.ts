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
        <span class="eyebrow">BEM-VINDO DE VOLTA</span>

        <h1>
          Entre na sua<br>
          <span class="lime">biblioteca.</span>
        </h1>

        <p class="muted">Continue registrando suas aventuras.</p>

        <form [formGroup]="form" (ngSubmit)="entrar()">
          <label>E-mail</label>
          <input
            type="email"
            formControlName="email"
            placeholder="voce@email.com"
          >

          <small
            class="field-error"
            *ngIf="form.controls.email.touched && form.controls.email.invalid"
          >
            Informe um e-mail válido.
          </small>

          <label>Senha</label>
          <input
            type="password"
            formControlName="senha"
            placeholder="Sua senha"
          >

          <small
            class="field-error"
            *ngIf="form.controls.senha.touched && form.controls.senha.invalid"
          >
            Informe sua senha.
          </small>

          <p class="error" *ngIf="erro">{{ erro }}</p>

          <button
            class="primary full"
            [disabled]="form.invalid || carregando"
          >
            {{ carregando ? 'Entrando...' : 'Entrar na GameShelf →' }}
          </button>
        </form>

        <p class="auth-foot">
          Ainda não tem uma conta?
          <a routerLink="/registro">Criar conta</a>
        </p>
      </div>

      <div class="auth-note">CATALOGUE. AVALIE. REVIVA.</div>
    </div>
  `
})
export class LoginComponent {
  form;
  erro = '';
  carregando = false;

  constructor(
    fb: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {
    this.form = fb.nonNullable.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required]
    });
  }

  entrar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.carregando = true;
    this.erro = '';

    const { email, senha } = this.form.getRawValue();

    this.auth.login(email, senha).subscribe({
      next: (res) => {
        this.carregando = false;
        // Mantém o fluxo de sucesso já existente no componente.
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.carregando = false;
        this.erro = err.error?.message || 'E-mail ou senha inválidos.';
      }
    });
  }
}