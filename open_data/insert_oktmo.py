import insert_from_folder

def insert():
    table = 'oktmo'
    columns = ['code', 'name']

    insert_from_folder.insert(table, columns, columns)