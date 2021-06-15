import insert_contract_status
import insert_country
import insert_currency
import insert_delivery_place_indication
import insert_plan_item_status
import insert_plan_status
import insert_purchase_category
import insert_purchase_notice_status
import insert_purchase_notice_type
import insert_purchase_type
import insert_purchase_method
import insert_application_rejection_reason
import threading

threads = [threading.Thread(target=insert_currency.insert), threading.Thread(target=insert_plan_item_status.insert),
           threading.Thread(target=insert_plan_status.insert), threading.Thread(target=insert_purchase_category.insert),
           threading.Thread(target=insert_country.insert), threading.Thread(target=insert_purchase_type.insert),
           threading.Thread(target=insert_contract_status.insert),
           threading.Thread(target=insert_purchase_notice_status.insert),
           threading.Thread(target=insert_delivery_place_indication.insert),
           threading.Thread(target=insert_purchase_notice_type.insert),
           threading.Thread(target=insert_purchase_method.insert),
           threading.Thread(target=insert_application_rejection_reason.insert)
           ]

for i in threads:
    i.start()

for i in threads:
    i.join()
