import insert_in_db

def insert():
    table = 'currency'
    columns = ['code', 'digital_code', 'name']
    json_fields = ['code', 'digitalCode', 'name']

    insert_in_db.insert(table, columns, json_fields)

