CREATE TABLE public.link (
	shortened_link uuid PRIMARY KEY ,
	original_link varchar(255) NULL
);