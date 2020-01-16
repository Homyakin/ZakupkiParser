import xmltodict
import json
with open('xml/data/okei.xml') as fd:
    data = xmltodict.parse(fd.read())
    data = data['ns2:nsiOkei']['ns2:body']
    out = []
    for i in data['ns2:item']:
        temp = {}
        temp['name'] = i['ns2:nsiOkeiData']['ns2:name']
        temp['code'] = i['ns2:nsiOkeiData']['ns2:code']
        out.append(temp)
    with open('xml/data/okei.json', 'w') as f:
        json.dump(out, f, ensure_ascii=False)