import insert_in_db

def insert():
    table = 'purchase_category'
    columns = ['code', 'description']

    insert_in_db.insert(table, columns, columns)
