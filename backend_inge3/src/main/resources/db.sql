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
                              CONSTRAINT sales_payment_type_check CHECK (((payment_type)::text = ANY ((ARRAY['CO'::character varying, 'CR'::character varying])::text[]))),
	CONSTRAINT sales_pkey PRIMARY KEY (sale_id),
	CONSTRAINT sales_status_check CHECK (((status)::text = ANY ((ARRAY['COMPLETED'::character varying, 'CANCELLED'::character varying, 'PENDING'::character varying])::text[]))),
	CONSTRAINT sales_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id) ON DELETE SET NULL
);


-- public.installments definition

-- Drop table

-- DROP TABLE public.installments;

CREATE TABLE public.installments (
                                     installment_id serial4 NOT NULL,
                                     invoice_id int4 NULL,
                                     installment_number int4 NOT NULL,
                                     amount numeric(10, 2) NOT NULL,
                                     due_date date NOT NULL,
                                     paid bool DEFAULT false NULL,
                                     payment_date date NULL,
                                     created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
                                     CONSTRAINT installments_pkey PRIMARY KEY (installment_id),
                                     CONSTRAINT installments_invoice_id_fkey FOREIGN KEY (invoice_id) REFERENCES public.invoices(invoice_id) ON DELETE CASCADE
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
                                   subtotal numeric(10, 2) GENERATED ALWAYS AS ((quantity::numeric * unit_price)) STORED NULL,
                                   CONSTRAINT sale_items_pkey PRIMARY KEY (sale_item_id),
                                   CONSTRAINT sale_items_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(product_id) ON DELETE RESTRICT,
                                   CONSTRAINT sale_items_sale_id_fkey FOREIGN KEY (sale_id) REFERENCES public.sales(sale_id) ON DELETE CASCADE
);