import os
import insert_from_file
import threading

def insert(table: str, columns: list, json_fields: list):
    threads = []
    print(f'Inserting {table}')
    for i in os.listdir(f'data/{table}'):
        threads.append(threading.Thread(target=insert_from_file.insert, args=[table, f'{table}/{i[:-5]}', columns, json_fields]))
    
    for i in threads:
        i.start()
    for i in threads:
        i.join()
    print(f'End {table}')

