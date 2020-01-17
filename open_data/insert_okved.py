import insert_in_db

def insert():
    table = 'okved'
    columns = ['code', 'name']

    insert_in_db.insert(table, columns, columns)
