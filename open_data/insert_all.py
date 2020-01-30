import insert_country
import insert_currency
import insert_okato
import insert_okdp
import insert_okei
import insert_okopf
import insert_okpd
import insert_okpd2
import insert_oktmo
import insert_okved
import insert_okved2
import insert_plan_item_status
import insert_plan_status
import insert_purchase_category
import insert_purchase_type
import threading

threads = [threading.Thread(target=insert_currency.insert), threading.Thread(target=insert_okato.insert),
           threading.Thread(target=insert_okdp.insert), threading.Thread(target=insert_okei.insert),
           threading.Thread(target=insert_okopf.insert), threading.Thread(target=insert_okpd2.insert),
           threading.Thread(target=insert_okved.insert), threading.Thread(target=insert_okved2.insert),
           threading.Thread(target=insert_plan_item_status.insert), threading.Thread(target=insert_plan_status.insert),
           threading.Thread(target=insert_purchase_category.insert), threading.Thread(target=insert_country.insert),
           threading.Thread(target=insert_purchase_type.insert), threading.Thread(target=insert_oktmo.insert),
           threading.Thread(target=insert_okpd.insert)]

for i in threads:
    i.start()

for i in threads:
    i.join()

