import insert

table = 'currency'
columns = ['code', 'digital_code', 'name']
json_fields = ['code', 'digitalCode', 'name']

insert.insert(table, columns, json_fields)
