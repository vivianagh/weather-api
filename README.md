Recursos:

## 🔨 Funcionalidades del proyecto

- `Funcionalidad 1`: Dada una ciudad obtener el código de la ciudad- 

***GET "/api/city?text=sanpablo"***

Response:
```json 
        {
          "Key": "263780",
          "LocalizedName": "San Pablo City"
        }
```

Consideré la primera ciudad de la lista de ciudades que me devolvía la api de AccuWeather

- `Funcionalidad 2`: Dada una ciudad, obtener el clima del dia actual

***GET "api/forecast/city/today?city=sanpablo"***

Response:
```json
        {
            "Date": "2023-03-17T07:00:00+08:00",
            "Temperature": {
                "Minimum": {
                    "Value": 73,
                    "Unit": "F",
                    "UnitType": 18
                },
                "Maximum": {
                    "Value": 89,
                    "Unit": "F",
                    "UnitType": 18
                }
            }
        }
```
Los datos que se guardan en la base de datos son los siguientes



```sql
CREATE TABLE `forecast` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `city` varchar(255) NOT NULL,
    `code` varchar(255) NOT NULL,
    `date` datetime NOT NULL,
    `temperature_maximum` int NOT NULL,
    `temperature_minimum` int NOT NULL,
    
    PRIMARY KEY (`id`)
);
```


Viviana Herrera

viviana.gh@gmail.com

