docker stop zakupki || true \
&& docker rm zakupki || true \
&& docker build -f Dockerfile -t zakupki_img . \
&& docker run --name zakupki -p 3306:3306 -e MYSQL_ROOT_PASSWORD=12345  -d mysql:8.0.19 \
&& sleep 15 \
&& docker exec -i zakupki mysql -uroot -p12345 < sql/script.sql

#-v $(pwd)/db_zakupki:/var/lib/mysql