import xmltodict
import json
import os

out = []
codes = {}
for i in os.listdir('xml/data/oktmo'):
    with open(f'xml/data/oktmo/{i}') as fd:
        data = xmltodict.parse(fd.read())
        data = data['ns2:nsiOktmo']['ns2:body']
        for i in data['ns2:item']:
            try:
                temp = {}
                temp['name'] = i['ns2:nsiOktmoData']['ns2:name']
                temp['code'] = i['ns2:nsiOktmoData']['ns2:code']
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
        with open(f'xml/data/oktmo/oktmo{file_number}.json', 'w') as f:
            json.dump(out, f, ensure_ascii=False)
        file_number += 1
        out = []
if len(out) != 0:
    with open(f'xml/data/oktmo/oktmo{file_number}.json', 'w') as f:
                json.dump(out, f, ensure_ascii=False)