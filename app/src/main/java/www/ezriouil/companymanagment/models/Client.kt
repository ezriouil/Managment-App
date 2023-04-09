package www.ezriouil.companymanagment.models

class Client {
    var id:String?=null
    var name:String?=null
    var price:Double?=0.00
    var total:Double?=0.00
    var phone:String?=null
    constructor()
    constructor(id:String,name:String,price:Double,total:Double,phone:String){
        this.id = id
        this.name =  name
        this.price = price
        this.total = total
        this.phone = phone
    }
}