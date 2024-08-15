CREATE SCHEMA IF NOT EXISTS project_management;

CREATE TABLE IF NOT EXISTS project_management.attachment
(
    id integer NOT NULL DEFAULT nextval('project_management.attachment_id_seq'::regclass),
    task_id integer NOT NULL,
    file_name character varying(255) COLLATE pg_catalog."default",
    file_path character varying(255) COLLATE pg_catalog."default",
    uploaded_by integer,
    uploaded_on timestamp without time zone,
    CONSTRAINT attachment_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS project_management.audit
(
    user_id integer,
    created_on timestamp without time zone NOT NULL,
    event jsonb
);

CREATE TABLE IF NOT EXISTS project_management.project
(
    id integer NOT NULL DEFAULT nextval('project_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    start_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    end_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    status character varying(25) COLLATE pg_catalog."default",
    created_by integer,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    modified_by integer,
    modified_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT project_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS project_management.task
(
    id integer NOT NULL DEFAULT nextval('project_management.task_id_seq'::regclass),
    proj_id integer NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    assignee integer,
    priority character varying(20) COLLATE pg_catalog."default",
    status character varying(20) COLLATE pg_catalog."default",
    due_date timestamp without time zone,
    created_by integer NOT NULL,
    created_on timestamp without time zone NOT NULL,
    modified_by integer NOT NULL,
    modified_on timestamp without time zone NOT NULL,
    CONSTRAINT task_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS project_management."user"
(
    id integer NOT NULL DEFAULT nextval('project_management.user_id_seq'::regclass),
    name character varying(55) COLLATE pg_catalog."default" NOT NULL,
    email character varying(55) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    org_role character varying(10) COLLATE pg_catalog."default" NOT NULL,
    created_on timestamp without time zone NOT NULL,
    modified_on timestamp without time zone NOT NULL,
    created_by character varying(55) COLLATE pg_catalog."default",
    username character varying(20) COLLATE pg_catalog."default" NOT NULL,
    modified_by character varying(20) COLLATE pg_catalog."default",
    is_active boolean NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT email UNIQUE (email),
    CONSTRAINT username UNIQUE (username)
);

INSERT INTO project_management."user"(
    name, email, password, org_role, created_on, modified_on, username, is_active)
    VALUES ('Rama', 'rama@gmail.com', '$2a$10$fhc9nZbDfBwnFo2kazfDOeOqjqOuVtLZ0sqKZJ8FF0xhKkiwit6hu', 'USER', now(), now(), 'rama', true);

INSERT INTO project_management.project(
    name, description, start_date, end_date, status, created_by, created_on, modified_by, modified_on)
    VALUES ('project1', 'new project1', now(), now()+ INTERVAL '1 year', 'CREATED', 1, now(), 1, now());


INSERT INTO project_management.task(
    proj_id, title, description, assignee, priority, status, due_date, created_by, created_on, modified_by, modified_on)
    VALUES (1, 'task1 for project1', 'description of task', 1, '', 'CREATED', null, 1, now(), 1, now());