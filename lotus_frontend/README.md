# lotus-clinic

## Potrebno je imati instaliran Node Package Manager
```
https://nodejs.org/en/download/package-manager/
```
## Instaliranje svih zavisnosti
```
npm install
```

### Kompajliranje i pokretanje na localhost:8081
```
npm run serve
```
Za lokalno testiranje potrebno je u datoteci main.js iskomentarisati liniju
```
Vue.axios.defaults.baseURL = "https://lotus-clinic-api.herokuapp.com";
```
i odkomentarisati liniju
```
Vue.axios.defaults.baseURL = "http://localhost:9001";
```
Ovako će frontend komunicirati sa lokalno hostovanim bekendom.
