import insert_from_file

def insert():
    table = 'contract_status'
    columns = ['code', 'name']

    insert_from_file.insert(table, table, columns, columns)
