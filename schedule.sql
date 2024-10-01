create table user
(
    id            int AUTO_INCREMENT PRIMARY KEY ,
    serialNumber char(8)      not null,
    name          varchar(100) not null,
    password      varchar(100) not null,
    FOREIGN KEY (serialNumber) REFERENCES schedul(serialNumber)
        on update cascade
        on delete cascade
);