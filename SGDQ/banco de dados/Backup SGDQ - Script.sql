--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.1
-- Dumped by pg_dump version 9.2.1
-- Started on 2013-12-16 22:38:24

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 42200)
-- Name: dbo; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA dbo;


ALTER SCHEMA dbo OWNER TO postgres;

--
-- TOC entry 191 (class 3079 OID 11727)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2046 (class 0 OID 0)
-- Dependencies: 191
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = dbo, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 178 (class 1259 OID 50403)
-- Name: Cidade; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "Cidade" (
    "idCidade" integer DEFAULT nextval(('id_cidade_seq'::text)::regclass) NOT NULL,
    "nmCidade" text,
    "idEstado" integer
);


ALTER TABLE dbo."Cidade" OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 50392)
-- Name: Endereco; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "Endereco" (
    "idEndereco" integer DEFAULT nextval(('id_endereco_seq'::text)::regclass) NOT NULL,
    "dsEndereco" text,
    "nmBairro" text,
    "idCidade" integer,
    "nuCEP" text
);


ALTER TABLE dbo."Endereco" OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 50414)
-- Name: Estado; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "Estado" (
    "idEstado" integer DEFAULT nextval(('id_estado_seq'::text)::regclass) NOT NULL,
    "nmEstado" text,
    "sgEstado" text
);


ALTER TABLE dbo."Estado" OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 50590)
-- Name: Fase; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "Fase" (
    "idFase" integer DEFAULT nextval(('id_fase_seq'::text)::regclass) NOT NULL,
    "nmFase" text,
    "dsFase" text
);


ALTER TABLE dbo."Fase" OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 50567)
-- Name: FaseTratamento; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "FaseTratamento" (
    "idFaseTratamento" integer DEFAULT nextval(('id_fasetratamento_seq'::text)::regclass) NOT NULL,
    "dtFaseTratamentoInicio" date,
    "dtFaseTratamentoFim" date,
    "idTratamento" integer,
    "idFase" integer,
    "dsMotivoFaseTratamento" text
);


ALTER TABLE dbo."FaseTratamento" OWNER TO postgres;

--
-- TOC entry 2047 (class 0 OID 0)
-- Dependencies: 187
-- Name: TABLE "FaseTratamento"; Type: COMMENT; Schema: dbo; Owner: postgres
--

COMMENT ON TABLE "FaseTratamento" IS 'Essa tabela armazena os dado referente a cada fase do tratamento. Serve para registro de histórico e descirções de cada fase';


--
-- TOC entry 174 (class 1259 OID 42219)
-- Name: Paciente; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "Paciente" (
    "idPaciente" integer DEFAULT nextval(('id_paciente_seq'::text)::regclass) NOT NULL,
    "idResponsavel" integer,
    "dtEntrada" date,
    "idPessoa" integer
);


ALTER TABLE dbo."Paciente" OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 42201)
-- Name: Pessoa; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "Pessoa" (
    "idPessoa" integer DEFAULT nextval(('id_pessoa_seq'::text)::regclass) NOT NULL,
    "nmPessoa" text,
    "nuCPF" text,
    "nmOrgaoEmissor" text,
    "sgSexo" "char",
    "idEstadoOrgaoEmissor" integer,
    "nuTelefone" text,
    "nuCelular" text,
    "idEndereco" integer,
    "dtNascimento" date,
    "idCidadeNascimento" integer,
    "nuRG" text,
    "dsEmail" text,
    "idResponsavel" integer
);


ALTER TABLE dbo."Pessoa" OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 50562)
-- Name: Prontuario; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "Prontuario" (
    "idProntuario" integer NOT NULL,
    "idPaciente" integer,
    "idResponsavelProntuario" integer,
    "idProntuarioStatus" integer,
    "dtProntuarioInicio" date
);


ALTER TABLE dbo."Prontuario" OWNER TO postgres;

--
-- TOC entry 2048 (class 0 OID 0)
-- Dependencies: 186
-- Name: TABLE "Prontuario"; Type: COMMENT; Schema: dbo; Owner: postgres
--

COMMENT ON TABLE "Prontuario" IS 'É a ficha de fato do paciente. Existe apenas um prontuário para cada paciente. Quando um paciente recai ou volta pra fazer novo tratamento é reutilizado os mesmos cadastros básicos';


--
-- TOC entry 182 (class 1259 OID 50470)
-- Name: Responsavel; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "Responsavel" (
    "idResponsavel" integer DEFAULT nextval(('id_responsavel_seq'::text)::regclass) NOT NULL,
    "idPessoa" integer,
    "nmVinculoFamiliar" text,
    "vrRendaFamiliar" money
);


ALTER TABLE dbo."Responsavel" OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 50546)
-- Name: Tratamento; Type: TABLE; Schema: dbo; Owner: postgres; Tablespace: 
--

CREATE TABLE "Tratamento" (
    "idTratamento" integer DEFAULT nextval(('id_tratamento_seq'::text)::regclass) NOT NULL,
    "idTipoTratamento" integer NOT NULL,
    "idStatusTratamento" integer NOT NULL,
    "dsMotivoStatus" text,
    "idPaciente" integer NOT NULL,
    "icAtivo" smallint DEFAULT 1 NOT NULL
);


ALTER TABLE dbo."Tratamento" OWNER TO postgres;

--
-- TOC entry 2049 (class 0 OID 0)
-- Dependencies: 184
-- Name: TABLE "Tratamento"; Type: COMMENT; Schema: dbo; Owner: postgres
--

COMMENT ON TABLE "Tratamento" IS 'Contém as informações do tratamento de um paciente, assim como o(s) responsável(eis). É nessa entidade que se mantém todo o controle do progresso e e informações gerais do negocio. Podemos dizer que se trata do núcleo do sistema de onde também irá partir todos os relatórios
Existe apenas um tratamento ativo por vez para um paciente. O tratamento pode ser do tipo ambulatorial ou internação. 
5 Motivos de finalização
Fases 1, 2 e 3';


--
-- TOC entry 172 (class 1259 OID 42204)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 2050 (class 0 OID 0)
-- Dependencies: 172
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 2, false);


--
-- TOC entry 179 (class 1259 OID 50411)
-- Name: id_cidade_seq; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE id_cidade_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.id_cidade_seq OWNER TO postgres;

--
-- TOC entry 2051 (class 0 OID 0)
-- Dependencies: 179
-- Name: id_cidade_seq; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('id_cidade_seq', 1, false);


--
-- TOC entry 177 (class 1259 OID 50395)
-- Name: id_endereco_seq; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE id_endereco_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.id_endereco_seq OWNER TO postgres;

--
-- TOC entry 2052 (class 0 OID 0)
-- Dependencies: 177
-- Name: id_endereco_seq; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('id_endereco_seq', 1, false);


--
-- TOC entry 181 (class 1259 OID 50422)
-- Name: id_estado_seq; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE id_estado_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.id_estado_seq OWNER TO postgres;

--
-- TOC entry 2053 (class 0 OID 0)
-- Dependencies: 181
-- Name: id_estado_seq; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('id_estado_seq', 1, false);


--
-- TOC entry 190 (class 1259 OID 50599)
-- Name: id_fase_seq; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE id_fase_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.id_fase_seq OWNER TO postgres;

--
-- TOC entry 2054 (class 0 OID 0)
-- Dependencies: 190
-- Name: id_fase_seq; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('id_fase_seq', 1, false);


--
-- TOC entry 188 (class 1259 OID 50582)
-- Name: id_fasetratamento_seq; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE id_fasetratamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.id_fasetratamento_seq OWNER TO postgres;

--
-- TOC entry 2055 (class 0 OID 0)
-- Dependencies: 188
-- Name: id_fasetratamento_seq; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('id_fasetratamento_seq', 1, false);


--
-- TOC entry 175 (class 1259 OID 42224)
-- Name: id_paciente_seq; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE id_paciente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.id_paciente_seq OWNER TO postgres;

--
-- TOC entry 2056 (class 0 OID 0)
-- Dependencies: 175
-- Name: id_paciente_seq; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('id_paciente_seq', 1, false);


--
-- TOC entry 173 (class 1259 OID 42206)
-- Name: id_pessoa_seq; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE id_pessoa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.id_pessoa_seq OWNER TO postgres;

--
-- TOC entry 2057 (class 0 OID 0)
-- Dependencies: 173
-- Name: id_pessoa_seq; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('id_pessoa_seq', 1, false);


--
-- TOC entry 183 (class 1259 OID 50478)
-- Name: id_responsavel_seq; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE id_responsavel_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.id_responsavel_seq OWNER TO postgres;

--
-- TOC entry 2058 (class 0 OID 0)
-- Dependencies: 183
-- Name: id_responsavel_seq; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('id_responsavel_seq', 1, false);


--
-- TOC entry 185 (class 1259 OID 50554)
-- Name: id_tratamento_seq; Type: SEQUENCE; Schema: dbo; Owner: postgres
--

CREATE SEQUENCE id_tratamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbo.id_tratamento_seq OWNER TO postgres;

--
-- TOC entry 2059 (class 0 OID 0)
-- Dependencies: 185
-- Name: id_tratamento_seq; Type: SEQUENCE SET; Schema: dbo; Owner: postgres
--

SELECT pg_catalog.setval('id_tratamento_seq', 1, false);


SET search_path = public, pg_catalog;

--
-- TOC entry 170 (class 1259 OID 34011)
-- Name: Pessoa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Pessoa" (
);


ALTER TABLE public."Pessoa" OWNER TO postgres;

--
-- TOC entry 169 (class 1259 OID 34008)
-- Name: Prontuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Prontuario" (
    id integer
);


ALTER TABLE public."Prontuario" OWNER TO postgres;

SET search_path = dbo, pg_catalog;

--
-- TOC entry 2032 (class 0 OID 50403)
-- Dependencies: 178
-- Data for Name: Cidade; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "Cidade" ("idCidade", "nmCidade", "idEstado") FROM stdin;


--
-- TOC entry 2031 (class 0 OID 50392)
-- Dependencies: 176
-- Data for Name: Endereco; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "Endereco" ("idEndereco", "dsEndereco", "nmBairro", "idCidade", "nuCEP") FROM stdin;



--
-- TOC entry 2033 (class 0 OID 50414)
-- Dependencies: 180
-- Data for Name: Estado; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "Estado" ("idEstado", "nmEstado", "sgEstado") FROM stdin;



--
-- TOC entry 2038 (class 0 OID 50590)
-- Dependencies: 189
-- Data for Name: Fase; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "Fase" ("idFase", "nmFase", "dsFase") FROM stdin;


--
-- TOC entry 2037 (class 0 OID 50567)
-- Dependencies: 187
-- Data for Name: FaseTratamento; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "FaseTratamento" ("idFaseTratamento", "dtFaseTratamentoInicio", "dtFaseTratamentoFim", "idTratamento", "idFase", "dsMotivoFaseTratamento") FROM stdin;



--
-- TOC entry 2030 (class 0 OID 42219)
-- Dependencies: 174
-- Data for Name: Paciente; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "Paciente" ("idPaciente", "idResponsavel", "dtEntrada", "idPessoa") FROM stdin;



--
-- TOC entry 2029 (class 0 OID 42201)
-- Dependencies: 171
-- Data for Name: Pessoa; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "Pessoa" ("idPessoa", "nmPessoa", "nuCPF", "nmOrgaoEmissor", "sgSexo", "idEstadoOrgaoEmissor", "nuTelefone", "nuCelular", "idEndereco", "dtNascimento", "idCidadeNascimento", "nuRG", "dsEmail", "idResponsavel") FROM stdin;



--
-- TOC entry 2036 (class 0 OID 50562)
-- Dependencies: 186
-- Data for Name: Prontuario; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "Prontuario" ("idProntuario", "idPaciente", "idResponsavelProntuario", "idProntuarioStatus", "dtProntuarioInicio") FROM stdin;



--
-- TOC entry 2034 (class 0 OID 50470)
-- Dependencies: 182
-- Data for Name: Responsavel; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "Responsavel" ("idResponsavel", "idPessoa", "nmVinculoFamiliar", "vrRendaFamiliar") FROM stdin;



--
-- TOC entry 2035 (class 0 OID 50546)
-- Dependencies: 184
-- Data for Name: Tratamento; Type: TABLE DATA; Schema: dbo; Owner: postgres
--

--COPY "Tratamento" ("idTratamento", "idTipoTratamento", "idStatusTratamento", "dsMotivoStatus", "idPaciente", "icAtivo") FROM stdin;



SET search_path = public, pg_catalog;

--
-- TOC entry 2028 (class 0 OID 34011)
-- Dependencies: 170
-- Data for Name: Pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

--COPY "Pessoa"  FROM stdin;



--
-- TOC entry 2027 (class 0 OID 34008)
-- Dependencies: 169
-- Data for Name: Prontuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

--COPY "Prontuario" (id) FROM stdin;



SET search_path = dbo, pg_catalog;

--
-- TOC entry 2004 (class 2606 OID 50410)
-- Name: Cidade_pkey; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Cidade"
    ADD CONSTRAINT "Cidade_pkey" PRIMARY KEY ("idCidade");


--
-- TOC entry 2002 (class 2606 OID 50399)
-- Name: Endereco_pkey; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Endereco"
    ADD CONSTRAINT "Endereco_pkey" PRIMARY KEY ("idEndereco");


--
-- TOC entry 2006 (class 2606 OID 50421)
-- Name: Estado_pkey; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Estado"
    ADD CONSTRAINT "Estado_pkey" PRIMARY KEY ("idEstado");


--
-- TOC entry 2016 (class 2606 OID 50597)
-- Name: Fase_pkey; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Fase"
    ADD CONSTRAINT "Fase_pkey" PRIMARY KEY ("idFase");


--
-- TOC entry 2014 (class 2606 OID 50571)
-- Name: FasesTratamento_pkey; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "FaseTratamento"
    ADD CONSTRAINT "FasesTratamento_pkey" PRIMARY KEY ("idFaseTratamento");


--
-- TOC entry 2012 (class 2606 OID 50566)
-- Name: Prontuario_pkey; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Prontuario"
    ADD CONSTRAINT "Prontuario_pkey" PRIMARY KEY ("idProntuario");


--
-- TOC entry 2008 (class 2606 OID 50477)
-- Name: Responsavel_pkey; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Responsavel"
    ADD CONSTRAINT "Responsavel_pkey" PRIMARY KEY ("idResponsavel");


--
-- TOC entry 2010 (class 2606 OID 50553)
-- Name: Tratamento_pkey; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Tratamento"
    ADD CONSTRAINT "Tratamento_pkey" PRIMARY KEY ("idTratamento");


--
-- TOC entry 2000 (class 2606 OID 42223)
-- Name: idPaciente_fk; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Paciente"
    ADD CONSTRAINT "idPaciente_fk" PRIMARY KEY ("idPaciente");


--
-- TOC entry 1998 (class 2606 OID 42215)
-- Name: idPessoa_fk; Type: CONSTRAINT; Schema: dbo; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Pessoa"
    ADD CONSTRAINT "idPessoa_fk" PRIMARY KEY ("idPessoa");


--
-- TOC entry 2022 (class 2606 OID 50425)
-- Name: Cidade_idEstado_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "Cidade"
    ADD CONSTRAINT "Cidade_idEstado_fkey" FOREIGN KEY ("idEstado") REFERENCES "Estado"("idEstado");


--
-- TOC entry 2021 (class 2606 OID 50430)
-- Name: Endereco_idCidade_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "Endereco"
    ADD CONSTRAINT "Endereco_idCidade_fkey" FOREIGN KEY ("idCidade") REFERENCES "Cidade"("idCidade");


--
-- TOC entry 2025 (class 2606 OID 50630)
-- Name: FasesTratamento_idFase_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "FaseTratamento"
    ADD CONSTRAINT "FasesTratamento_idFase_fkey" FOREIGN KEY ("idFase") REFERENCES "Fase"("idFase");


--
-- TOC entry 2026 (class 2606 OID 50635)
-- Name: FasesTratamento_idTratamento_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "FaseTratamento"
    ADD CONSTRAINT "FasesTratamento_idTratamento_fkey" FOREIGN KEY ("idTratamento") REFERENCES "Tratamento"("idTratamento");


--
-- TOC entry 2017 (class 2606 OID 50526)
-- Name: Pessoa_idCidadeNascimento_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "Pessoa"
    ADD CONSTRAINT "Pessoa_idCidadeNascimento_fkey" FOREIGN KEY ("idCidadeNascimento") REFERENCES "Cidade"("idCidade");


--
-- TOC entry 2018 (class 2606 OID 50531)
-- Name: Pessoa_idEndereco_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "Pessoa"
    ADD CONSTRAINT "Pessoa_idEndereco_fkey" FOREIGN KEY ("idEndereco") REFERENCES "Endereco"("idEndereco");


--
-- TOC entry 2019 (class 2606 OID 50536)
-- Name: Pessoa_idEstadoOrgaoEmissor_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "Pessoa"
    ADD CONSTRAINT "Pessoa_idEstadoOrgaoEmissor_fkey" FOREIGN KEY ("idEstadoOrgaoEmissor") REFERENCES "Estado"("idEstado");


--
-- TOC entry 2020 (class 2606 OID 50541)
-- Name: Pessoa_idResponsavel_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "Pessoa"
    ADD CONSTRAINT "Pessoa_idResponsavel_fkey" FOREIGN KEY ("idResponsavel") REFERENCES "Responsavel"("idResponsavel");


--
-- TOC entry 2023 (class 2606 OID 50521)
-- Name: Responsavel_idPessoa_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "Responsavel"
    ADD CONSTRAINT "Responsavel_idPessoa_fkey" FOREIGN KEY ("idPessoa") REFERENCES "Pessoa"("idPessoa");


--
-- TOC entry 2024 (class 2606 OID 50648)
-- Name: Tratamento_idPaciente_fkey; Type: FK CONSTRAINT; Schema: dbo; Owner: postgres
--

ALTER TABLE ONLY "Tratamento"
    ADD CONSTRAINT "Tratamento_idPaciente_fkey" FOREIGN KEY ("idPaciente") REFERENCES "Paciente"("idPaciente");


--
-- TOC entry 2045 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2013-12-16 22:38:26

--
-- PostgreSQL database dump complete
--

