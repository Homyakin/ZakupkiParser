import xmltodict
import json
with open('xml/data/okei.xml') as fd:
    data = xmltodict.parse(fd.read())
    data = data['ns2:nsiOkei']['ns2:body']
    out = []
    codes = {}
    for i in data['ns2:item']:
        try:
            temp = {}
            temp['name'] = i['ns2:nsiOkeiData']['ns2:name']
            temp['code'] = i['ns2:nsiOkeiData']['ns2:code']
            if temp['code'] not in codes:
                codes[temp['code']] = temp['name']
            else:
                if len(codes[temp['code']]) < len(temp['name']):
                    codes[temp['code']] = temp['name']
                print(codes[temp['code']])
                print(temp['code'], temp['name'])
        except Exception as e:
            print(e)
            print(i)
    for i in codes:
        temp = {}
        temp['name'] = codes[i]
        temp['code'] = i
        out.append(temp)
    with open('xml/data/okei.json', 'w') as f:
        json.dump(out, f, ensure_ascii=False)