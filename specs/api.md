# Wildcart API
## Entity carrito
### Carrito get (USER)
* Entity: carrito
* Operation: get
* URL: http://127.0.0.1:8082/producto/3
* Method: GET
* Arguments: id
* Payload: none
* Profile: user
* Description: Returns one producto row
* Process: none
* JSON Return example:
```json
{
  "id": 3,
  "codigo": "97691N",
  "nombre": "Armario de cartón de color azul de tamaño mediano",
  "existencias": 12,
  "precio": 98.36,
  "imagen": 4,
  "descuento": 39,
  "tipoproducto": {
    "id": 73,
    "nombre": "Productos retro para el hogar",
    "productos": 8
  },
  "carritos": 0,
  "compras": 0
}
```



