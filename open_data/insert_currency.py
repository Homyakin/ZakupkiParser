import insert_from_file

def insert():
    table = 'currency'
    columns = ['code', 'digital_code', 'name']
    json_fields = ['code', 'digitalCode', 'name']

    insert_from_file.insert(table, table, columns, json_fields)

