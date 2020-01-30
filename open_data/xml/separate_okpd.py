import json

out = []
codes = {}
with open(f'xml/data/okpd.json') as json_file:
    data = json.load(json_file)
    for i in data:
        try:
            temp = {}
            temp['name'] = i['Name']
            temp['code'] = i['Kod']
            if temp['code'] in codes:
                print(codes[temp['code']])
                print(temp['code'], temp['name'])
            else:
                codes[temp['code']] = temp['name']
        except Exception as e:
            print(e)
            print(i)

file_number = 1
for i in codes:
    temp = {}
    temp['code'] = i
    temp['name'] = codes[i]
    out.append(temp)
    if len(out) == 5000:
        with open(f'xml/data/okpd/okpd{file_number}.json', 'w') as f:
            json.dump(out, f, ensure_ascii=False)
        file_number += 1
        out = []
if len(out) != 0:
    with open(f'xml/data/okpd/okpd{file_number}.json', 'w') as f:
                json.dump(out, f, ensure_ascii=False)
