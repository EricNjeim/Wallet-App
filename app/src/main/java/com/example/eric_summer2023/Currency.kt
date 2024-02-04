package com.example.eric_summer2023

class Currency(_name:String, _symbol:String, _price:Double) {
    private  var name: String
    private  var symbol: String
    private var price:Double
    init {
    this.name = _name
    this.symbol = _symbol
    this.price = _price
}
    fun getName():String {
    return name }
    fun getSymbol():String {
    return symbol }
     fun getPrice():Double {
        return price }
}
