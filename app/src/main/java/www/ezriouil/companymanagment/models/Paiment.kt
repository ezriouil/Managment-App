package www.ezriouil.companymanagment.models

class Paiment {

    var id:String?=null
    var montant:Double= 0.00
    var time:String?= null

    constructor()

    constructor(id:String, montant:Double, time:String){
        this.id = id
        this.montant =  montant
        this.time = time
    }

}