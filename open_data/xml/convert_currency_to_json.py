import xmltodict
import json
with open('xml/data/currency.xml') as fd:
    data = xmltodict.parse(fd.read())
    data = data['ns2:nsiOkv']['ns2:body']
    out = []
    codes = {}
    for i in data['ns2:item']:
        try:
            temp = {}
            temp['name'] = i['ns2:nsiOkvData']['ns2:name']
            temp['code'] = i['ns2:nsiOkvData']['ns2:code']
            temp['digitalCode'] = i['ns2:nsiOkvData']['ns2:digitalCode']
            if temp['code'] not in codes:
                codes[temp['code']] = temp['name']
            else:
                print(codes[temp['code']])
                print(temp['code'], temp['name'])
            out.append(temp)
        except Exception as e:
            print(e)
            print(i)
    with open('xml/data/currency.json', 'w') as f:
        json.dump(out, f, ensure_ascii=False)