-- public.customers definition

-- Drop table

-- DROP TABLE public.customers;

CREATE TABLE public.customers (
                                  customer_id serial4 NOT NULL,
                                  "name" varchar(255) NOT NULL,
                                  ci_ruc varchar(50) NOT NULL,
                                  email varchar(100) NULL,
                                  phone varchar(20) NULL,
                                  address text NULL,
                                  created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
                                  CONSTRAINT customers_ci_ruc_key UNIQUE (ci_ruc),
                                  CONSTRAINT customers_email_key UNIQUE (email),
                                  CONSTRAINT customers_pkey PRIMARY KEY (customer_id)
);


-- public.products definition

-- Drop table

-- DROP TABLE public.products;

CREATE TABLE public.products (
                                 product_id serial4 NOT NULL,
                                 "name" varchar(255) NOT NULL,
                                 description text NULL,
                                 price numeric(10, 2) NOT NULL,
                                 stock int4 DEFAULT 0 NOT NULL,
                                 created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
                                 CONSTRAINT products_pkey PRIMARY KEY (product_id)
);


-- public.invoices definition

-- Drop table

-- DROP TABLE public.invoices;

CREATE TABLE public.invoices (
                                 invoice_id serial4 NOT NULL,
                                 customer_id int4 NULL,
                                 "date" date NOT NULL,
                                 total_amount numeric(10, 2) NOT NULL,
                                 payment_type varchar(2) NOT NULL,
                                 installment_count int4 NULL,
                                 installment_days varchar(255) NULL,
                                 status varchar(20) DEFAULT 'PENDING'::character varying NULL,
                                 created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
                                 CONSTRAINT invoices_payment_type_check CHECK (((payment_type)::text = ANY (ARRAY[('CO'::character varying)::text, ('CR'::character varying)::text]))),
	CONSTRAINT invoices_pkey PRIMARY KEY (invoice_id),
	CONSTRAINT invoices_status_check CHECK (((status)::text = ANY (ARRAY[('PENDING'::character varying)::text, ('PAID'::character varying)::text, ('CANCELLED'::character varying)::text]))),
	CONSTRAINT invoices_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id) ON DELETE CASCADE
);


-- public.sales definition

-- Drop table

-- DROP TABLE public.sales;

CREATE TABLE public.sales (
                              sale_id serial4 NOT NULL,
                              customer_id int4 NULL,
                              sale_date date NOT NULL,
                              total_amount numeric(10, 2) NOT NULL,
                              payment_type varchar(2) NOT NULL,
                              status varchar(20) DEFAULT 'COMPLETED'::character varying NULL,
                              created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
                              installment_count int4 NULL,
                              installment_days varchar(255) NULL,
                              CONSTRAINT sales_payment_type_check CHECK (((payment_type)::text = ANY ((ARRAY['CO'::character varying, 'CR'::character varying])::text[]))),
	CONSTRAINT sales_pkey PRIMARY KEY (sale_id),
	CONSTRAINT sales_status_check CHECK (((status)::text = ANY ((ARRAY['COMPLETED'::character varying, 'CANCELLED'::character varying, 'PENDING'::character varying])::text[]))),
	CONSTRAINT sales_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id) ON DELETE SET NULL
);

-- Table Triggers

create trigger trigger_generar_cuotas after
    insert
    on
        public.sales for each row execute function generar_cuotas();


-- public.installments definition

-- Drop table

-- DROP TABLE public.installments;

CREATE TABLE public.installments (
                                     installment_id serial4 NOT NULL,
                                     sale_id int4 NULL,
                                     installment_number int4 NOT NULL,
                                     amount numeric(10, 2) NOT NULL,
                                     due_date date NOT NULL,
                                     paid bool DEFAULT false NULL,
                                     payment_date date NULL,
                                     created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
                                     invoice_id int4 NULL,
                                     CONSTRAINT installments_pkey PRIMARY KEY (installment_id),
                                     CONSTRAINT fkm6qa6od3bn83buqut2l3mxu6k FOREIGN KEY (invoice_id) REFERENCES public.invoices(invoice_id) ON DELETE CASCADE,
                                     CONSTRAINT installments_sale_id_fkey FOREIGN KEY (sale_id) REFERENCES public.sales(sale_id) ON DELETE CASCADE
);


-- public.sale_items definition

-- Drop table

-- DROP TABLE public.sale_items;

CREATE TABLE public.sale_items (
                                   sale_item_id serial4 NOT NULL,
                                   sale_id int4 NOT NULL,
                                   product_id int4 NOT NULL,
                                   quantity int4 NOT NULL,
                                   unit_price numeric(10, 2) NOT NULL,
                                   subtotal numeric(10, 2) NULL,
                                   CONSTRAINT sale_items_pkey PRIMARY KEY (sale_item_id),
                                   CONSTRAINT sale_items_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(product_id) ON DELETE RESTRICT,
                                   CONSTRAINT sale_items_sale_id_fkey FOREIGN KEY (sale_id) REFERENCES public.sales(sale_id) ON DELETE CASCADE
);


-- ============================================
-- FUNCTION AND TRIGGER
-- ============================================

CREATE OR REPLACE FUNCTION generar_cuotas()
RETURNS TRIGGER AS $$
DECLARE
cuota_monto NUMERIC(10, 2);
    cuota_fecha DATE;
    v_dias_cuotas VARCHAR;
    v_dia INTEGER;
BEGIN
    IF NEW.payment_type = 'CR' AND NEW.installment_count IS NOT NULL THEN
        cuota_monto := NEW.total_amount / NEW.installment_count;

        IF NEW.installment_days IS NULL THEN
            -- Vencimiento regular (cada 30 días)
            FOR i IN 1..NEW.installment_count LOOP
                cuota_fecha := NEW.sale_date + (i * 30);
INSERT INTO installments (sale_id, installment_number, amount, due_date)
VALUES (NEW.sale_id, i, cuota_monto, cuota_fecha);
END LOOP;
ELSE
            -- Vencimiento irregular
            v_dias_cuotas := NEW.installment_days;

FOR i IN 1..NEW.installment_count LOOP
                v_dia := (STRING_TO_ARRAY(v_dias_cuotas, ','))[i]::INTEGER;

                IF v_dia IS NULL THEN
                    EXIT;
END IF;

		        cuota_fecha := NEW.sale_date + v_dia;

INSERT INTO installments (sale_id, installment_number, amount, due_date)
VALUES (NEW.sale_id, i, cuota_monto, cuota_fecha);
END LOOP;
END IF;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_generar_cuotas
    AFTER INSERT ON invoices
    FOR EACH ROW
    EXECUTE FUNCTION generar_cuotas();
