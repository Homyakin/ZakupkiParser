import insert_in_db

def insert():
    table = 'okved2'
    columns = ['code', 'name']

    insert_in_db.insert(table, columns, columns)
