import insert_in_db

def insert():
    table = 'okdp'
    columns = ['code', 'name']

    insert_in_db.insert(table, columns, columns)
