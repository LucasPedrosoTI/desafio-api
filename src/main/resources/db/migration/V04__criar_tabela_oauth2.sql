CREATE TABLE oauth_client_details (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  client_id VARCHAR(50) NOT NULL,
  resource_ids VARCHAR(50) NOT NULL,
  client_secret VARCHAR(255) NOT NULL,
  scope VARCHAR(50) NOT NULL,
  authorized_grant_types VARCHAR(255) NOT NULL,
  web_server_redirect_uri VARCHAR(255) NOT NULL,
  authorities VARCHAR(50) NOT NULL,
  access_token_validity INTEGER NOT NULL,
  refresh_token_validity INTEGER NOT NULL,
  additional_information VARCHAR (255),
  autoapprove BOOLEAN
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
insert into `oauth_client_details`(
    `client_id`,
    `resource_ids`,
    `client_secret`,
    `scope`,
    `authorized_grant_types`,
    `web_server_redirect_uri`,
    `authorities`,
    `access_token_validity`,
    `refresh_token_validity`,
    `additional_information`,
    `autoapprove`
  )
values (
    'admin',
    'desafio-api',
    '$2a$10$65SHsLWdcbZZWnSspsDtUeWrbMW87KBukDqdUZcT86UGFBWq8feUK',
    'read, write',
    'password,authorization_code,refresh_token,client_credentials,implicit',
    'https://www.getpostman.com/oauth2/callback',
    'ADMIN',
    10800,
    2592000,
    null,
    null
  );