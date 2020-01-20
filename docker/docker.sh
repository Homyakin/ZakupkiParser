docker stop zakupki || true \
&& docker rm zakupki || true \
&& docker build -f Dockerfile -t zakupki_img . \
&& docker run --name zakupki -p 3306:3306 -v $(pwd)/db_zakupki:/var/lib/mysql -d zakupki_img