USE coffee_order;

CREATE TABLE users (
                       id            BIGINT      NOT NULL AUTO_INCREMENT,
                       name          VARCHAR(50) NOT NULL,
                       point_balance BIGINT      NOT NULL DEFAULT 0,
                       created_at    DATETIME(6) NOT NULL,
                       updated_at    DATETIME(6) NOT NULL,
                       PRIMARY KEY (id),
                       CONSTRAINT chk_users_point_balance CHECK (point_balance >= 0)
);

CREATE TABLE menus (
                       id         BIGINT       NOT NULL AUTO_INCREMENT,
                       name       VARCHAR(100) NOT NULL,
                       price      BIGINT       NOT NULL,
                       created_at DATETIME(6)  NOT NULL,
                       updated_at DATETIME(6)  NOT NULL,
                       PRIMARY KEY (id),
                       CONSTRAINT chk_menus_price CHECK (price > 0)
);

CREATE TABLE orders (
                        id             BIGINT       NOT NULL AUTO_INCREMENT,
                        user_id        BIGINT       NOT NULL,
                        menu_id        BIGINT       NOT NULL,
                        menu_name      VARCHAR(100) NOT NULL,
                        payment_amount BIGINT       NOT NULL,
                        ordered_at     DATETIME(6)  NOT NULL,
                        PRIMARY KEY (id),
                        CONSTRAINT fk_orders_user
                            FOREIGN KEY (user_id) REFERENCES users (id),
                        CONSTRAINT fk_orders_menu
                            FOREIGN KEY (menu_id) REFERENCES menus (id),
                        INDEX idx_orders_ordered_at_menu_id (ordered_at, menu_id)
);