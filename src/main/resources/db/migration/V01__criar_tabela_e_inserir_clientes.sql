CREATE TABLE IF NOT EXISTS cliente (
	id bigint not null unique auto_increment primary key,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    senha varchar(255) not null,
    documento varchar(50) not null unique,
    data_cadastro date not null
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

insert into cliente (id, nome, email, senha, documento, data_cadastro) values
(1, 'Admin', 'admin@gft.com', '$2a$10$lDhQP0PMbXdYfcoYhmJAs.enejcGd8p2LMQJVxEWjwvpsPlDv9Ue6', '123456788', '2020-11-27'), 
(2, 'Lucas', 'lps@gft.com', '$2a$10$9r/foeAc1SbZ1Orp9YjvYeAju5zQk3qtqUE2wZz906mqcYFSA6tHm', '123456789', '2020-11-30'), 
(3, 'Ellon', 'ellon@gft.com', '$2a$10$j41kKXIn64fXrF5WO5lYRuszTjJZJMUYC.yygiGoH8CBRq4gKeXLO', '123456790', '2020-11-29'),
(4, 'Mark', 'mark@gft.com', '$2a$10$bFwAz6uGIMY3yFtYpiInOeaOGqdRdQ.IrFZCtryTEHdBrUyYsDjx.', '123456791', '2020-11-28');

