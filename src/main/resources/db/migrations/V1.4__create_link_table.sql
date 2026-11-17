Alter TABLE public.link
    add created_At TIMESTAMPTZ NULL;
Alter TABLE public.link
    drop column creation_date;