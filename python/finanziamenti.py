import matplotlib.pyplot as plt 
import pandas as pd 
import numpy as np 
from sklearn.preprocessing import LabelEncoder

fin = pd.read_csv('finanziamenti.csv', sep=';',decimal=',') # comando decimal per indicare che i decimali sono indicati con una virgola e così non vengono convertiti a stringhe
print(fin.info()) # per sapere informazioni sul datatframe

# .2
print(fin['UNITA'].value_counts())
# .3
# fin['UNITA'].value_counts().plot.bar()

# unit = LabelEncoder()
# unit.fit(fin['UNITA'])
# fin['UNITA'] = unit.transform(fin['UNITA'])
# unit_freq= fin['UNITA'].value_counts().sort_index()
# print(unit_freq)
# plt.plot(unit_freq.index,unit_freq.get_values())

plt.show()

# .5
sottoinsieme = fin[fin['FinProvincia'].isna()==False & (fin['FinRegione'].isna()==False)] # elimino i Nan
progetti_a = fin[fin['FinProvincia']<fin['FinRegione']]
print(len(progetti_a))
progetti_b = fin[fin['FinProvincia']>=fin['FinRegione']]
print(len(progetti_b))

# .6
selezione_progetti_a = progetti_a[200<=progetti_a['FinProvincia']]
selezione_progetti_a = selezione_progetti_a[selezione_progetti_a['FinProvincia']<1000]
print(selezione_progetti_a['FinProvincia'])

ampiezza = [i*100 for i in range(2,11)]

selezione_progetti_a['FinProvincia'].plot.hist(bins=ampiezza)
#selezione_progetti_a['FinProvincia'].plot.box()

print(sum(selezione_progetti_a['FinProvincia'])/len(selezione_progetti_a['FinProvincia'])) # media
print(selezione_progetti_a['FinProvincia'].std()) # deviazione standard

print(len(selezione_progetti_a[(selezione_progetti_a['FinProvincia']>=500) & (selezione_progetti_a['FinProvincia']<=700)]))
# selezione_progetti_a = selezione_progetti_a[selezione_progetti_a['TotSpese']]

print(selezione_progetti_a.corr()['FinProvincia'])
print(selezione_progetti_a['FinProvincia'].corr(selezione_progetti_a['TotSpese']))
#plt.scatter(selezione_progetti_a['FinProvincia'],selezione_progetti_a['TotSpese'])


plt.show()
