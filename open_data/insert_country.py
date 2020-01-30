import pymysql
from pymysql.cursors import DictCursor
import json

def insert():
    table = 'country'
    columns = ['code', 'short_name', 'full_name']

    connection = pymysql.connect(
        host='localhost',
        user='root',
        password='12345',
        db='zakupki',
        charset='utf8mb4',
        cursorclass=DictCursor
    )
    print(f'Inserting {table}')
    with open(f'data/{table}.json') as json_file:
        data = json.load(json_file)
        for i in data:
            try:
                with connection.cursor() as cursor:
                    s = ", ".join(columns)
                    template = ', '.join(['%s' for _ in range(len(columns))])
                    sql = f'INSERT INTO zakupki.{table} ({s}) VALUES ({template})'
                    fields = [i['CODE'], i['SHORTNAME']]
                    try:
                        fields.append(i['FULLNAME'])
                    except Exception as _:
                        fields.append(None)
                    cursor.execute(sql, fields)
                    connection.commit()
            except Exception as e:
                print(table, i, e)
    print(f'End {table}')
    connection.close()