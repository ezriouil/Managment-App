package www.ezriouil.companymanagment.models

class Order {

    var id:String?=null
    var item:Int=0
    var totalItems:Double= 0.00
    var time:String?= null

    constructor()

    constructor(id:String,item:Int,totalItems:Double,time:String){
        this.id = id
        this.item =  item
        this.totalItems = totalItems
        this.time = time
    }

}