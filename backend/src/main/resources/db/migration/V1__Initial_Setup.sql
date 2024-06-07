
CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
--    there will be a customer_id_seq relation created in the db because of the BIGSERIAL line.
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    age INT NOT NULL
);