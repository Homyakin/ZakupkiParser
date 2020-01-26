import insert_from_file

def insert():
    table = 'okopf'
    columns = ['code', 'name']

    insert_from_file.insert(table, table, columns, columns)

