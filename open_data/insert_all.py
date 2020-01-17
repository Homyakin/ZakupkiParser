import insert_currency
import insert_okato
import insert_okdp
import insert_okei
import insert_okopf
import insert_okpd2
import insert_okved
import insert_okved2
import insert_plan_item_status
import insert_plan_status
import insert_purchase_category
import threading

threads = []
threads.append(threading.Thread(target=insert_currency.insert))
threads.append(threading.Thread(target=insert_okato.insert))
threads.append(threading.Thread(target=insert_okdp.insert))
threads.append(threading.Thread(target=insert_okei.insert))
threads.append(threading.Thread(target=insert_okopf.insert))
threads.append(threading.Thread(target=insert_okpd2.insert))
threads.append(threading.Thread(target=insert_okved.insert))
threads.append(threading.Thread(target=insert_okved2.insert))
threads.append(threading.Thread(target=insert_plan_item_status.insert))
threads.append(threading.Thread(target=insert_plan_status.insert))
threads.append(threading.Thread(target=insert_purchase_category.insert))

for i in threads:
    i.start()

for i in threads:
    i.join()

