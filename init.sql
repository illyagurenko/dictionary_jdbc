create table dictionary_en_ru (
	id serial primary key,
	english_word varchar(100) not null unique,
	russian_word varchar(100) not null
);

insert into dictionary_en_ru (english_word, russian_word) values ('hello', 'привет');
insert into dictionary_en_ru (english_word, russian_word) values ('world', 'мир');
