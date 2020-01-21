docker stop zakupki || true \
&& docker rm zakupki || true \
&& docker build -f Dockerfile -t zakupki_img . \
&& docker run --name zakupki -p 3306:3306 -e MYSQL_ROOT_PASSWORD=12345 -v $(pwd)/db_zakupki:/var/lib/mysql -d zakupki_img \
&& sleep 10 \
&& docker exec -i zakupki mysql -uroot -p12345 < sql/script.sql
