FROM mysql:8.0.19

ENV MYSQL_ROOT_PASSWORD=12345
ENV MYSQL_DATABASE=zakupki

COPY sql/script.sql /docker-entrypoint-initdb.d/

EXPOSE 3306

VOLUME /var/lib/mysql
