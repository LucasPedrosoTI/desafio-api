CREATE TABLE IF NOT EXISTS fornecedor (
	id bigint not null unique auto_increment primary key,
    nome varchar(100) not null,
    cnpj varchar(14) not null unique
) ENGINE = innoDB default charset = utf8mb4;

INSERT INTO fornecedor (nome, cnpj) values
('Apple', '89000895000178'),
('Samsung', '28023099000166');

CREATE TABLE IF NOT EXISTS produto (
	id bigint not null unique auto_increment primary key,
    nome varchar(100) not null,
    codigo_produto varchar(25) not null unique,
    valor decimal(10,2) not null,
    promocao boolean not null,
    valor_promo decimal(10,2),
    categoria varchar(25) not null,
    imagem varchar(255),
    quantidade bigint not null,
    fornecedor_id bigint not null,
    
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT chk_valores CHECK (valor >= 0 AND valor_promo >= 0 AND quantidade >= 0) 
) ENGINE = innoDB default charset = utf8mb4;

insert into produto (nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, fornecedor_id) values
('iPhone 11', 'APL01', 5000.00, false, null, 'SMARTPHONE', 'iphone11.png', 100, 1),
('iPad Pro', 'APL02', 7000.00, true, 6500.00, 'TABLET', 'ipad-pro.png', 50, 1),
('Galaxy S20', 'SAM01', 3000.00, false, null, 'SMARTPHONE', 'galaxy-s20.png', 5, 2),
('Galaxy Tab S7', 'SAM02', 4800.50, true, 4200.00, 'TABLET', 'galaxy-tab-s7.png', 2, 2);