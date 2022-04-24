import os

l = os.listdir('./integration_schemes')
for i in l:
    # В пути не должно быть кириллических символов
    os.system(f'xjc -d . ./integration_schemes/{i}')