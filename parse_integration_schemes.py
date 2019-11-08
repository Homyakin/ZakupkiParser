import os

l = os.listdir('./integration_schemes')
for i in l:
    os.system(f'xjc -d . ./integration_schemes/{i}')