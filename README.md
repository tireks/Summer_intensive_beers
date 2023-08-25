# Project Title

Pet-project. Simple "Frontend" for [Punk API](https://punkapi.com/documentation/v2)


## Contents

 - [Implemented Features](#Features)
 - [Used stack](#Used-stack)


## Features

- [Start screen](#Start-screen)
- [SearchForm screen](#SearchForm-screen)
- [Search results screen](#Search-results-screen)
- [Details screen](#Details-screen)


### Start screen
Basic RecyclerView finite screen with toolbar for navigation menu.

![ezgif com-video-to-gif](https://github.com/tireks/Summer_intensive_beers/assets/33616425/d5749f49-376a-4a82-bcb6-50f5ecbab032) 
![1](https://github.com/tireks/Summer_intensive_beers/assets/33616425/4a85345a-4ff4-487a-8841-8cac35711155)

Tapping on each RecyclerView's item redirects to item's description on [Details screen](#Details-screen).


Tapping on `Random pick` menu option also redirects to description on [Details screen](#Details-screen) but using [Punk API's](https://punkapi.com/documentation/v2) random request instead of item's id.


Tapping on `Search` menu option redirects to [SearchForm screen](#SearchForm-screen), where you can set search parameters

![6](https://github.com/tireks/Summer_intensive_beers/assets/33616425/4956669f-70af-486b-8b9d-e15ae898512d)
![5](https://github.com/tireks/Summer_intensive_beers/assets/33616425/d5bd2d93-1bf8-43ac-bbcc-453268da82e0)
![3](https://github.com/tireks/Summer_intensive_beers/assets/33616425/772da2d2-d35d-4ed1-b122-e432b5dc87f6)


Implemented self-made pagination (without Pager, Paging etc.) with help of [Punk API's](https://punkapi.com/documentation/v2) internal implementation of pagination

![7](https://github.com/tireks/Summer_intensive_beers/assets/33616425/119f9a2c-d360-4453-ab90-d62754d9919a)


### SearchForm screen
Scrollable search form with all of request parameters, available via [Punk API](https://punkapi.com/documentation/v2). 

!!!!плейсхолдер для гифок общей и функциональной

For date choosing there is custom date picker implemented (custom picker, because api only supports dates in mm-yyyy format, user don't need to choose day, only month and year)

!!!!плейсхолдер для гифки дэйтпикера

Since there are double inequalities among the parameters (dates before and after, numerical parameters more or less), the current data check for correctness is implemented. Validation is triggered with every data change in certain fields, and if an error is caught, error processing is immediately started

!!!!плейсхолдер гифки с ошибкой

### Search results screen
sdsdsds
dsdsd
sdsd

### Details screen
sdsdsdsd
sdsds
dsdsdsd

## Used stack
ыфффыы
