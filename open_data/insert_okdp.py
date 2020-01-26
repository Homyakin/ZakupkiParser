import insert_from_folder

def insert():
    table = 'okdp'
    columns = ['code', 'name']

    insert_from_folder.insert(table, columns, columns)
