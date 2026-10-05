USE coffee_order;

INSERT INTO users (name, point_balance, created_at, updated_at) VALUES
                                                                    ('김커피', 0, NOW(6), NOW(6)),
                                                                    ('이라떼', 0, NOW(6), NOW(6)),
                                                                    ('박모카', 0, NOW(6), NOW(6));

INSERT INTO menus (name, price, created_at, updated_at) VALUES
                                                            ('아메리카노', 4500, NOW(6), NOW(6)),
                                                            ('카페라떼', 5000, NOW(6), NOW(6)),
                                                            ('바닐라라떼', 5500, NOW(6), NOW(6)),
                                                            ('카페모카', 5500, NOW(6), NOW(6)),
                                                            ('콜드브루', 4800, NOW(6), NOW(6));