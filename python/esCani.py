import matplotlib.pyplot as plt 
import pandas as pd 
import numpy as np 

df = pd.read_csv('cani.csv', sep=';', decimal=',')
#print(df['MORTE'])
#print(df.info())
#1
print("Numero cani: {}".format(len(df)))

#2
print("Numero di cani con ipertensione: {}".format(len(df[df['IP']=='SI'])))

#3.1
eta = df['EtaAnni']
print("max: {}, min: {}".format(eta.max(), eta.min()))
cestini = np.arange(1, 18)
# df['EtaAnni'].plot.hist(cestini)
plt.show()
#3.2
print("Media eta': {}".format(df['EtaAnni'].mean()))
print("Mediana eta': {}".format(df['EtaAnni'].median()))
print("Moda eta': {}".format(df['EtaAnni'].mode()))
print("Varianza eta: {}".format(df['EtaAnni'].var()))
#3.3
print("Cani con eta>=12 e eta<13: {}".format(len(df[(df['EtaAnni']>=12)&(df['EtaAnni']<13)])))
#3.4
print("Anni cane piu' anziano: {}".format(df['EtaAnni'].max()))



#4.1
print(len(df[df['MORTE']==1]))
#4.2
print(len(df[df['MC'].isna()]))
#4.3
print(len( (df[(df['MORTE']==0) & (df['MC']==1)])))
#4.4
print(len(df[df['MC']==1]))
#4.5
print("cani morti: {}".format(len(df[df['MORTE']==1])))

#5.2
print("GravitaIP assume valori tra {} e {}".format(df['GravitaIP'].min(),df['GravitaIP'].max()))
#5.3
print(df['GravitaIP'].value_counts()/len(df['GravitaIP']))
#5.4
c = np.arange(0, 6)
# df['GravitaIP'].plot.hist(bins=c)
plt.show()

#6.1
print("Fr.assolute Antiaritmico:\n{}".format(df['Antiaritmico'].value_counts()))
print("\n")
#6.3
print(pd.crosstab(index=df['Antiaritmico'], columns=df['MC']))  # TABELLA FREQUENZE ASSOLTE CONGIUNTE con due argomenti
print("\n")
#6.4
MC_Arit= pd.crosstab(index=df['Antiaritmico'].dropna(), columns=df['MC'].dropna(),normalize='all')
print(MC_Arit)  # TABELLA FREQUENZE RELATIVE CONGIUNTE con due argomenti
print("\n")
#6.5
print("Percentuale di MC che assumeva un farmaco antiaritmico: {}%".format(MC_Arit[1][1]))

#7.3
bins=[i*365 for i in range(10)]
#df['SURVIVALTIME'].plot.hist(bins=bins)
#plt.xlabel('giorni')
plt.show()
#7.4
quantile25= df['SURVIVALTIME'].quantile(.25)
quantile75= df['SURVIVALTIME'].quantile(.75)
print("Primo quantile: {}".format(quantile25))
print("Terzo quantile: {}".format(quantile75))
#7.5
caniBetween= df[(df['SURVIVALTIME']>=quantile25)&(df['SURVIVALTIME']<=quantile75)]
print("animali compresi all’interno della scatola (estremi inclusi): {}".format(len(caniBetween)))
#7.6
#caniBetween['SURVIVALTIME'].hist()
plt.show()
#7.8
print("Tempo di soppravvivenza medio: {}".format(df['SURVIVALTIME'].mean()))
print("Deviazione standard del tempo di soppravvivenza: {}".format(df['SURVIVALTIME'].std()))

#8
media=df['Allodiast'].mean()
std = df['Allodiast'].std()
print("Media {}, Dev.standard {}".format(media,std))

print("Percentuale intervallo di semi ampiezza 2 deviazioni standard e centrato sulla media: {}%".format(len(df['Allodiast'].sort_values()[(df['Allodiast']<=media+2*std)&(df['Allodiast']>=media-2*std)])/len(df['Allodiast'])*100))

estrai = df['Cartella'].value_counts()  # estrai = serie con indici il num.cartella e valore le frequenze
estrai = estrai[estrai.get_values()>1]

[print(x) for x in df['Cartella'] for y in estrai.index if x==y ] # doppio for per trovare i duplicati nel dataframe

df=df.drop_duplicates(subset=['Cartella'],keep='first') # come eliminare i duplicati secondo cartelle
print(len(df))

#9
print(df['Allodiast'].corr(df['EDVI']))
















