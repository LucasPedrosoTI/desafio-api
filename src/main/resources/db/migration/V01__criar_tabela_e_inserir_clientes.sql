CREATE TABLE IF NOT EXISTS cliente (
	id bigint not null unique auto_increment primary key,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    senha varchar(255) not null,
    documento varchar(50) not null unique,
    data_cadastro date not null
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

insert into cliente (nome, email, senha, documento, data_cadastro) values 
('Lucas', 'lps@gft.com', '$2a$10$LhB/om0e2w7zkekXORg0SuAHWCTGxzBQcLmU3wffGBnv6ZQ/A8pcu
', '123456789', '2020-11-30'), 
('Ellon', 'ellon@gft.com', '$2a$10$6AlHJja8efafa2aKVDgKP.jaO6h8KmFvlw7BjcwwUVBmx.FgKXhCC
', '123456790', '2020-11-29'),
('Mark', 'mark@gft.com', '$2a$10$SKYyccm84bKnoBgJbYcai.tTnFRfHkmOg6C.J39JWvto.GBbnDeMC
', '123456791', '2020-11-28');

