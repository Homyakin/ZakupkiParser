import insert_from_file

def insert():
    table = 'delivery_place_indication'
    columns = ['code', 'name']

    insert_from_file.insert(table, table, columns, columns)
