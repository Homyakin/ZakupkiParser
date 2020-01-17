import insert_in_db

def insert():
    table = 'plan_item_status'
    columns = ['code', 'name']

    insert_in_db.insert(table, columns, columns)
