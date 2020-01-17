import insert_in_db

def insert():
    table = 'okopf'
    columns = ['code', 'name']

    insert_in_db.insert(table, columns, columns)

