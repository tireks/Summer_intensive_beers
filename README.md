# BeerPunk App

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
<details>
 <summary>animated demonstration</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/d5749f49-376a-4a82-bcb6-50f5ecbab032">
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/4a85345a-4ff4-487a-8841-8cac35711155">
</details>

Tapping on each RecyclerView's item redirects to item's description on [Details screen](#Details-screen).
<details>
 <summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/4956669f-70af-486b-8b9d-e15ae898512d">
</details>

Tapping on `Random pick` menu option also redirects to description on [Details screen](#Details-screen) but using [Punk API's](https://punkapi.com/documentation/v2) random request instead of item's id.
<details>
 <summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/d5bd2d93-1bf8-43ac-bbcc-453268da82e0">
</details>

Tapping on `Search` menu option redirects to [SearchForm screen](#SearchForm-screen), where you can set search parameters
<details>
 <summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/772da2d2-d35d-4ed1-b122-e432b5dc87f6">
</details>

Implemented self-made pagination (without Pager, Paging etc.) with help of [Punk API's](https://punkapi.com/documentation/v2) internal implementation of pagination
Tapping on `Search` menu option redirects to [SearchForm screen](#SearchForm-screen), where you can set search parameters
<details>
 <summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/119f9a2c-d360-4453-ab90-d62754d9919a">
</details>


### SearchForm screen
Scrollable search form with all of request parameters, available via [Punk API](https://punkapi.com/documentation/v2). Also implemented return to the previous screen
<details>
 <summary>animated demonstration</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/29b8612f-13b1-4b1e-9e7c-edac90b6ebd7">
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/5e9dc0a6-aa15-442c-8473-ddddd8eaaf30">
</details>

For date choosing there is custom date picker implemented (custom picker, because api only supports dates in mm-yyyy format, user don't need to choose day, only month and year)
<details>
 <summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/401a3564-3533-476b-aeaa-29b84e3aa501">
</details>

Since there are double inequalities among the parameters (dates before and after, numerical parameters more or less), the current data check for correctness is implemented. Validation is triggered with every data change in certain fields, and if an error is caught, error processing is immediately started
<details>
 <summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/69ade412-a36d-4bb5-b50d-af1aabf8e1eb">
</details>


### Search results screen
Endless scrollable RecyclerView, loading new data only when bottom of the list is reached. Also implemented return to the previous screen
<details>
 <summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/b38800da-7e54-4cf5-b23f-4d86b554a572">
</details>

Loading progress is displayed with progressBar, it spins until new data is loaded. ProgressBar spawns only when user scrolled to bottom, user sees the moment, whem it spawns. After loading, last empty element with progressbar replaces with the next loaded list element. User doesnt need to scroll, to see first of new elements.
<details>
 <summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/542596f1-38de-44e3-ac01-cffdfa4320bc">
</details>

Tapping on each RecyclerView's item redirects to item's description on [Details screen](#Details-screen). (Works with any item in RecyclerView, even with new loaded items)
<details>
<summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/7b442e43-a8da-4b94-9adf-25ce70aae551">
</details>

If there are no more search results, on the first such response from the server, a loading animation will be shown, after which the attention snackbar will be shown. If then user will try to trigger the loading of new data again, nothing will happen.
<details>
<summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/1beac2fc-ebbd-47b1-98a5-a0a6a3178534">
</details>

### Details screen
Scrollable info screen with expandable collapsing toolbar, which contains picture of item and it's name. Also implemented return to the previous screen.
<details>
<summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/9a906718-c039-4181-bced-30330d9b5ba3">
</details>

Because categories malt and hops have no limit on the number, there can be any number of elements. And all of them will be displayed on the screen
<details>
<summary>gif</summary>
 <img src="https://github.com/tireks/Summer_intensive_beers/assets/33616425/b72e367a-c62f-43f8-85e0-7b0418a7fbad">
</details>

## Used stack
- MVVM
- Jetpack Navigation
- Retrofit2
- OkHttp
- Coroutines
- XML design
- Material Design 3
- Glide lib for every pictures

