docker stop zakupki || true \
&& docker rm zakupki || true \
&& docker run --name zakupki -p 3306:3306 -e MYSQL_ROOT_PASSWORD=12345 -v $(pwd)/db_zakupki:/var/lib/mysql -d mysql:8.0.19
