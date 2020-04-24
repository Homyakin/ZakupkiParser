import insert_from_file

def insert():
    table = 'delivary_place_indication'
    columns = ['code', 'name']

    insert_from_file.insert(table, table, columns, columns)
