## Функционал помимо обязательного
Если книга с полученым названием существует, заменит её данные на переданные, если такой книги нет, то добавит её, как новый элемент:
```
edit books "<title>" <count> <cost> <sold count>
```
**Пример:**
```
edit books "BookTitle" 23 100 0
```
___
Устанавливает значения баланса на переданное:
```
set balance <amount>
```
**Пример:**
```
set balance 10000
```
## Примечание
Синтаксис команд не гибкий и должен полностью соблюдаться:
1. Должны передаваться все указанные переменные
2. Не должно быть лишних пробелов и ковычек
