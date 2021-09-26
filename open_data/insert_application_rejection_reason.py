import insert_from_file

def insert():
    table = 'application_rejection_reason'
    columns = ['code', 'description']

    insert_from_file.insert(table, table, columns, columns)
