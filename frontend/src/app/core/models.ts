export type StatusJogo = 'QUERO_JOGAR'|'JOGANDO'|'CONCLUIDO'|'ABANDONADO';
export interface Usuario {id:number;nome:string;email:string}
export interface AuthResponse {token:string;tipo:string;expiracaoMs:number;usuario:Usuario}
export interface Jogo {id:number;titulo:string;desenvolvedora:string|null;plataforma:string|null;genero:string|null;anoLancamento:number|null;capaUrl:string|null;status:StatusJogo;avaliacao:number|null;resenha:string|null;criadoEm:string;atualizadoEm:string}
export interface JogoPayload {titulo:string;desenvolvedora:string;plataforma:string;genero:string;anoLancamento:number|null;capaUrl:string;status:StatusJogo;avaliacao:number|null;resenha:string}
export interface Dashboard {total:number;concluidos:number;jogando:number;queroJogar:number;abandonados:number;mediaAvaliacao:number;recentes:Jogo[]}
