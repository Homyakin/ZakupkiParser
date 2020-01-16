import xmltodict
import json
with open('xml/data/okopf.xml') as fd:
    data = xmltodict.parse(fd.read())
    data = data['ns2:nsiOkopf']['ns2:body']
    out = []
    codes = {}
    for i in data['ns2:item']:
        try:
            temp = {}
            temp['name'] = i['ns2:nsiOkopfData']['ns2:name']
            temp['code'] = i['ns2:nsiOkopfData']['ns2:code']
            if temp['code'] not in codes:
                codes[temp['code']] = temp['name']
            else:
                print(codes[temp['code']])
                print(temp['code'], temp['name'])
            out.append(temp)
        except Exception as e:
            print(e)
            print(i)
    with open('xml/data/okopf.json', 'w') as f:
        json.dump(out, f, ensure_ascii=False)