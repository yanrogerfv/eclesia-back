DROP TABLE if exists escala;
DROP TABLE if exists log;
DROP TABLE if exists levita;
DROP TABLE if exists instrumentos;
DROP TABLE if exists musica;

CREATE TABLE escala
(
	escala_id	uuid primary key default gen_random_uuid(),
	data		timestamp without time zone,
	titulo		text not null,
	ministro_id	uuid not null,
	baixo_id	uuid,
	bateria_id	uuid,
	guitarra_id	uuid,
	teclado_id	uuid,
	violao_id	uuid,
	observacoes	text,
	quarta		boolean default false,
	domingo		boolean default false,
	especial	boolean default false,
	foreign key (ministro_id) references levita (levita_id),
	foreign key (baixo_id) references levita (levita_id),
	foreign key (bateria_id) references levita (levita_id),
	foreign key (guitarra_id) references levita (levita_id),
	foreign key (teclado_id) references levita (levita_id),
	foreign key (violao_id) references levita (levita_id)
);

CREATE TABLE levita
(
	levita_id	uuid primary key default gen_random_uuid(),
	nome		text not null,
	contato		text,
	email		text,
	disponivel	boolean default true
);

CREATE TABLE instrumentos
(
    numero      SERIAL primary key,
    nome        text not null
);

CREATE TABLE musica
(
    musica_id   uuid primary key default gen_random_uuid(),
    nome        text not null,
    link        text,
    cifra       text
);

CREATE TABLE log
(
    log_id         uuid primary key default gen_random_uuid(),
    referencia_id  uuid,
    tipo_log       text not null,
    action         text,
    description    text,
    object         jsonb
);
