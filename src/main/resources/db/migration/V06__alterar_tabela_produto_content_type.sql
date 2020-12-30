ALTER TABLE produto
ADD COLUMN content_type VARCHAR(50)
AFTER imagem_bytes;