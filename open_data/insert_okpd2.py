import insert_from_folder

def insert():
    table = 'okpd2'
    columns = ['code', 'name']

    insert_from_folder.insert(table, columns, columns)
