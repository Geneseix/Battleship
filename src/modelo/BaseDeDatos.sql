--
-- PostgreSQL database cluster dump
--

-- Started on 2024-04-26 23:06:12

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;

--
-- User Configurations
--






--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 15.3

-- Started on 2024-04-26 23:06:12

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
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2790 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2024-04-26 23:06:12

--
-- PostgreSQL database dump complete
--

--
-- Database "battleship" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 15.3

-- Started on 2024-04-26 23:06:12

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
-- TOC entry 2850 (class 1262 OID 16384)
-- Name: battleship; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE battleship WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C';


ALTER DATABASE battleship OWNER TO postgres;

\connect battleship

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
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 596 (class 1247 OID 16402)
-- Name: estado_juego; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.estado_juego AS ENUM (
    'activo',
    'finalizado'
);


ALTER TYPE public.estado_juego OWNER TO postgres;

--
-- TOC entry 603 (class 1247 OID 16427)
-- Name: resultado_turno; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.resultado_turno AS ENUM (
    'agua',
    'impacto',
    'hundido'
);


ALTER TYPE public.resultado_turno OWNER TO postgres;

SET default_tablespace = '';

--
-- TOC entry 203 (class 1259 OID 16454)
-- Name: barcos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.barcos (
    id_barco integer NOT NULL,
    id_juego integer,
    id_jugador integer,
    tipo integer,
    posicion character varying(50)
);


ALTER TABLE public.barcos OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16452)
-- Name: barcos_id_barco_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.barcos_id_barco_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.barcos_id_barco_seq OWNER TO postgres;

--
-- TOC entry 2852 (class 0 OID 0)
-- Dependencies: 202
-- Name: barcos_id_barco_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.barcos_id_barco_seq OWNED BY public.barcos.id_barco;


--
-- TOC entry 199 (class 1259 OID 16409)
-- Name: partidas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.partidas (
    id_juego integer NOT NULL,
    id_jugador1 integer,
    id_jugador2 integer,
    estado public.estado_juego DEFAULT 'activo'::public.estado_juego,
    ganador character varying(200)
);


ALTER TABLE public.partidas OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16407)
-- Name: partidas_id_juego_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.partidas_id_juego_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.partidas_id_juego_seq OWNER TO postgres;

--
-- TOC entry 2853 (class 0 OID 0)
-- Dependencies: 198
-- Name: partidas_id_juego_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.partidas_id_juego_seq OWNED BY public.partidas.id_juego;


--
-- TOC entry 201 (class 1259 OID 16435)
-- Name: turnos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turnos (
    id_turno integer NOT NULL,
    id_juego integer,
    id_jugador integer,
    coordenada character varying(5),
    resultado public.resultado_turno DEFAULT 'agua'::public.resultado_turno
);


ALTER TABLE public.turnos OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16433)
-- Name: turnos_id_turno_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.turnos_id_turno_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.turnos_id_turno_seq OWNER TO postgres;

--
-- TOC entry 2854 (class 0 OID 0)
-- Dependencies: 200
-- Name: turnos_id_turno_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.turnos_id_turno_seq OWNED BY public.turnos.id_turno;


--
-- TOC entry 197 (class 1259 OID 16387)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios (
    id_jugador integer NOT NULL,
    nombre character varying(255) NOT NULL,
    contrasena character varying(255) NOT NULL,
    puntaje integer DEFAULT 0
);


ALTER TABLE public.usuarios OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16385)
-- Name: usuarios_id_jugador_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuarios_id_jugador_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuarios_id_jugador_seq OWNER TO postgres;

--
-- TOC entry 2855 (class 0 OID 0)
-- Dependencies: 196
-- Name: usuarios_id_jugador_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuarios_id_jugador_seq OWNED BY public.usuarios.id_jugador;


--
-- TOC entry 2701 (class 2604 OID 16457)
-- Name: barcos id_barco; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.barcos ALTER COLUMN id_barco SET DEFAULT nextval('public.barcos_id_barco_seq'::regclass);


--
-- TOC entry 2697 (class 2604 OID 16412)
-- Name: partidas id_juego; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partidas ALTER COLUMN id_juego SET DEFAULT nextval('public.partidas_id_juego_seq'::regclass);


--
-- TOC entry 2699 (class 2604 OID 16438)
-- Name: turnos id_turno; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turnos ALTER COLUMN id_turno SET DEFAULT nextval('public.turnos_id_turno_seq'::regclass);


--
-- TOC entry 2695 (class 2604 OID 16390)
-- Name: usuarios id_jugador; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios ALTER COLUMN id_jugador SET DEFAULT nextval('public.usuarios_id_jugador_seq'::regclass);


--
-- TOC entry 2844 (class 0 OID 16454)
-- Dependencies: 203
-- Data for Name: barcos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.barcos (id_barco, id_juego, id_jugador, tipo, posicion) FROM stdin;
\.


--
-- TOC entry 2840 (class 0 OID 16409)
-- Dependencies: 199
-- Data for Name: partidas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.partidas (id_juego, id_jugador1, id_jugador2, estado, ganador) FROM stdin;
\.


--
-- TOC entry 2842 (class 0 OID 16435)
-- Dependencies: 201
-- Data for Name: turnos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.turnos (id_turno, id_juego, id_jugador, coordenada, resultado) FROM stdin;
\.


--
-- TOC entry 2838 (class 0 OID 16387)
-- Dependencies: 197
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuarios (id_jugador, nombre, contrasena, puntaje) FROM stdin;
4	b	H0D8ktokFpR1CXnubPWC8tXX0o4YM13gWrxU0FYOD1MChgxlK/CNVgJSql50IQVG82n7u86MEs/HlXsmUv6adQ==	0
3	a	H0D8ktokFpR1CXnubPWC8tXX0o4YM13gWrxU0FYOD1MChgxlK/CNVgJSql50IQVG82n7u86MEs/HlXsmUv6adQ==	30
\.


--
-- TOC entry 2856 (class 0 OID 0)
-- Dependencies: 202
-- Name: barcos_id_barco_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.barcos_id_barco_seq', 1, false);


--
-- TOC entry 2857 (class 0 OID 0)
-- Dependencies: 198
-- Name: partidas_id_juego_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.partidas_id_juego_seq', 91, true);


--
-- TOC entry 2858 (class 0 OID 0)
-- Dependencies: 200
-- Name: turnos_id_turno_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.turnos_id_turno_seq', 1, false);


--
-- TOC entry 2859 (class 0 OID 0)
-- Dependencies: 196
-- Name: usuarios_id_jugador_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuarios_id_jugador_seq', 1, false);


--
-- TOC entry 2709 (class 2606 OID 16459)
-- Name: barcos barcos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.barcos
    ADD CONSTRAINT barcos_pkey PRIMARY KEY (id_barco);


--
-- TOC entry 2705 (class 2606 OID 16415)
-- Name: partidas partidas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partidas
    ADD CONSTRAINT partidas_pkey PRIMARY KEY (id_juego);


--
-- TOC entry 2707 (class 2606 OID 16441)
-- Name: turnos turnos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turnos
    ADD CONSTRAINT turnos_pkey PRIMARY KEY (id_turno);


--
-- TOC entry 2703 (class 2606 OID 16396)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id_jugador);


--
-- TOC entry 2714 (class 2606 OID 16460)
-- Name: barcos barcos_id_juego_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.barcos
    ADD CONSTRAINT barcos_id_juego_fkey FOREIGN KEY (id_juego) REFERENCES public.partidas(id_juego);


--
-- TOC entry 2715 (class 2606 OID 16465)
-- Name: barcos barcos_id_jugador_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.barcos
    ADD CONSTRAINT barcos_id_jugador_fkey FOREIGN KEY (id_jugador) REFERENCES public.usuarios(id_jugador);


--
-- TOC entry 2710 (class 2606 OID 16416)
-- Name: partidas partidas_id_jugador1_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partidas
    ADD CONSTRAINT partidas_id_jugador1_fkey FOREIGN KEY (id_jugador1) REFERENCES public.usuarios(id_jugador);


--
-- TOC entry 2711 (class 2606 OID 16421)
-- Name: partidas partidas_id_jugador2_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partidas
    ADD CONSTRAINT partidas_id_jugador2_fkey FOREIGN KEY (id_jugador2) REFERENCES public.usuarios(id_jugador);


--
-- TOC entry 2712 (class 2606 OID 16442)
-- Name: turnos turnos_id_juego_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turnos
    ADD CONSTRAINT turnos_id_juego_fkey FOREIGN KEY (id_juego) REFERENCES public.partidas(id_juego);


--
-- TOC entry 2713 (class 2606 OID 16447)
-- Name: turnos turnos_id_jugador_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turnos
    ADD CONSTRAINT turnos_id_jugador_fkey FOREIGN KEY (id_jugador) REFERENCES public.usuarios(id_jugador);


--
-- TOC entry 2851 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2024-04-26 23:06:12

--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

\connect postgres

--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 15.3

-- Started on 2024-04-26 23:06:13

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
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2790 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2024-04-26 23:06:13

--
-- PostgreSQL database dump complete
--

-- Completed on 2024-04-26 23:06:13

--
-- PostgreSQL database cluster dump complete
--

