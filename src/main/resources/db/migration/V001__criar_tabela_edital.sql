CREATE TABLE edital(
    	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	numero VARCHAR(20) NOT NULL,
	data_inicio DATE NOT NULL,
	data_termino DATE NOT NULL,
	vigente BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
