CREATE TABLE IF NOT EXISTS venda (
	id bigint not null unique auto_increment primary key,
    cliente_id bigint,
    data_compra date not null,
    total_compra decimal(10,2),
    
    CHECK (total_compra >= 0),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = innoDB default charset = utf8mb4;

INSERT INTO venda (cliente_id, data_compra, total_compra) values
(1, '2020-11-29', 12000.00),
(2, '2020-11-30', 7800.50);

CREATE TABLE IF NOT EXISTS venda_produto (
	id bigint not null unique auto_increment primary key,
    venda_id bigint not null,
    produto_id bigint,
    
    FOREIGN KEY (venda_id) REFERENCES venda(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = innoDB default charset = utf8mb4;

INSERT INTO venda_produto (venda_id, produto_id) values
(1, 1),
(1, 2),
(2, 3),
(2, 4);