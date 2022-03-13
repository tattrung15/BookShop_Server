--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

-- Started on 2022-03-13 14:33:54

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 25800)
-- Name: banners; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banners (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    image_url character varying(255),
    is_active boolean NOT NULL,
    title character varying(255) NOT NULL,
    type integer NOT NULL,
    updated_at timestamp without time zone
);


ALTER TABLE public.banners OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 25799)
-- Name: banners_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.banners_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.banners_id_seq OWNER TO postgres;

--
-- TOC entry 3410 (class 0 OID 0)
-- Dependencies: 209
-- Name: banners_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.banners_id_seq OWNED BY public.banners.id;


--
-- TOC entry 212 (class 1259 OID 25809)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    description character varying(100000),
    is_author boolean NOT NULL,
    name character varying(255) NOT NULL,
    slug character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    parent_category_id bigint
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 25808)
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categories_id_seq OWNER TO postgres;

--
-- TOC entry 3411 (class 0 OID 0)
-- Dependencies: 211
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- TOC entry 214 (class 1259 OID 25818)
-- Name: deliveries; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.deliveries (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    index character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    value character varying(255) NOT NULL
);


ALTER TABLE public.deliveries OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 25817)
-- Name: deliveries_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.deliveries_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.deliveries_id_seq OWNER TO postgres;

--
-- TOC entry 3412 (class 0 OID 0)
-- Dependencies: 213
-- Name: deliveries_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.deliveries_id_seq OWNED BY public.deliveries.id;


--
-- TOC entry 216 (class 1259 OID 25827)
-- Name: order_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_items (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    quantity integer NOT NULL,
    updated_at timestamp without time zone,
    product_id bigint,
    sale_order_id bigint
);


ALTER TABLE public.order_items OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 25826)
-- Name: order_items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_items_id_seq OWNER TO postgres;

--
-- TOC entry 3413 (class 0 OID 0)
-- Dependencies: 215
-- Name: order_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_items_id_seq OWNED BY public.order_items.id;


--
-- TOC entry 218 (class 1259 OID 25834)
-- Name: product_images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_images (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    image_public_id character varying(255),
    image_url character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    product_id bigint
);


ALTER TABLE public.product_images OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 25833)
-- Name: product_images_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_images_id_seq OWNER TO postgres;

--
-- TOC entry 3414 (class 0 OID 0)
-- Dependencies: 217
-- Name: product_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_images_id_seq OWNED BY public.product_images.id;


--
-- TOC entry 220 (class 1259 OID 25843)
-- Name: product_rates; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_rates (
    id bigint NOT NULL,
    comment character varying(255),
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    value integer NOT NULL,
    product_id bigint,
    user_id bigint
);


ALTER TABLE public.product_rates OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 25842)
-- Name: product_rates_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_rates_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_rates_id_seq OWNER TO postgres;

--
-- TOC entry 3415 (class 0 OID 0)
-- Dependencies: 219
-- Name: product_rates_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_rates_id_seq OWNED BY public.product_rates.id;


--
-- TOC entry 222 (class 1259 OID 25850)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id bigint NOT NULL,
    author character varying(255),
    created_at timestamp without time zone,
    current_number integer NOT NULL,
    long_description character varying(100000) NOT NULL,
    number_of_page integer NOT NULL,
    price bigint NOT NULL,
    quantity_purchased integer,
    short_description character varying(255),
    slug character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    category_id bigint
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 25849)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq OWNER TO postgres;

--
-- TOC entry 3416 (class 0 OID 0)
-- Dependencies: 221
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 224 (class 1259 OID 25859)
-- Name: sale_orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sale_orders (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    customer_address character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    delivery_id bigint,
    user_id bigint
);


ALTER TABLE public.sale_orders OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 25858)
-- Name: sale_orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sale_orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sale_orders_id_seq OWNER TO postgres;

--
-- TOC entry 3417 (class 0 OID 0)
-- Dependencies: 223
-- Name: sale_orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sale_orders_id_seq OWNED BY public.sale_orders.id;


--
-- TOC entry 226 (class 1259 OID 25868)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    amount bigint NOT NULL,
    created_at timestamp without time zone,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 25867)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 3418 (class 0 OID 0)
-- Dependencies: 225
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3204 (class 2604 OID 25803)
-- Name: banners id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banners ALTER COLUMN id SET DEFAULT nextval('public.banners_id_seq'::regclass);


--
-- TOC entry 3205 (class 2604 OID 25812)
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- TOC entry 3206 (class 2604 OID 25821)
-- Name: deliveries id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deliveries ALTER COLUMN id SET DEFAULT nextval('public.deliveries_id_seq'::regclass);


--
-- TOC entry 3207 (class 2604 OID 25830)
-- Name: order_items id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items ALTER COLUMN id SET DEFAULT nextval('public.order_items_id_seq'::regclass);


--
-- TOC entry 3208 (class 2604 OID 25837)
-- Name: product_images id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_images ALTER COLUMN id SET DEFAULT nextval('public.product_images_id_seq'::regclass);


--
-- TOC entry 3209 (class 2604 OID 25846)
-- Name: product_rates id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rates ALTER COLUMN id SET DEFAULT nextval('public.product_rates_id_seq'::regclass);


--
-- TOC entry 3210 (class 2604 OID 25853)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 3211 (class 2604 OID 25862)
-- Name: sale_orders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sale_orders ALTER COLUMN id SET DEFAULT nextval('public.sale_orders_id_seq'::regclass);


--
-- TOC entry 3212 (class 2604 OID 25871)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3388 (class 0 OID 25800)
-- Dependencies: 210
-- Data for Name: banners; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.banners (id, created_at, image_url, is_active, title, type, updated_at) VALUES (1, '2022-03-13 07:26:22.545', NULL, false, 'Nhân vật hạ cấp Tomozaki', 2, '2022-03-13 07:26:22.545');
INSERT INTO public.banners (id, created_at, image_url, is_active, title, type, updated_at) VALUES (2, '2022-03-13 07:26:22.545', NULL, false, '''Cậu'' ma nhà xí Hanako', 1, '2022-03-13 07:26:22.545');
INSERT INTO public.banners (id, created_at, image_url, is_active, title, type, updated_at) VALUES (3, '2022-03-13 07:26:22.545', NULL, false, 'Nhà có cụ mèo già', 2, '2022-03-13 07:26:22.545');
INSERT INTO public.banners (id, created_at, image_url, is_active, title, type, updated_at) VALUES (4, '2022-03-13 07:26:22.545', NULL, false, 'Nhật kí những trái tim xanh đỏ', 2, '2022-03-13 07:26:22.545');


--
-- TOC entry 3390 (class 0 OID 25809)
-- Dependencies: 212
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (1, '2022-03-13 07:26:22.554', NULL, false, 'Lịch sử truyền thống', 'lich-su-truyen-thong', '2022-03-13 07:26:22.554', NULL);
INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (2, '2022-03-13 07:26:22.554', NULL, false, 'Sách công cụ Đoàn - Đội', 'sach-cong-cu-doan-doi', '2022-03-13 07:26:22.554', NULL);
INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (3, '2022-03-13 07:26:22.554', NULL, false, 'Kiến thức - Khoa học', 'kien-thuc-khoa-hoc', '2022-03-13 07:26:22.554', NULL);
INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (4, '2022-03-13 07:26:22.554', NULL, false, 'Văn học Việt Nam', 'van-hoc-viet-nam', '2022-03-13 07:26:22.554', NULL);
INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (5, '2022-03-13 07:26:22.554', NULL, false, 'Văn học nước ngoài', 'van-hoc-nuoc-ngoai', '2022-03-13 07:26:22.554', NULL);
INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (6, '2022-03-13 07:26:22.554', NULL, false, 'Truyện tranh', 'truyen-tranh', '2022-03-13 07:26:22.554', NULL);
INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (7, '2022-03-13 07:26:22.555', NULL, false, 'Manga - comic', 'manga-comic', '2022-03-13 07:26:22.555', NULL);
INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (8, '2022-03-13 07:26:22.555', NULL, false, 'Wings Books', 'wings-books', '2022-03-13 07:26:22.555', NULL);
INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (9, '2022-03-13 07:26:22.555', NULL, false, 'Giải mã bản thân', 'giai-ma-ban-than', '2022-03-13 07:26:22.555', NULL);
INSERT INTO public.categories (id, created_at, description, is_author, name, slug, updated_at, parent_category_id) VALUES (10, '2022-03-13 07:26:22.555', NULL, false, 'Combo', 'combo', '2022-03-13 07:26:22.555', NULL);


--
-- TOC entry 3392 (class 0 OID 25818)
-- Dependencies: 214
-- Data for Name: deliveries; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.deliveries (id, created_at, index, updated_at, value) VALUES (1, '2022-03-13 14:26:02.795', 'DaThemVaoGio', '2022-03-13 14:26:02.795', 'Đã thêm vào giỏ');
INSERT INTO public.deliveries (id, created_at, index, updated_at, value) VALUES (2, '2022-03-13 14:26:02.796', 'ChoXacNhan', '2022-03-13 14:26:02.796', 'Chờ xác nhận');
INSERT INTO public.deliveries (id, created_at, index, updated_at, value) VALUES (3, '2022-03-13 14:26:02.797', 'DangGiaoHang', '2022-03-13 14:26:02.797', 'Đang giao hàng');
INSERT INTO public.deliveries (id, created_at, index, updated_at, value) VALUES (4, '2022-03-13 14:26:02.797', 'DaGiao', '2022-03-13 14:26:02.797', 'Đã giao');
INSERT INTO public.deliveries (id, created_at, index, updated_at, value) VALUES (5, '2022-03-13 14:26:02.798', 'DaHuy', '2022-03-13 14:26:02.798', 'Đã hủy');


--
-- TOC entry 3394 (class 0 OID 25827)
-- Dependencies: 216
-- Data for Name: order_items; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3396 (class 0 OID 25834)
-- Dependencies: 218
-- Data for Name: product_images; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3398 (class 0 OID 25843)
-- Dependencies: 220
-- Data for Name: product_rates; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3400 (class 0 OID 25850)
-- Dependencies: 222
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (1, 'Masashi Kishimoto', '2022-03-13 07:26:22.604', 201, 'Naruto - Tập 71', 208, 22500, 0, 'Naruto - Tập 71', 'naruto-tap-71', 'Naruto - Tập 71', '2022-03-13 07:26:22.604', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (2, 'Tatsuki Nohda', '2022-03-13 07:26:22.604', 427, 'Vua sáng chế - Tập 5', 192, 18000, 0, 'Vua sáng chế - Tập 5', 'vua-sang-che-tap-5', 'Vua sáng chế - Tập 5', '2022-03-13 07:26:22.604', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (3, 'Takaya Kagami,Yamato Yamamoto,Daisuke Furuya', '2022-03-13 07:26:22.604', 176, 'Thiên thần diệt thế - Seraph of the end - Tập 22', 196, 19800, 0, 'Thiên thần diệt thế - Seraph of the end - Tập 22', 'thien-than-diet-the-seraph-of-the-end-tap-22', 'Thiên thần diệt thế - Seraph of the end - Tập 22', '2022-03-13 07:26:22.604', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (4, 'Hikaru Nakamura', '2022-03-13 07:26:22.604', 188, 'Bên dưới cây cầu Arakawa - Tập 10', 180, 27000, 0, 'Bên dưới cây cầu Arakawa - Tập 10', 'ben-duoi-cay-cau-arakawa-tap-10', 'Bên dưới cây cầu Arakawa - Tập 10', '2022-03-13 07:26:22.604', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (5, 'Huy Thông', '2022-03-13 07:26:22.604', 366, 'Cơ Bản là Cơ Bản', 208, 43200, 0, 'Cơ Bản là Cơ Bản', 'co-ban-la-co-ban', 'Cơ Bản là Cơ Bản', '2022-03-13 07:26:22.604', 4);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (6, 'Cương Tuyết Ấn', '2022-03-13 07:26:22.604', 210, 'Combo Hồ sơ tội phạm (2 quyển)', 0, 156000, 0, 'Combo Hồ sơ tội phạm (2 quyển)', 'combo-ho-so-toi-pham-2-quyen', 'Combo Hồ sơ tội phạm (2 quyển)', '2022-03-13 07:26:22.604', 10);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (7, 'Shirley Jackson,Mary Shelley,Robert Louis Stevenson,Henry James', '2022-03-13 07:26:22.604', 418, 'Combo Sách Kinh điển (4 quyển)', 0, 186600, 0, 'Combo Sách Kinh điển (4 quyển)', 'combo-sach-kinh-dien-4-quyen', 'Combo Sách Kinh điển (4 quyển)', '2022-03-13 07:26:22.604', 10);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (8, 'Liz Marvin,Annie Davidson,Richard Harrington,Annie Davidson,Oliver Luke Delorie', '2022-03-13 07:26:22.605', 295, 'Combo Triết lí sống đẹp (3 quyển)', 0, 135000, 0, 'Combo Triết lí sống đẹp (3 quyển)', 'combo-triet-li-song-dep-3-quyen', 'Combo Triết lí sống đẹp (3 quyển)', '2022-03-13 07:26:22.605', 10);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (9, 'Nhật Xuất Tiểu Thái Dương,Yaya,Mèo Lọc Cọc', '2022-03-13 07:26:22.605', 126, 'Combo Artbook mĩ thuật cổ trang (3 quyển)', 0, 268800, 0, 'Combo Artbook mĩ thuật cổ trang (3 quyển)', 'combo-artbook-mi-thuat-co-trang-3-quyen', 'Combo Artbook mĩ thuật cổ trang (3 quyển)', '2022-03-13 07:26:22.605', 10);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (10, 'Lí Tư Viên,Tokio Godo,Lý Thành Cơ', '2022-03-13 07:26:22.605', 90, 'Combo Sức mạnh của sự cô đơn (3 quyển)', 0, 153600, 0, 'Combo Sức mạnh của sự cô đơn (3 quyển)', 'combo-suc-manh-cua-su-co-don-3-quyen', 'Combo Sức mạnh của sự cô đơn (3 quyển)', '2022-03-13 07:26:22.605', 10);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (11, 'AidaIro', '2022-03-13 07:26:22.605', 314, '''Cậu'' ma nhà xí Hanako - Tập 14', 178, 27000, 0, '''Cậu'' ma nhà xí Hanako - Tập 14', 'cau-ma-nha-xi-hanako-tap-14', '''Cậu'' ma nhà xí Hanako - Tập 14', '2022-03-13 07:26:22.605', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (12, 'Fujiko F Fujio', '2022-03-13 07:26:22.605', 35, 'Doraemon truyện dài - Tập 8 - Nobita và hiệp sĩ rồng', 192, 16200, 0, 'Doraemon truyện dài - Tập 8 - Nobita và hiệp sĩ rồng', 'doraemon-truyen-dai-tap-8-nobita-va-hiep-si-rong', 'Doraemon truyện dài - Tập 8 - Nobita và hiệp sĩ rồng', '2022-03-13 07:26:22.605', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (13, 'Fujiko F Fujio', '2022-03-13 07:26:22.605', 244, 'Doraemon truyện dài - Tập 7 - Nobita và binh đoàn người sắt', 192, 16200, 0, 'Doraemon truyện dài - Tập 7 - Nobita và binh đoàn người sắt', 'doraemon-truyen-dai-tap-7-nobita-va-binh-doan-nguoi-sat', 'Doraemon truyện dài - Tập 7 - Nobita và binh đoàn người sắt', '2022-03-13 07:26:22.605', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (14, 'Fujiko F Fujio', '2022-03-13 07:26:22.605', 197, 'Doraemon tuyển tập tranh truyện màu - Tập 5', 160, 36000, 0, 'Doraemon tuyển tập tranh truyện màu - Tập 5', 'doraemon-tuyen-tap-tranh-truyen-mau-tap-5', 'Doraemon tuyển tập tranh truyện màu - Tập 5', '2022-03-13 07:26:22.605', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (15, 'Fujiko F Fujio,Mugiwara Shintaro', '2022-03-13 07:26:22.605', 73, 'Doraemon bóng chày - Truyền kì về bóng chày siêu cấp - Tập 4', 192, 16200, 0, 'Doraemon bóng chày - Truyền kì về bóng chày siêu cấp - Tập 4', 'doraemon-bong-chay-truyen-ki-ve-bong-chay-sieu-cap-tap-4', 'Doraemon bóng chày - Truyền kì về bóng chày siêu cấp - Tập 4', '2022-03-13 07:26:22.605', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (16, 'Fujiko F Fujio', '2022-03-13 07:26:22.605', 262, 'Doraemon - Nobita và người khổng lồ xanh (2020)', 194, 20250, 0, 'Doraemon - Nobita và người khổng lồ xanh (2020)', 'doraemon-nobita-va-nguoi-khong-lo-xanh-2020', 'Doraemon - Nobita và người khổng lồ xanh (2020)', '2022-03-13 07:26:22.605', 7);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (17, 'Fujihito', '2022-03-13 07:26:22.605', 371, 'Nhà có cụ mèo già', 128, 56000, 0, 'Nhà có cụ mèo già', 'nha-co-cu-meo-gia', 'Nhà có cụ mèo già', '2022-03-13 07:26:22.605', 8);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (18, 'Yuki Yaku,Fly', '2022-03-13 07:26:22.605', 252, 'Nhân vật hạ cấp Tomozaki - Tập 2 - Bản giới hạn', 424, 77700, 0, 'Nhân vật hạ cấp Tomozaki - Tập 2 - Bản giới hạn', 'nhan-vat-ha-cap-tomozaki-tap-2-ban-gioi-han', 'Nhân vật hạ cấp Tomozaki - Tập 2 - Bản giới hạn', '2022-03-13 07:26:22.605', 8);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (19, 'Nekomaki (ms-work)', '2022-03-13 07:26:22.605', 54, 'Thị trấn mèo - Tập 6', 176, 47600, 0, 'Thị trấn mèo - Tập 6', 'thi-tran-meo-tap-6', 'Thị trấn mèo - Tập 6', '2022-03-13 07:26:22.605', 8);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (20, 'H. P. Lovecraft', '2022-03-13 07:26:22.605', 377, 'Lời hiệu triệu của Cthulhu - Tuyển tập H.P. Lovecraft', 252, 56000, 0, 'Lời hiệu triệu của Cthulhu - Tuyển tập H.P. Lovecraft', 'loi-hieu-trieu-cua-cthulhu-tuyen-tap-hp-lovecraft', 'Lời hiệu triệu của Cthulhu - Tuyển tập H.P. Lovecraft', '2022-03-13 07:26:22.605', 8);
INSERT INTO public.products (id, author, created_at, current_number, long_description, number_of_page, price, quantity_purchased, short_description, slug, title, updated_at, category_id) VALUES (21, 'Saekisan,Hanekoto', '2022-03-13 07:26:22.605', 153, 'Thiên sứ nhà bên - Tập 2 - Bản giới hạn', 332, 66500, 0, 'Thiên sứ nhà bên - Tập 2 - Bản giới hạn', 'thien-su-nha-ben-tap-2-ban-gioi-han', 'Thiên sứ nhà bên - Tập 2 - Bản giới hạn', '2022-03-13 07:26:22.605', 8);


--
-- TOC entry 3402 (class 0 OID 25859)
-- Dependencies: 224
-- Data for Name: sale_orders; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3404 (class 0 OID 25868)
-- Dependencies: 226
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (id, address, amount, created_at, email, first_name, last_name, password, phone, role, updated_at, username) VALUES (1, 'Ha Noi', 9999999999, '2022-03-13 14:26:02.78', 'example@gmail.com', 'Admin', 'Admin', '$2a$10$eyE6n/HfQTvanh4BEr4EDOkcRvzpn.WuJX.bCViq47ZJ0k8TURk.u', '0123456789', 'ADMIN', '2022-03-13 14:26:02.78', 'admin');


--
-- TOC entry 3419 (class 0 OID 0)
-- Dependencies: 209
-- Name: banners_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.banners_id_seq', 1, false);


--
-- TOC entry 3420 (class 0 OID 0)
-- Dependencies: 211
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categories_id_seq', 1, false);


--
-- TOC entry 3421 (class 0 OID 0)
-- Dependencies: 213
-- Name: deliveries_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.deliveries_id_seq', 5, true);


--
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 215
-- Name: order_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_items_id_seq', 1, false);


--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 217
-- Name: product_images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_images_id_seq', 1, false);


--
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 219
-- Name: product_rates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_rates_id_seq', 1, false);


--
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 221
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq', 1, false);


--
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 223
-- Name: sale_orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sale_orders_id_seq', 1, false);


--
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 225
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- TOC entry 3214 (class 2606 OID 25807)
-- Name: banners banners_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banners
    ADD CONSTRAINT banners_pkey PRIMARY KEY (id);


--
-- TOC entry 3216 (class 2606 OID 25816)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 3218 (class 2606 OID 25825)
-- Name: deliveries deliveries_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deliveries
    ADD CONSTRAINT deliveries_pkey PRIMARY KEY (id);


--
-- TOC entry 3222 (class 2606 OID 25832)
-- Name: order_items order_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (id);


--
-- TOC entry 3226 (class 2606 OID 25841)
-- Name: product_images product_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT product_images_pkey PRIMARY KEY (id);


--
-- TOC entry 3228 (class 2606 OID 25848)
-- Name: product_rates product_rates_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rates
    ADD CONSTRAINT product_rates_pkey PRIMARY KEY (id);


--
-- TOC entry 3232 (class 2606 OID 25857)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 3234 (class 2606 OID 25866)
-- Name: sale_orders sale_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sale_orders
    ADD CONSTRAINT sale_orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3220 (class 2606 OID 25877)
-- Name: deliveries uk_kyqdkfm9upsgcubxa20o37i18; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deliveries
    ADD CONSTRAINT uk_kyqdkfm9upsgcubxa20o37i18 UNIQUE (index);


--
-- TOC entry 3236 (class 2606 OID 25883)
-- Name: users uk_r43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- TOC entry 3224 (class 2606 OID 25879)
-- Name: order_items uknrjaeb0gey1e706e2ivn9sygs; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT uknrjaeb0gey1e706e2ivn9sygs UNIQUE (product_id, sale_order_id);


--
-- TOC entry 3230 (class 2606 OID 25881)
-- Name: product_rates ukrc0so10gt0yy6hy7xlt1vkyib; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rates
    ADD CONSTRAINT ukrc0so10gt0yy6hy7xlt1vkyib UNIQUE (user_id, product_id);


--
-- TOC entry 3238 (class 2606 OID 25875)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3241 (class 2606 OID 25894)
-- Name: order_items fk617qjnbugdcu19l0ujrmvc0k0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT fk617qjnbugdcu19l0ujrmvc0k0 FOREIGN KEY (sale_order_id) REFERENCES public.sale_orders(id);


--
-- TOC entry 3247 (class 2606 OID 25924)
-- Name: sale_orders fk878qlowl178in2cloggfloetw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sale_orders
    ADD CONSTRAINT fk878qlowl178in2cloggfloetw FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3239 (class 2606 OID 25884)
-- Name: categories fk9il7y6fehxwunjeepq0n7g5rd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT fk9il7y6fehxwunjeepq0n7g5rd FOREIGN KEY (parent_category_id) REFERENCES public.categories(id);


--
-- TOC entry 3244 (class 2606 OID 25909)
-- Name: product_rates fkgaxwtr1nvvmb66in9enivvgax; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rates
    ADD CONSTRAINT fkgaxwtr1nvvmb66in9enivvgax FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3243 (class 2606 OID 25904)
-- Name: product_rates fkghguf5u0jqoeqe644on63rtqi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rates
    ADD CONSTRAINT fkghguf5u0jqoeqe644on63rtqi FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 3240 (class 2606 OID 25889)
-- Name: order_items fkocimc7dtr037rh4ls4l95nlfi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT fkocimc7dtr037rh4ls4l95nlfi FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 3245 (class 2606 OID 25914)
-- Name: products fkog2rp4qthbtt2lfyhfo32lsw9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fkog2rp4qthbtt2lfyhfo32lsw9 FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- TOC entry 3246 (class 2606 OID 25919)
-- Name: sale_orders fkonw1ulg2n52kvuqry9tx8fqqy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sale_orders
    ADD CONSTRAINT fkonw1ulg2n52kvuqry9tx8fqqy FOREIGN KEY (delivery_id) REFERENCES public.deliveries(id);


--
-- TOC entry 3242 (class 2606 OID 25899)
-- Name: product_images fkqnq71xsohugpqwf3c9gxmsuy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT fkqnq71xsohugpqwf3c9gxmsuy FOREIGN KEY (product_id) REFERENCES public.products(id);


-- Completed on 2022-03-13 14:33:54

--
-- PostgreSQL database dump complete
--

