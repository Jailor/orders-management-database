--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    client_id integer NOT NULL,
    name character varying(100) NOT NULL,
    address character varying(100),
    phone_number character varying(45) NOT NULL
);


ALTER TABLE public.client OWNER TO postgres;

--
-- Name: client_client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.client_client_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.client_client_id_seq OWNER TO postgres;

--
-- Name: client_client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_client_id_seq OWNED BY public.client.client_id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    order_id integer NOT NULL,
    client_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer NOT NULL
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: order_orderid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_orderid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_orderid_seq OWNER TO postgres;

--
-- Name: order_orderid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_orderid_seq OWNED BY public.orders.order_id;


--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    product_id integer NOT NULL,
    name character varying(100) NOT NULL,
    quantity integer NOT NULL,
    price integer NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Name: product_productid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_productid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_productid_seq OWNER TO postgres;

--
-- Name: product_productid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_productid_seq OWNED BY public.product.product_id;


--
-- Name: client client_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client ALTER COLUMN client_id SET DEFAULT nextval('public.client_client_id_seq'::regclass);


--
-- Name: orders order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN order_id SET DEFAULT nextval('public.order_orderid_seq'::regclass);


--
-- Name: product product_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product ALTER COLUMN product_id SET DEFAULT nextval('public.product_productid_seq'::regclass);


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client (client_id, name, address, phone_number) FROM stdin;
1	andrei	aici	0724251358
2	Sydnee Nielsen	Ap #212-9204 Risus. Av.	0749680153
3	Keely Rowland	2665 Lacus St.	0732068710
4	Octavia Finley	P.O. Box 644, 3455 Libero St.	0757897146
5	Anthony Bell	303-6663 Etiam Street	0763188045
6	Hiram Whitley	7992 Quisque Road	0748174816
7	Echo May	Ap #431-4058 Senectus Avenue	0756678867
8	Pascale Shepard	3317 Lacinia. Rd.	0746843257
9	Moses Gutierrez	Ap #369-2251 Praesent Av.	0759287160
10	Gisela Strong	Ap #131-591 Magna. St.	0700917417
11	Echo Perez	309-319 Dui. Street	0756908303
12	Tudor Trasculescuu	Ceahlau 77	0732523511
13	Eric Toader	Ceahlau 78	0712345678
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (order_id, client_id, product_id, quantity) FROM stdin;
1	1	1	10
2	2	2	20
4	1	46	50
6	3	3	5
7	5	48	14
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (product_id, name, quantity, price) FROM stdin;
4	Gummi Bears	300	10
5	Sandwiches	120	11
6	Pizza	2500	12
7	Pear	1000	13
8	Orange	200	14
9	Rose	1110	15
10	Havarti cheese	2500	16
11	Cinnamon Bread	1000	17
12	Cream Cheese Frosting	3200	18
13	Mango Lassi	320	19
14	Carbonara	1200	20
15	Pineapple	600	21
16	Fan	352	22
17	Alarm clock	125	23
18	Pocket knife	35	24
19	Sword	568	25
20	Basket	253	26
21	Skateboard	326	27
22	Handlebars	120	28
23	Wine holder	135	29
24	Calendar	2000	30
25	Baking tray	1632	31
26	Bed	1354	32
27	Table	1203	33
28	Floor lamp	130	34
29	Cutting board	256	35
30	Mug	456	36
31	Fork	852	37
32	Calculator	547	38
33	Canoe	587	39
34	Rocking chair	423	40
35	Vase	52	41
36	Flashlight	48	42
37	Spoon	8874	43
38	Glasses	458	44
39	Can opener	258	45
40	Bowl	544	46
41	Playing cards	88	47
42	Ring	77	48
43	Toy train	56	49
44	Frying pan	426	50
47	mp2	100	124
1	Skittles	290	10
2	Red Vines	480	20
46	myprod	75	123
3	Reeses pieces	1195	30
48	Paste PT	86	12
\.


--
-- Name: client_client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_client_id_seq', 13, true);


--
-- Name: order_orderid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_orderid_seq', 7, true);


--
-- Name: product_productid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_productid_seq', 48, true);


--
-- Name: client clientid_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT clientid_unique PRIMARY KEY (client_id);


--
-- Name: product idproduct_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT idproduct_unique PRIMARY KEY (product_id);


--
-- Name: orders oderid_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT oderid_unique PRIMARY KEY (order_id);


--
-- Name: clientid_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX clientid_idx ON public.orders USING btree (client_id);


--
-- Name: productid_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX productid_idx ON public.orders USING btree (product_id);


--
-- Name: orders client_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT client_id FOREIGN KEY (client_id) REFERENCES public.client(client_id);


--
-- Name: orders product_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT product_id FOREIGN KEY (product_id) REFERENCES public.product(product_id);


--
-- PostgreSQL database dump complete
--

