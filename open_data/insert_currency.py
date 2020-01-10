import insert

table = 'currency'
columns = ['code', 'digital_code', 'name']
json_fields = ['STRCODE', 'CODE', 'NAME']

insert.insert(table, columns, json_fields)