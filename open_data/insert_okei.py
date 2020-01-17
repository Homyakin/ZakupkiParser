import insert_in_db

def insert():
    table = 'okei'
    columns = ['code', 'name']

    insert_in_db.insert(table, columns, columns)
