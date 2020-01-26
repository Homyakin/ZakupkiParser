import insert_from_file

def insert():
    table = 'okved'
    columns = ['code', 'name']

    insert_from_file.insert(table, table, columns, columns)
