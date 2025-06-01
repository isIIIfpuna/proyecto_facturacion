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
                                 CONSTRAINT invoices_payment_type_check CHECK (((payment_type)::text = ANY ((ARRAY['CO'::character varying, 'CR'::character varying])::text[]))),
	CONSTRAINT invoices_pkey PRIMARY KEY (invoice_id),
	CONSTRAINT invoices_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'PAID'::character varying, 'CANCELLED'::character varying])::text[]))),
	CONSTRAINT invoices_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id) ON DELETE CASCADE
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