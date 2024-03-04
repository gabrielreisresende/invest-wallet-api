-- Insert data for User
INSERT INTO tb_users (user_id, email, password, role, deleted)
VALUES (1, 'customer@email.com', '$2a$10$OaUBgW6BfMAS/X8gTqlnvesDgMAAg88tzo0Cbl6rqCLpDvBEOO7/2', 'CUSTOMER', false),
       (2, 'admin@email.com', '$2a$10$OaUBgW6BfMAS/X8gTqlnvesDgMAAg88tzo0Cbl6rqCLpDvBEOO7/2', 'ADMIN', false);

-- Insert data for Customer
INSERT INTO tb_customers (customer_id, user_id, birth_date, cpf, deleted, first_name, last_name, phone)
VALUES (1, 1, NOW(), '43434234234', FALSE, 'Gabriel', 'Teste', '4123434324');

-- Insert data for Wallet
INSERT INTO tb_wallets (wallet_id, customer_id, name)
VALUES (1, 1, 'Dividendos');

-- Insert data for ActiveType
INSERT INTO tb_active_types (active_type_id, active_type)
VALUES (1, 'Papel'),
       (2, 'Tijolo');

-- Insert data for ActiveSector
INSERT INTO tb_active_sectors (active_sector_id, active_sector)
VALUES (1, 'Hibrido'),
       (2, 'Shopping');

-- Insert data for ActiveCode
INSERT INTO tb_active_codes (active_code, active_type_id, active_sector_id)
VALUES ('MXRF11', 1, 1),
       ('XPML11', 2, 2);

-- Insert data for Active
INSERT INTO tb_actives (active_id, wallet_id, active_code, quantity, average_value)
VALUES (1, 1, 'MXRF11', 1, 9.98),
       (2, 1, 'XPML11', 1, 110.98);
