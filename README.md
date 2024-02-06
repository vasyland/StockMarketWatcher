## StockMarketWatcher
```
Getting current prices from the yahoo finance on a schedule basis
https://finance.yahoo.com/quote/TD.TO/history?p=TD.TO
```

## Database
```
MySql 8.0.28 (MySql Community Server - GPL)

mysql> describe symbol_status;
+--------------------------+---------------+------+-----+---------+-------+
| Field                    | Type          | Null | Key | Default | Extra |
+--------------------------+---------------+------+-----+---------+-------+
| symbol                   | varchar(10)   | NO   | PRI | NULL    |       |
| current_price            | decimal(10,4) | YES  |     | NULL    |       |
| quoterly_dividend_amount | decimal(10,4) | YES  |     | NULL    |       |
| current_yield            | decimal(6,4)  | YES  |     | NULL    |       |
| upper_yield              | decimal(6,4)  | YES  |     | NULL    |       |
| lower_yield              | decimal(6,4)  | YES  |     | NULL    |       |
| allowed_buy_yield        | decimal(6,4)  | YES  |     | NULL    |       |
| sell_point_yield         | decimal(6,4)  | YES  |     | NULL    |       |
| allowed_buy_price        | decimal(10,2) | YES  |     | NULL    |       |
| best_buy_price           | decimal(10,2) | YES  |     | NULL    |       |
| recommended_action       | varchar(15)   | YES  |     | NULL    |       |
| updated_on               | datetime(6)   | YES  |     | NULL    |       |
+--------------------------+---------------+------+-----+---------+-------+
12 rows in set (0.00 sec)

mysql> describe watch_symbol;
+--------------------------+---------------+------+-----+---------+-------+
| Field                    | Type          | Null | Key | Default | Extra |
+--------------------------+---------------+------+-----+---------+-------+
| symbol                   | varchar(10)   | NO   | PRI | NULL    |       |
| quoterly_dividend_amount | decimal(10,4) | YES  |     | NULL    |       |
| upper_yield              | decimal(6,4)  | YES  |     | NULL    |       |
| lower_yield              | decimal(6,4)  | YES  |     | NULL    |       |
| updated_on               | datetime(6)   | YES  |     | NULL    |       |
+--------------------------+---------------+------+-----+---------+-------+
5 rows in set (0.01 sec)
```
## Install Kotlin pluging for eclipse
https://in-kotlin.com/ide/eclipse/

## Install Gradle
```
Gradle 8.4
```
## Build a deployment package from Eclipse
```
1. run Gradle Tasks -> StackMarketWatcher -> BootJar
2. Take a ready to go package here: C:\tmp\StockMarketWatcher\build\libs\StockMarketWatcher-0.0.1.jar
```
## Current Version
```
version = "2.0.0"
```

## Usefull Links
```
# http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html
```
